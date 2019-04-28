package boards;

import boards.layout.MPBoard;
import boards.spaces.BaseSpace;
import boards.spaces.BlueSpace;
import boards.spaces.RedSpace;
import boards.spaces.SpaceFactory;
import boards.spaces.StarSpace;
import boards.spaces.events.EventSpace;
import boards.spaces.events.MoveEventSpace;
import boards.spaces.events.SandBridgeCollapse;
import lombok.Getter;
import org.jgrapht.Graphs;
import org.jgrapht.graph.builder.GraphBuilder;
import simulation.Player;
import simulation.PlayerGroup;
import stattracker.GameStatTracker;
import utils.RandomUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseBoard {
    @Getter
    protected MPBoard<BaseSpace, MPEdge> board;

    @Getter
    protected String fileOutputName;

    protected GraphBuilder<BaseSpace, MPEdge, MPBoard<BaseSpace, MPEdge>> graphBuilder;

    protected List<StarSpace> starSpaces = new ArrayList<>();

    protected PlayerGroup playerGroup;

    protected SpaceFactory spaceFactory;

    protected void initializeBoard() {
        board = new MPBoard<>(MPEdge.class);
        board.setKamekBoard(false);
        graphBuilder = new GraphBuilder<>(new MPBoard<>(MPEdge.class));
        spaceFactory = new SpaceFactory();

        buildInitialGraph();

        //All non-movement spaces will have the edges leading to them have no weight.
        for (MPEdge edge : board.edgeSet()) {
            if (!edge.getTarget().affectsMovement()) {
                board.setEdgeWeight(edge, 0.0);
            }
        }

        starSpaces = board.vertexSet().stream()
                .filter(space -> space instanceof StarSpace)
                .map(space -> (StarSpace)space)
                .collect(Collectors.toList());

        changeStarSpace();
    }

    protected abstract void buildInitialGraph();

    public void resetBoard() {
        initializeBoard();
    }

    public void setPlayerGroup(PlayerGroup players) {
        playerGroup = players;
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
        board.setNeedToMoveStar(false);
        board.setStarCost(board.INIT_STAR_COST);
    }

    public BaseSpace getStartSpace() {
        return board.getStartSpace();
    }

    public int getTotalBoardSize() {
        return board.getGraphSize();
    }

    public BaseSpace getNextSpace(BaseSpace startingSpace, GameStatTracker gameStatTracker) {
        List<BaseSpace> nextSpaces = getNextSpaces(startingSpace);

        return nextSpaces.get(nextSpaces.size() > 1 ? RandomUtils.getRandomInt(nextSpaces.size() - 1) : 0);
    }

    public List<BaseSpace> getNextSpaces(BaseSpace startingSpace) {
        return Graphs.successorListOf(board, startingSpace);
    }

    public BaseSpace getDestination(Player player, int distance) {
        BaseSpace currentSpace = player.getCurrentSpace();

        for (int i = 0; i < distance; ++i) {
            currentSpace = getNextSpace(currentSpace, player.getGameStatTracker());

            if (!currentSpace.affectsMovement()) {
                --i;
            }

            if (currentSpace.isPassingEvent()) {
                currentSpace = processEvent(player, currentSpace);
            }
        }

        //We set the color before processing the event, because of the case where
        // the player lands on a space that moves them, but the color for this turn should
        // still reflect that move event space they landed on, not the actual space they end their turn on.
        player.setLandedSpaceColor(currentSpace.getSpaceColor());

        if ((currentSpace instanceof EventSpace || currentSpace instanceof MoveEventSpace)
                && !currentSpace.isPassingEvent()) {

            currentSpace = processEvent(player, currentSpace);
        }

        return currentSpace;
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

    protected BaseSpace processEvent(Player player, BaseSpace currentSpace) {
        if (currentSpace instanceof SandBridgeCollapse) {
            currentSpace = processBridgeCollapseEvent(player, currentSpace);
        }
        else if (currentSpace instanceof MoveEventSpace && currentSpace.moveToSpace() > -1) {
            MoveEventSpace eventSpace = (MoveEventSpace) currentSpace;
            currentSpace = board.getVertexById(eventSpace.moveToSpace());

            //This is where the event space gets transformed into a blue space.
            eventSpace.processEvent(board, player, playerGroup);
        }
        else {
            currentSpace.processEvent(board, player, playerGroup);
            currentSpace = board.getVertexById(currentSpace.getSpaceID());
        }

        if (board.isNeedToMoveStar()) {
            changeStarSpace();
        }

        return currentSpace;
    }

    protected BaseSpace processBridgeCollapseEvent(Player player, BaseSpace currentSpace) {
        boolean bridgeCollapsed = currentSpace.processEvent(board, player, playerGroup);

        if (bridgeCollapsed) {
            return board.getVertexById(currentSpace.moveToSpace());
        }
        return currentSpace;
    }
}
