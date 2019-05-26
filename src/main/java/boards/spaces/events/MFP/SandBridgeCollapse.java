package boards.spaces.events.MFP;

import boards.MPEdge;
import boards.layout.MPBoard;
import boards.spaces.BaseSpace;
import boards.spaces.events.MoveEventSpace;
import lombok.ToString;
import simulation.Player;
import simulation.PlayerGroup;
import utils.RandomUtils;

@ToString(callSuper = true)
public class SandBridgeCollapse extends MoveEventSpace {

    protected int countdown;

    public SandBridgeCollapse(int spaceID, Integer spaceToMoveToID) {
        this(spaceID, spaceToMoveToID, -1, -1);
    }

    public SandBridgeCollapse(int spaceID, Integer spaceToMoveToID, int x, int y) {
        super(spaceID, spaceToMoveToID, x, y);
        countdown = RandomUtils.getRandomInt(4, 5);
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
    public boolean processEvent(MPBoard<BaseSpace, MPEdge> gameBoard,
                                Player currentPlayer, PlayerGroup playerGroup) {
        decrementCountdown();

        if (countdown <= 0) {
            //The bridge collapses
            gameBoard.removeEdge(gameBoard.getVertexById(9), gameBoard.getVertexById(59));
            gameBoard.removeEdge(gameBoard.getVertexById(59), gameBoard.getVertexById(14));
            currentPlayer.addCoins(-3);
            return true;
        }
        return false;
    }

    @Override
    public void reset() {
        countdown = RandomUtils.getRandomInt(4, 5);
    }

    protected void decrementCountdown() {
        countdown--;
    }
}
