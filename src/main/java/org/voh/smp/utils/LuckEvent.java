package org.voh.smp.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.Range;

import java.util.Objects;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class LuckEvent {
    //Meeehhhh, I hate this, but the only other thing I can think of right now is to
    // turn this class into another enum, and the idea of LuckyEventTable being
    // an enum of a list of enums makes me cry on the inside.
    //Going to continue thinking over how to do this.
    private int coinChange;
    private int rivalCoinChange;

    private boolean addAlly;
    private boolean moveStar;
    private boolean loseStar;
    private boolean doubleStarCost;
    private boolean giveCoinsToLast;
    private boolean giveCoinsToRandom;
    private boolean giveCoinsToAll;

    //If the player has no stars to lose in this event, they are pitied and given 20 coins.
    private boolean loseStarOrGainCoins;

    private Range<Integer> chanceRange;

    public LuckEvent(Range<Integer> chanceRange) {
        this(0, chanceRange);
    }

    public LuckEvent(int coinChange, Range<Integer> chanceRange) {
        this(coinChange, false, chanceRange);
    }

    public LuckEvent(boolean moveStar, Range<Integer> chanceRange) {
        this(0, moveStar, chanceRange);
    }

    public LuckEvent(int coinChange, boolean moveStar, Range<Integer> chanceRange) {
        this.coinChange = coinChange;
        addAlly = false;
        this.moveStar = moveStar;
        loseStar = false;
        doubleStarCost = false;
        giveCoinsToLast = false;
        giveCoinsToRandom = false;
        giveCoinsToAll = false;
        loseStarOrGainCoins = false;
        this.chanceRange = chanceRange;
    }

    public static LuckEvent buildEmptyEvent(Range<Integer> chanceRange) {
        return new LuckEvent(chanceRange);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (null == o || getClass() != o.getClass()) {
            return false;
        }
        LuckEvent luckEvent = (LuckEvent) o;
        return getChanceRange().equals(luckEvent.getChanceRange());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getChanceRange());
    }
}
