package boards;

import boards.spaces.BaseSpace;
import boards.spaces.BlueSpace;
import boards.spaces.RedSpace;
import boards.spaces.SpaceFactory;
import boards.spaces.StarSpace;
import boards.spaces.events.EventSpace;
import boards.spaces.events.KTT.ChainChompSpace;
import boards.spaces.events.KTT.ChangeStarPriceSpace;
import boards.spaces.events.KTT.ForcedShopSpace;
import boards.spaces.events.KTT.ThwompShortcutSpace;
import simulation.Player;
import stattracker.GameStatTracker;
import utils.RandomUtils;

import java.util.Arrays;
import java.util.List;

public class KameksTantalizingTower extends BaseBoard  {
    private List<Integer> possibleStarPrices = Arrays.asList(5, 10, 15);

    public KameksTantalizingTower() {
        initializeBoard();
    }

    @Override
    protected void buildInitialGraph() {
        int index = 0;

        graphBuilder.addEdgeChain(
                //Start
                SpaceFactory.createStartSpace(index++, 7, 2), //ID = 0
                //Main path to star
                SpaceFactory.createMoveEventSpace(index++, 23, 7, 7),
                newBlueKamekSpace(index++, 13, 7),
                newBlueKamekSpace(index++, 19, 7),
                newBlueKamekSpace(index++, 24, 7),
                SpaceFactory.createNonMovementSpace(index++, 28, 7),
                newBlueKamekSpace(index++, 28, 11),
                newBlueKamekSpace(index++, 28, 16),
                newBlueKamekSpace(index++, 24, 16),
                SpaceFactory.createNonMovementSpace(index++, 20, 16), //ID = 9
                SpaceFactory.createLuckySpace(index++, 18, 20),
                newBlueKamekSpace(index++, 23, 20),
                new ForcedShopSpace(index++, 23, 23), //ID = 12
                newBlueKamekSpace(index++, 23, 27),
                newBlueKamekSpace(index++, 23, 30),
                new ChainChompSpace(index++, 20, 30),
                new ChainChompSpace(index++, 16, 30),
                newBlueKamekSpace(index++, 7, 30), //ID = 17
                SpaceFactory.createBadLuckSpace(index++, 7, 34),
                new ChangeStarPriceSpace(index++, 15, 34),
                SpaceFactory.createStarSpace(index++, 18, 34),
                SpaceFactory.createNonMovementSpace(index++, 20, 38) //ID = 21
        ).addEdgeChain(
                //Past the Thwomp
                newRedKamekSpace(index++, 16, 16), //ID = 22
                newBlueKamekSpace(index++, 12, 16),
                newBlueKamekSpace(index++, 8, 16),
                SpaceFactory.createMoveEventSpace(index++, 30, 6, 20), //ID = 25
                SpaceFactory.createNonMovementSpace(index++, 10, 20), //ID = 26
                SpaceFactory.createItemSpace(index++, 14, 20) //ID = 27
        ).addEdgeChain(
                //Through the Thwomp
                new ThwompShortcutSpace(index++, 10, 22), //ID = 28
                newBlueKamekSpace(index++, 7, 26) //ID = 29
        ).addEdgeChain(
                //Odd side path
                newBlueKamekSpace(index++, 2, 26), //ID = 30
                SpaceFactory.createNonMovementSpace(index++, 2, 29),
                SpaceFactory.createVSSpace(index++, 2, 32),
                SpaceFactory.createNonMovementSpace(index++, 2, 36)  //ID = 33
        ).addEdgeChain(
                //And wrapping it back around
                newRedKamekSpace(index++, 2, 16), //ID = 34
                SpaceFactory.createItemSpace(index++, 2, 13),
                SpaceFactory.createAllySpace(index++, 2, 10),
                SpaceFactory.createVSSpace(index, 2, 7) //ID = 37
        );

        board = graphBuilder.build();
        connectPaths();
    }

    @Override
    public void lastThreeTurns() {
        setRedAndBlueCoinAmounts(10);
    }

    @Override
    public void resetRedAndBlueCoinAmounts() {
        setRedAndBlueCoinAmounts(6);
    }

    @Override
    public void resetBoard() {
        board.setOrReplaceVertex(32, SpaceFactory.createVSSpace(32));
        board.setOrReplaceVertex(37, SpaceFactory.createVSSpace(37));

        board.setOrReplaceVertex(28, new ThwompShortcutSpace(28));

        resetRedAndBlueCoinAmounts();
    }

    private void connectPaths() {
        board.addEdge(board.getVertexById(9), board.getVertexById(22));
        board.addEdge(board.getVertexById(27), board.getVertexById(10));
        board.addEdge(board.getVertexById(26), board.getVertexById(28));
        board.addEdge(board.getVertexById(29), board.getVertexById(17));
        board.addEdge(board.getVertexById(37), board.getVertexById(1));

        board.addEdge(board.getVertexById(21), board.getVertexById(34));
        board.addEdge(board.getVertexById(33), board.getVertexById(34));
    }
    
    private BlueSpace newBlueKamekSpace(int index, int x, int y) {
        return SpaceFactory.createBlueSpace(index,6, x, y);
    }
    
    private RedSpace newRedKamekSpace(int index, int x, int y) {
        return SpaceFactory.createRedSpace(index,-6, x, y);
    }

    @Override
    public BaseSpace getDestination(Player player, int distance) {
        BaseSpace currentSpace = player.getCurrentSpace();
        GameStatTracker gameStatTracker = player.getGameStatTracker();

        for (int i = 0; i < distance; ++i) {
            currentSpace = getNextSpace(currentSpace, gameStatTracker);

            if (!currentSpace.affectsMovement()) {
                --i;
            }

            if (currentSpace.isPassingEvent()) {
                currentSpace = processEvent(player, currentSpace);
            }
        }

        if (currentSpace instanceof EventSpace && !currentSpace.isPassingEvent()) {
            currentSpace = processEvent(player, currentSpace);
        }

        return currentSpace;
    }

    @Override
    public void changeStarSpace() {
        StarSpace currentStarSpace = starSpaces.stream()
                .filter(StarSpace::isStarActive)
                .findFirst()
                .orElse(null);

        //If no star is currently active, it's the beginning of the game.
        if (currentStarSpace == null) {
            currentStarSpace = starSpaces.get(0);
            currentStarSpace.activateStar();

            board.setStarCost(board.INIT_STAR_COST);
        }
        //Otherwise, leave the star alone, except for changing the price.
        else {
            changeKamekStarPrice();
        }

        board.setNeedToMoveStar(false);
    }

    /**
     * The next price of the star must be different from the current price.
     */
    public void changeKamekStarPrice() {
        int nextPrice;
        do {
            nextPrice = possibleStarPrices.get(RandomUtils.getRandomInt(possibleStarPrices.size() - 1));
        } while (nextPrice == board.getStarCost());

        board.setStarCost(nextPrice);
    }
}
