package org.voh.smp.simulation;

import org.voh.smp.boards.spaces.BaseSpace;
import org.voh.smp.boards.spaces.SpaceColor;
import lombok.Getter;
import lombok.Setter;
import org.voh.smp.partydice.Dice;
import org.voh.smp.results.DieResult;
import org.voh.smp.stattracker.GameStatTracker;

import java.util.Objects;

@Getter
@Setter
public class Player implements Comparable<Player> {
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
        currentSpace = null;
    }

    public DieResult rollCharacterDie() {
        return characterDice.roll();
    }

    public boolean isFirstOrSecond() {
        return 1 == currentPlace || 2 == currentPlace;
    }

    public boolean isInLastPlace() {
        return 3 == currentPlace;
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
        if (0 > gameStatTracker.getCoinTotal() - coinLoss) {
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
    public int compareTo(Player otherPlayer) {
        int result = otherPlayer.gameStatTracker.getStarCount() - gameStatTracker.getStarCount();

        if (0 == result) {
            return otherPlayer.gameStatTracker.getCoinTotal() - gameStatTracker.getCoinTotal();
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (null == o || getClass() != o.getClass()) {
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
