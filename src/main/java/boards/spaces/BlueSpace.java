package boards.spaces;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class BlueSpace extends BaseSpace {

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
