package boards;

import boards.layout.MPBoard;
import boards.spaces.AllySpace;
import boards.spaces.BlueSpace;
import boards.spaces.NonMovementSpace;
import boards.spaces.OtherSpace;
import boards.spaces.RedSpace;
import boards.spaces.StartSpace;
import boards.spaces.events.BadLuckSpace;
import boards.spaces.events.EventSpace;
import boards.spaces.events.LuckySpace;
import boards.spaces.events.MoveEventSpace;
import boards.spaces.events.SandBridgeCollapse;
import boards.spaces.events.VSSpace;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.builder.GraphBuilder;

public class MegafruitParadise extends BaseBoard {
    private static int I0_DEST = 7;
    private static int I1_DEST = 19;
    private static int I2_DEST = 37;
    private static int I3_DEST = 53;

    public MegafruitParadise() {
        initializeBoard();
    }

    @Override
    protected void initializeBoard() {
        board = new MPBoard<>(DefaultEdge.class);
        graphBuilder = new GraphBuilder<>(new MPBoard<>(DefaultEdge.class));

        buildInitialGraph();
    }

    private void buildInitialGraph() {
        int index = 0;

        graphBuilder.addEdgeChain(
                //Start
                new StartSpace(index++), //ID = 0
                new BlueSpace(index++),
                new BlueSpace(index++),
                new BlueSpace(index++),
                new OtherSpace(index++),
                new BlueSpace(index++),
                new MoveEventSpace(index++, I2_DEST), //ID = 6
                new MoveEventSpace(index++, I2_DEST),
                new BlueSpace(index++),
                new NonMovementSpace(index++), //ID = 9
                new LuckySpace(index++),
                new NonMovementSpace(index++),
                new BlueSpace(index++),
                new RedSpace(index++) //ID = 13
            ).addEdgeChain(
                new NonMovementSpace(index++), //ID = 14
                new OtherSpace(index++),
                new OtherSpace(index++),
                new RedSpace(index++),
                new MoveEventSpace(index++, I3_DEST), //ID = 18
                new MoveEventSpace(index++, I3_DEST),
                new BlueSpace(index++),
                new AllySpace(index++),
                new LuckySpace(index++),
                new BlueSpace(index++),
                new NonMovementSpace(index++),
                new BlueSpace(index++),
                new BlueSpace(index++),
                new LuckySpace(index++) //ID = 27
            ).addEdgeChain(
                new OtherSpace(index++), //ID = 28
                new BadLuckSpace(index++),
                new AllySpace(index++),
                new NonMovementSpace(index++),
                new BlueSpace(index++),
                new BlueSpace(index++),
                new OtherSpace(index++),
                new BlueSpace(index++),
                new MoveEventSpace(index++, I0_DEST), //ID = 36
                new MoveEventSpace(index++, I0_DEST),
                new BlueSpace(index++),
                new RedSpace(index++),
                new NonMovementSpace(index++), //ID = 40
                new MoveEventSpace(index++, 0),
                new BlueSpace(index++),
                new MoveEventSpace(index++, 0),
                new BlueSpace(index++),
                new MoveEventSpace(index++, 0),
                new NonMovementSpace(index++), //ID = 46
                new BlueSpace(index++),
                new EventSpace(index++),
                new OtherSpace(index++),
                new OtherSpace(index++),
                new BlueSpace(index++),
                new MoveEventSpace(index++, I1_DEST), //ID = 52
                new MoveEventSpace(index++, I1_DEST),
                new BadLuckSpace(index++),
                new BlueSpace(index++),
                new OtherSpace(index++),
                new VSSpace(index++),
                new BlueSpace(index++) //ID = 58
        )
                .addVertex(new SandBridgeCollapse(index, 8));

        board = graphBuilder.build();
        connectPaths();
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
