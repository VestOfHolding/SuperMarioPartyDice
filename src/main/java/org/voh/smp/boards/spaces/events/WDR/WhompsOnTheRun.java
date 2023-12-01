package org.voh.smp.boards.spaces.events.WDR;

import org.voh.smp.boards.layout.MPBoard;
import org.voh.smp.boards.spaces.BaseSpace;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.voh.smp.simulation.Player;
import org.voh.smp.simulation.PlayerGroup;
import org.voh.smp.stattracker.GameStatTracker;

@Getter
@Setter
@ToString(callSuper = true)
public class WhompsOnTheRun extends BaseSpace {
    private static int COST = 3;

    private int partnerID;

    @ToString.Exclude
    private boolean active;

    public WhompsOnTheRun(int spaceID, int partnerID, boolean active, int x, int y) {
        super(spaceID);
        this.partnerID = partnerID;
        this.active = active;
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean processEvent(MPBoard gameBoard,
                                Player currentPlayer, PlayerGroup playerGroup) {
        if (!isActive()) {
            return false;
        }

        currentPlayer.addCoins(-COST);

        whompSwitch(gameBoard);
        return true;
    }

    public void whompSwitch(MPBoard gameBoard) {
        active = !active;

        WhompsOnTheRun secondWhomp = (WhompsOnTheRun)gameBoard.getVertexById(partnerID);
        secondWhomp.setActive(!secondWhomp.isActive());
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
    public boolean hasToll() {
        return isActive();
    }

    @Override
    public boolean canCross(GameStatTracker gameStatTracker, int starCost) {
        return 0 <= gameStatTracker.getCoinTotal() - COST - starCost;
    }

}
