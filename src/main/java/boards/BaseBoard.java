package boards;

import boards.layout.MPBoard;
import boards.spaces.BaseSpace;
import boards.spaces.events.EventSpace;
import boards.spaces.events.MoveEventSpace;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.builder.GraphBuilder;
import stattracker.GameStatTracker;

import java.util.List;
import java.util.Random;

public abstract class BaseBoard {
    protected Random random = new Random();

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

        return nextSpaces.get(nextSpaces.size() > 1 ? random.nextInt(nextSpaces.size()) : 0);
    }

    public BaseSpace getDestination(BaseSpace currentSpace, int distance, GameStatTracker gameStatTracker) {
        for (int i = 0; i < distance; ++i) {
            currentSpace = getNextSpace(currentSpace);

            if (!currentSpace.affectsMovement()) {
                i -= 1;
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
