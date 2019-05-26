package boards.spaces.events;

import boards.MPEdge;
import boards.layout.MPBoard;
import boards.spaces.BaseSpace;
import boards.spaces.BlueSpace;
import boards.spaces.SpaceColor;
import lombok.ToString;
import simulation.Player;
import simulation.PlayerGroup;

import java.util.Objects;

@ToString(callSuper = true)
public class MoveEventSpace extends BlueSpace {
    protected Integer spaceToMoveToID;

    protected boolean turnsBlue;

    private boolean used;

    public MoveEventSpace(int spaceID, Integer spaceToMoveToID, int x, int y) {
        super(spaceID, x, y);
        this.spaceToMoveToID = Objects.requireNonNullElse(spaceToMoveToID, -1);
        turnsBlue = false;
    }

    public MoveEventSpace(int spaceID, Integer spaceToMoveToID, boolean turnsBlue) {
        super(spaceID);
        this.spaceToMoveToID = Objects.requireNonNullElse(spaceToMoveToID, -1);
        this.turnsBlue = turnsBlue;
    }

    @Override
    public int moveToSpace() {
        if (turnsBlue && used) {
            return super.moveToSpace();
        }
        return spaceToMoveToID;
    }

    @Override
    public boolean processEvent(MPBoard<BaseSpace, MPEdge> gameBoard,
                                Player currentPlayer, PlayerGroup playerGroup) {
        if (turnsBlue && used) {
            super.processEvent(gameBoard, currentPlayer, playerGroup);
            return true;
        }
        else if (turnsBlue) {
            used = true;
            return true;
        }
        return false;
    }

    @Override
    public void reset() {
        used = false;
    }

    @Override
    public SpaceColor getSpaceColor() {
        return SpaceColor.GREEN;
    }
}
