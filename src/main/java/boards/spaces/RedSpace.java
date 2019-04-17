package boards.spaces;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import utils.SpaceUIClass;

@Getter
@Setter
@ToString(callSuper = true)
public class RedSpace extends BaseSpace {

    @ToString.Exclude
    private int coins;

    public RedSpace(int spaceID, int coins) {
        super(spaceID);
        this.coins = coins;
    }

    @Override
    public int coinGain() {
        return coins;
    }

    @Override
    public SpaceUIClass getNodeClass() {
        return SpaceUIClass.RED;
    }
}
