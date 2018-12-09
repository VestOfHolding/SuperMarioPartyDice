package boards;

import boards.layout.MPBoard;
import boards.spaces.BaseSpace;
import boards.spaces.events.EventSpace;
import boards.spaces.events.MoveEventSpace;
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
                currentSpace.processEvent(board, gameStatTracker, currentSpace);
            }
        }

        if (currentSpace instanceof EventSpace && !currentSpace.isPassingEvent()) {
            if (currentSpace instanceof MoveEventSpace && ((MoveEventSpace) currentSpace).getSpaceToMoveToID() > -1) {
                MoveEventSpace eventSpace = (MoveEventSpace) currentSpace;
                currentSpace = board.getVertexById(eventSpace.getSpaceToMoveToID());
                //This is where the event space gets transformed into a blue space.
                eventSpace.processEvent(board, gameStatTracker, eventSpace);
            }
            else {
                currentSpace.processEvent(board, gameStatTracker, currentSpace);
                currentSpace = board.getVertexById(currentSpace.getSpaceID());
            }
        }

        return currentSpace;
    }
}
