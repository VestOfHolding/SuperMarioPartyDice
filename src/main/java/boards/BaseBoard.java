package boards;

import boards.layout.Board;
import boards.layout.Edge;
import boards.spaces.BaseSpace;
import boards.spaces.events.EventSpace;
import boards.spaces.events.MoveEventSpace;
import org.apache.commons.collections4.CollectionUtils;
import stattracker.GameStatTracker;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public abstract class BaseBoard {
    protected Board gameBoard;

    protected abstract void initializeBoard();

    public abstract void resetBoard();

    public List<BaseSpace> travelToNextSpaces(BaseSpace startingSpace) {
        return travelToNextSpaces(Collections.singletonList(startingSpace));
    }

    protected List<BaseSpace> travelToNextSpaces(List<BaseSpace> startingSpaces) {
        if (CollectionUtils.isEmpty(startingSpaces)) {
            return Collections.emptyList();
        }
        try {
            return startingSpaces.stream()
                    .flatMap(space -> space.getEdges().stream())
                    .map(Edge::getDestination)
                    .collect(Collectors.toList());
        } catch (NullPointerException npe) {
            System.out.println("Null pointer on this space: " + String.join(",", startingSpaces.toString()));
        }

        return Collections.emptyList();
    }

    public BaseSpace getStartSpace() {
        return gameBoard.getStartNode();
    }

    public int getTotalBoardSize() {
        return gameBoard.getNodesSize();
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
                currentSpace = gameBoard.getNode(((MoveEventSpace) currentSpace).getSpaceToMoveToID());
                //This is where the event space gets transformed into a blue space.
                eventSpace.processEvent(gameBoard, gameStatTracker, eventSpace);
            }
            else {
                currentSpace.processEvent(gameBoard, gameStatTracker, currentSpace);
            }
        }

        return currentSpace;
    }
}
