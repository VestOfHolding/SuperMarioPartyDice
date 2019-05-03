package boards;

import boards.spaces.BaseSpace;
import boards.spaces.events.LakituSpace;
import boards.spaces.events.WDR.ChooseTreasureChestEvent;
import boards.spaces.events.WDR.WhompSwitch;
import boards.spaces.events.WDR.WhompsOnTheRun;
import stattracker.GameStatTracker;
import utils.RandomUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class WhompsDominoRuins extends BaseBoard {

    private static final int WHOMP1_1 = 6;
    private static final int WHOMP1_2 = 46;
    private static final int WHOMP2_1 = 32;
    private static final int WHOMP2_2 = 60;

    public WhompsDominoRuins() {
        initializeBoard();
        fileOutputName = "WhompsDominoRuins.txt";
    }

    @Override
    protected void buildInitialGraph() {
        int index = 0;

        graphBuilder.addEdgeChain(
                //Start
                spaceFactory.createStartSpace(index++, 28, 1), //ID = 0
                spaceFactory.createBlueSpace(index++, 28, 6),
                spaceFactory.createRedSpace(index++, 30, 8),
                spaceFactory.createBlueSpace(index++, 30, 11),
                new WhompSwitch(index++, WHOMP1_1, WHOMP1_2, 28, 13),
                spaceFactory.createNonMovementSpace(index++, 28, 16), //ID = 5
                new WhompsOnTheRun(index++, WHOMP1_2, false, 28, 18),
                spaceFactory.createBlueSpace(index++, 28, 20),
                spaceFactory.createNonMovementSpace(index++, 28, 22), //ID = 8
                spaceFactory.createItemSpace(index++, 28, 25),
                spaceFactory.createStarSpace(index++, 28, 28),
                spaceFactory.createBlueSpace(index++, 28, 30), //ID = 11
                spaceFactory.createMoveEventSpace(index++, getMoveEventDestinationID(), true, 28, 32),
                spaceFactory.createBlueSpace(index++, 25, 32),
                spaceFactory.createBlueSpace(index++, 23, 32),
                spaceFactory.createAllySpace(index++, 20, 32), //ID = 15
                spaceFactory.createItemSpace(index++, 18, 32),
                spaceFactory.createStarSpace(index++, 15, 32),
                spaceFactory.createItemSpace(index++, 13, 32),
                spaceFactory.createBlueSpace(index++, 11, 32),
                spaceFactory.createMoveEventSpace(index++, getMoveEventDestinationID(), true, 8, 32),
                spaceFactory.createNonMovementSpace(index++, 6, 32), //ID = 21
                spaceFactory.createMoveEventSpace(index++, getMoveEventDestinationID(), true, 4, 32),
                spaceFactory.createStarSpace(index++, 2, 32),
                spaceFactory.createBlueSpace(index++, 2, 30),
                spaceFactory.createBlueSpace(index++, 2, 28),
                new LakituSpace(index++, 2, 26),
                spaceFactory.createBlueSpace(index++, 2, 24),
                spaceFactory.createBadLuckSpace(index++, 2, 22),
                spaceFactory.createItemSpace(index++, 2, 20), //ID = 28
                new WhompSwitch(index++, WHOMP2_1, WHOMP2_2, 2, 18),
                spaceFactory.createNonMovementSpace(index++, 2, 16), //ID = 30
                new WhompsOnTheRun(index++, WHOMP2_2, false, 2, 14),
                spaceFactory.createVSSpace(index++, 2, 12),
                spaceFactory.createNonMovementSpace(index++, 2, 10), //ID = 33
                spaceFactory.createBlueSpace(index++, 2, 7),
                spaceFactory.createNonMovementSpace(index++, 2, 4),
                spaceFactory.createLuckySpace(index++, 2, 2),
                spaceFactory.createLuckySpace(index++, 6, 2),
                spaceFactory.createStarSpace(index++, 10, 2),
                spaceFactory.createBlueSpace(index++, 14, 2),
                spaceFactory.createAllySpace(index++, 14, 6), //ID = 40
                spaceFactory.createBadLuckSpace(index++, 17, 6),
                spaceFactory.createBlueSpace(index++, 20, 6), //ID = 42
                spaceFactory.createNonMovementSpace(index++, 22, 6),
                spaceFactory.createBlueSpace(index++, 25, 6) //ID = 44
        ).addEdgeChain(
                //Inner Loop
                new WhompsOnTheRun(index++, WHOMP1_1, true, 26, 16), //ID = 45
                new WhompSwitch(index++, WHOMP1_1, WHOMP1_2, 23, 16),
                spaceFactory.createAllySpace(index++, 20, 16),
                spaceFactory.createStarSpace(index++, 20, 13),
                spaceFactory.createLuckySpace(index++, 20, 10) //ID = 49
        ).addEdgeChain(
                //Side Path 1
                spaceFactory.createBlueSpace(index++, 25, 22),  //ID = 50
                spaceFactory.createNonMovementSpace(index++, 23, 22),
                spaceFactory.createBlueSpace(index++, 20, 22),
                new ChooseTreasureChestEvent(index++, 20, 26),
                spaceFactory.createBadLuckSpace(index++, 20, 30) //ID = 54
        ).addEdgeChain(
                //Side Path 2
                spaceFactory.createRedSpace(index++, 6, 30), //ID = 55
                spaceFactory.createStarSpace(index++, 6, 27),
                spaceFactory.createVSSpace(index++, 6, 23),
                spaceFactory.createAllySpace(index++, 6, 20) //ID = 58
        ).addEdgeChain(
                //Side Path 3
                new WhompsOnTheRun(index++, WHOMP2_1, true, 4, 16),  //ID = 59
                new WhompSwitch(index++, WHOMP2_1, WHOMP2_2, 6, 16),
                spaceFactory.createItemSpace(index++, 9, 16),
                spaceFactory.createBlueSpace(index++, 12, 16),
                spaceFactory.createStarSpace(index++, 12, 20),
                spaceFactory.createBadLuckSpace(index++, 10, 20),
                spaceFactory.createBadLuckSpace(index++, 8, 20) //ID = 65
        ).addEdgeChain(
                //Side Path 4
                spaceFactory.createRedSpace(index++, 5, 10), //ID = 66
                spaceFactory.createStarSpace(index++, 8, 10),
                spaceFactory.createBlueSpace(index++, 11, 10),
                spaceFactory.createItemSpace(index, 14, 10) //ID = 69
        );

        board = graphBuilder.build();
        connectPaths();
    }

    @Override
    public void resetBoard() {
        board.getVertexById(12).reset();
        board.getVertexById(20).reset();
        board.getVertexById(22).reset();

        board.getVertexById(55).reset();

        board.getVertexById(33).reset();
        board.getVertexById(59).reset();

        ((WhompsOnTheRun)board.getVertexById(WHOMP1_1)).setActive(true);
        ((WhompsOnTheRun)board.getVertexById(WHOMP1_2)).setActive(false);
        ((WhompsOnTheRun)board.getVertexById(WHOMP2_1)).setActive(true);
        ((WhompsOnTheRun)board.getVertexById(WHOMP2_2)).setActive(false);

        resetRedAndBlueCoinAmounts();
    }

    private void connectPaths() {
        board.addEdge(board.getVertexById(45), board.getVertexById(1));

        //Inner Loop
        board.addEdge(board.getVertexById(5), board.getVertexById(46));
        board.addEdge(board.getVertexById(50), board.getVertexById(43));

        //Side Path 1
        board.addEdge(board.getVertexById(8), board.getVertexById(51));
        board.addEdge(board.getVertexById(55), board.getVertexById(15));

        //Side Path 2
        board.addEdge(board.getVertexById(21), board.getVertexById(56));
        board.addEdge(board.getVertexById(59), board.getVertexById(29));

        //Side Path 3
        board.addEdge(board.getVertexById(31), board.getVertexById(60));
        board.addEdge(board.getVertexById(66), board.getVertexById(59));

        //Side Path 4
        board.addEdge(board.getVertexById(34), board.getVertexById(67));
        board.addEdge(board.getVertexById(70), board.getVertexById(41));
    }

    private int getMoveEventDestinationID() {
        return 11;
    }

    @Override
    public BaseSpace getNextSpace(BaseSpace startingSpace, GameStatTracker gameStatTracker) {
        List<BaseSpace> nextSpaces = new ArrayList<>();

        for (BaseSpace nextSpace : getNextSpaces(startingSpace)) {
            if (!nextSpace.hasToll() || nextSpace.canCross(gameStatTracker, board.getStarCost())) {
                nextSpaces.add(nextSpace);
            }
        }

        if (nextSpaces.size() == 1) {
            return nextSpaces.get(0);
        }

        if (gameStatTracker.getCoinTotal() >= board.getStarCost()) {
            return nextSpaces.stream().min(Comparator.comparing(BaseSpace::getDistanceToStar)).orElse(null);
        }
        return nextSpaces.get(RandomUtils.getRandomInt(nextSpaces.size() - 1));
    }
}
