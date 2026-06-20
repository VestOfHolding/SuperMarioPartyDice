package org.voh.smp.simulation;

import org.voh.smp.boards.BaseBoard;
import org.voh.smp.boards.KingBobombsPowderkegMine;
import org.voh.smp.boards.spaces.BaseSpace;
import org.voh.smp.mg.MinigameManager;
import org.apache.commons.collections4.CollectionUtils;
import org.voh.smp.stattracker.SimulationStatTracker;
import org.voh.smp.partydice.BobombAlly;
import org.voh.smp.partydice.Dice;
import org.voh.smp.stattracker.AllyStatTracker;
import org.voh.smp.stattracker.GameStatTracker;
import org.voh.smp.utils.RandomUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CharacterSimulation implements Runnable {

    protected static final int TURN_COUNT = 20;
    protected int SIM_COUNT = 10;

    protected final BaseBoard gameBoard;

    protected SimulationStatTracker simulationStatTracker;

    protected final String fileOutputName;

    protected final MinigameManager minigameManager;

    protected final Dice characterDie;

    // Reusable 4-element scratch so place calculation allocates nothing per call.
    private final Player[] placeScratch = new Player[4];

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
        GameStatTracker gameStatTracker = currentPlayer.getGameStatTracker();

        Dice die = currentPlayer.getCharacterDice();
        int face = die.rollFace();

        int coin = die.coin(face);
        if (0 != coin) {
            currentPlayer.addCoins(coin);
        }

        int allyCountAtRoll = gameStatTracker.getAllyTotal();

        int moveAmount = die.move(face);
        if (0 < allyCountAtRoll) {
            // Confirmed rule: a 0-move face still moves you via the ally bonus -- handled naturally
            // because moveAmount started at die.move(face) (possibly 0) and we add the bonus here.
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
        for (int i = 0; i < 4; i++) {
            placeScratch[i] = players.get(i);
        }

        //If a player is tied with the previous player, then that player
        // is considered in the same placing as that previous player.
        // For example, if three players share the same number of stars and coins,
        // with the fourth player lagging behind, then the placement spread
        // would be: 1st, 1st, 1st, 4th.
        for (int i = 1; i < 4; i++) {
            Player key = placeScratch[i];
            int j = i - 1;
            while (j >= 0 && placeScratch[j].compareTo(key) > 0) {
                placeScratch[j + 1] = placeScratch[j];
                j--;
            }
            placeScratch[j + 1] = key;
        }

        for (int i = 0; i < 4; i++) {
            if (i > 0 && 0 == placeScratch[i - 1].compareTo(placeScratch[i])) {
                placeScratch[i].setCurrentPlace(placeScratch[i - 1].getCurrentPlace());
            }
            else {
                placeScratch[i].setCurrentPlace(i + 1);
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
