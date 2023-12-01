package org.voh.smp.boards.spaces.events.KBPM;

import org.voh.smp.boards.layout.KingBobombsBoard;
import org.voh.smp.boards.layout.MPBoard;
import org.voh.smp.boards.spaces.BaseSpace;
import lombok.ToString;
import org.voh.smp.simulation.Player;
import org.voh.smp.simulation.PlayerGroup;

@ToString(callSuper = true)
public class RoyalExplosionEvent extends BaseSpace {

    public RoyalExplosionEvent(int spaceID, int x, int y) {
        super(spaceID, x, y);
    }

    @Override
    public boolean processEvent(MPBoard gameBoard,
                                Player currentPlayer, PlayerGroup playerGroup) {
        if (!(gameBoard instanceof KingBobombsBoard)) {
            return false;
        }

        boolean explosionTime = ((KingBobombsBoard)gameBoard).decrementCountdown();

        if (explosionTime) {
            currentPlayer.getGameStatTracker().setCoinTotal(currentPlayer.getGameStatTracker().getCoinTotal() / 2);
            return true;
        }
        return false;
    }
}
