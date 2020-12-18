package boards;

import boards.layout.KingBobombsBoard;
import boards.spaces.events.KBPM.BobombAllySpace;
import boards.spaces.events.KBPM.GoldMineGambleEvent;
import boards.spaces.events.KBPM.KBPMChangePathEvent;
import boards.spaces.events.KBPM.RoyalExplosionEvent;
import boards.spaces.events.LakituSpace;

public class KingBobombsPowderkegMine extends BaseBoard {
    public KingBobombsPowderkegMine() {
        initializeBoard(new KingBobombsBoard());
        fileOutputName = "KingBobombsPowderkegMine.txt";
    }

    @Override
    protected void buildInitialGraph() {
        int index = 0;

        graphBuilder.addEdgeChain(
                //Start
                spaceFactory.createStartSpace(index++, 16, 2), //ID = 0
                spaceFactory.createBlueSpace(index++, 16, 8),
                spaceFactory.createRedSpace(index++, 16, 12),
                spaceFactory.createBlueSpace(index++, 16, 16), //ID = 3
                new RoyalExplosionEvent(index++, 18, 16),
                spaceFactory.createAllySpace(index++, 20, 16),
                new RoyalExplosionEvent(index++, 20, 18),
                spaceFactory.createNonMovementSpace(index++, 20, 20), //ID = 7
                spaceFactory.createItemSpace(index++, 20, 22),
                spaceFactory.createLuckySpace(index++, 20, 24),
                spaceFactory.createBlueSpace(index++, 18, 24),
                spaceFactory.createStarSpace(index++, 16, 24),
                spaceFactory.createRedSpace(index++, 14, 24),
                new RoyalExplosionEvent(index++, 12, 24), //ID = 13
                spaceFactory.createItemSpace(index++, 12, 22),
                spaceFactory.createStarSpace(index++, 12, 20),
                new RoyalExplosionEvent(index++, 12, 18),
                spaceFactory.createNonMovementSpace(index++, 12, 16), //ID = 17
                spaceFactory.createBlueSpace(index++, 14, 16)
        ).addEdgeChain(
                //Upper Loop
                spaceFactory.createStarSpace(index++, 24, 20),
                spaceFactory.createNonMovementSpace(index++, 28, 20), //ID = 20
                spaceFactory.createItemSpace(index++, 28, 22),
                spaceFactory.createItemSpace(index++, 28, 24),
                spaceFactory.createNonMovementSpace(index++, 28, 28),
                spaceFactory.createBlueSpace(index++, 28, 31),
                spaceFactory.createMoveEventSpace(index++, 54, 28, 34), //ID = 25
                spaceFactory.createStarSpace(index++, 25, 32),
                new KBPMChangePathEvent(index++, 22, 32), //ID = 27
                spaceFactory.createLuckySpace(index++, 20, 30),
                spaceFactory.createLuckySpace(index++, 12, 30),
                new KBPMChangePathEvent(index++, 10, 32), //ID = 30
                spaceFactory.createBlueSpace(index++, 7, 32),
                spaceFactory.createStarSpace(index++, 4, 32),
                spaceFactory.createBadLuckSpace(index++, 4, 30),
                new LakituSpace(index++, 4, 28),
                spaceFactory.createBlueSpace(index++, 4, 24), //ID = 35
                spaceFactory.createVSSpace(index++, 8, 24) //ID = 36
        ).addEdgeChain(
                //Upper Loop Side Path
                spaceFactory.createBlueSpace(index++, 20, 34), //ID = 37
                new BobombAllySpace(index++, 16, 34),
                spaceFactory.createVSSpace(index++, 12, 34) //ID = 39
        ).addEdgeChain(
                //Lower Right
                spaceFactory.createBlueSpace(index++, 28, 18), //ID = 40
                spaceFactory.createBlueSpace(index++, 28, 16),
                spaceFactory.createRedSpace(index++, 28, 14),
                spaceFactory.createItemSpace(index++, 28, 12),
                spaceFactory.createBadLuckSpace(index++, 28, 8),
                spaceFactory.createStarSpace(index++, 24, 8),
                spaceFactory.createItemSpace(index++, 22, 8),
                spaceFactory.createAllySpace(index++, 19, 8) //ID = 47
        ).addEdgeChain(
                //Lower Left
                spaceFactory.createBadLuckSpace(index++, 8, 16), //ID = 48
                spaceFactory.createNonMovementSpace(index++, 4, 16), //ID = 49
                spaceFactory.createRedSpace(index++, 4, 14),
                spaceFactory.createBlueSpace(index++, 4, 12),
                spaceFactory.createNonMovementSpace(index++, 4, 9),
                spaceFactory.createMoveEventSpace(index++, 25, 4, 6), //ID = 53
                spaceFactory.createAllySpace(index++, 8, 8),
                spaceFactory.createStarSpace(index++, 10, 8),
                spaceFactory.createVSSpace(index++, 13, 8) //ID = 56
        ).addEdgeChain(
                //Middle Left
                new GoldMineGambleEvent(index++, 2, 18), //ID = 57
                spaceFactory.createBlueSpace(index++, 2, 20),
                new GoldMineGambleEvent(index, 2, 22) //ID = 59
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
        ((KingBobombsBoard)board).resetCountdown();

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
