package org.voh.smp.simulation;

import org.voh.smp.boards.BaseBoard;
import org.voh.smp.boards.KingBobombsPowderkegMine;
import org.voh.smp.boards.spaces.BaseSpace;
import org.voh.smp.mg.MinigameManager;
import org.apache.commons.collections4.CollectionUtils;
import org.voh.smp.stattracker.SimulationStatTracker;
import org.voh.smp.partydice.BobombAlly;
import org.voh.smp.partydice.Dice;
import org.voh.smp.results.CoinResult;
import org.voh.smp.results.DieResult;
import org.voh.smp.results.MoveResult;
import org.voh.smp.stattracker.AllyStatTracker;
import org.voh.smp.stattracker.GameStatTracker;
import org.voh.smp.utils.RandomUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CharacterSimulation implements Runnable {

    protected static final int TURN_COUNT = 20;
    protected int SIM_COUNT = 10;

    protected final BaseBoard gameBoard;

    protected SimulationStatTracker simulationStatTracker;

    protected final String fileOutputName;

    protected final MinigameManager minigameManager;

    protected final Dice characterDie;

    public CharacterSimulation(BaseBoard gameBoard, Dice characterDie, int simCount) {
        SIM_COUNT = simCount;
        this.gameBoard = gameBoard;
        this.characterDie = characterDie;
        fileOutputName = "output/" + gameBoard.getFileOutputName();
        minigameManager = new MinigameManager();
    }

    @Override
    public void run() {
        simulationStatTracker = new SimulationStatTracker(characterDie);

        for (int i = 0; i < SIM_COUNT; ++i) {
            simulateGame();
            simulationStatTracker.endGame();
            gameBoard.resetBoard();
        }

        printSimulationResult(characterDie, gameBoard.getTotalBoardSize());
    }

    protected void simulateGame() {
        PlayerGroup players = simulationStatTracker.startNewGame(TURN_COUNT);
        gameBoard.setPlayerGroup(players);

        players.allPlayers().forEach(player -> player.setCurrentSpace(gameBoard.getStartSpace()));

        for (int j = 0; TURN_COUNT > j; ++j) {
            if (TURN_COUNT - 3 == j) {
                lastThreeTurns(gameBoard);
            }

            for (Player player : players.allPlayers()) {
                simulateTurn(player, players);
            }
            minigameManager.runMinigame(players);
            calculatePlaces(players.allPlayers());
        }
        handleBonusStars(players);
    }

    protected void handleBonusStars(PlayerGroup players) {
        for (BonusStar bonusStar : BonusStar.randomlyGetBonusStars()) {
            Player bonusStarPlayer = bonusStar.findWinningPlayer(players);
            if (null != bonusStarPlayer) {
                bonusStarPlayer.addStar();
            }
        }
        calculatePlaces(players.allPlayers());
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

        if (0 < gameStatTracker.getAllyTotal()) {
            moveAmount += rollAllies(gameStatTracker);
        }

        gameStatTracker.addDistance(moveAmount);

        BaseSpace currentSpace = currentPlayer.getCurrentSpace();
        if (0 < moveAmount) {
            currentSpace = gameBoard.getDestination(currentPlayer, moveAmount);
        }
        else {
            currentPlayer.setLandedSpaceColor(currentSpace.getSpaceColor());
        }

//        gameStatTracker.addLandedSpace(currentSpace);
        gameStatTracker.incrementTurn();

        currentPlayer.setCurrentSpace(currentSpace);

        calculatePlaces(allPlayers.allPlayers());
    }

    public void calculatePlaces(List<Player> players) {
        List<Player> sortedPlayerList = new ArrayList<>(players);

        Collections.sort(sortedPlayerList);

        //If a player is tied with the previous player, then that player
        // is considered in the same placing as that previous player.
        // For example, if three players share the same number of stars and coins,
        // with the fourth player lagging behind, then the placement spread
        // would be: 1st, 1st, 1st, 4th.
        for (int i = 0; 4 > i; ++i) {
            if (sortedPlayerList.get(i).getCurrentPlace() == i + 1) {
                continue;
            }

            if (0 < i && 0 == sortedPlayerList.get(i - 1).compareTo(sortedPlayerList.get(i))) {
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
