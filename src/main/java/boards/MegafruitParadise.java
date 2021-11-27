package boards;

import boards.spaces.events.MFP.PresentBoxSpace;
import boards.spaces.events.MFP.StarSparklerSpace;
import boards.spaces.events.MFP.SandBridgeCollapse;

public class MegafruitParadise extends BaseBoard {
    private static final int I0_DEST = 7;
    private static final int I1_DEST = 19;
    private static final int I2_DEST = 37;
    private static final int I3_DEST = 53;

    public MegafruitParadise() {
        initializeBoard();
        fileOutputName = "MegafruitParadise.txt";
    }

    @Override
    protected void buildInitialGraph() {
        int index = 0;

        graphBuilder.addEdgeChain(
                //Start
                spaceFactory.createStartSpace(index++, 42, 1), //ID = 0
                spaceFactory.createBlueSpace(index++, 42, 4),
                spaceFactory.createBlueSpace(index++, 42, 8),
                spaceFactory.createStarSpace(index++, 41, 12),
                spaceFactory.createItemSpace(index++, 38, 12),
                spaceFactory.createBlueSpace(index++, 36, 12),
                spaceFactory.createMoveEventSpace(index++, I2_DEST, 34, 12), //ID = 6
                spaceFactory.createMoveEventSpace(index++, I2_DEST, 32, 12),
                spaceFactory.createBlueSpace(index++, 30, 10),
                spaceFactory.createNonMovementSpace(index++, 30, 8), //ID = 9
                spaceFactory.createLuckySpace(index++, 30, 4),
                spaceFactory.createNonMovementSpace(index++, 33, 4),
                spaceFactory.createStarSpace(index++, 36, 4),
                spaceFactory.createRedSpace(index++, 39, 4) //ID = 13
            ).addEdgeChain(
                spaceFactory.createNonMovementSpace(index++, 16, 8), //ID = 14
                spaceFactory.createItemSpace(index++, 16, 12),
                spaceFactory.createItemSpace(index++, 14, 12),
                spaceFactory.createRedSpace(index++, 12, 12),
                spaceFactory.createMoveEventSpace(index++, I3_DEST, 9, 12), //ID = 18
                spaceFactory.createMoveEventSpace(index++, I3_DEST, 7, 12),
                spaceFactory.createStarSpace(index++, 4, 12),
                spaceFactory.createAllySpace(index++, 4, 8),
                spaceFactory.createLuckySpace(index++, 4, 4),
                spaceFactory.createBlueSpace(index++, 6, 2),
                spaceFactory.createNonMovementSpace(index++, 9, 2),
                spaceFactory.createBlueSpace(index++, 12, 2),
                spaceFactory.createStarSpace(index++, 14, 2),
                spaceFactory.createLuckySpace(index++, 16, 4) //ID = 27
            ).addEdgeChain(
                spaceFactory.createNonMovementSpace(index++, 10, 24), //ID = 28
                spaceFactory.createItemSpace(index++, 8, 26),
                spaceFactory.createBadLuckSpace(index++, 8, 29),
                spaceFactory.createAllySpace(index++, 8, 32),
                new PresentBoxSpace(index++, 10, 34),
                spaceFactory.createBlueSpace(index++, 6, 34),
                spaceFactory.createStarSpace(index++, 3, 32),
                spaceFactory.createItemSpace(index++, 2, 28),
                spaceFactory.createBlueSpace(index++, 2, 26),
                spaceFactory.createMoveEventSpace(index++, I0_DEST, 2, 23), //ID = 37
                spaceFactory.createMoveEventSpace(index++, I0_DEST, 4, 20),
                spaceFactory.createStarSpace(index++, 7, 20),
                spaceFactory.createRedSpace(index++, 10, 20),
                spaceFactory.createNonMovementSpace(index++, 10, 23), //ID = 41
                spaceFactory.createMoveEventSpace(index++, 0, 16, 23),
                spaceFactory.createBlueSpace(index++, 18, 23),
                spaceFactory.createMoveEventSpace(index++, 0, 20, 23),
                spaceFactory.createBlueSpace(index++, 22, 23),
                spaceFactory.createMoveEventSpace(index++, 0, 24, 23),
                spaceFactory.createNonMovementSpace(index++, 30, 23), //ID = 47
                spaceFactory.createBlueSpace(index++, 32, 22),
                new StarSparklerSpace(index++, 36, 20),
                spaceFactory.createItemSpace(index++, 40, 22),
                spaceFactory.createItemSpace(index++, 42, 24),
                spaceFactory.createStarSpace(index++, 42, 27),
                spaceFactory.createMoveEventSpace(index++, I1_DEST, 42, 31), //ID = 53
                spaceFactory.createMoveEventSpace(index++, I1_DEST, 40, 33),
                spaceFactory.createBadLuckSpace(index++, 38, 34),
                spaceFactory.createStarSpace(index++, 34, 34),
                spaceFactory.createItemSpace(index++, 32, 33),
                spaceFactory.createVSSpace(index++, 30, 30),
                spaceFactory.createBlueSpace(index++, 30, 27),
                spaceFactory.createNonMovementSpace(index++, 30, 24), //ID = 60
                //Effectively adding a second bridge in the other direction to mimic
                // the fact that the bridge is two-way in the game.
                spaceFactory.createMoveEventSpace(index++, 0, 16, 24), //ID = 61
                spaceFactory.createBlueSpace(index++, 18, 24),
                spaceFactory.createMoveEventSpace(index++, 0, 20, 24),
                spaceFactory.createBlueSpace(index++, 22, 24),
                spaceFactory.createMoveEventSpace(index++, 0, 24, 24) //ID = 65
        )
                .addVertex(new SandBridgeCollapse(index, 8, 22, 8));

        board = graphBuilder.build();
        connectPaths();
    }

    @Override
    public void resetBoard() {
        //Reset and repair sand bridge
        board.getVertexById(66).reset();

        board.addEdge(board.getVertexById(9), board.getVertexById(59));
        board.addEdge(board.getVertexById(59), board.getVertexById(14));

        board.getVertexById(49).reset();
        board.getVertexById(58).reset();

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
