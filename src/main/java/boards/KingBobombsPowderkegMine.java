package boards;

import boards.layout.CustomSimpleDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class KingBobombsPowderkegMine extends BaseBoard {
    public KingBobombsPowderkegMine() {
        initializeBoard();
    }

    @Override
    protected void initializeBoard() {
        board = new CustomSimpleDirectedGraph<>(DefaultEdge.class);

        addStartAndInnerLoop();
        addUpperOuterLoop();
    }

    private void addStartAndInnerLoop() {
        int index = 0;

        //Start
//        gameBoard.addNode(new StartSpace(index++));//ID = 0
//
//        gameBoard.addNode(new BlueSpace(index++));
//        gameBoard.addNode(new RedSpace(index++));
//
//        gameBoard.addNode(new BlueSpace(index++));
//        gameBoard.addNode(new EventSpace(index++));
//        gameBoard.addNode(new AllySpace(index++));
//        gameBoard.addNode(new EventSpace(index++));
//        gameBoard.addNode(new NonMovementSpace(index++));//ID = 8
//
//        gameBoard.addNode(new OtherSpace(index++));
//        gameBoard.addNode(new LuckySpace(index++));
//        gameBoard.addNode(new BlueSpace(index++));
//        gameBoard.addNode(new BlueSpace(index++));
//        gameBoard.addNode(new RedSpace(index++));
//        gameBoard.addNode(new EventSpace(index++));//ID = 14
//
//        gameBoard.addNode(new OtherSpace(index++));
//        gameBoard.addNode(new BlueSpace(index++));
//        gameBoard.addNode(new EventSpace(index++));
//        gameBoard.addNode(new NonMovementSpace(index));//ID = 18
//
//        //Edges
//        for (int i = 0; i < 18; ++i) {
//            gameBoard.addEdge(gameBoard.getNode(i), gameBoard.getNode(i+1));
//        }
//
//        gameBoard.addEdge(gameBoard.getNode(18), gameBoard.getNode(3));
    }

    private void addUpperOuterLoop() {
        int index = 19;
//
//        gameBoard.addNode(new BlueSpace(index++));//ID = 19
//        gameBoard.addNode(new NonMovementSpace(index++));
//
//        gameBoard.addNode(new OtherSpace(index++));//ID = 21
//        gameBoard.addNode(new OtherSpace(index++));
//        gameBoard.addNode(new NonMovementSpace(index++));
//        gameBoard.addNode(new BlueSpace(index++));
//        gameBoard.addNode(new MoveEventSpace(index++, -1, false));
//        gameBoard.addNode(new BlueSpace(index++));
//        gameBoard.addNode(new EventSpace(index++));//ID = 27
//
//        gameBoard.addNode(new LuckySpace(index++));//ID = 28
//        gameBoard.addNode(new OtherSpace(index++));
//        gameBoard.addNode(new LuckySpace(index++));
//
//        gameBoard.addNode(new BlueSpace(index++));//ID = 31
//        gameBoard.addNode(new NonMovementSpace(index++));
//        gameBoard.addNode(new VSSpace(index++));
//
//        gameBoard.addNode(new EventSpace(index++));//ID = 34
//        gameBoard.addNode(new BlueSpace(index++));
//        gameBoard.addNode(new BlueSpace(index++));
//        gameBoard.addNode(new BadLuckSpace(index++));
//        gameBoard.addNode(new NonMovementSpace(index++));
//        gameBoard.addNode(new BlueSpace(index++));
//        gameBoard.addNode(new VSSpace(index));//ID = 40
//
//        //Edges
//        gameBoard.addEdge(gameBoard.getNode(8), gameBoard.getNode(19));
//
//        for (int i = 19; i < 30; ++i) {
//            gameBoard.addEdge(gameBoard.getNode(i), gameBoard.getNode(i+1));
//        }
//
//        //These are initially not attached.
//        gameBoard.addEdge(gameBoard.getNode(31), gameBoard.getNode(32));
//        gameBoard.addEdge(gameBoard.getNode(32), gameBoard.getNode(33));
//
//        gameBoard.addEdge(gameBoard.getNode(30), gameBoard.getNode(34));
//
//        for (int i = 34; i < 40; ++i) {
//            gameBoard.addEdge(gameBoard.getNode(i), gameBoard.getNode(i+1));
//        }
//
//        gameBoard.addEdge(gameBoard.getNode(40), gameBoard.getNode(14));
    }
}
