package org.voh.smp.stattracker;

import lombok.Getter;
import org.voh.smp.partydice.Dice;
import org.voh.smp.simulation.Player;
import org.voh.smp.simulation.PlayerGroup;
import org.voh.smp.utils.RandomUtils;

import java.util.*;

@Getter
public class SimulationStatTracker {

    private static final Dice[] DICE_VALUES = Dice.values();

    private final Player mainPlayer;
    private final Player[] players = new Player[4];
    private final PlayerGroup playerGroup;

    private List<Player> allPlayers;

    private final Map<Integer, AllyStatTracker> allyStatTrackers;

    private final boolean[] dieUsed = new boolean[DICE_VALUES.length];

    public SimulationStatTracker(Dice characterDie) {
        mainPlayer = new Player(characterDie, new GameStatTracker(1));

        players[0] = mainPlayer;
        for (int i = 1; i < 4; i++) {
            // placeholder die; reassigned every game in startNewGame
            players[i] = new Player(Dice.NORMAL_DICE, new GameStatTracker(1));
        }

        playerGroup = new PlayerGroup(Arrays.asList(players));

        allyStatTrackers = Map.of(0, new AllyStatTracker(0),
                1, new AllyStatTracker(1),
                2, new AllyStatTracker(2),
                3, new AllyStatTracker(3),
                4, new AllyStatTracker(4));
    }

    public PlayerGroup startNewGame(int turnCount) {
        // reset every player's tracker + per-game fields
        for (Player p : players) {
            p.getGameStatTracker().reset(turnCount);
            p.setCurrentPlace(0);
            p.setCurrentSpace(null);
        }

        // main keeps its die; pick 3 distinct opponent dice (distinct from each other and from main)
        Arrays.fill(dieUsed, false);
        dieUsed[mainPlayer.getCharacterDice().ordinal()] = true;
        for (int i = 1; i < 4; i++) {
            Dice d;
            do {
                d = DICE_VALUES[RandomUtils.nextIntExclusive(DICE_VALUES.length)];
            } while (dieUsed[d.ordinal()]);
            dieUsed[d.ordinal()] = true;
            players[i].setCharacterDice(d);
        }

        // random, consistent-for-the-game turn order (in-place Fisher-Yates; reflected in playerGroup)
        for (int i = 3; i > 0; i--) {
            int j = RandomUtils.nextIntExclusive(i + 1);
            Player tmp = players[i];
            players[i] = players[j];
            players[j] = tmp;
        }

        return playerGroup;
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
