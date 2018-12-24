package boards.spaces.events;

import boards.layout.MPBoard;
import boards.spaces.BaseSpace;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.jgrapht.graph.DefaultEdge;
import stattracker.GameStatTracker;
import utils.RandomUtils;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class SandBridgeCollapse extends MoveEventSpace {

    @EqualsAndHashCode.Exclude
    protected int countdown;

    public SandBridgeCollapse(int spaceID, Integer spaceToMoveToID) {
        super(spaceID, spaceToMoveToID);
        countdown = 5;
    }

    @Override
    public boolean affectsMovement() {
        return false;
    }

    @Override
    public boolean isPassingEvent() {
        return true;
    }

    @Override
    public int moveToSpace() {
        return countdown > 0 ? -1 : spaceToMoveToID;
    }

    @Override
    public boolean processEvent(MPBoard<BaseSpace, DefaultEdge> gameBoard,
                                GameStatTracker gameStatTracker, BaseSpace space) {
        decrementCountdown();

        if (countdown <= 0) {
            //The bridge collapses
            gameBoard.removeEdge(gameBoard.getVertexById(9), gameBoard.getVertexById(59));
            gameBoard.removeEdge(gameBoard.getVertexById(59), gameBoard.getVertexById(14));
            gameStatTracker.addCoins(-3);
            return true;
        }
        return false;
    }

    protected void decrementCountdown() {
        countdown--;

        //Admittedly arbitrary. At least better than 0%.

        //Throw in a 5% chance that three players went across
        // since the last time you were at the bridge.
        int result = RandomUtils.getRandomInt(1, 20);
        if (result <= 1) {
            countdown -= 3;
        }
        //15% chance that one other player went across the bridge
        else if (result <= 3) {
            countdown -= 2;
        }
        //40% chance that one other player went across the bridge
        else if (result <= 8) {
            countdown -= 1;
        }
    }
}
