package boards;

import boards.spaces.BaseSpace;
import boards.spaces.SpaceFactory;
import boards.spaces.events.EventSpace;
import boards.spaces.events.LakituSpace;
import boards.spaces.events.WDR.ChooseTreasureChestEvent;
import boards.spaces.events.WDR.WhompSwitch;
import boards.spaces.events.WDR.WhompsOnTheRun;
import stattracker.GameStatTracker;

public class WhompsDominoRuins extends BaseBoard {

    private static final int WHOMP1_1 = 6;
    private static final int WHOMP1_2 = 46;
    private static final int WHOMP2_1 = 32;
    private static final int WHOMP2_2 = 60;

    public WhompsDominoRuins() {
        initializeBoard();
    }

    @Override
    protected void buildInitialGraph() {
        int index = 0;

        graphBuilder.addEdgeChain(
                //Start
                SpaceFactory.createStartSpace(index++, 28, 1), //ID = 0
                SpaceFactory.createBlueSpace(index++, 28, 6),
                SpaceFactory.createRedSpace(index++, 30, 8),
                SpaceFactory.createBlueSpace(index++, 30, 11),
                new WhompSwitch(index++, WHOMP1_1, WHOMP1_2, 28, 13),
                SpaceFactory.createNonMovementSpace(index++, 28, 16), //ID = 5
                new WhompsOnTheRun(index++, WHOMP1_2, false, 28, 18),
                SpaceFactory.createBlueSpace(index++, 28, 20),
                SpaceFactory.createNonMovementSpace(index++, 28, 22), //ID = 8
                SpaceFactory.createItemSpace(index++, 28, 25),
                SpaceFactory.createBlueSpace(index++, 28, 28),
                SpaceFactory.createBlueSpace(index++, 28, 30), //ID = 11
                SpaceFactory.createMoveEventSpace(index++, getMoveEventDestinationID(), true, 28, 32),
                SpaceFactory.createBlueSpace(index++, 25, 32),
                SpaceFactory.createBlueSpace(index++, 23, 32),
                SpaceFactory.createAllySpace(index++, 20, 32), //ID = 15
                SpaceFactory.createItemSpace(index++, 18, 32),
                SpaceFactory.createBlueSpace(index++, 15, 32),
                SpaceFactory.createItemSpace(index++, 13, 32),
                SpaceFactory.createBlueSpace(index++, 11, 32),
                SpaceFactory.createMoveEventSpace(index++, getMoveEventDestinationID(), true, 8, 32),
                SpaceFactory.createNonMovementSpace(index++, 6, 32), //ID = 21
                SpaceFactory.createMoveEventSpace(index++, getMoveEventDestinationID(), true, 4, 32),
                SpaceFactory.createBlueSpace(index++, 2, 32),
                SpaceFactory.createBlueSpace(index++, 2, 30),
                SpaceFactory.createBlueSpace(index++, 2, 28),
                new LakituSpace(index++, 2, 26),
                SpaceFactory.createBlueSpace(index++, 2, 24),
                SpaceFactory.createBadLuckSpace(index++, 2, 22),
                SpaceFactory.createItemSpace(index++, 2, 20), //ID = 28
                new WhompSwitch(index++, WHOMP2_1, WHOMP2_2, 2, 18),
                SpaceFactory.createNonMovementSpace(index++, 2, 16), //ID = 30
                new WhompsOnTheRun(index++, WHOMP2_2, false, 2, 14),
                SpaceFactory.createVSSpace(index++, 2, 12),
                SpaceFactory.createNonMovementSpace(index++, 2, 10), //ID = 33
                SpaceFactory.createBlueSpace(index++, 2, 7),
                SpaceFactory.createNonMovementSpace(index++, 2, 4),
                SpaceFactory.createLuckySpace(index++, 2, 2),
                SpaceFactory.createLuckySpace(index++, 6, 2),
                SpaceFactory.createBlueSpace(index++, 10, 2),
                SpaceFactory.createBlueSpace(index++, 14, 2),
                SpaceFactory.createAllySpace(index++, 14, 6), //ID = 40
                SpaceFactory.createBadLuckSpace(index++, 17, 6),
                SpaceFactory.createBlueSpace(index++, 20, 6), //ID = 42
                SpaceFactory.createNonMovementSpace(index++, 22, 6),
                SpaceFactory.createBlueSpace(index++, 25, 6) //ID = 44
        ).addEdgeChain(
                //Inner Loop
                new WhompsOnTheRun(index++, WHOMP1_1, true, 26, 16), //ID = 45
                new WhompSwitch(index++, WHOMP1_1, WHOMP1_2, 23, 16),
                SpaceFactory.createAllySpace(index++, 20, 16),
                SpaceFactory.createBlueSpace(index++, 20, 13),
                SpaceFactory.createLuckySpace(index++, 20, 10) //ID = 49
        ).addEdgeChain(
                //Side Path 1
                SpaceFactory.createBlueSpace(index++, 25, 22),  //ID = 50
                SpaceFactory.createNonMovementSpace(index++, 23, 22),
                SpaceFactory.createBlueSpace(index++, 20, 22),
                new ChooseTreasureChestEvent(index++, 20, 26),
                SpaceFactory.createBadLuckSpace(index++, 20, 30) //ID = 54
        ).addEdgeChain(
                //Side Path 2
                SpaceFactory.createRedSpace(index++, 6, 30), //ID = 55
                SpaceFactory.createBlueSpace(index++, 6, 27),
                SpaceFactory.createVSSpace(index++, 6, 23),
                SpaceFactory.createAllySpace(index++, 6, 20) //ID = 58
        ).addEdgeChain(
                //Side Path 3
                new WhompsOnTheRun(index++, WHOMP2_1, true, 4, 16),  //ID = 59
                new WhompSwitch(index++, WHOMP2_1, WHOMP2_2, 6, 16),
                SpaceFactory.createItemSpace(index++, 9, 16),
                SpaceFactory.createBlueSpace(index++, 12, 16),
                SpaceFactory.createBlueSpace(index++, 12, 20),
                SpaceFactory.createBadLuckSpace(index++, 10, 20),
                SpaceFactory.createBadLuckSpace(index++, 8, 20) //ID = 65
        ).addEdgeChain(
                //Side Path 4
                SpaceFactory.createRedSpace(index++, 5, 10), //ID = 66
                SpaceFactory.createBlueSpace(index++, 8, 10),
                SpaceFactory.createBlueSpace(index++, 11, 10),
                SpaceFactory.createItemSpace(index, 14, 10) //ID = 69
        );

        board = graphBuilder.build();
        connectPaths();
    }

    @Override
    public void resetBoard() {
        board.setOrReplaceVertex(12, SpaceFactory.createMoveEventSpace(12, getMoveEventDestinationID(), true));
        board.setOrReplaceVertex(20, SpaceFactory.createMoveEventSpace(20, getMoveEventDestinationID(), true));
        board.setOrReplaceVertex(22, SpaceFactory.createMoveEventSpace(22, getMoveEventDestinationID(), true));

        board.setOrReplaceVertex(55, new ChooseTreasureChestEvent(55));

        board.setOrReplaceVertex(33, SpaceFactory.createVSSpace(33));
        board.setOrReplaceVertex(59, SpaceFactory.createVSSpace(59));

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
    public BaseSpace getDestination(BaseSpace currentSpace, int distance, GameStatTracker gameStatTracker) {

        for (int i = 0; i < distance; ++i) {
            currentSpace = getNextSpace(currentSpace, gameStatTracker);

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
}
