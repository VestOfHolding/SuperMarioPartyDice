package boards;

import boards.layout.MPBoard;
import boards.spaces.BaseSpace;
import boards.spaces.BlueSpace;
import boards.spaces.RedSpace;
import boards.spaces.events.EventSpace;
import boards.spaces.events.MoveEventSpace;
import boards.spaces.events.SandBridgeCollapse;
import lombok.Getter;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.builder.GraphBuilder;
import stattracker.GameStatTracker;
import utils.RandomUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BaseBoard {
    @Getter
    protected MPBoard<BaseSpace, DefaultEdge> board;

    protected GraphBuilder<BaseSpace, DefaultEdge, MPBoard<BaseSpace, DefaultEdge>> graphBuilder;

    protected void initializeBoard() {
        board = new MPBoard<>(DefaultEdge.class);
        graphBuilder = new GraphBuilder<>(new MPBoard<>(DefaultEdge.class));

        buildInitialGraph();
    }

    protected abstract void buildInitialGraph();

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
        List<BaseSpace> nextSpaces = getNextSpaces(startingSpace);

        return nextSpaces.get(nextSpaces.size() > 1 ? RandomUtils.getRandomInt(nextSpaces.size() - 1) : 0);
    }

    public List<BaseSpace> getNextSpaces(BaseSpace startingSpace) {
        return Graphs.successorListOf(board, startingSpace);
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

                return returnList;
            }
            else {
                return currentSpaces;
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
