package simulation;

import boards.BaseBoard;
import boards.KingBobombsPowderkegMine;
import boards.WhompsDominoRuins;
import boards.spaces.BaseSpace;
import mg.MinigameManager;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.time.StopWatch;
import partydice.BobombAlly;
import partydice.Dice;
import results.CoinResult;
import results.DieResult;
import results.MoveResult;
import stattracker.AllyStatTracker;
import stattracker.GameStatTracker;
import stattracker.SimulationStatTracker;
import utils.RandomUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Simulation implements Runnable{
    protected final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("####.#######");

    protected static final int TURN_COUNT = 20;
    protected int SIM_COUNT = 10000000;

    protected BaseBoard gameBoard;

    protected SimulationStatTracker simulationStatTracker;

    protected String fileOutputName;

    protected MinigameManager minigameManager;

    public Simulation() {
        this.gameBoard = new WhompsDominoRuins();
    }

    public Simulation(BaseBoard gameBoard, int simCount) {
        SIM_COUNT = simCount;
        this.gameBoard = gameBoard;
        fileOutputName = "output/" + gameBoard.getFileOutputName();
        minigameManager = new MinigameManager();
    }

    @Override
    public void run() {
        simulate();
    }

    public void simulate() {
        StopWatch stopWatch = StopWatch.createStarted();
        System.out.println(gameBoard.getFileOutputName() + " time start!\t\t" + LocalDateTime.now());
        printTableHeaders();

        for (Dice characterDie : Dice.values()) {
            simulationStatTracker = new SimulationStatTracker(characterDie);

            for (int i = 0; i < SIM_COUNT; ++i) {
                simulateGame();
                simulationStatTracker.endGame();
                gameBoard.resetBoard();
            }

            printSimulationResult(characterDie, gameBoard.getTotalBoardSize());
        }

        stopWatch.split();
        System.out.println(gameBoard.getFileOutputName() + " time elapsed: " + stopWatch.toSplitString() + "\t\t" + LocalDateTime.now());
        stopWatch.stop();
    }

    protected void printTableHeaders() {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileOutputName, false))) {

            writer.write("Character\tAllyCount\tFrequency\t" +
                    "Min Turn Gained\t1st Quartile Turn Gained\tAvg Turn Gained\t3rd Quartile Turn Gained\tMax Turn Gained\tTurn Gained SD\t" +
                    "Min Distance\t1st Quartile Distance\tDistance Avg\t3rd Quartile Distance\tMax Distance\tDistance SD\t" +
                    "Min Coins\t1st Quartile Coins\tCoin Avg\t3rd Quartile Coins\tMax Coins\tCoin SD\t" +
                    "Min Stars\t1st Quartile Stars\tStar Avg\t3rd Quartile Stars\tMax Stars\tStars SD\t" +
                    "Average Place");
            writer.newLine();
    //        for (int i = 0; i < gameBoard.getTotalBoardSize(); ++i) {
    //            System.out.print("Space" + i + "\t");
    //        }
        } catch (IOException exception) {
            System.out.println("ERROR: Could not write table headers to file: " + fileOutputName);
        }
        System.out.println();
    }

    protected void simulateGame() {
        PlayerGroup players = simulationStatTracker.startNewGame(TURN_COUNT);
        gameBoard.setPlayerGroup(players);

        players.getAllPlayers().forEach(player -> player.setCurrentSpace(gameBoard.getStartSpace()));

        for (int j = 0; j < TURN_COUNT; ++j) {
            if (j == TURN_COUNT - 3) {
                lastThreeTurns(gameBoard);
            }

            for (Player player : players.getAllPlayers()) {
                simulateTurn(player, players);
            }
            minigameManager.runMinigame(players);
            calculatePlaces(players.getAllPlayers());
        }
    }

    protected void simulateTurn(Player currentPlayer, PlayerGroup allPlayers) {
        DieResult result = currentPlayer.rollCharacterDie();
        GameStatTracker gameStatTracker = currentPlayer.getGameStatTracker();

        int moveAmount = 0;

        if (result instanceof MoveResult) {
            moveAmount = result.getResult();
        }
        else if (result instanceof CoinResult) {
            currentPlayer.addCoins(result.getResult());
        }

        if (gameStatTracker.getAllyTotal() > 0) {
            moveAmount += rollAllies(gameStatTracker);
        }

        gameStatTracker.addDistance(moveAmount);

        BaseSpace currentSpace = currentPlayer.getCurrentSpace();
        if (moveAmount > 0) {
            currentSpace = gameBoard.getDestination(currentPlayer, moveAmount);
        }
        else {
            currentPlayer.setLandedSpaceColor(currentSpace.getSpaceColor());
        }

//        gameStatTracker.addLandedSpace(currentSpace);

        currentPlayer.addCoins(currentSpace.coinGain());
        gameStatTracker.incrementTurn();

        currentPlayer.setCurrentSpace(currentSpace);

        calculatePlaces(allPlayers.getAllPlayers());
    }

    public void calculatePlaces(List<Player> players) {
        List<Player> sortedPlayerList = new ArrayList<>(players);

        Collections.sort(sortedPlayerList);

        //If a player is tied with the previous player, then that player
        // is considered in the same placing as that previous player.
        // For example, if three players share the same number of stars and coins,
        // with the fourth player lagging behind, then the placement spread
        // would be: 1st, 1st, 1st, 4th.
        for (int i = 0; i < 4; ++i) {
            if (sortedPlayerList.get(i).getCurrentPlace() == i + 1) {
                continue;
            }

            if (i > 0 &&
                    sortedPlayerList.get(i -1).compareTo(sortedPlayerList.get(i)) == 0) {
                sortedPlayerList.get(i).setCurrentPlace(sortedPlayerList.get(i - 1).getCurrentPlace());
            }
            else {
                sortedPlayerList.get(i).setCurrentPlace(i + 1);
            }
        }
    }

    protected void lastThreeTurns(BaseBoard gameBoard) {
        gameBoard.lastThreeTurns();
    }

    protected void printSimulationResult(Dice characterDie, int possibleSpaces) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileOutputName, true))) {

            for (AllyStatTracker allyStatTracker : simulationStatTracker.getAllyStatTrackers().values()) {
                writer.write(characterDie.getName() + "\t" + allyStatTracker.toStatString(SIM_COUNT, possibleSpaces));
                writer.newLine();
            }
        } catch (IOException exception) {
            System.out.println("ERROR: Could not write table contents to file: " + fileOutputName);
        }
    }

    protected int rollAllies(GameStatTracker gameStatTracker) {
        int numAllies = Math.min(gameStatTracker.getAllyTotal(), 4);
        int result = 0;

        //Each ally rolls either 1 or 2.
        for (int i = 0; i < numAllies; ++i) {
            result += RandomUtils.getRandomInt(1, 2);
        }

        //Let's short circuit this a bit since we know only King Bo-bomb's map
        // even has Bo-bomb allies.
        if (gameBoard instanceof KingBobombsPowderkegMine) {
            int explodedBobombAllyCount = 0;

            for (BobombAlly bobombAlly : CollectionUtils.emptyIfNull(gameStatTracker.getBobombAllies())) {
                result += bobombAlly.rollBobombAlly();

                if (bobombAlly.explode()) {
                    explodedBobombAllyCount++;
                }
            }

            for (int i = 0; i < explodedBobombAllyCount; ++i) {
                gameStatTracker.removeBobombAlly();
            }
        }

        return result;
    }
}
