package boards.spaces.events.KBPM;

import boards.MPEdge;
import boards.layout.MPBoard;
import boards.spaces.BaseSpace;
import lombok.ToString;
import simulation.Player;
import simulation.PlayerGroup;

@ToString(callSuper = true)
public class RoyalExplosionEvent extends BaseSpace {

    public RoyalExplosionEvent(int spaceID, int x, int y) {
        super(spaceID, x, y);
    }

    @Override
    public boolean processEvent(MPBoard<BaseSpace, MPEdge> gameBoard,
                                Player currentPlayer, PlayerGroup playerGroup) {
        boolean explosionTime = gameBoard.decrementCountdown();

        if (explosionTime) {
            currentPlayer.getGameStatTracker().setCoinTotal(currentPlayer.getGameStatTracker().getCoinTotal() / 2);
            return true;
        }
        return false;
    }
}
