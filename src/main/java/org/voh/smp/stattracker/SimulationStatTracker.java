package org.voh.smp.stattracker;

import lombok.Getter;
import org.voh.smp.partydice.Dice;
import org.voh.smp.simulation.Player;
import org.voh.smp.simulation.PlayerGroup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
public class SimulationStatTracker {
    private final Player mainPlayer;

    private List<Player> allPlayers;

    private final Map<Integer, AllyStatTracker> allyStatTrackers;

    public SimulationStatTracker(Dice characterDie) {
        mainPlayer = new Player(characterDie);

        allPlayers = new ArrayList<>(4);

        allyStatTrackers = Map.of(0, new AllyStatTracker(0),
                1, new AllyStatTracker(1),
                2, new AllyStatTracker(2),
                3, new AllyStatTracker(3),
                4, new AllyStatTracker(4));
    }

    public PlayerGroup startNewGame(int turnCount) {
        mainPlayer.setGameStatTracker(new GameStatTracker(turnCount));

        //Easier to keep adding players to this set until there are 4 unique players.
        Set<Player> allPlayerSet = new HashSet<>(4);
        allPlayerSet.add(mainPlayer);

        //Across a series of simulations, only the main character we're testing will remain the same.
        while (4 > allPlayerSet.size()) {
            allPlayerSet.add(new Player(Dice.getRandomCharacterDie(), new GameStatTracker(turnCount)));
        }

        //Since order is not preserved in sets, this also kind of accidentally simulates a random
        // turn order for the characters that will now be consistent for the game.
        allPlayers = new ArrayList<>(allPlayerSet);

        return new PlayerGroup(allPlayers);
    }

    public void endGame() {
        GameStatTracker gameStatTracker = mainPlayer.getGameStatTracker();
        int allyCount = gameStatTracker.getAllyTotal();

        AllyStatTracker allyStatTracker = allyStatTrackers.get(allyCount);
        allyStatTracker.addCoinCount(gameStatTracker.getCoinTotal());
        allyStatTracker.addDistance(gameStatTracker.getDistanceTotal());
        allyStatTracker.addStarCount(gameStatTracker.getStarCount());
        allyStatTracker.addPlacement(mainPlayer.getCurrentPlace());
//        allyStatTracker.addLandedSpaceCount(gameStatTracker.getLandedSpacesAmounts());

        //Keep track of what turns all allies were gained, not just the last one.
        for (int allies : gameStatTracker.getAllyGainOnTurn().keySet()) {
            allyStatTrackers.get(allies).addTurnAdded(gameStatTracker.getAllyGainOnTurn().get(allies));
        }
    }
}
