package boards;

import boards.layout.CustomSimpleDirectedGraph;
import boards.spaces.AllySpace;
import boards.spaces.BlueSpace;
import boards.spaces.NonMovementSpace;
import boards.spaces.OtherSpace;
import boards.spaces.RedSpace;
import boards.spaces.StartSpace;
import boards.spaces.events.BadLuckSpace;
import boards.spaces.events.ChooseTreasureChestEvent;
import boards.spaces.events.EventSpace;
import boards.spaces.events.LuckySpace;
import boards.spaces.events.MoveEventSpace;
import boards.spaces.events.VSSpace;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.builder.GraphBuilder;

public class WhompsDominoRuins extends BaseBoard {

    public WhompsDominoRuins() {
        initializeBoard();
    }

    @Override
    protected void initializeBoard() {
        board = new CustomSimpleDirectedGraph<>(DefaultEdge.class);

        graphBuilder = new GraphBuilder<>(new CustomSimpleDirectedGraph<>(DefaultEdge.class));

        buildInitialGraph();
    }

    private void buildInitialGraph() {
        int index = 0;

        graphBuilder.addEdgeChain(
                //Start
                new StartSpace(index++), //ID = 0
                new BlueSpace(index++),
                new RedSpace(index++),
                new BlueSpace(index++),
                new EventSpace(index++),
                new NonMovementSpace(index++), //ID = 5
                new BlueSpace(index++),
                new NonMovementSpace(index++), //ID = 7
                new OtherSpace(index++),
                new BlueSpace(index++),
                new BlueSpace(index++), //ID = 10
                new MoveEventSpace(index++, getMoveEventDestinationID()),
                new BlueSpace(index++),
                new BlueSpace(index++),
                new AllySpace(index++),
                new OtherSpace(index++), //ID = 15
                new BlueSpace(index++),
                new OtherSpace(index++),
                new MoveEventSpace(index++, getMoveEventDestinationID()),
                new NonMovementSpace(index++), //ID = 19
                new MoveEventSpace(index++, getMoveEventDestinationID()),
                new BlueSpace(index++),
                new BlueSpace(index++),
                new NonMovementSpace(index++),
                new BlueSpace(index++),
                new BadLuckSpace(index++),
                new OtherSpace(index++), //ID = 26
                new EventSpace(index++),
                new NonMovementSpace(index++), //ID = 28
                new VSSpace(index++),
                new NonMovementSpace(index++), //ID = 30
                new BlueSpace(index++),
                new NonMovementSpace(index++),
                new LuckySpace(index++),
                new LuckySpace(index++),
                new BlueSpace(index++),
                new BlueSpace(index++),
                new AllySpace(index++), //ID = 37
                new BadLuckSpace(index++),
                new BlueSpace(index++), //ID = 39
                new NonMovementSpace(index++),
                new BlueSpace(index++)
        ).addEdgeChain(
                //Inner Loop
                new EventSpace(index++), //ID = 42
                new AllySpace(index++),
                new BlueSpace(index++),
                new LuckySpace(index++) //ID = 45
        ).addEdgeChain(
                //Side Path 1
                new BlueSpace(index++),  //ID = 46
                new NonMovementSpace(index++),
                new BlueSpace(index++),
                new ChooseTreasureChestEvent(index++),
                new BadLuckSpace(index++) //ID = 50
        ).addEdgeChain(
                //Side Path 2
                new RedSpace(index++), //ID = 51
                new BlueSpace(index++),
                new VSSpace(index++),
                new AllySpace(index++) //ID = 54
        ).addEdgeChain(
                //Side Path 3
                new EventSpace(index++), //ID = 55
                new OtherSpace(index++),
                new BlueSpace(index++),
                new BlueSpace(index++),
                new BadLuckSpace(index++),
                new BadLuckSpace(index++) //ID = 60
        ).addEdgeChain(
                //Side Path 4
                new RedSpace(index++), //ID = 61
                new BlueSpace(index++),
                new BlueSpace(index++),
                new OtherSpace(index) //ID = 64
        );

        board = graphBuilder.build();
        connectPaths();
    }

    public void connectPaths() {
        board.addEdge(board.getVertexById(5), board.getVertexById(42));
        board.addEdge(board.getVertexById(45), board.getVertexById(39));

        board.addEdge(board.getVertexById(7), board.getVertexById(46));
        board.addEdge(board.getVertexById(50), board.getVertexById(14));

        board.addEdge(board.getVertexById(19), board.getVertexById(51));
        board.addEdge(board.getVertexById(54), board.getVertexById(26));

        board.addEdge(board.getVertexById(19), board.getVertexById(51));
        board.addEdge(board.getVertexById(54), board.getVertexById(26));

        board.addEdge(board.getVertexById(28), board.getVertexById(55));
        board.addEdge(board.getVertexById(60), board.getVertexById(54));

        board.addEdge(board.getVertexById(30), board.getVertexById(61));
        board.addEdge(board.getVertexById(64), board.getVertexById(37));
    }

    public int getMoveEventDestinationID() {
        return 10;
    }
}
