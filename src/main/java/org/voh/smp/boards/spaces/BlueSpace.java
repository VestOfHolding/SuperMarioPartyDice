package org.voh.smp.boards.spaces;

import org.voh.smp.boards.layout.MPBoard;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.voh.smp.simulation.Player;
import org.voh.smp.simulation.PlayerGroup;

@Getter
@Setter
@ToString(callSuper = true)
public class BlueSpace extends BaseSpace {

    @ToString.Exclude
    private int coins;

    public BlueSpace(int spaceID) {
        super(spaceID);
        coins = 3;
    }

    public BlueSpace(int spaceID, int coins) {
        super(spaceID);
        this.coins = coins;
    }

    public BlueSpace(int spaceID, int x, int y) {
        super(spaceID, x, y);
    }

    @Override
    public boolean processEvent(MPBoard gameBoard, Player currentPlayer, PlayerGroup playerGroup) {
        currentPlayer.addCoins(coins);
        return false;
    }

    @Override
    public SpaceColor getSpaceColor() {
        return SpaceColor.BLUE;
    }
}
