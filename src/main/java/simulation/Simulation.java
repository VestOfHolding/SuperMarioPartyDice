package simulation;

import boards.BaseBoard;
import boards.spaces.AllySpace;
import boards.spaces.BaseSpace;
import boards.spaces.BlueSpace;
import boards.spaces.RedSpace;
import partydice.Dice;
import results.CoinResult;
import results.DieResult;
import results.MoveResult;
import stattracker.AllyStatTracker;
import stattracker.GameStatTracker;
import stattracker.SimulationStatTracker;
import utils.OnlineStatistics;

import java.text.DecimalFormat;
import java.util.Random;

public class Simulation {
    protected static final Random RANDOM = new Random();

    protected final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("####.#######");

    protected static final int TURN_COUNT = 20;
    protected static final int SIM_COUNT = 10000000;

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
        System.out.print("Character\tAllyCount\tFrequency\tAvg Turn Gained\tTurn Gained SD\tDistance Avg\tDistance SD\tCoin Avg\tCoin SD\t");
        for (int i = 0; i < gameBoard.getTotalBoardSize(); ++i) {
            System.out.print("Space" + i + "\t");
        }
        System.out.println();
    }

    protected GameStatTracker simulateGame(Dice characterDie, BaseBoard gameBoard, SimulationStatTracker simulationStatTracker) {
        GameStatTracker gameStatTracker = simulationStatTracker.startNewGame();

        BaseSpace currentSpace = gameBoard.getStartSpace();

        gameStatTracker.setCoinTotal(5);
        DieResult result;

        int coinSpaceMultiply = 1;

        for (int j = 0; j < TURN_COUNT; ++j) {
            result = characterDie.roll();

            int moveAmount = 0;

            if (j > TURN_COUNT - 3) {
                coinSpaceMultiply = 2;
            }

            if (result instanceof MoveResult) {
                moveAmount = result.getResult();
            }
            else if (result instanceof CoinResult) {
                gameStatTracker.addCoins(result.getResult());
            }

            if (gameStatTracker.getAllyTotal() > 0) {
                moveAmount += rollAllies(gameStatTracker.getAllyTotal());
            }

            gameStatTracker.addDistance(moveAmount);

            if (moveAmount > 0) {
                currentSpace = gameBoard.getDestination(currentSpace, moveAmount, gameStatTracker);
            }

            gameStatTracker.addLandedSpace(currentSpace);

            if (currentSpace instanceof BlueSpace || currentSpace instanceof RedSpace) {
                gameStatTracker.addCoins(currentSpace.coinGain() * coinSpaceMultiply);
            }
            else if (currentSpace instanceof AllySpace) {
                gameStatTracker.addAlly(j + 1);
            }
        }

        return gameStatTracker;
    }

    protected void printSimulationResult(Dice characterDie,
                                         SimulationStatTracker simulationStatTracker,
                                         int possibleSpaces) {
        for (AllyStatTracker allyStatTracker : simulationStatTracker.getAllyStatTrackers().values()) {
            System.out.println(characterDie.getName() + "\t" + allyStatTracker.toStatString(SIM_COUNT, possibleSpaces));
        }
    }

    protected int rollAllies(int numAllies) {
        numAllies = Math.min(numAllies, 4);
        int result = 0;

        //Each ally rolls either 1 or 2.
        for (int i = 0; i < numAllies; ++i) {
            result += RANDOM.nextInt(2) + 1;
        }

        return result;
    }
}
