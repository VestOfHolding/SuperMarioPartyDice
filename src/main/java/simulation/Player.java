package simulation;

import boards.spaces.BaseSpace;
import boards.spaces.SpaceColor;
import lombok.Getter;
import lombok.Setter;
import partydice.Dice;
import results.DieResult;
import stattracker.GameStatTracker;

import java.util.Objects;

@Getter
@Setter
public class Player implements Comparable {
    private Dice characterDice;

    private GameStatTracker gameStatTracker;

    private BaseSpace currentSpace;

    private int currentPlace;

    private SpaceColor landedSpaceColor;

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
        return currentPlace == 1|| currentPlace == 2;
    }

    public boolean isInLastPlace() {
        return currentPlace == 3;
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

    /**
     * @param coinLoss How many coins you want to take from this player.
     * @return The actual number of coins lost. Might be less than coinLoss if the player
     *      doesn't actually have that many coins.
     */
    public int takeCoins(int coinLoss) {
        int actualLoss = coinLoss;
        if (gameStatTracker.getCoinTotal() - coinLoss < 0) {
            actualLoss = gameStatTracker.getCoinTotal();
        }
        gameStatTracker.addCoins(-coinLoss);
        return actualLoss;
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

        int result = otherPlayer.gameStatTracker.getStarCount() - gameStatTracker.getStarCount();

        if (result == 0) {
            return otherPlayer.gameStatTracker.getCoinTotal() - gameStatTracker.getCoinTotal();
        }
        return result;
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

    @Override
    public String toString() {
        return "Player{" +
                "stars=" + gameStatTracker.getStarCount() +
                ", coins=" + gameStatTracker.getCoinTotal() +
                ", currentPlace=" + currentPlace +
                '}';
    }
}
