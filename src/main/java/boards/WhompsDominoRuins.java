package boards;

import boards.spaces.BaseSpace;
import boards.spaces.SpaceFactory;
import boards.spaces.events.EventSpace;
import boards.spaces.events.LakituSpace;
import boards.spaces.events.SandBridgeCollapse;
import boards.spaces.events.WDR.ChooseTreasureChestEvent;
import boards.spaces.events.WDR.WhompSwitch;
import boards.spaces.events.WDR.WhompsOnTheRun;
import stattracker.GameStatTracker;

public class WhompsDominoRuins extends BaseBoard {

    private static final int WHOMP1_1 = 6;
    private static final int WHOMP1_2 = 45;
    private static final int WHOMP2_1 = 31;
    private static final int WHOMP2_2 = 59;

    public WhompsDominoRuins() {
        initializeBoard();
    }

    @Override
    protected void buildInitialGraph() {
        int index = 0;

        graphBuilder.addEdgeChain(
                //Start
                SpaceFactory.createStartSpace(index++), //ID = 0
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createRedSpace(index++),
                SpaceFactory.createBlueSpace(index++),
                new WhompSwitch(index++, WHOMP1_1, WHOMP1_2),
                SpaceFactory.createNonMovementSpace(index++), //ID = 5
                new WhompsOnTheRun(index++, WHOMP1_2, false),
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createNonMovementSpace(index++), //ID = 8
                SpaceFactory.createOtherSpace(index++),
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createBlueSpace(index++), //ID = 11
                SpaceFactory.createMoveEventSpace(index++, getMoveEventDestinationID(), true),
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createAllySpace(index++), //ID = 15
                SpaceFactory.createOtherSpace(index++),
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createOtherSpace(index++),
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createMoveEventSpace(index++, getMoveEventDestinationID(), true),
                SpaceFactory.createNonMovementSpace(index++), //ID = 21
                SpaceFactory.createMoveEventSpace(index++, getMoveEventDestinationID(), true),
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createBlueSpace(index++),
                new LakituSpace(index++),
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createBadLuckSpace(index++),
                SpaceFactory.createOtherSpace(index++), //ID = 28
                new WhompSwitch(index++, WHOMP2_1, WHOMP2_2),
                SpaceFactory.createNonMovementSpace(index++), //ID = 30
                new WhompsOnTheRun(index++, WHOMP2_2, false),
                SpaceFactory.createVSSpace(index++),
                SpaceFactory.createNonMovementSpace(index++), //ID = 33
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createNonMovementSpace(index++),
                SpaceFactory.createLuckySpace(index++),
                SpaceFactory.createLuckySpace(index++),
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createAllySpace(index++), //ID = 40
                SpaceFactory.createBadLuckSpace(index++),
                SpaceFactory.createBlueSpace(index++), //ID = 42
                SpaceFactory.createNonMovementSpace(index++),
                SpaceFactory.createBlueSpace(index++) //ID = 44
        ).addEdgeChain(
                //Inner Loop
                new WhompsOnTheRun(index++, WHOMP1_1, true), //ID = 45
                new WhompSwitch(index++, WHOMP1_1, WHOMP1_2),
                SpaceFactory.createAllySpace(index++),
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createLuckySpace(index++) //ID = 49
        ).addEdgeChain(
                //Side Path 1
                SpaceFactory.createBlueSpace(index++),  //ID = 50
                SpaceFactory.createNonMovementSpace(index++),
                SpaceFactory.createBlueSpace(index++),
                new ChooseTreasureChestEvent(index++),
                SpaceFactory.createBadLuckSpace(index++) //ID = 54
        ).addEdgeChain(
                //Side Path 2
                SpaceFactory.createRedSpace(index++), //ID = 55
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createVSSpace(index++),
                SpaceFactory.createAllySpace(index++) //ID = 58
        ).addEdgeChain(
                //Side Path 3
                new WhompsOnTheRun(index++, WHOMP2_1, true),  //ID = 59
                new WhompSwitch(index++, WHOMP2_1, WHOMP2_2),
                SpaceFactory.createOtherSpace(index++),
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createBadLuckSpace(index++),
                SpaceFactory.createBadLuckSpace(index++) //ID = 65
        ).addEdgeChain(
                //Side Path 4
                SpaceFactory.createRedSpace(index++), //ID = 66
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createOtherSpace(index) //ID = 69
        );

        board = graphBuilder.build();
        connectPaths();
    }

    @Override
    public void resetBoard() {
        board.setOrReplaceVertex(12, SpaceFactory.createMoveEventSpace(12, getMoveEventDestinationID(), true));
        board.setOrReplaceVertex(20, SpaceFactory.createMoveEventSpace(20, getMoveEventDestinationID(), true));
        board.setOrReplaceVertex(22, SpaceFactory.createMoveEventSpace(22, getMoveEventDestinationID(), true));

        board.setOrReplaceVertex(54, new ChooseTreasureChestEvent(54));

        board.setOrReplaceVertex(32, SpaceFactory.createVSSpace(32));
        board.setOrReplaceVertex(58, SpaceFactory.createVSSpace(58));

        ((WhompsOnTheRun)board.getVertexById(WHOMP1_1)).setActive(true);
        ((WhompsOnTheRun)board.getVertexById(WHOMP1_2)).setActive(false);
        ((WhompsOnTheRun)board.getVertexById(WHOMP2_1)).setActive(true);
        ((WhompsOnTheRun)board.getVertexById(WHOMP2_2)).setActive(false);

        resetRedAndBlueCoinAmounts();
    }

    private void connectPaths() {
        board.addEdge(board.getVertexById(44), board.getVertexById(1));

        //Inner Loop
        board.addEdge(board.getVertexById(5), board.getVertexById(45));
        board.addEdge(board.getVertexById(49), board.getVertexById(42));

        //Side Path 1
        board.addEdge(board.getVertexById(8), board.getVertexById(50));
        board.addEdge(board.getVertexById(54), board.getVertexById(15));

        //Side Path 2
        board.addEdge(board.getVertexById(21), board.getVertexById(55));
        board.addEdge(board.getVertexById(58), board.getVertexById(28));

        //Side Path 3
        board.addEdge(board.getVertexById(30), board.getVertexById(59));
        board.addEdge(board.getVertexById(65), board.getVertexById(58));

        //Side Path 4
        board.addEdge(board.getVertexById(33), board.getVertexById(66));
        board.addEdge(board.getVertexById(69), board.getVertexById(40));
    }

    private int getMoveEventDestinationID() {
        return 11;
    }

    @Override
    public BaseSpace getDestination(BaseSpace currentSpace, int distance, GameStatTracker gameStatTracker) {

        for (int i = 0; i < distance; ++i) {
            currentSpace = getNextSpace(currentSpace, gameStatTracker);

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
}
