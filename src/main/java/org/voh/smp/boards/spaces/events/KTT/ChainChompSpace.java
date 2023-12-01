package org.voh.smp.boards.spaces.events.KTT;

import org.voh.smp.boards.layout.MPBoard;
import org.voh.smp.boards.spaces.BaseSpace;
import lombok.ToString;
import org.voh.smp.simulation.Player;
import org.voh.smp.simulation.PlayerGroup;
import org.voh.smp.utils.RandomUtils;

@ToString(callSuper = true)
public class ChainChompSpace extends BaseSpace {

    public ChainChompSpace(int spaceID, int x, int y) {
        super(spaceID, x, y);
    }

    @Override
    public boolean processEvent(MPBoard gameBoard,
                                Player currentPlayer, PlayerGroup playerGroup) {
        //The Chain Chomp comes and steals coins.
        // Not fully sure what the range of possibilities is here yet.
        // I've seen both 5 and 7, though not sure what the percentage chance is.
        currentPlayer.addCoins(RandomUtils.isFlippedCoinHeads() ? -5 : -7);
        return true;
    }
}
