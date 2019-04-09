package boards.spaces.events.KBPM;

import boards.MPEdge;
import boards.layout.MPBoard;
import boards.spaces.BaseSpace;
import boards.spaces.events.EventSpace;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import simulation.Player;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class RoyalExplosionEvent extends EventSpace {
    public RoyalExplosionEvent(int spaceID) {
        super(spaceID);
    }

    public RoyalExplosionEvent(int spaceID, int x, int y) {
        super(spaceID, x, y);
    }

    @Override
    public boolean processEvent(MPBoard<BaseSpace, MPEdge> gameBoard,
                                Player currentPlayer, List<Player> allPlayers) {
        boolean explosionTime = gameBoard.decrementCountdown();

        if (explosionTime) {
            currentPlayer.getGameStatTracker().setCoinTotal(currentPlayer.getGameStatTracker().getCoinTotal() / 2);
            return true;
        }
        return false;
    }
}
