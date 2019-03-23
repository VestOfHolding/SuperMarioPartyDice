package simulation;

import boards.BaseBoard;
import boards.spaces.BaseSpace;
import partydice.Dice;
import results.CoinResult;
import results.DieResult;
import results.MoveResult;
import stattracker.AllyStatTracker;
import stattracker.GameStatTracker;
import stattracker.SimulationStatTracker;
import utils.OnlineStatistics;
import utils.RandomUtils;

import java.text.DecimalFormat;

public class

Simulation {
    protected final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("####.#######");

    protected static final int TURN_COUNT = 20;
    protected static final int SIM_COUNT = 6000000;

    public void simulate() throws Exception {
        System.out.println("Character\tDistance Avg\tDistance SD\tCoin Avg\tCoin SD");

        OnlineStatistics coins;
        OnlineStatistics distance;
        GameStatTracker gameStatTracker;

        for (Dice characterDie : Dice.values()) {
            coins = new OnlineStatistics();
            distance = new OnlineStatistics();

            for (int i = 0; i < SIM_COUNT; ++i) {
                gameStatTracker = new GameStatTracker();

                for (int j = 0; j < TURN_COUNT; ++j) {


                    gameStatTracker.addDiceResult(characterDie.roll());
                }

                coins.addValue(gameStatTracker.getCoinTotal());
                distance.addValue(gameStatTracker.getDistanceTotal());
            }

            System.out.println(String.join("\t",
                    characterDie.getName(),
                    DECIMAL_FORMAT.format(distance.getMean()),
                    DECIMAL_FORMAT.format(distance.getStandardDeviation()),
                    DECIMAL_FORMAT.format(coins.getMean()),
                    DECIMAL_FORMAT.format(coins.getStandardDeviation())));
        }
    }

    protected void printTableHeaders(BaseBoard gameBoard) {
        System.out.print("Character\tAllyCount\tFrequency\t" +
                "Min Turn Gained\t1st Quartile Turn Gained\tAvg Turn Gained\t3rd Quartile Turn Gained\tMax Turn Gained\tTurn Gained SD\t" +
                "Min Distance\t1st Quartile Distance\tDistance Avg\t3rd Quartile Distance\tMax Distance\tDistance SD\t" +
                "Min Coins\t1st Quartile Coins\tCoin Avg\t3rd Quartile Coins\tMax Coins\tCoin SD\t");
//        for (int i = 0; i < gameBoard.getTotalBoardSize(); ++i) {
//            System.out.print("Space" + i + "\t");
//        }
        System.out.println();
    }

    protected GameStatTracker simulateGame(Dice characterDie, BaseBoard gameBoard, SimulationStatTracker simulationStatTracker) {
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

            if (currentSpace.addAlly()) {
                gameStatTracker.addAlly(j + 1);
            }
            gameStatTracker.decreamentTurn();
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
