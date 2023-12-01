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
public class RedSpace extends BaseSpace {

    @ToString.Exclude
    private int coins;

    public RedSpace(int spaceID, int coins) {
        super(spaceID);
        this.coins = coins;
    }

    @Override
    public boolean processEvent(MPBoard gameBoard, Player currentPlayer, PlayerGroup playerGroup) {
        currentPlayer.addCoins(coins);
        return false;
    }

    @Override
    public SpaceColor getSpaceColor() {
        return SpaceColor.RED;
    }
}
