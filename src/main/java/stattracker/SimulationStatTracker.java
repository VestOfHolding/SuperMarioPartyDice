package stattracker;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import partydice.Dice;
import simulation.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimulationStatTracker {
    private Dice characterDie;

    private Player mainPlayer;

    private List<Player> allPlayers;

    private Map<Integer, AllyStatTracker> allyStatTrackers;

    public SimulationStatTracker(Dice characterDie) {
        mainPlayer = new Player(characterDie);

        allPlayers = new ArrayList<>(4);

        allyStatTrackers = Map.of(0, new AllyStatTracker(0),
                1, new AllyStatTracker(1),
                2, new AllyStatTracker(2),
                3, new AllyStatTracker(3),
                4, new AllyStatTracker(4));
    }

    public Player startNewGame(int turnCount) {
        mainPlayer.setGameStatTracker(new GameStatTracker(turnCount));

        //Easier to keep adding players to this set until there are 4 unique players.
        Set<Player> allPlayerSet = new HashSet<>();
        allPlayerSet.add(mainPlayer);

        //Across a series of simulations, only the main character we're testing will remain the same.
        while (allPlayerSet.size() < 4) {
            allPlayerSet.add(new Player(Dice.getRandomCharacterDie(), new GameStatTracker(turnCount)));
        }

        //Since order is not preserved in sets, this also kind of accidentally simulates a random
        // turn order for the characters that will now be consistent for the game.
        allPlayers = new ArrayList<>(allPlayerSet);

        return mainPlayer;
    }

    public void endGame() {
        GameStatTracker gameStatTracker = mainPlayer.getGameStatTracker();
        int allyCount = gameStatTracker.getAllyTotal();

        AllyStatTracker allyStatTracker = allyStatTrackers.get(allyCount);
        allyStatTracker.addCoinCount(gameStatTracker.getCoinTotal());
        allyStatTracker.addDistance(gameStatTracker.getDistanceTotal());
        allyStatTracker.addStarCount(gameStatTracker.getStarCount());
//        allyStatTracker.addLandedSpaceCount(gameStatTracker.getLandedSpacesAmounts());

        //Keep track of what turns all allies were gained, not just the last one.
        for (int allies : gameStatTracker.getAllyGainOnTurn().keySet()) {
            allyStatTrackers.get(allies).addTurnAdded(gameStatTracker.getAllyGainOnTurn().get(allies));
        }
    }
}
