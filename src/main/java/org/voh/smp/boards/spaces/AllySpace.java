package org.voh.smp.boards.spaces;

import org.voh.smp.boards.layout.MPBoard;
import lombok.ToString;
import org.voh.smp.simulation.Player;
import org.voh.smp.simulation.PlayerGroup;

@ToString(callSuper = true)
public class AllySpace extends BaseSpace {

    public AllySpace(int spaceID) {
        super(spaceID);
    }

    @Override
    public boolean processEvent(MPBoard gameBoard, Player currentPlayer, PlayerGroup playerGroup) {
        currentPlayer.getGameStatTracker().addAlly(playerGroup);
        return true;
    }
}
