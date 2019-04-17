package boards.spaces;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import utils.SpaceUIClass;

@Getter
@Setter
@NoArgsConstructor
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

    @Override
    public SpaceUIClass getNodeClass() {
        return SpaceUIClass.BLUE;
    }
}
