package org.voh.smp.boards.spaces.events.KBPM;

import org.voh.smp.boards.layout.MPBoard;
import org.voh.smp.boards.spaces.BaseSpace;
import lombok.ToString;
import org.voh.smp.simulation.Player;
import org.voh.smp.simulation.PlayerGroup;

@ToString(callSuper = true)
public class BobombAllySpace extends BaseSpace {

    public BobombAllySpace(int spaceID, int x, int y) {
        super(spaceID, x, y);
    }

    @Override
    public boolean isPassingEvent() {
        return true;
    }

    @Override
    public boolean processEvent(MPBoard gameBoard,
                                Player currentPlayer, PlayerGroup playerGroup) {
        currentPlayer.getGameStatTracker().addBobombAlly();
        return true;
    }
}
