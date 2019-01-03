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
                SpaceFactory.createStartSpace(index++), //ID = 0
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createRedSpace(index++),
                SpaceFactory.createBlueSpace(index++), //ID = 3
                new RoyalExplosionEvent(index++),
                SpaceFactory.createAllySpace(index++),
                new RoyalExplosionEvent(index++),
                SpaceFactory.createNonMovementSpace(index++), //ID = 7
                SpaceFactory.createOtherSpace(index++),
                SpaceFactory.createLuckySpace(index++),
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createRedSpace(index++),
                new RoyalExplosionEvent(index++), //ID = 13
                SpaceFactory.createOtherSpace(index++),
                SpaceFactory.createBlueSpace(index++),
                new RoyalExplosionEvent(index++),
                SpaceFactory.createNonMovementSpace(index++), //ID = 17
                SpaceFactory.createBlueSpace(index++)
        ).addEdgeChain(
                //Upper Loop
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createNonMovementSpace(index++), //ID = 20
                SpaceFactory.createOtherSpace(index++),
                SpaceFactory.createOtherSpace(index++),
                SpaceFactory.createNonMovementSpace(index++),
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createMoveEventSpace(index++, 54), //ID = 25
                SpaceFactory.createBlueSpace(index++),
                new KBPMChangePathEvent(index++), //ID = 27
                SpaceFactory.createLuckySpace(index++),
                SpaceFactory.createOtherSpace(index++),
                SpaceFactory.createLuckySpace(index++),
                new KBPMChangePathEvent(index++), //ID = 31
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createBadLuckSpace(index++),
                new LakituSpace(index++),
                SpaceFactory.createBlueSpace(index++), //ID = 36
                SpaceFactory.createVSSpace(index++) //ID = 37
        ).addEdgeChain(
                //Upper Loop Side Path
                SpaceFactory.createBlueSpace(index++), //ID = 38
                new BobombAllySpace(index++),
                SpaceFactory.createVSSpace(index++) //ID = 40
        ).addEdgeChain(
                //Lower Right
                SpaceFactory.createBlueSpace(index++), //ID = 41
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createRedSpace(index++),
                SpaceFactory.createOtherSpace(index++),
                SpaceFactory.createBadLuckSpace(index++),
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createOtherSpace(index++),
                SpaceFactory.createAllySpace(index++) //ID = 48
        ).addEdgeChain(
                //Lower Left
                SpaceFactory.createBadLuckSpace(index++), //ID = 49
                SpaceFactory.createNonMovementSpace(index++), //ID = 50
                SpaceFactory.createRedSpace(index++),
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createNonMovementSpace(index++),
                SpaceFactory.createMoveEventSpace(index++, 25), //ID = 54
                SpaceFactory.createAllySpace(index++),
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createVSSpace(index++) //ID = 57
        ).addEdgeChain(
                //Middle Right
                SpaceFactory.createEventSpace(index++), //ID = 58
                SpaceFactory.createBlueSpace(index++),
                SpaceFactory.createEventSpace(index) //ID = 60
        );

        board = graphBuilder.build();
        connectPaths();
    }

    private void connectPaths() {
        board.addEdge(board.getVertexById(18), board.getVertexById(3));

        board.addEdge(board.getVertexById(7), board.getVertexById(19));
        board.addEdge(board.getVertexById(37), board.getVertexById(13));

        board.addEdge(board.getVertexById(20), board.getVertexById(41));
        board.addEdge(board.getVertexById(48), board.getVertexById(1));

        board.addEdge(board.getVertexById(17), board.getVertexById(49));
        board.addEdge(board.getVertexById(57), board.getVertexById(1));

        board.addEdge(board.getVertexById(50), board.getVertexById(58));
        board.addEdge(board.getVertexById(60), board.getVertexById(36));

        board.addEdge(board.getVertexById(40), board.getVertexById(31));
    }
}
