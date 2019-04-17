package boards.spaces;

import lombok.ToString;

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

}
