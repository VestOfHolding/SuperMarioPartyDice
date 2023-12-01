package org.voh.smp.boards.spaces.events.MFP;

import org.voh.smp.boards.layout.MPBoard;
import org.voh.smp.boards.spaces.BaseSpace;
import org.voh.smp.boards.spaces.events.LakituSpace;
import lombok.ToString;
import org.voh.smp.simulation.Player;
import org.voh.smp.simulation.PlayerGroup;
import org.voh.smp.utils.RandomUtils;

@ToString(callSuper = true)
public class PresentBoxSpace extends BaseSpace {
    private static final LakituSpace lakituEvent = new LakituSpace(-1, -1, -1);

    public PresentBoxSpace(int spaceID, int x, int y) {
        super(spaceID);
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean processEvent(MPBoard gameBoard,
                                Player currentPlayer, PlayerGroup playerGroup) {
        int chanceResult = RandomUtils.getRandomInt(2);

        //A one in three chance of each event happening.
        if (0 == chanceResult) {
            currentPlayer.addCoins(-RandomUtils.getRandomInt(3, 10));
        } else if (1 == chanceResult) {
            lakituEvent.processEvent(gameBoard, currentPlayer, playerGroup);
        }
        //The third possibility is that the player gets a Koopa Paratroopa, which is not implemented.
        //  This event would allow the player to pay coins to move to someone else's space.

        return true;
    }

    @Override
    public boolean affectsMovement() {
        return false;
    }

    @Override
    public boolean isPassingEvent() {
        return true;
    }
}
