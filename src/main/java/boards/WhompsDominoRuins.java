package boards;

import boards.layout.Board;
import boards.spaces.AllySpace;
import boards.spaces.BlueSpace;
import boards.spaces.EventSpace;
import boards.spaces.OtherSpace;
import boards.spaces.RedSpace;

public class WhompsDominoRuins extends BaseBoard {
    public WhompsDominoRuins() {
        initializeBoard();
    }

    @Override
    protected void initializeBoard() {
        gameBoard = new Board();

        addInitialLoop();
        addSectionTwo();
        addSectionThree();
        addSectionFour();
    }

    private void addInitialLoop() {
        int index = 0;

        //Start
        gameBoard.addNode(OtherSpace.builder().spaceID(index++).build());//ID = 0

        gameBoard.addNode(BlueSpace.builder().spaceID(index++).build());
        gameBoard.addNode(RedSpace.builder().spaceID(index++).build());
        gameBoard.addNode(BlueSpace.builder().spaceID(index++).build());
        gameBoard.addNode(EventSpace.builder().spaceID(index++).build());

        //Path Path 1-1
        gameBoard.addNode(EventSpace.builder().spaceID(index++).build());
        gameBoard.addNode(AllySpace.builder().spaceID(index++).build());
        gameBoard.addNode(BlueSpace.builder().spaceID(index++).build());
        gameBoard.addNode(OtherSpace.builder().spaceID(index++).build());
        gameBoard.addNode(BlueSpace.builder().spaceID(index++).build());
        gameBoard.addNode(BlueSpace.builder().spaceID(index++).build());//ID = 10

        //Path Path 1-2
        gameBoard.addNode(BlueSpace.builder().spaceID(index).build());//ID = 11

        //Edges
        for (int i = 0; i < 10; ++i) {
            gameBoard.addEdge(gameBoard.getNode(i), gameBoard.getNode(i+1));
        }

        gameBoard.addEdge(gameBoard.getNode(10), gameBoard.getNode(1));
        gameBoard.addEdge(gameBoard.getNode(4), gameBoard.getNode(11));
    }

    private void addSectionTwo() {
        int index = 12;

        //Path Split 2-1
        gameBoard.addNode(OtherSpace.builder().spaceID(index++).build());//ID = 12
        gameBoard.addNode(BlueSpace.builder().spaceID(index++).build());
        //14 here is where the event spaces along the top row can send you back to
        gameBoard.addNode(BlueSpace.builder().spaceID(index++).build());//ID = 14
        gameBoard.addNode(EventSpace.builder().spaceID(index++).spaceToMoveToID(14).build());
        gameBoard.addNode(BlueSpace.builder().spaceID(index++).build());
        gameBoard.addNode(BlueSpace.builder().spaceID(index++).build());//ID = 17

        //Path Split 2-2
        gameBoard.addNode(BlueSpace.builder().spaceID(index++).build());//ID = 18
        gameBoard.addNode(BlueSpace.builder().spaceID(index++).build());
        gameBoard.addNode(EventSpace.builder().spaceID(index++).build());
        gameBoard.addNode(BlueSpace.builder().spaceID(index++).build());
        gameBoard.addNode(AllySpace.builder().spaceID(index++).build());//ID = 22

        gameBoard.addNode(OtherSpace.builder().spaceID(index++).build());//ID = 23
        gameBoard.addNode(BlueSpace.builder().spaceID(index++).build());
        gameBoard.addNode(OtherSpace.builder().spaceID(index++).build());
        gameBoard.addNode(BlueSpace.builder().spaceID(index++).build());
        gameBoard.addNode(EventSpace.builder().spaceID(index).spaceToMoveToID(14).build());//ID = 27

        //Edges
        for (int i = 11; i < 17; ++i) {
            gameBoard.addEdge(gameBoard.getNode(i), gameBoard.getNode(i+1));
        }

        gameBoard.addEdge(gameBoard.getNode(11), gameBoard.getNode(18));

        for (int i = 18; i < 22; ++i) {
            gameBoard.addEdge(gameBoard.getNode(i), gameBoard.getNode(i+1));
        }

        gameBoard.addEdge(gameBoard.getNode(17), gameBoard.getNode(22));

        for (int i = 22; i < 27; ++i) {
            gameBoard.addEdge(gameBoard.getNode(i), gameBoard.getNode(i+1));
        }
    }

    private void addSectionThree() {
        int index = 28;

        //Path Split 3-1
        gameBoard.addNode(EventSpace.builder().spaceID(index++).spaceToMoveToID(14).build());//ID = 28
        gameBoard.addNode(BlueSpace.builder().spaceID(index++).build());
        gameBoard.addNode(BlueSpace.builder().spaceID(index++).build());
        gameBoard.addNode(BlueSpace.builder().spaceID(index++).build());
        gameBoard.addNode(BlueSpace.builder().spaceID(index++).build());
        gameBoard.addNode(OtherSpace.builder().spaceID(index++).build());
        gameBoard.addNode(OtherSpace.builder().spaceID(index++).build());
        gameBoard.addNode(EventSpace.builder().spaceID(index++).build());//ID = 35

        //Path Split 3-2
        gameBoard.addNode(RedSpace.builder().spaceID(index++).build());//ID = 36
        gameBoard.addNode(BlueSpace.builder().spaceID(index++).build());
        gameBoard.addNode(OtherSpace.builder().spaceID(index++).build());//ID = 38

        //Path Split 3-2-1
        gameBoard.addNode(OtherSpace.builder().spaceID(index++).build());//ID = 39
        gameBoard.addNode(OtherSpace.builder().spaceID(index++).build());
        gameBoard.addNode(BlueSpace.builder().spaceID(index++).build());
        gameBoard.addNode(BlueSpace.builder().spaceID(index++).build());
        gameBoard.addNode(OtherSpace.builder().spaceID(index++).build());
        gameBoard.addNode(EventSpace.builder().spaceID(index++).build());//ID = 44

        //Path Split 3-2-2
        gameBoard.addNode(AllySpace.builder().spaceID(index).build());//ID = 45

        //Edges
        for (int i = 28; i < 35; ++i) {
            gameBoard.addEdge(gameBoard.getNode(i), gameBoard.getNode(i+1));
        }

        gameBoard.addEdge(gameBoard.getNode(27), gameBoard.getNode(36));
        gameBoard.addEdge(gameBoard.getNode(27), gameBoard.getNode(28));

        for (int i = 36; i < 44; ++i) {
            gameBoard.addEdge(gameBoard.getNode(i), gameBoard.getNode(i+1));
        }

        gameBoard.addEdge(gameBoard.getNode(27), gameBoard.getNode(36));
        gameBoard.addEdge(gameBoard.getNode(38), gameBoard.getNode(45));
        gameBoard.addEdge(gameBoard.getNode(45), gameBoard.getNode(34));
    }

    private void addSectionFour() {
        int index = 46;

        gameBoard.addNode(OtherSpace.builder().spaceID(index++).build());//ID = 46

        //Path Split 4-1
        gameBoard.addNode(BlueSpace.builder().spaceID(index++).build());//ID = 47
        gameBoard.addNode(OtherSpace.builder().spaceID(index++).build());
        gameBoard.addNode(OtherSpace.builder().spaceID(index++).build());
        gameBoard.addNode(BlueSpace.builder().spaceID(index++).build());
        gameBoard.addNode(BlueSpace.builder().spaceID(index++).build());
        gameBoard.addNode(AllySpace.builder().spaceID(index++).build());//ID = 52

        //Path Split 4-2
        gameBoard.addNode(RedSpace.builder().spaceID(index++).build());//ID = 53
        gameBoard.addNode(BlueSpace.builder().spaceID(index++).build());
        gameBoard.addNode(OtherSpace.builder().spaceID(index++).build());//ID = 55

        gameBoard.addNode(OtherSpace.builder().spaceID(index).build());//ID = 56

        //Edges

        gameBoard.addEdge(gameBoard.getNode(35), gameBoard.getNode(46));
        gameBoard.addEdge(gameBoard.getNode(44), gameBoard.getNode(46));

        for (int i = 46; i < 52; ++i) {
            gameBoard.addEdge(gameBoard.getNode(i), gameBoard.getNode(i+1));
        }

        gameBoard.addEdge(gameBoard.getNode(46), gameBoard.getNode(53));

        for (int i = 53; i < 55; ++i) {
            gameBoard.addEdge(gameBoard.getNode(i), gameBoard.getNode(i+1));
        }

        gameBoard.addEdge(gameBoard.getNode(55), gameBoard.getNode(52));
        gameBoard.addEdge(gameBoard.getNode(52), gameBoard.getNode(56));
        gameBoard.addEdge(gameBoard.getNode(56), gameBoard.getNode(9));
    }
}
