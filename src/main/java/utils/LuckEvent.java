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
    private Range<Integer> chanceRange;

    public LuckEvent(int coinGain, Range<Integer> chanceRange) {
        this.coinGain = coinGain;
        addAlly = false;
        this.chanceRange = chanceRange;
    }

    public LuckEvent(boolean addAlly, Range<Integer> chanceRange) {
        this.coinGain = 0;
        this.addAlly = addAlly;
        this.chanceRange = chanceRange;
    }

    public static LuckEvent buildEmptyEvent(Range<Integer> chanceRange) {
        return new LuckEvent(0, false, chanceRange);
    }
}
