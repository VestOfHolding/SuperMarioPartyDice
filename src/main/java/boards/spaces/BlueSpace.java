package boards.spaces;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class BlueSpace extends BaseSpace {

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private int coins;

    public BlueSpace(int spaceID) {
        super(spaceID);
        coins = 3;
    }

    public BlueSpace(int spaceID, int coins) {
        super(spaceID);
        this.coins = coins;
    }

    @Override
    public int coinGain() {
        return coins;
    }
}
