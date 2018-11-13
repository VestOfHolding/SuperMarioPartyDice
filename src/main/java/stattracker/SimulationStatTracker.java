package stattracker;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import partydice.Dice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimulationStatTracker {
    private Dice characterDie;

    private Map<Integer, AllyStatTracker> allyStatTrackers;

    private List<GameStatTracker> gameStatTrackers;

    public SimulationStatTracker(Dice characterDie) {
        this.characterDie = characterDie;
        gameStatTrackers = new ArrayList<>();
        allyStatTrackers = Map.of(0, new AllyStatTracker(0),
                1, new AllyStatTracker(1),
                2, new AllyStatTracker(2),
                3, new AllyStatTracker(3),
                4, new AllyStatTracker(4));
    }

    public GameStatTracker startNewGame() {
        return new GameStatTracker(characterDie);
    }

    public void endGame(GameStatTracker gameStatTracker) {
        gameStatTrackers.add(gameStatTracker);
        int allyCount = gameStatTracker.getAllyTotal();

        AllyStatTracker allyStatTracker = allyStatTrackers.get(allyCount);
        allyStatTracker.addCoinCount(gameStatTracker.getCoinTotal());
        allyStatTracker.addDistance(gameStatTracker.getDistanceTotal());
        allyStatTracker.addTurnAdded(gameStatTracker.getAllyGainOnTurn().get(allyCount));
        allyStatTracker.addLandedSpaceCount(gameStatTracker.getLandedSpacesAmounts());
    }

    public double getAverageDistance() {
        return gameStatTrackers.stream()
                .map(GameStatTracker::getDistanceTotal)
                .mapToDouble(a -> a)
                .average()
                .orElse(0.0);
    }

    public double getAverageCoins() {
        return gameStatTrackers.stream()
                .map(GameStatTracker::getCoinTotal)
                .mapToDouble(a -> a)
                .average()
                .orElse(0.0);
    }
}
