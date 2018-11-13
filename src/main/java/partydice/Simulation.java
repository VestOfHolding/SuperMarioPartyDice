package partydice;

import boards.BaseBoard;
import boards.WhompsDominoRuins;
import boards.spaces.AllySpace;
import boards.spaces.BaseSpace;
import boards.spaces.BlueSpace;
import boards.spaces.RedSpace;
import results.CoinResult;
import results.DieResult;
import results.MoveResult;
import stattracker.AllyStatTracker;
import stattracker.GameStatTracker;
import stattracker.SimulationStatTracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulation {
    private static final int TURN_COUNT = 20;

    private static final int SIM_COUNT = 500000;

    public void simulateBasic() {
        for (Dice characterDie : Dice.values()) {
            double averageCoins;
            double averageDistance;

            List<Double> coinsPerGame = new ArrayList<>();
            List<Double> distancesPerGame = new ArrayList<>();

            for (int i = 0; i < SIM_COUNT; ++i) {
                double totalCoins = 0;
                double totalDistance = 0;

                for (int j = 0; j < TURN_COUNT; ++j) {
                    DieResult result = characterDie.roll();

                    if (result instanceof MoveResult) {
                        totalDistance += result.getResult();
                    }
                    else if (result instanceof CoinResult) {
                        totalCoins += result.getResult();
                    }
                }

                coinsPerGame.add(totalCoins);
                distancesPerGame.add(totalDistance);
            }

            averageCoins = coinsPerGame.stream().mapToDouble(a -> a).average().orElse(0.0);
            averageDistance = distancesPerGame.stream().mapToDouble(a -> a).average().orElse(0.0);

            System.out.println(String.join(",",
                    characterDie.getName(),
                    String.valueOf(averageCoins),
                    String.valueOf(Math.round(averageCoins)),
                    String.valueOf(averageDistance),
                    String.valueOf(Math.round(averageDistance))));
        }
    }

    public void simulateWhomps() {
        WhompsDominoRuins whompsRuinsBoard = new WhompsDominoRuins();
        SimulationStatTracker simulationStatTracker;

        printTableHeaders(whompsRuinsBoard);

        for (Dice characterDie : Dice.values()) {
            simulationStatTracker = new SimulationStatTracker(characterDie);

            for (int i = 0; i < SIM_COUNT; ++i) {
                GameStatTracker gameStatTracker = simulateGame(characterDie, whompsRuinsBoard, simulationStatTracker);
                simulationStatTracker.endGame(gameStatTracker);
            }

            printSimulationResult(characterDie, simulationStatTracker, whompsRuinsBoard.getTotalBoardSize());
        }
    }

    protected void printTableHeaders(BaseBoard gameBoard) {
        System.out.print("Character\tAllyCount\tFrequency\tAvg Turn Gained\tDistance Avg\tCoin Avg\t");
        for (int i = 0; i < gameBoard.getTotalBoardSize(); ++i) {
            System.out.print("Space" + i + "\t");
        }
        System.out.println();
    }

    protected GameStatTracker simulateGame(Dice characterDie, BaseBoard gameBoard, SimulationStatTracker simulationStatTracker) {
        GameStatTracker gameStatTracker = simulationStatTracker.startNewGame();

        BaseSpace currentSpace = gameBoard.getStartSpace();

        int coinCount = 5;
        int totalDistance = 0;

        for (int j = 0; j < TURN_COUNT; ++j) {
            DieResult result = characterDie.roll();

            int moveAmount = 0;

            if (result instanceof MoveResult) {
                moveAmount = result.getResult();
            }
            else if (result instanceof CoinResult) {
                coinCount += result.getResult();
            }

            if (gameStatTracker.getAllyTotal() > 0) {
                moveAmount += rollAllies(gameStatTracker.getAllyTotal());
            }

            totalDistance += moveAmount;

            if (moveAmount > 0) {
                currentSpace = gameBoard.getDestination(currentSpace, moveAmount);
            }

            gameStatTracker.addLandedSpace(currentSpace);

            if (currentSpace instanceof BlueSpace || currentSpace instanceof RedSpace) {
                coinCount += currentSpace.coinGain();
            }
            else if (currentSpace instanceof AllySpace) {
                gameStatTracker.addAlly(j + 1);
            }
        }
        gameStatTracker.setCoinTotal(coinCount);
        gameStatTracker.setDistanceTotal(totalDistance);

        return gameStatTracker;
    }

    protected void printSimulationResult(Dice characterDie, SimulationStatTracker simulationStatTracker,
                                         int possibleSpaces) {
        for (AllyStatTracker allyStatTracker : simulationStatTracker.getAllyStatTrackers().values()) {
            System.out.println(characterDie.getName() + "\t" + allyStatTracker.toStatString(SIM_COUNT, possibleSpaces));
        }
    }

    protected int rollAllies(int numAllies) {
        numAllies = Math.min(numAllies, 4);
        int result = 0;
        Random random = new Random();

        //Each ally rolls either 1 or 2.
        for (int i = 0; i < numAllies; ++i) {
            result += random.nextInt(2) + 1;
        }

        return result;
    }
}
