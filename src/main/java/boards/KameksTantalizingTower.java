package boards;

import boards.spaces.BaseSpace;
import boards.spaces.BlueSpace;
import boards.spaces.RedSpace;
import boards.spaces.StarSpace;
import boards.spaces.events.KTT.ChainChompSpace;
import boards.spaces.events.KTT.ChangeStarPriceSpace;
import boards.spaces.events.KTT.ForcedShopSpace;
import boards.spaces.events.KTT.ThwompShortcutSpace;
import stattracker.GameStatTracker;
import utils.RandomUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KameksTantalizingTower extends BaseBoard  {
    private List<Integer> possibleStarPrices = Arrays.asList(5, 10, 15);

    public KameksTantalizingTower() {
        initializeBoard();
        fileOutputName = "KameksTantalizingTower.txt";
    }

    @Override
    protected void buildInitialGraph() {
        int index = 0;

        graphBuilder.addEdgeChain(
                //Start
                spaceFactory.createStartSpace(index++, 7, 2), //ID = 0
                //Main path to star
                spaceFactory.createMoveEventSpace(index++, 23, 7, 7),
                newBlueKamekSpace(index++, 13, 7),
                newBlueKamekSpace(index++, 19, 7),
                newBlueKamekSpace(index++, 24, 7),
                spaceFactory.createNonMovementSpace(index++, 28, 7),
                newBlueKamekSpace(index++, 28, 11),
                newBlueKamekSpace(index++, 28, 16),
                newBlueKamekSpace(index++, 24, 16),
                spaceFactory.createNonMovementSpace(index++, 20, 16), //ID = 9
                spaceFactory.createLuckySpace(index++, 18, 20),
                newBlueKamekSpace(index++, 23, 20),
                new ForcedShopSpace(index++, 23, 23), //ID = 12
                newBlueKamekSpace(index++, 23, 27),
                newBlueKamekSpace(index++, 23, 30),
                new ChainChompSpace(index++, 20, 30),
                new ChainChompSpace(index++, 16, 30),
                newBlueKamekSpace(index++, 7, 30), //ID = 17
                spaceFactory.createBadLuckSpace(index++, 7, 34),
                new ChangeStarPriceSpace(index++, 15, 34),
                spaceFactory.createStarSpace(index++, 18, 34),
                spaceFactory.createNonMovementSpace(index++, 20, 38) //ID = 21
        ).addEdgeChain(
                //Past the Thwomp
                newRedKamekSpace(index++, 16, 16), //ID = 22
                newBlueKamekSpace(index++, 12, 16),
                newBlueKamekSpace(index++, 8, 16),
                spaceFactory.createMoveEventSpace(index++, 30, 6, 20), //ID = 25
                spaceFactory.createNonMovementSpace(index++, 10, 20), //ID = 26
                spaceFactory.createItemSpace(index++, 14, 20) //ID = 27
        ).addEdgeChain(
                //Through the Thwomp
                new ThwompShortcutSpace(index++, 10, 22), //ID = 28
                newBlueKamekSpace(index++, 7, 26) //ID = 29
        ).addEdgeChain(
                //Odd side path
                newBlueKamekSpace(index++, 2, 26), //ID = 30
                spaceFactory.createNonMovementSpace(index++, 2, 29),
                spaceFactory.createVSSpace(index++, 2, 32),
                spaceFactory.createNonMovementSpace(index++, 2, 36)  //ID = 33
        ).addEdgeChain(
                //And wrapping it back around
                newRedKamekSpace(index++, 2, 16), //ID = 34
                spaceFactory.createItemSpace(index++, 2, 13),
                spaceFactory.createAllySpace(index++, 2, 10),
                spaceFactory.createVSSpace(index, 2, 7) //ID = 37
        );

        board = graphBuilder.build();
        connectPaths();
        board.setKamekBoard(true);
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
        board.getVertexById(32).reset();
        board.getVertexById(37).reset();

        board.getVertexById(28).reset();

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
        return spaceFactory.createBlueSpace(index,6, x, y);
    }
    
    private RedSpace newRedKamekSpace(int index, int x, int y) {
        return spaceFactory.createRedSpace(index,-6, x, y);
    }

    @Override
    public BaseSpace getNextSpace(BaseSpace startingSpace, GameStatTracker gameStatTracker) {
        List<BaseSpace> nextSpaces = new ArrayList<>();

        for (BaseSpace nextSpace : getNextSpaces(startingSpace)) {
            if (!nextSpace.hasToll() || nextSpace.canCross(gameStatTracker, board.getStarCost())) {
                nextSpaces.add(nextSpace);
            }
        }

        return nextSpaces.get(nextSpaces.size() > 1 ? RandomUtils.getRandomInt(nextSpaces.size() - 1) : 0);
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
