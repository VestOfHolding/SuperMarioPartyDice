package org.voh.smp.boards.spaces.events.MFP;

import org.voh.smp.boards.layout.MPBoard;
import org.voh.smp.boards.spaces.events.MoveEventSpace;
import lombok.ToString;
import org.voh.smp.simulation.Player;
import org.voh.smp.simulation.PlayerGroup;
import org.voh.smp.utils.RandomUtils;

@ToString(callSuper = true)
public class SandBridgeCollapse extends MoveEventSpace {

    protected int countdown;

    public SandBridgeCollapse(int spaceID, Integer spaceToMoveToID) {
        this(spaceID, spaceToMoveToID, -1, -1);
    }

    public SandBridgeCollapse(int spaceID, Integer spaceToMoveToID, int x, int y) {
        super(spaceID, spaceToMoveToID, x, y);
        countdown = RandomUtils.getRandomInt(4, 5);
    }

    @Override
    public boolean affectsMovement() {
        return false;
    }

    @Override
    public boolean isPassingEvent() {
        return true;
    }

    @Override
    public int moveToSpace() {
        return 0 < countdown ? -1 : spaceToMoveToID;
    }

    @Override
    public boolean processEvent(MPBoard gameBoard,
                                Player currentPlayer, PlayerGroup playerGroup) {
        decrementCountdown();

        if (0 >= countdown) {
            //The bridge collapses
            gameBoard.removeEdge(gameBoard.getVertexById(9), gameBoard.getVertexById(59));
            gameBoard.removeEdge(gameBoard.getVertexById(59), gameBoard.getVertexById(14));
            currentPlayer.addCoins(-3);
            return true;
        }
        return false;
    }

    @Override
    public void reset() {
        countdown = RandomUtils.getRandomInt(4, 5);
    }

    protected void decrementCountdown() {
        countdown--;
    }
}
