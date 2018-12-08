package boards.spaces.events.KBPM;

import boards.layout.MPBoard;
import boards.spaces.BaseSpace;
import boards.spaces.events.EventSpace;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.jgrapht.graph.DefaultEdge;
import stattracker.GameStatTracker;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class RoyalExplosionEvent extends EventSpace {
    public RoyalExplosionEvent(int spaceID) {
        super(spaceID);
    }

    @Override
    public void processEvent(MPBoard<BaseSpace, DefaultEdge> gameBoard,
                             GameStatTracker gameStatTracker, BaseSpace space) {
        boolean explosionTime = gameBoard.decrementCountdown();

        if (explosionTime) {
            gameStatTracker.setCoinTotal(gameStatTracker.getCoinTotal() / 2);
        }
    }
}
