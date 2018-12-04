package boards;

import boards.layout.Board;
import boards.spaces.AllySpace;
import boards.spaces.BlueSpace;
import boards.spaces.OtherSpace;
import boards.spaces.RedSpace;
import boards.spaces.StartSpace;
import boards.spaces.events.BadLuckSpace;
import boards.spaces.events.ChooseTreasureChestEvent;
import boards.spaces.events.EventSpace;
import boards.spaces.events.LuckySpace;
import boards.spaces.events.MoveEventSpace;
import boards.spaces.events.VSSpace;

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
        gameBoard.addNode(new StartSpace(index++));//ID = 0

        gameBoard.addNode(new BlueSpace(index++));
        gameBoard.addNode(new RedSpace(index++));
        gameBoard.addNode(new BlueSpace(index++));
        gameBoard.addNode(new EventSpace(index++));

        //Path Path 1-1
        gameBoard.addNode(new EventSpace(index++));
        gameBoard.addNode(new AllySpace(index++));
        gameBoard.addNode(new BlueSpace(index++));
        gameBoard.addNode(new LuckySpace(index++));
        gameBoard.addNode(new BlueSpace(index++));
        gameBoard.addNode(new BlueSpace(index++));//ID = 10

        //Path Path 1-2
        gameBoard.addNode(new BlueSpace(index));//ID = 11

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
        gameBoard.addNode(new OtherSpace(index++));//ID = 12
        gameBoard.addNode(new BlueSpace(index++));
        //14 here is where the event spaces along the top row can send you back destination
        gameBoard.addNode(new BlueSpace(index++));//ID = 14
        gameBoard.addNode(new MoveEventSpace(index++, getMoveEventDestinationID()));
        gameBoard.addNode(new BlueSpace(index++));
        gameBoard.addNode(new BlueSpace(index++));//ID = 17

        //Path Split 2-2
        gameBoard.addNode(new BlueSpace(index++));//ID = 18
        gameBoard.addNode(new BlueSpace(index++));
        gameBoard.addNode(new ChooseTreasureChestEvent(index++));
        gameBoard.addNode(new BadLuckSpace(index++));
        gameBoard.addNode(new AllySpace(index++));//ID = 22

        gameBoard.addNode(new OtherSpace(index++));//ID = 23
        gameBoard.addNode(new BlueSpace(index++));
        gameBoard.addNode(new OtherSpace(index++));
        gameBoard.addNode(new BlueSpace(index++));
        gameBoard.addNode(new MoveEventSpace(index, getMoveEventDestinationID()));//ID = 27

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
        gameBoard.addNode(new MoveEventSpace(index++, getMoveEventDestinationID()));//ID = 28
        gameBoard.addNode(new BlueSpace(index++));
        gameBoard.addNode(new BlueSpace(index++));
        gameBoard.addNode(new BlueSpace(index++));
        gameBoard.addNode(new BlueSpace(index++));
        gameBoard.addNode(new BadLuckSpace(index++));
        gameBoard.addNode(new OtherSpace(index++));
        gameBoard.addNode(new EventSpace(index++));//ID = 35

        //Path Split 3-2
        gameBoard.addNode(new RedSpace(index++));//ID = 36
        gameBoard.addNode(new BlueSpace(index++));
        gameBoard.addNode(new VSSpace(index++));
        gameBoard.addNode(new AllySpace(index++));//ID = 39

        //Path Split 3-3
        gameBoard.addNode(new EventSpace(index++));//ID = 40
        gameBoard.addNode(new OtherSpace(index++));
        gameBoard.addNode(new BlueSpace(index++));
        gameBoard.addNode(new BlueSpace(index++));
        gameBoard.addNode(new BadLuckSpace(index++));
        gameBoard.addNode(new BadLuckSpace(index));//ID = 45

        //Edges
        for (int i = 28; i < 35; ++i) {
            gameBoard.addEdge(gameBoard.getNode(i), gameBoard.getNode(i+1));
        }

        gameBoard.addEdge(gameBoard.getNode(27), gameBoard.getNode(36));
        gameBoard.addEdge(gameBoard.getNode(27), gameBoard.getNode(28));

        for (int i = 36; i < 39; ++i) {
            gameBoard.addEdge(gameBoard.getNode(i), gameBoard.getNode(i+1));
        }

        for (int i = 40; i < 45; ++i) {
            gameBoard.addEdge(gameBoard.getNode(i), gameBoard.getNode(i+1));
        }

        gameBoard.addEdge(gameBoard.getNode(35), gameBoard.getNode(40));
        gameBoard.addEdge(gameBoard.getNode(39), gameBoard.getNode(34));
        gameBoard.addEdge(gameBoard.getNode(45), gameBoard.getNode(39));
    }

    private void addSectionFour() {
        int index = 46;

        gameBoard.addNode(new VSSpace(index++));//ID = 46

        //Path Split 4-1
        gameBoard.addNode(new BlueSpace(index++));//ID = 47
        gameBoard.addNode(new LuckySpace(index++));
        gameBoard.addNode(new LuckySpace(index++));
        gameBoard.addNode(new BlueSpace(index++));
        gameBoard.addNode(new BlueSpace(index++));
        gameBoard.addNode(new AllySpace(index++));//ID = 52

        //Path Split 4-2
        gameBoard.addNode(new RedSpace(index++));//ID = 53
        gameBoard.addNode(new BlueSpace(index++));
        gameBoard.addNode(new OtherSpace(index++));//ID = 55

        gameBoard.addNode(new BadLuckSpace(index));//ID = 56

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

    public int getMoveEventDestinationID() {
        return 14;
    }
}
