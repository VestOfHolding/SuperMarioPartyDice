package boards.spaces;

import lombok.ToString;
import utils.SpaceUIClass;

@ToString(callSuper = true)
public class NonMovementSpace extends BaseSpace {

    public NonMovementSpace(int spaceID) {
        super(spaceID);
    }

    public NonMovementSpace(int spaceID, int x, int y) {
        super(spaceID, x, y);
    }

    @Override
    public boolean affectsMovement() {
        return false;
    }

    @Override
    public SpaceUIClass getNodeClass() {
        return SpaceUIClass.NONMOVEMENT;
    }
}
