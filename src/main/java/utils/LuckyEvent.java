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
public class LuckyEvent {
    private int coinGain;
    private boolean addAlly;
    private Range<Integer> chanceRange;

    public LuckyEvent(int coinGain, Range<Integer> chanceRange) {
        this.coinGain = coinGain;
        addAlly = false;
        this.chanceRange = chanceRange;
    }

    public LuckyEvent(boolean addAlly, Range<Integer> chanceRange) {
        this.coinGain = 0;
        this.addAlly = addAlly;
        this.chanceRange = chanceRange;
    }

    public static LuckyEvent buildEmptyEvent(Range<Integer> chanceRange) {
        return new LuckyEvent(0, false, chanceRange);
    }
}
