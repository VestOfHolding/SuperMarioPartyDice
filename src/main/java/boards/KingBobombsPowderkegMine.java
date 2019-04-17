package boards;

import boards.spaces.SpaceFactory;
import boards.spaces.events.KBPM.BobombAllySpace;
import boards.spaces.events.KBPM.KBPMChangePathEvent;
import boards.spaces.events.KBPM.RoyalExplosionEvent;
import boards.spaces.events.LakituSpace;

public class KingBobombsPowderkegMine extends BaseBoard {
    public KingBobombsPowderkegMine() {
        initializeBoard();
    }

    @Override
    protected void buildInitialGraph() {
        int index = 0;

        graphBuilder.addEdgeChain(
                //Start
                SpaceFactory.createStartSpace(index++, 16, 2), //ID = 0
                SpaceFactory.createBlueSpace(index++, 16, 8),
                SpaceFactory.createRedSpace(index++, 16, 12),
                SpaceFactory.createBlueSpace(index++, 16, 16), //ID = 3
                new RoyalExplosionEvent(index++, 18, 16),
                SpaceFactory.createAllySpace(index++, 20, 16),
                new RoyalExplosionEvent(index++, 20, 18),
                SpaceFactory.createNonMovementSpace(index++, 20, 20), //ID = 7
                SpaceFactory.createItemSpace(index++, 20, 22),
                SpaceFactory.createLuckySpace(index++, 20, 24),
                SpaceFactory.createBlueSpace(index++, 18, 24),
                SpaceFactory.createStarSpace(index++, 16, 24),
                SpaceFactory.createRedSpace(index++, 14, 24),
                new RoyalExplosionEvent(index++, 12, 24), //ID = 13
                SpaceFactory.createItemSpace(index++, 12, 22),
                SpaceFactory.createStarSpace(index++, 12, 20),
                new RoyalExplosionEvent(index++, 12, 18),
                SpaceFactory.createNonMovementSpace(index++, 12, 16), //ID = 17
                SpaceFactory.createBlueSpace(index++, 14, 16)
        ).addEdgeChain(
                //Upper Loop
                SpaceFactory.createStarSpace(index++, 24, 20),
                SpaceFactory.createNonMovementSpace(index++, 28, 20), //ID = 20
                SpaceFactory.createItemSpace(index++, 28, 22),
                SpaceFactory.createItemSpace(index++, 28, 24),
                SpaceFactory.createNonMovementSpace(index++, 28, 28),
                SpaceFactory.createBlueSpace(index++, 28, 31),
                SpaceFactory.createMoveEventSpace(index++, 54, 28, 34), //ID = 25
                SpaceFactory.createStarSpace(index++, 25, 32),
                new KBPMChangePathEvent(index++, 22, 32), //ID = 27
                SpaceFactory.createLuckySpace(index++, 20, 30),
                SpaceFactory.createLuckySpace(index++, 12, 30),
                new KBPMChangePathEvent(index++, 10, 32), //ID = 30
                SpaceFactory.createBlueSpace(index++, 7, 32),
                SpaceFactory.createStarSpace(index++, 4, 32),
                SpaceFactory.createBadLuckSpace(index++, 4, 30),
                new LakituSpace(index++, 4, 28),
                SpaceFactory.createBlueSpace(index++, 4, 24), //ID = 35
                SpaceFactory.createVSSpace(index++, 8, 24) //ID = 36
        ).addEdgeChain(
                //Upper Loop Side Path
                SpaceFactory.createBlueSpace(index++, 20, 34), //ID = 37
                new BobombAllySpace(index++, 16, 34),
                SpaceFactory.createVSSpace(index++, 12, 34) //ID = 39
        ).addEdgeChain(
                //Lower Right
                SpaceFactory.createBlueSpace(index++, 28, 18), //ID = 40
                SpaceFactory.createBlueSpace(index++, 28, 16),
                SpaceFactory.createRedSpace(index++, 28, 14),
                SpaceFactory.createItemSpace(index++, 28, 12),
                SpaceFactory.createBadLuckSpace(index++, 28, 8),
                SpaceFactory.createStarSpace(index++, 24, 8),
                SpaceFactory.createItemSpace(index++, 22, 8),
                SpaceFactory.createAllySpace(index++, 19, 8) //ID = 47
        ).addEdgeChain(
                //Lower Left
                SpaceFactory.createBadLuckSpace(index++, 8, 16), //ID = 48
                SpaceFactory.createNonMovementSpace(index++, 4, 16), //ID = 49
                SpaceFactory.createRedSpace(index++, 4, 14),
                SpaceFactory.createBlueSpace(index++, 4, 12),
                SpaceFactory.createNonMovementSpace(index++, 4, 9),
                SpaceFactory.createMoveEventSpace(index++, 25, 4, 6), //ID = 53
                SpaceFactory.createAllySpace(index++, 8, 8),
                SpaceFactory.createStarSpace(index++, 10, 8),
                SpaceFactory.createVSSpace(index++, 13, 8) //ID = 56
        ).addEdgeChain(
                //Middle Right
                SpaceFactory.createEventSpace(index++, 2, 18), //ID = 57
                SpaceFactory.createBlueSpace(index++, 2, 20),
                SpaceFactory.createEventSpace(index, 2, 22) //ID = 59
        );

        board = graphBuilder.build();
        connectPaths();
    }

    @Override
    public void resetBoard() {
        board.getVertexById(36).reset();
        board.getVertexById(39).reset();
        board.getVertexById(56).reset();

        resetRedAndBlueCoinAmounts();
        board.resetCountdown();

        //Reset the path if needed
        if (board.containsEdge(board.getVertexById(27), board.getVertexById(37))) {
            board.removeEdge(board.getVertexById(27), board.getVertexById(37));
            board.addEdge(board.getVertexById(27), board.getVertexById(28));
        }
    }

    private void connectPaths() {
        board.addEdge(board.getVertexById(18), board.getVertexById(3));

        board.addEdge(board.getVertexById(7), board.getVertexById(19));
        board.addEdge(board.getVertexById(36), board.getVertexById(13));

        board.addEdge(board.getVertexById(20), board.getVertexById(40));
        board.addEdge(board.getVertexById(47), board.getVertexById(1));

        board.addEdge(board.getVertexById(17), board.getVertexById(48));
        board.addEdge(board.getVertexById(56), board.getVertexById(1));

        board.addEdge(board.getVertexById(49), board.getVertexById(57));
        board.addEdge(board.getVertexById(59), board.getVertexById(35));

        board.addEdge(board.getVertexById(39), board.getVertexById(30));
    }
}
