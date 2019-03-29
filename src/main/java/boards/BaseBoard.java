package boards;

import boards.layout.MPBoard;
import boards.spaces.BaseSpace;
import boards.spaces.BlueSpace;
import boards.spaces.RedSpace;
import boards.spaces.StarSpace;
import boards.spaces.events.EventSpace;
import boards.spaces.events.MoveEventSpace;
import boards.spaces.events.SandBridgeCollapse;
import lombok.Getter;
import lombok.Setter;
import org.jgrapht.Graphs;
import org.jgrapht.graph.builder.GraphBuilder;
import stattracker.GameStatTracker;
import utils.RandomUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseBoard {
    @Getter
    protected MPBoard<BaseSpace, MPEdge> board;

    protected GraphBuilder<BaseSpace, MPEdge, MPBoard<BaseSpace, MPEdge>> graphBuilder;

    protected List<StarSpace> starSpaces = new ArrayList<>();

    protected void initializeBoard() {
        board = new MPBoard<>(MPEdge.class);
        graphBuilder = new GraphBuilder<>(new MPBoard<>(MPEdge.class));

        buildInitialGraph();

        starSpaces = board.vertexSet().stream()
                .filter(space -> space instanceof StarSpace)
                .map(space -> (StarSpace)space)
                .collect(Collectors.toList());

        changeStarSpace();
    }

    protected abstract void buildInitialGraph();

    public void resetBoard() {
        board = new MPBoard<>(MPEdge.class);
        initializeBoard();
    }

    /**
     * Change which of the star spaces is currently active and selling a star.
     * If none is currently active, activate one.
     *
     * Which star space is chosen to active is done by selecting one at random,
     * as long as it's not the same space as the one already currently active.
     *
     * This also deactivates the currently active space, if one is already active.
     */
    public void changeStarSpace() {
        StarSpace currentStarSpace = starSpaces.stream()
                .filter(StarSpace::isStarActive)
                .findFirst()
                .orElse(null);

        StarSpace nextStar;

        do {
            nextStar = starSpaces.get(RandomUtils.getRandomInt(starSpaces.size() - 1));
        } while (currentStarSpace != null && currentStarSpace.getSpaceID() == nextStar.getSpaceID());

        if (currentStarSpace != null) {
            currentStarSpace.deactivateStar();
        }
        nextStar.activateStar();
    }

    public BaseSpace getStartSpace() {
        return board.getStartSpace();
    }

    public int getTotalBoardSize() {
        return board.getGraphSize();
    }

    public BaseSpace getNextSpace(BaseSpace startingSpace) {
        List<BaseSpace> nextSpaces = getNextSpaces(startingSpace);

        return nextSpaces.get(nextSpaces.size() > 1 ? RandomUtils.getRandomInt(nextSpaces.size() - 1) : 0);
    }

    /**
     * This function is way noticeably expensive for some reason, so only use it with boards
     *  that actually have a toll somewhere. Otherwise, use the function above.
     */
    public BaseSpace getNextSpace(BaseSpace startingSpace, GameStatTracker gameStatTracker) {
        List<BaseSpace> nextSpaces = new ArrayList<>();

        for (BaseSpace nextSpace : getNextSpaces(startingSpace)) {
            if (!nextSpace.hasToll() || nextSpace.canCross(gameStatTracker)) {
                nextSpaces.add(nextSpace);
            }
        }

        return nextSpaces.get(nextSpaces.size() > 1 ? RandomUtils.getRandomInt(nextSpaces.size() - 1) : 0);
    }

    public List<BaseSpace> getNextSpaces(BaseSpace startingSpace) {
        return Graphs.successorListOf(board, startingSpace);
    }

    public BaseSpace getDestination(BaseSpace currentSpace, int distance, GameStatTracker gameStatTracker) {

        for (int i = 0; i < distance; ++i) {
            currentSpace = getNextSpace(currentSpace);

            if (!currentSpace.affectsMovement()) {
                --i;
            }

            if (currentSpace.isPassingEvent()) {
                currentSpace = processEvent(gameStatTracker, currentSpace);
            }
        }

        if (currentSpace instanceof EventSpace && !currentSpace.isPassingEvent()) {
            currentSpace = processEvent(gameStatTracker, currentSpace);
        }

        return currentSpace;
    }

    public List<BaseSpace> getDestinations(BaseSpace startingSpace, int distance, GameStatTracker gameStatTracker) {
        List<BaseSpace> currentSpaces = new ArrayList<>(Collections.singletonList(startingSpace));

       return depthFirstSearch(currentSpaces, distance, gameStatTracker);
    }

    protected List<BaseSpace> depthFirstSearch(List<BaseSpace> currentSpaces, int distance, GameStatTracker gameStatTracker) {
        List<BaseSpace> returnList = new ArrayList<>();

        if (distance <= 0) {
            //If we're left with any non-movement spaces, push those forward one more.
            if (currentSpaces.stream().anyMatch(s -> !s.affectsMovement())) {
                for (BaseSpace space : currentSpaces) {
                    if (space.affectsMovement()) {
                        returnList.add(space);
                    }
                    else {
                        returnList.addAll(processSpaceForDFS(space, 0, gameStatTracker));
                    }
                }

                return processEventsOnLandedSpaces(returnList, gameStatTracker);
            }
            else {
                return processEventsOnLandedSpaces(currentSpaces, gameStatTracker);
            }
        }

        for (BaseSpace space : currentSpaces) {
            returnList.addAll(processSpaceForDFS(space, distance, gameStatTracker));
        }

        return returnList;
    }

    private List<BaseSpace> processSpaceForDFS(BaseSpace space, int distance, GameStatTracker gameStatTracker) {
        boolean affectsMovement = space.affectsMovement();
        if (space.isPassingEvent()) {
            space = processEvent(gameStatTracker, space);
        }

        return depthFirstSearch(getNextSpaces(space),
                affectsMovement ? distance - 1 : distance,
                gameStatTracker);
    }

    private List<BaseSpace> processEventsOnLandedSpaces(List<BaseSpace> spaces, GameStatTracker gameStatTracker) {
        List<BaseSpace> resultSpaces = new ArrayList<>();

        for (BaseSpace space : spaces) {
            if (!space.isPassingEvent()) {
                resultSpaces.add(processEvent(gameStatTracker, space));
            }
            else {
                resultSpaces.add(space);
            }
        }

        return resultSpaces;
    }

    public void lastThreeTurns() {
        setRedAndBlueCoinAmounts(6);
    }

    public void resetRedAndBlueCoinAmounts() {
        setRedAndBlueCoinAmounts(3);
    }

    protected void setRedAndBlueCoinAmounts(int newCoinAmount) {
        for (BaseSpace space : board.vertexSet()) {
            if (space instanceof BlueSpace) {
                ((BlueSpace) space).setCoins(newCoinAmount);
            }
            else if (space instanceof RedSpace) {
                ((RedSpace) space).setCoins(-newCoinAmount);
            }
        }
    }

    protected BaseSpace processEvent(GameStatTracker gameStatTracker, BaseSpace currentSpace) {
        if (currentSpace instanceof SandBridgeCollapse) {
            currentSpace = processBridgeCollapseEvent(gameStatTracker, currentSpace);
        }
        else if (currentSpace instanceof MoveEventSpace && currentSpace.moveToSpace() > -1) {
            MoveEventSpace eventSpace = (MoveEventSpace) currentSpace;
            currentSpace = board.getVertexById(eventSpace.moveToSpace());

            //This is where the event space gets transformed into a blue space.
            eventSpace.processEvent(board, gameStatTracker);
        }
        else if (this instanceof KameksTantalizingTower) {
            currentSpace.processKamekEvent(board, gameStatTracker);
        }
        else {
            currentSpace.processEvent(board, gameStatTracker);
            currentSpace = board.getVertexById(currentSpace.getSpaceID());
        }

        if (board.isNeedToMoveStar()) {
            changeStarSpace();
            board.setNeedToMoveStar(false);
        }

        return currentSpace;
    }

    protected BaseSpace processBridgeCollapseEvent(GameStatTracker gameStatTracker, BaseSpace currentSpace) {
        boolean bridgeCollapsed = currentSpace.processEvent(board, gameStatTracker);

        if (bridgeCollapsed) {
            return board.getVertexById(currentSpace.moveToSpace());
        }
        return currentSpace;
    }
}
