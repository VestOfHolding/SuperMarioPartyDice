package simulation;

import boards.BaseBoard;
import boards.WhompsDominoRuins;
import boards.spaces.BaseSpace;
import partydice.Dice;
import results.CoinResult;
import results.DieResult;
import results.MoveResult;
import stattracker.AllyStatTracker;
import stattracker.GameStatTracker;
import stattracker.SimulationStatTracker;
import utils.RandomUtils;

import java.text.DecimalFormat;

public class Simulation {
    protected final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("####.#######");

    protected static final int TURN_COUNT = 20;
    protected static final int SIM_COUNT = 6000000;

    protected BaseBoard gameBoard;

    public Simulation() {
        this.gameBoard = new WhompsDominoRuins();
    }

    public Simulation(BaseBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public void simulate() {
        SimulationStatTracker simulationStatTracker;

        printTableHeaders();

        for (Dice characterDie : Dice.values()) {
            simulationStatTracker = new SimulationStatTracker(characterDie);

            for (int i = 0; i < SIM_COUNT; ++i) {
                simulationStatTracker.endGame(simulateGame(characterDie, simulationStatTracker));
                gameBoard.resetBoard();
            }

            printSimulationResult(characterDie, simulationStatTracker, gameBoard.getTotalBoardSize());
        }
    }

    protected void printTableHeaders() {
        System.out.print("Character\tAllyCount\tFrequency\t" +
                "Min Turn Gained\t1st Quartile Turn Gained\tAvg Turn Gained\t3rd Quartile Turn Gained\tMax Turn Gained\tTurn Gained SD\t" +
                "Min Distance\t1st Quartile Distance\tDistance Avg\t3rd Quartile Distance\tMax Distance\tDistance SD\t" +
                "Min Coins\t1st Quartile Coins\tCoin Avg\t3rd Quartile Coins\tMax Coins\tCoin SD\t" +
                "Min Stars\t1st Quartile Stars\tStar Avg\t3rd Quartile Stars\tMax Stars\tStars SD\t");
//        for (int i = 0; i < gameBoard.getTotalBoardSize(); ++i) {
//            System.out.print("Space" + i + "\t");
//        }
        System.out.println();
    }

    protected GameStatTracker simulateGame(Dice characterDie, SimulationStatTracker simulationStatTracker) {
        GameStatTracker gameStatTracker = simulationStatTracker.startNewGame(TURN_COUNT);

        BaseSpace currentSpace = gameBoard.getStartSpace();

        gameStatTracker.setCoinTotal(5);
        DieResult result;

        for (int j = 0; j < TURN_COUNT; ++j) {
            result = characterDie.roll();

            int moveAmount = 0;

            if (j == TURN_COUNT - 3) {
                lastThreeTurns(gameBoard);
            }

            if (result instanceof MoveResult) {
                moveAmount = result.getResult();
            }
            else if (result instanceof CoinResult) {
                gameStatTracker.addCoins(result.getResult());
            }

            if (gameStatTracker.getAllyTotal() > 0) {
                moveAmount += rollAllies(gameStatTracker);
            }

            gameStatTracker.addDistance(moveAmount);

            if (moveAmount > 0) {
                currentSpace = gameBoard.getDestination(currentSpace, moveAmount, gameStatTracker);
            }

//            gameStatTracker.addLandedSpace(currentSpace);

            gameStatTracker.addCoins(currentSpace.coinGain());
            gameStatTracker.incrementTurn();
        }

        return gameStatTracker;
    }

    protected void lastThreeTurns(BaseBoard gameBoard) {
        gameBoard.lastThreeTurns();
    }

    protected void printSimulationResult(Dice characterDie,
                                         SimulationStatTracker simulationStatTracker,
                                         int possibleSpaces) {
        for (AllyStatTracker allyStatTracker : simulationStatTracker.getAllyStatTrackers().values()) {
            System.out.println(characterDie.getName() + "\t" + allyStatTracker.toStatString(SIM_COUNT, possibleSpaces));
        }
    }

    protected int rollAllies(GameStatTracker gameStatTracker) {
        int numAllies = Math.min(gameStatTracker.getAllyTotal(), 4);
        int result = 0;

        //Each ally rolls either 1 or 2.
        for (int i = 0; i < numAllies; ++i) {
            result += RandomUtils.getRandomInt(1, 2);
        }

        return result;
    }
}
