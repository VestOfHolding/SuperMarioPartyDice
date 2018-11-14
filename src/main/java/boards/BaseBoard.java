package boards;

import boards.layout.Board;
import boards.layout.Edge;
import boards.spaces.BaseSpace;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class BaseBoard {
    protected Board gameBoard;

    protected abstract void initializeBoard();

    protected List<BaseSpace> getPossibleDestinations(int startID, int distance) {
        List<BaseSpace> destinations = new ArrayList<>();

        BaseSpace startSpace = gameBoard.getNode(startID);

        if (startSpace == null || distance < 0) {
            return destinations;
        }

        destinations = Stream.of(startSpace).collect(Collectors.toList());

        for (int i = 0; i < distance; ++i) {
            destinations = travelToNextSpaces(destinations);
        }

        for (int i = 0; i < destinations.size(); i++) {
            BaseSpace space = destinations.get(i);
            if (space.moveToSpace() > 0) {
                destinations.set(i, gameBoard.getNode(space.moveToSpace()));
            }
        }

        return destinations;
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
                    .flatMap(space -> space.getEdges().stream())
                    .map(Edge::getTo)
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

    public BaseSpace getDestination(BaseSpace startSpace, int distance) {
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
        }

        if (currentSpace.moveToSpace() > 0) {
            currentSpace = gameBoard.getNode(currentSpace.moveToSpace());
        }

        return currentSpace;
    }
}
