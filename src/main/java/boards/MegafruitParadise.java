package boards;

import boards.spaces.SpaceFactory;
import boards.spaces.events.MFP.StarSparklerSpace;
import boards.spaces.events.SandBridgeCollapse;

public class MegafruitParadise extends BaseBoard {
    private static int I0_DEST = 7;
    private static int I1_DEST = 19;
    private static int I2_DEST = 37;
    private static int I3_DEST = 53;

    public MegafruitParadise() {
        initializeBoard();
    }

    @Override
    protected void buildInitialGraph() {
        int index = 0;

        graphBuilder.addEdgeChain(
                //Start
                SpaceFactory.createStartSpace(index++, 42, 1), //ID = 0
                SpaceFactory.createBlueSpace(index++, 42, 4),
                SpaceFactory.createBlueSpace(index++, 42, 8),
                SpaceFactory.createStarSpace(index++, 41, 12),
                SpaceFactory.createItemSpace(index++, 38, 12),
                SpaceFactory.createBlueSpace(index++, 36, 12),
                SpaceFactory.createMoveEventSpace(index++, I2_DEST, 34, 12), //ID = 6
                SpaceFactory.createMoveEventSpace(index++, I2_DEST, 32, 12),
                SpaceFactory.createBlueSpace(index++, 30, 10),
                SpaceFactory.createNonMovementSpace(index++, 30, 8), //ID = 9
                SpaceFactory.createLuckySpace(index++, 30, 4),
                SpaceFactory.createNonMovementSpace(index++, 33, 4),
                SpaceFactory.createStarSpace(index++, 36, 4),
                SpaceFactory.createRedSpace(index++, 39, 4) //ID = 13
            ).addEdgeChain(
                SpaceFactory.createNonMovementSpace(index++, 16, 8), //ID = 14
                SpaceFactory.createItemSpace(index++, 16, 12),
                SpaceFactory.createItemSpace(index++, 14, 12),
                SpaceFactory.createRedSpace(index++, 12, 12),
                SpaceFactory.createMoveEventSpace(index++, I3_DEST, 9, 12), //ID = 18
                SpaceFactory.createMoveEventSpace(index++, I3_DEST, 7, 12),
                SpaceFactory.createStarSpace(index++, 4, 12),
                SpaceFactory.createAllySpace(index++, 4, 8),
                SpaceFactory.createLuckySpace(index++, 4, 4),
                SpaceFactory.createBlueSpace(index++, 6, 2),
                SpaceFactory.createNonMovementSpace(index++, 9, 2),
                SpaceFactory.createBlueSpace(index++, 12, 2),
                SpaceFactory.createStarSpace(index++, 14, 2),
                SpaceFactory.createLuckySpace(index++, 16, 4) //ID = 27
            ).addEdgeChain(
                SpaceFactory.createNonMovementSpace(index++, 10, 24), //ID = 28
                SpaceFactory.createItemSpace(index++, 8, 26),
                SpaceFactory.createBadLuckSpace(index++, 8, 29),
                SpaceFactory.createAllySpace(index++, 8, 32),
                SpaceFactory.createNonMovementSpace(index++, 10, 34),
                SpaceFactory.createBlueSpace(index++, 6, 34),
                SpaceFactory.createStarSpace(index++, 3, 32),
                SpaceFactory.createItemSpace(index++, 2, 28),
                SpaceFactory.createBlueSpace(index++, 2, 26),
                SpaceFactory.createMoveEventSpace(index++, I0_DEST, 2, 23), //ID = 37
                SpaceFactory.createMoveEventSpace(index++, I0_DEST, 4, 20),
                SpaceFactory.createStarSpace(index++, 7, 20),
                SpaceFactory.createRedSpace(index++, 10, 20),
                SpaceFactory.createNonMovementSpace(index++, 10, 23), //ID = 41
                SpaceFactory.createMoveEventSpace(index++, 0, 16, 23),
                SpaceFactory.createBlueSpace(index++, 18, 23),
                SpaceFactory.createMoveEventSpace(index++, 0, 20, 23),
                SpaceFactory.createBlueSpace(index++, 22, 23),
                SpaceFactory.createMoveEventSpace(index++, 0, 24, 23),
                SpaceFactory.createNonMovementSpace(index++, 30, 23), //ID = 47
                SpaceFactory.createBlueSpace(index++, 32, 22),
                new StarSparklerSpace(index++, 36, 20),
                SpaceFactory.createItemSpace(index++, 40, 22),
                SpaceFactory.createItemSpace(index++, 42, 24),
                SpaceFactory.createStarSpace(index++, 42, 27),
                SpaceFactory.createMoveEventSpace(index++, I1_DEST, 42, 31), //ID = 53
                SpaceFactory.createMoveEventSpace(index++, I1_DEST, 40, 33),
                SpaceFactory.createBadLuckSpace(index++, 38, 34),
                SpaceFactory.createStarSpace(index++, 34, 34),
                SpaceFactory.createItemSpace(index++, 32, 33),
                SpaceFactory.createVSSpace(index++, 30, 30),
                SpaceFactory.createBlueSpace(index++, 30, 27),
                SpaceFactory.createNonMovementSpace(index++, 30, 24), //ID = 60
                //Effectively adding a second bridge in the other direction to mimic
                // the fact that the bridge is two-way in the game.
                SpaceFactory.createMoveEventSpace(index++, 0, 16, 24), //ID = 61
                SpaceFactory.createBlueSpace(index++, 18, 24),
                SpaceFactory.createMoveEventSpace(index++, 0, 20, 24),
                SpaceFactory.createBlueSpace(index++, 22, 24),
                SpaceFactory.createMoveEventSpace(index++, 0, 24, 24) //ID = 65
        )
                .addVertex(new SandBridgeCollapse(index, 8, 22, 8));

        board = graphBuilder.build();
        connectPaths();
    }

    @Override
    public void resetBoard() {
        board.setOrReplaceVertex(66, new SandBridgeCollapse(66, 8));

        board.setOrReplaceVertex(58, SpaceFactory.createVSSpace(58));

        resetRedAndBlueCoinAmounts();
    }

    private void connectPaths() {
        //Sand bridge
        board.addEdge(board.getVertexById(9), board.getVertexById(66));
        board.addEdge(board.getVertexById(66), board.getVertexById(14));

        //Island loops
        board.addEdge(board.getVertexById(13), board.getVertexById(1));
        board.addEdge(board.getVertexById(27), board.getVertexById(14));
        board.addEdge(board.getVertexById(41), board.getVertexById(28));
        board.addEdge(board.getVertexById(60), board.getVertexById(47));

        board.addEdge(board.getVertexById(65), board.getVertexById(28));
    }
}
