package boards;

import boards.spaces.SpaceFactory;
import boards.spaces.events.ChooseTreasureChestEvent;
import boards.spaces.events.LakituSpace;

public class WhompsDominoRuins extends BaseBoard {

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
                SpaceFactory.createEventSpace(index++),
                SpaceFactory.createNonMovementSpace(index++), //ID = 5
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createNonMovementSpace(index++), //ID = 7
                SpaceFactory.createOtherSpace(index++),
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createBlueSpace(index++), //ID = 10
                SpaceFactory.createMoveEventSpace(index++, getMoveEventDestinationID(), true),
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createAllySpace(index++),
                SpaceFactory.createOtherSpace(index++), //ID = 15
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createOtherSpace(index++),
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createMoveEventSpace(index++, getMoveEventDestinationID(), true),
                SpaceFactory.createNonMovementSpace(index++), //ID = 20
                SpaceFactory.createMoveEventSpace(index++, getMoveEventDestinationID(), true),
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createBlueSpace(index++),
                new LakituSpace(index++),
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createBadLuckSpace(index++),
                SpaceFactory.createOtherSpace(index++), //ID = 27
                SpaceFactory.createEventSpace(index++),
                SpaceFactory.createNonMovementSpace(index++), //ID = 29
                SpaceFactory.createVSSpace(index++),
                SpaceFactory.createNonMovementSpace(index++), //ID = 31
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createNonMovementSpace(index++),
                SpaceFactory.createLuckySpace(index++),
                SpaceFactory.createLuckySpace(index++),
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createAllySpace(index++), //ID = 38
                SpaceFactory.createBadLuckSpace(index++),
                SpaceFactory.createBlueSpace(index++), //ID = 40
                SpaceFactory.createNonMovementSpace(index++),
                SpaceFactory.createBlueSpace(index++)
        ).addEdgeChain(
                //Inner Loop
                SpaceFactory.createEventSpace(index++), //ID = 43
                SpaceFactory.createAllySpace(index++),
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createLuckySpace(index++) //ID = 46
        ).addEdgeChain(
                //Side Path 1
                SpaceFactory.createBlueSpace(index++),  //ID = 47
                SpaceFactory.createNonMovementSpace(index++),
                SpaceFactory.createBlueSpace(index++),
                new ChooseTreasureChestEvent(index++),
                SpaceFactory.createBadLuckSpace(index++) //ID = 51
        ).addEdgeChain(
                //Side Path 2
                SpaceFactory.createRedSpace(index++), //ID = 52
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createVSSpace(index++),
                SpaceFactory.createAllySpace(index++) //ID = 55
        ).addEdgeChain(
                //Side Path 3
                SpaceFactory.createEventSpace(index++), //ID = 56
                SpaceFactory.createOtherSpace(index++),
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createBadLuckSpace(index++),
                SpaceFactory.createBadLuckSpace(index++) //ID = 61
        ).addEdgeChain(
                //Side Path 4
                SpaceFactory.createRedSpace(index++), //ID = 62
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createOtherSpace(index) //ID = 65
        );

        board = graphBuilder.build();
        connectPaths();
    }

    @Override
    public void resetBoard() {
        board.setOrReplaceVertex(11, SpaceFactory.createMoveEventSpace(11, getMoveEventDestinationID(), true));
        board.setOrReplaceVertex(19, SpaceFactory.createMoveEventSpace(19, getMoveEventDestinationID(), true));
        board.setOrReplaceVertex(21, SpaceFactory.createMoveEventSpace(21, getMoveEventDestinationID(), true));

        board.setOrReplaceVertex(50, new ChooseTreasureChestEvent(50));

        board.setOrReplaceVertex(30, SpaceFactory.createVSSpace(30));
        board.setOrReplaceVertex(54, SpaceFactory.createVSSpace(54));
    }

    private void connectPaths() {
        board.addEdge(board.getVertexById(42), board.getVertexById(1));

        board.addEdge(board.getVertexById(5), board.getVertexById(43));
        board.addEdge(board.getVertexById(46), board.getVertexById(40));

        board.addEdge(board.getVertexById(7), board.getVertexById(47));
        board.addEdge(board.getVertexById(51), board.getVertexById(14));

        board.addEdge(board.getVertexById(20), board.getVertexById(52));
        board.addEdge(board.getVertexById(55), board.getVertexById(27));

        board.addEdge(board.getVertexById(20), board.getVertexById(52));
        board.addEdge(board.getVertexById(55), board.getVertexById(27));

        board.addEdge(board.getVertexById(29), board.getVertexById(56));
        board.addEdge(board.getVertexById(61), board.getVertexById(55));

        board.addEdge(board.getVertexById(31), board.getVertexById(62));
        board.addEdge(board.getVertexById(65), board.getVertexById(38));
    }

    private int getMoveEventDestinationID() {
        return 10;
    }
}
