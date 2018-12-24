package boards.spaces.events.KTT;

import boards.spaces.events.MoveEventSpace;

public class NonMovementMoveSpace extends MoveEventSpace {
    public NonMovementMoveSpace(int spaceID, Integer spaceToMoveToID) {
        super(spaceID, spaceToMoveToID);
    }

    @Override
    public boolean isPassingEvent() {
        return true;
    }

    @Override
    public boolean affectsMovement() {
        return false;
    }
}
