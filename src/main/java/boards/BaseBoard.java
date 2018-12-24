package boards;

import boards.layout.MPBoard;
import boards.spaces.BaseSpace;
import boards.spaces.BlueSpace;
import boards.spaces.RedSpace;
import boards.spaces.events.EventSpace;
import boards.spaces.events.MoveEventSpace;
import boards.spaces.events.SandBridgeCollapse;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.builder.GraphBuilder;
import stattracker.GameStatTracker;
import utils.RandomUtils;

import java.util.List;

public abstract class BaseBoard {
    protected MPBoard<BaseSpace, DefaultEdge> board;

    protected GraphBuilder<BaseSpace, DefaultEdge, MPBoard<BaseSpace, DefaultEdge>> graphBuilder;

    protected abstract void initializeBoard();

    public void resetBoard() {
        board = new MPBoard<>(DefaultEdge.class);
        initializeBoard();
    }

    public BaseSpace getStartSpace() {
        return board.getStartSpace();
    }

    public int getTotalBoardSize() {
        return board.getGraphSize();
    }

    public BaseSpace getNextSpace(BaseSpace startingSpace) {
        List<BaseSpace> nextSpaces = Graphs.successorListOf(board, startingSpace);

        return nextSpaces.get(nextSpaces.size() > 1 ? RandomUtils.getRandomInt(nextSpaces.size() - 1) : 0);
    }

    public BaseSpace getDestination(BaseSpace currentSpace, int distance, GameStatTracker gameStatTracker) {

        for (int i = 0; i < distance; ++i) {
            currentSpace = getNextSpace(currentSpace);

            if (!currentSpace.affectsMovement()) {
                i -= 1;
            }

            if (currentSpace.isPassingEvent()) {
                if (currentSpace instanceof SandBridgeCollapse) {
                    currentSpace = processBridgeCollapseEvent(gameStatTracker, currentSpace);
                }
                else {
                    currentSpace = processEvent(gameStatTracker, currentSpace);
                }
            }
        }

        if (currentSpace instanceof EventSpace && !currentSpace.isPassingEvent()) {
            currentSpace = processEvent(gameStatTracker, currentSpace);
        }

        return currentSpace;
    }

    public void lastThreeTurns(int coinChangeAmount) {
        for (BaseSpace space : board.vertexSet()) {
            if (space instanceof BlueSpace) {
                ((BlueSpace) space).setCoins(((BlueSpace) space).getCoins() + coinChangeAmount);
            }
            else if (space instanceof RedSpace) {
                ((RedSpace) space).setCoins(((RedSpace) space).getCoins() - coinChangeAmount);
            }
        }
    }

    protected BaseSpace processBridgeCollapseEvent(GameStatTracker gameStatTracker, BaseSpace currentSpace) {
        boolean bridgeCollapsed = currentSpace.processEvent(board, gameStatTracker, currentSpace);

        if (bridgeCollapsed) {
            return board.getVertexById(currentSpace.moveToSpace());
        }
        return currentSpace;
    }

    protected BaseSpace processEvent(GameStatTracker gameStatTracker, BaseSpace currentSpace) {
        if (currentSpace instanceof MoveEventSpace && currentSpace.moveToSpace() > -1) {
            MoveEventSpace eventSpace = (MoveEventSpace) currentSpace;
            currentSpace = board.getVertexById(eventSpace.moveToSpace());
            //This is where the event space gets transformed into a blue space.
            eventSpace.processEvent(board, gameStatTracker, eventSpace);
        }
        else {
            currentSpace.processEvent(board, gameStatTracker, currentSpace);
            currentSpace = board.getVertexById(currentSpace.getSpaceID());
        }

        return currentSpace;
    }
}
