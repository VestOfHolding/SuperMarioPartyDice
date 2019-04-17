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
public class RedSpace extends BaseSpace {

    @ToString.Exclude
    private int coins;

    public RedSpace(int spaceID) {
        super(spaceID);
        coins = -3;
    }

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
