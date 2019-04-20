package boards.spaces.events;

import boards.MPEdge;
import boards.layout.MPBoard;
import boards.spaces.BaseSpace;
import lombok.ToString;
import simulation.Player;
import simulation.PlayerGroup;
import utils.RandomUtils;

import java.util.Comparator;

@ToString(callSuper = true)
public class LakituSpace extends EventSpace {

    @ToString.Exclude
    private static final int STAR_STEAL_COST = 30;

    public LakituSpace(int spaceID, int x, int y) {
        super(spaceID, x, y);
    }

    @Override
    public boolean isPassingEvent() {
        return true;
    }

    @Override
    public boolean processEvent(MPBoard<BaseSpace, MPEdge> gameBoard,
                                Player currentPlayer, PlayerGroup playerGroup) {
        //If we have the coins, steal a star.
        if (currentPlayer.getCoinTotal() >= STAR_STEAL_COST + gameBoard.getStarCost()
                //Oh right, and the opponents should actually have stars to steal.
                && playerGroup.getAllPlayersExceptCurrent(currentPlayer).stream()
                .anyMatch(p -> p.getStarCount() > 0)) {

            //Ok, we're definitely stealing a star now. Steal from the person in first place/has the most stars.
            Player victim = playerGroup.getAllPlayersExceptCurrent(currentPlayer).stream()
                    .max(Comparator.comparing(Player::getCurrentPlace))
                    .orElse(null);
            
            if (victim != null) {
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
