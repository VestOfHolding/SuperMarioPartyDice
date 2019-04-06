package utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.Range;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class LuckEvent {
    private int coinGain;
    private boolean addAlly;
    private boolean moveStar;
    private boolean loseStar;
    private boolean doubleStarCost;

    //If the player has no stars to lose in this event, they are pitied and given 20 coins.
    private boolean loseStarOrGainCoins;

    private Range<Integer> chanceRange;

    public LuckEvent(Range<Integer> chanceRange) {
        this(0, chanceRange);
    }

    public LuckEvent(int coinGain, Range<Integer> chanceRange) {
        this.coinGain = coinGain;
        addAlly = false;
        moveStar = false;
        loseStar = false;
        loseStarOrGainCoins = false;
        this.chanceRange = chanceRange;
    }

    public LuckEvent(boolean moveStar, Range<Integer> chanceRange) {
        coinGain = 0;
        addAlly = false;
        this.moveStar = moveStar;
        loseStar = false;
        loseStarOrGainCoins = false;
        this.chanceRange = chanceRange;
    }

    public static LuckEvent buildEmptyEvent(Range<Integer> chanceRange) {
        return new LuckEvent(chanceRange);
    }
}
