package boards.spaces.events.KTT;

import boards.MPEdge;
import boards.layout.MPBoard;
import boards.spaces.BaseSpace;
import lombok.ToString;
import simulation.Player;
import simulation.PlayerGroup;
import utils.RandomUtils;

@ToString(callSuper = true)
public class ChainChompSpace extends BaseSpace {

    public ChainChompSpace(int spaceID, int x, int y) {
        super(spaceID, x, y);
    }

    @Override
    public boolean processEvent(MPBoard<BaseSpace, MPEdge> gameBoard,
                                Player currentPlayer, PlayerGroup playerGroup) {
        //The Chain Chomp comes and steals coins.
        // Not fully sure what the range of possibilities is here yet.
        // I've seen both 5 and 7, though not sure what the percentage chance is.
        currentPlayer.addCoins(RandomUtils.isFlippedCoinHeads() ? -5 : -7);
        return true;
    }
}
