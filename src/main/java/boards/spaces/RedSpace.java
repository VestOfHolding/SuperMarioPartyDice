package boards.spaces;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import utils.SpaceUIClass;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class RedSpace extends BaseSpace {

    @EqualsAndHashCode.Exclude
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
