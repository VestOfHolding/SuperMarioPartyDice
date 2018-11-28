package boards;

import boards.layout.Board;
import boards.layout.Edge;
import boards.spaces.BaseSpace;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public abstract class BaseBoard {
    protected Board gameBoard;

    protected abstract void initializeBoard();

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

            if (!currentSpace.affectsMovement()) {
                i -= 1;
            }
        }

        if (currentSpace.moveToSpace() > 0) {
            currentSpace = gameBoard.getNode(currentSpace.moveToSpace());
        }

        return currentSpace;
    }
}
