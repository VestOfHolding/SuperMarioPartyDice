package simulation;

import boards.spaces.BaseSpace;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import partydice.Dice;
import results.DieResult;
import stattracker.GameStatTracker;
import stattracker.Place;

import java.util.Comparator;

@Data
@AllArgsConstructor
public class Player implements Comparable {
    private Dice characterDice;

    @EqualsAndHashCode.Exclude
    private GameStatTracker gameStatTracker;

    @EqualsAndHashCode.Exclude
    private BaseSpace currentSpace;

    @EqualsAndHashCode.Exclude
    private Place currentPlace;

    public Player(Dice characterDice) {
        this(characterDice, null);
    }

    public Player(Dice characterDice, GameStatTracker gameStatTracker) {
        this.characterDice = characterDice;
        this.gameStatTracker = gameStatTracker;
        this.currentSpace = null;
    }

    public DieResult rollCharacterDie() {
        return characterDice.roll();
    }

    @Override
    public int compareTo(Object o) {
        Player otherPlayer = (Player)o;

        return Comparator.comparing(GameStatTracker::getStarCount).reversed()
                .thenComparing(GameStatTracker::getCoinTotal).reversed()
                .compare(gameStatTracker, otherPlayer.gameStatTracker);
    }

    public boolean isFirstOrSecond() {
        return currentPlace == Place.FIRST || currentPlace == Place.SECOND;
    }

    public boolean isInLastPlace() {
        return currentPlace == Place.FOURTH;
    }
}
