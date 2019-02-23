package boards.spaces;

import boards.MPEdge;
import boards.layout.MPBoard;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import stattracker.GameStatTracker;
import utils.SpaceUIClass;

@NoArgsConstructor
@Data
@ToString
public class BaseSpace {

    protected int spaceID;

    protected int x;
    protected int y;

    public BaseSpace(int spaceID) {
        this.spaceID = spaceID;
    }

    public int coinGain() {
        return 0;
    }

    public boolean addAlly() {
        return false;
    }

    public int moveToSpace() {
        return -1;
    }

    public boolean processEvent(MPBoard<BaseSpace, MPEdge> gameBoard, GameStatTracker gameStatTracker, BaseSpace space) {
        return false;
    }

    public boolean affectsMovement() {
        return true;
    }

    public boolean isPassingEvent() {
        return false;
    }

    public boolean hasToll() {
        return false;
    }

    public boolean canCross(GameStatTracker gameStatTracker) {
        return false;
    }

    public SpaceUIClass getNodeClass() {
        return SpaceUIClass.OTHER;
    }
}
