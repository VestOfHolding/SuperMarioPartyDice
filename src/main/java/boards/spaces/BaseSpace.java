package boards.spaces;

import boards.MPEdge;
import boards.layout.MPBoard;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import simulation.Player;
import simulation.PlayerGroup;
import stattracker.GameStatTracker;
import utils.SpaceUIClass;

import java.util.Objects;

@Getter
@Setter
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

    public boolean processEvent(MPBoard<BaseSpace, MPEdge> gameBoard, Player currentPlayer, PlayerGroup playerGroup) {
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

    public boolean canCross(GameStatTracker gameStatTracker, int starCost) {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BaseSpace baseSpace = (BaseSpace) o;
        return getSpaceID() == baseSpace.getSpaceID();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSpaceID());
    }
}
