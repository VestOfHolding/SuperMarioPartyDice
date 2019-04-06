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

    public BaseSpace(int spaceID, int x, int y) {
        this.spaceID = spaceID;
        this.x = x;
        this.y = y;
    }

    public int coinGain() {
        return 0;
    }

    public int moveToSpace() {
        return -1;
    }

    public boolean processEvent(MPBoard<BaseSpace, MPEdge> gameBoard, GameStatTracker gameStatTracker) {
        return false;
    }

    public boolean processKamekEvent(MPBoard<BaseSpace, MPEdge> gameBoard, GameStatTracker gameStatTracker) {
        return processEvent(gameBoard, gameStatTracker);
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

    public boolean canCross(GameStatTracker gameStatTracker, int starCost) {
        return false;
    }

    public SpaceUIClass getNodeClass() {
        return SpaceUIClass.OTHER;
    }
}
