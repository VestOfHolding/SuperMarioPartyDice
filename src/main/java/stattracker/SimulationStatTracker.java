package stattracker;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import partydice.Dice;

import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimulationStatTracker {
    private Dice characterDie;

    private Map<Integer, AllyStatTracker> allyStatTrackers;

    public SimulationStatTracker(Dice characterDie) {
        this.characterDie = characterDie;
        allyStatTrackers = Map.of(0, new AllyStatTracker(0),
                1, new AllyStatTracker(1),
                2, new AllyStatTracker(2),
                3, new AllyStatTracker(3),
                4, new AllyStatTracker(4));
    }

    public GameStatTracker startNewGame(int turnCount) {
        return new GameStatTracker(characterDie, turnCount);
    }

    public void endGame(GameStatTracker gameStatTracker) {
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
