package boards;

import boards.layout.Board;
import boards.spaces.AllySpace;
import boards.spaces.BlueSpace;
import boards.spaces.NonMovementSpace;
import boards.spaces.OtherSpace;
import boards.spaces.RedSpace;
import boards.spaces.StartSpace;
import boards.spaces.events.EventSpace;
import boards.spaces.events.LuckySpace;

public class KingBobombsPowderkegMine extends BaseBoard {
    public KingBobombsPowderkegMine() {
        initializeBoard();
    }

    @Override
    protected void initializeBoard() {
        gameBoard = new Board();

        addStartAndInnerLoop();
    }

    private void addStartAndInnerLoop() {
        int index = 0;

        //Start
        gameBoard.addNode(new StartSpace(index++));//ID = 0

        gameBoard.addNode(new BlueSpace(index++));
        gameBoard.addNode(new RedSpace(index++));

        gameBoard.addNode(new BlueSpace(index++));
        gameBoard.addNode(new EventSpace(index++));
        gameBoard.addNode(new AllySpace(index++));
        gameBoard.addNode(new EventSpace(index++));
        gameBoard.addNode(new NonMovementSpace(index++));//ID = 8

        gameBoard.addNode(new OtherSpace(index++));
        gameBoard.addNode(new LuckySpace(index++));
        gameBoard.addNode(new BlueSpace(index++));
        gameBoard.addNode(new BlueSpace(index++));
        gameBoard.addNode(new RedSpace(index++));
        gameBoard.addNode(new EventSpace(index++));//ID = 14

        gameBoard.addNode(new OtherSpace(index++));
        gameBoard.addNode(new BlueSpace(index++));
        gameBoard.addNode(new EventSpace(index++));
        gameBoard.addNode(new NonMovementSpace(index));//ID = 18

        //Edges
        for (int i = 0; i < 18; ++i) {
            gameBoard.addEdge(gameBoard.getNode(i), gameBoard.getNode(i+1));
        }

        gameBoard.addEdge(gameBoard.getNode(18), gameBoard.getNode(3));
    }
}
