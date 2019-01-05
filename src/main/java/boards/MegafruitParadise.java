package boards;

import boards.spaces.SpaceFactory;
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
                SpaceFactory.createStartSpace(index++), //ID = 0
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createOtherSpace(index++),
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createMoveEventSpace(index++, I2_DEST), //ID = 6
                SpaceFactory.createMoveEventSpace(index++, I2_DEST),
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createNonMovementSpace(index++), //ID = 9
                SpaceFactory.createLuckySpace(index++),
                SpaceFactory.createNonMovementSpace(index++),
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createRedSpace(index++) //ID = 13
            ).addEdgeChain(
                SpaceFactory.createNonMovementSpace(index++), //ID = 14
                SpaceFactory.createOtherSpace(index++),
                SpaceFactory.createOtherSpace(index++),
                SpaceFactory.createRedSpace(index++),
                SpaceFactory.createMoveEventSpace(index++, I3_DEST), //ID = 18
                SpaceFactory.createMoveEventSpace(index++, I3_DEST),
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createAllySpace(index++),
                SpaceFactory.createLuckySpace(index++),
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createNonMovementSpace(index++),
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createLuckySpace(index++) //ID = 27
            ).addEdgeChain(
                SpaceFactory.createOtherSpace(index++), //ID = 28
                SpaceFactory.createBadLuckSpace(index++),
                SpaceFactory.createAllySpace(index++),
                SpaceFactory.createNonMovementSpace(index++),
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createOtherSpace(index++),
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createMoveEventSpace(index++, I0_DEST), //ID = 36
                SpaceFactory.createMoveEventSpace(index++, I0_DEST),
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createRedSpace(index++),
                SpaceFactory.createNonMovementSpace(index++), //ID = 40
                SpaceFactory.createMoveEventSpace(index++, 0),
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createMoveEventSpace(index++, 0),
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createMoveEventSpace(index++, 0),
                SpaceFactory.createNonMovementSpace(index++), //ID = 46
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createEventSpace(index++),
                SpaceFactory.createOtherSpace(index++),
                SpaceFactory.createOtherSpace(index++),
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createMoveEventSpace(index++, I1_DEST), //ID = 52
                SpaceFactory.createMoveEventSpace(index++, I1_DEST),
                SpaceFactory.createBadLuckSpace(index++),
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createOtherSpace(index++),
                SpaceFactory.createVSSpace(index++),
                SpaceFactory.createBlueSpace(index++) //ID = 58
        )
                .addVertex(new SandBridgeCollapse(index, 8));

        board = graphBuilder.build();
        connectPaths();
    }

    @Override
    public void resetBoard() {
        board.setOrReplaceVertex(59, new SandBridgeCollapse(59, 8));

        board.setOrReplaceVertex(57, SpaceFactory.createVSSpace(57));

        resetRedAndBlueCoinAmounts();
    }

    private void connectPaths() {
        //Sand bridge
        board.addEdge(board.getVertexById(9), board.getVertexById(59));
        board.addEdge(board.getVertexById(59), board.getVertexById(14));

        //Island loops
        board.addEdge(board.getVertexById(13), board.getVertexById(1));
        board.addEdge(board.getVertexById(27), board.getVertexById(14));
        board.addEdge(board.getVertexById(40), board.getVertexById(28));
        board.addEdge(board.getVertexById(58), board.getVertexById(46));
    }
}
