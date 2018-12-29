package simulation;

import boards.BaseBoard;
import boards.spaces.AllySpace;
import boards.spaces.BaseSpace;
import boards.spaces.BlueSpace;
import boards.spaces.RedSpace;
import org.apache.commons.collections4.CollectionUtils;
import partydice.BobombAlly;
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

public class Simulation {
    protected final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("####.#######");

    protected static final int TURN_COUNT = 20;
    protected static final int SIM_COUNT = 2500000;

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
//        for (int i = 0; i < gameBoard.getTotalBoardSize(); ++i) {
//            System.out.print("Space" + i + "\t");
//        }
        System.out.println();
    }

    protected GameStatTracker simulateGame(Dice characterDie, BaseBoard gameBoard, SimulationStatTracker simulationStatTracker) {
        GameStatTracker gameStatTracker = simulationStatTracker.startNewGame();

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

            if (currentSpace instanceof BlueSpace || currentSpace instanceof RedSpace) {
                gameStatTracker.addCoins(currentSpace.coinGain());
            }
            else if (currentSpace.addAlly()) {
                gameStatTracker.addAlly(j + 1);
            }
        }

        return gameStatTracker;
    }

    protected void lastThreeTurns(BaseBoard gameBoard) {
        gameBoard.lastThreeTurns(3);
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

        if (CollectionUtils.isNotEmpty(gameStatTracker.getBobombAllies())) {
            for (BobombAlly bobombAlly : gameStatTracker.getBobombAllies()) {
                result += bobombAlly.rollBobombAlly();
            }

            gameStatTracker.getBobombAllies().removeIf(BobombAlly::explode);
        }

        return result;
    }
}
