package simulation;

import boards.spaces.BaseSpace;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import partydice.Dice;
import results.DieResult;
import stattracker.GameStatTracker;
import stattracker.Place;

import java.util.Comparator;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class Player implements Comparable {
    private Dice characterDice;

    private GameStatTracker gameStatTracker;

    private BaseSpace currentSpace;

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

    public boolean isFirstOrSecond() {
        return currentPlace == Place.FIRST || currentPlace == Place.SECOND;
    }

    public boolean isInLastPlace() {
        return currentPlace == Place.FOURTH;
    }

    public void addCoins(int coins) {
        gameStatTracker.addCoins(coins);
    }

    public void addStar() {
        gameStatTracker.addStar();
    }

    public void loseStar() {
        gameStatTracker.loseStar();
    }

    public int getCoinTotal() {
        return gameStatTracker.getCoinTotal();
    }

    public int getStarCount() {
        return gameStatTracker.getStarCount();
    }

    @Override
    public int compareTo(Object o) {
        Player otherPlayer = (Player)o;

        return Comparator.comparing(GameStatTracker::getStarCount).reversed()
                .thenComparing(GameStatTracker::getCoinTotal).reversed()
                .compare(gameStatTracker, otherPlayer.gameStatTracker);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Player player = (Player) o;
        return characterDice == player.characterDice;
    }

    @Override
    public int hashCode() {
        return Objects.hash(characterDice);
    }
}
