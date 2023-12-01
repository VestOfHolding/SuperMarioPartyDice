package org.voh.smp.boards.spaces.events;

import org.voh.smp.boards.layout.MPBoard;
import org.voh.smp.boards.spaces.BaseSpace;
import lombok.ToString;
import org.voh.smp.simulation.Player;
import org.voh.smp.simulation.PlayerGroup;
import org.voh.smp.utils.RandomUtils;

import java.util.Comparator;

@ToString(callSuper = true)
public class LakituSpace extends BaseSpace {

    private static final int STAR_STEAL_COST = 30;

    public LakituSpace(int spaceID, int x, int y) {
        super(spaceID, x, y);
    }

    @Override
    public boolean isPassingEvent() {
        return true;
    }

    @Override
    public boolean processEvent(MPBoard gameBoard,
                                Player currentPlayer, PlayerGroup playerGroup) {
        //If we have the coins, steal a star.
        if (currentPlayer.getCoinTotal() >= STAR_STEAL_COST + gameBoard.getStarCost()
                //Oh right, and the opponents should actually have stars to steal.
                && playerGroup.getAllPlayersExceptCurrent(currentPlayer).stream()
                .anyMatch(p -> 0 < p.getStarCount())) {

            //Ok, we're definitely stealing a star now. Steal from the person in first place/has the most stars.
            Player victim = playerGroup.getAllPlayersExceptCurrent(currentPlayer).stream()
                    .max(Comparator.comparing(Player::getCurrentPlace))
                    .orElse(null);
            
            if (null != victim) {
                victim.loseStar();
                currentPlayer.addStar();
                currentPlayer.addCoins(-STAR_STEAL_COST);
            }
        }
        //Otherwise, steal coins at no cost.
        else {
            int stolenCoinAmount = playerGroup.getRandomPlayerBesidesCurrent(currentPlayer)
                    .takeCoins(RandomUtils.getRandomInt(5, 10));

            currentPlayer.addCoins(stolenCoinAmount);
        }

        return true;
    }
}
