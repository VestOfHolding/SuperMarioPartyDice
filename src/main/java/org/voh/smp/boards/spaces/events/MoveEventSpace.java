package org.voh.smp.boards.spaces.events;

import org.voh.smp.boards.layout.MPBoard;
import org.voh.smp.boards.spaces.BlueSpace;
import org.voh.smp.boards.spaces.SpaceColor;
import lombok.ToString;
import org.voh.smp.simulation.Player;
import org.voh.smp.simulation.PlayerGroup;

import java.util.Objects;

@ToString(callSuper = true)
public class MoveEventSpace extends BlueSpace {
    protected final Integer spaceToMoveToID;

    protected final boolean turnsBlue;

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
    public boolean processEvent(MPBoard gameBoard,
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
