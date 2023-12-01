package org.voh.smp.boards.spaces;

import org.voh.smp.boards.layout.MPBoard;
import lombok.Getter;
import lombok.Setter;
import org.voh.smp.simulation.Player;
import org.voh.smp.simulation.PlayerGroup;
import org.voh.smp.stattracker.GameStatTracker;

@Getter
@Setter
public class BaseSpace {

    protected int spaceID;

    protected int x;
    protected int y;

    protected int distanceToStar = Integer.MAX_VALUE;

    public BaseSpace(int spaceID) {
        this.spaceID = spaceID;
    }

    public BaseSpace(int spaceID, int x, int y) {
        this.spaceID = spaceID;
        this.x = x;
        this.y = y;
    }

    public int moveToSpace() {
        return -1;
    }

    public boolean processEvent(MPBoard gameBoard, Player currentPlayer, PlayerGroup playerGroup) {
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

    public void reset() { }

    public SpaceColor getSpaceColor() {
        return SpaceColor.GREEN;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (null == o || getClass() != o.getClass()) {
            return false;
        }
        BaseSpace baseSpace = (BaseSpace) o;
        return getSpaceID() == baseSpace.getSpaceID();
    }

    @Override
    public int hashCode() {
        return spaceID;
    }

    public String toString() {
        return "BaseSpace(spaceID=" + getSpaceID() +
                ", x=" + getX() +
                ", y=" + getY() + ")";
    }
}
