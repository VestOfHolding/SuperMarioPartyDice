package boards;

import boards.spaces.AllySpace;
import boards.spaces.BlueSpace;
import boards.spaces.NonMovementSpace;
import boards.spaces.OtherSpace;
import boards.spaces.RedSpace;
import boards.spaces.StartSpace;
import boards.spaces.events.BadLuckSpace;
import boards.spaces.events.EventSpace;
import boards.spaces.events.KBPM.BobombAllySpace;
import boards.spaces.events.KBPM.KBPMChangePathEvent;
import boards.spaces.events.KBPM.RoyalExplosionEvent;
import boards.spaces.events.LakituSpace;
import boards.spaces.events.LuckySpace;
import boards.spaces.events.MoveEventSpace;
import boards.spaces.events.VSSpace;

public class KingBobombsPowderkegMine extends BaseBoard {
    public KingBobombsPowderkegMine() {
        initializeBoard();
    }

    @Override
    protected void buildInitialGraph() {
        int index = 0;

        graphBuilder.addEdgeChain(
                //Start
                new StartSpace(index++), //ID = 0
                new BlueSpace(index++),
                new RedSpace(index++),
                new BlueSpace(index++), //ID = 3
                new RoyalExplosionEvent(index++),
                new AllySpace(index++),
                new RoyalExplosionEvent(index++),
                new NonMovementSpace(index++), //ID = 7
                new OtherSpace(index++),
                new LuckySpace(index++),
                new BlueSpace(index++),
                new BlueSpace(index++),
                new RedSpace(index++),
                new RoyalExplosionEvent(index++), //ID = 13
                new OtherSpace(index++),
                new BlueSpace(index++),
                new RoyalExplosionEvent(index++),
                new NonMovementSpace(index++), //ID = 17
                new BlueSpace(index++)
        ).addEdgeChain(
                //Upper Loop
                new BlueSpace(index++),
                new NonMovementSpace(index++), //ID = 20
                new OtherSpace(index++),
                new OtherSpace(index++),
                new NonMovementSpace(index++),
                new BlueSpace(index++),
                new MoveEventSpace(index++, 54), //ID = 25
                new BlueSpace(index++),
                new KBPMChangePathEvent(index++), //ID = 27
                new LuckySpace(index++),
                new OtherSpace(index++),
                new LuckySpace(index++),
                new KBPMChangePathEvent(index++), //ID = 31
                new BlueSpace(index++),
                new BlueSpace(index++),
                new BadLuckSpace(index++),
                new LakituSpace(index++),
                new BlueSpace(index++), //ID = 36
                new VSSpace(index++) //ID = 37
        ).addEdgeChain(
                //Upper Loop Side Path
                new BlueSpace(index++), //ID = 38
                new BobombAllySpace(index++),
                new VSSpace(index++) //ID = 40
        ).addEdgeChain(
                //Lower Right
                new BlueSpace(index++), //ID = 41
                new BlueSpace(index++),
                new RedSpace(index++),
                new OtherSpace(index++),
                new BadLuckSpace(index++),
                new BlueSpace(index++),
                new OtherSpace(index++),
                new AllySpace(index++) //ID = 48
        ).addEdgeChain(
                //Lower Left
                new BadLuckSpace(index++), //ID = 49
                new NonMovementSpace(index++), //ID = 50
                new RedSpace(index++),
                new BlueSpace(index++),
                new NonMovementSpace(index++),
                new MoveEventSpace(index++, 25), //ID = 54
                new AllySpace(index++),
                new BlueSpace(index++),
                new VSSpace(index++) //ID = 57
        ).addEdgeChain(
                //Middle Right
                new EventSpace(index++), //ID = 58
                new BlueSpace(index++),
                new EventSpace(index) //ID = 60
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
