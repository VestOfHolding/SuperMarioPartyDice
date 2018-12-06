package boards;

import boards.layout.CustomSimpleDirectedGraph;
import boards.spaces.BaseSpace;
import boards.spaces.events.EventSpace;
import boards.spaces.events.MoveEventSpace;
import org.apache.commons.collections4.CollectionUtils;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.builder.GraphBuilder;
import stattracker.GameStatTracker;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public abstract class BaseBoard {
    protected CustomSimpleDirectedGraph<BaseSpace, DefaultEdge> board;

    protected GraphBuilder<BaseSpace, DefaultEdge, CustomSimpleDirectedGraph<BaseSpace, DefaultEdge>> graphBuilder;

    protected abstract void initializeBoard();

    public void resetBoard() {
        board = new CustomSimpleDirectedGraph<>(DefaultEdge.class);
        initializeBoard();
    }

    public List<BaseSpace> travelToNextSpaces(BaseSpace startingSpace) {
        return travelToNextSpaces(Collections.singletonList(startingSpace));
    }

    protected List<BaseSpace> travelToNextSpaces(List<BaseSpace> startingSpaces) {
        if (CollectionUtils.isEmpty(startingSpaces)) {
            return Collections.emptyList();
        }
        try {
            return startingSpaces.stream()
                    .flatMap(space -> Graphs.successorListOf(board, space).stream())
                    .distinct()
                    .collect(Collectors.toList());

        } catch (NullPointerException npe) {
            System.out.println("Null pointer on this space: " + String.join(",", startingSpaces.toString()));
        }

        return Collections.emptyList();
    }

    public BaseSpace getStartSpace() {
        return board.getStartSpace();
    }

    public int getTotalBoardSize() {
        return board.getGraphSize();
    }

    public BaseSpace getDestination(BaseSpace startSpace, int distance, GameStatTracker gameStatTracker) {
        BaseSpace currentSpace = startSpace;
        Random random = new Random();

        for (int i = 0; i < distance; ++i) {
            List<BaseSpace> nextSpaces = travelToNextSpaces(currentSpace);

            if (nextSpaces.size() > 1) {
                currentSpace = nextSpaces.get(random.nextInt(nextSpaces.size()));
            }
            else {
                currentSpace = nextSpaces.get(0);
            }

            if (!currentSpace.affectsMovement()) {
                i -= 1;
            }
        }

        if (currentSpace instanceof EventSpace && !currentSpace.isPassingEvent()) {
            if (currentSpace instanceof MoveEventSpace && ((MoveEventSpace) currentSpace).getSpaceToMoveToID() > -1) {
                MoveEventSpace eventSpace = (MoveEventSpace) currentSpace;
                currentSpace = board.getVertexById(((MoveEventSpace) currentSpace).getSpaceToMoveToID());
                //This is where the event space gets transformed into a blue space.
                eventSpace.processEvent(board, gameStatTracker, eventSpace);
            }
            else {
                currentSpace.processEvent(board, gameStatTracker, currentSpace);
            }
        }

        return currentSpace;
    }
}
