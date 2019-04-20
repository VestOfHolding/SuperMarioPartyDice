package boards.spaces.events;

import boards.MPEdge;
import boards.layout.MPBoard;
import boards.spaces.BaseSpace;
import lombok.ToString;
import simulation.Player;
import simulation.PlayerGroup;
import utils.RandomUtils;

@ToString(callSuper = true)
public class LakituSpace extends EventSpace {

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
        int stolenCoinAmount = playerGroup.getRandomPlayerBesidesCurrent(currentPlayer)
                .takeCoins(RandomUtils.getRandomInt(5, 10));

        currentPlayer.addCoins(stolenCoinAmount);
        return true;
    }
}
