package org.voh.smp.boards.spaces.events.KTT;

import org.voh.smp.boards.layout.MPBoard;
import org.voh.smp.boards.spaces.NonMovementSpace;
import lombok.ToString;
import org.voh.smp.simulation.Player;
import org.voh.smp.simulation.PlayerGroup;
import org.voh.smp.stattracker.GameStatTracker;

@ToString(callSuper = true)
public class ThwompShortcutSpace extends NonMovementSpace {
    private int cost;

    private int nextCost;

    public ThwompShortcutSpace(int spaceID) {
        super(spaceID);
    }

    public ThwompShortcutSpace(int spaceID, int x, int y) {
        super(spaceID, x, y);
    }

    @Override
    public boolean isPassingEvent() {
        return true;
    }

    @Override
    public boolean processEvent(MPBoard gameBoard,
                                Player currentPlayer, PlayerGroup playerGroup) {
        if (0 > nextCost) {
            nextCost = calculateNextCost();
        }

        cost = nextCost;
        currentPlayer.addCoins(-cost);

        nextCost = -1;
        return true;
    }

    @Override
    public boolean hasToll() {
        return true;
    }

    @Override
    public boolean canCross(GameStatTracker gameStatTracker, int starCost) {
        if (0 > nextCost) {
            nextCost = calculateNextCost();
        }
        return 0 <= gameStatTracker.getCoinTotal() - nextCost - starCost;
    }

    private int calculateNextCost() {
        //Every time you pass Thwomp, he wants at least one more coin than before.
        return cost + 1;
    }

    @Override
    public void reset() {
        cost = 0;
        nextCost = -1;
    }
}
