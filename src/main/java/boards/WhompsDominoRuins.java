package boards;

import boards.spaces.AllySpace;
import boards.spaces.BlueSpace;
import boards.spaces.NonMovementSpace;
import boards.spaces.OtherSpace;
import boards.spaces.RedSpace;
import boards.spaces.StartSpace;
import boards.spaces.events.BadLuckSpace;
import boards.spaces.events.ChooseTreasureChestEvent;
import boards.spaces.events.EventSpace;
import boards.spaces.events.LakituSpace;
import boards.spaces.events.LuckySpace;
import boards.spaces.events.MoveEventSpace;
import boards.spaces.events.VSSpace;

public class WhompsDominoRuins extends BaseBoard {

    public WhompsDominoRuins() {
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
                new BlueSpace(index++),
                new EventSpace(index++),
                new NonMovementSpace(index++), //ID = 5
                new BlueSpace(index++),
                new NonMovementSpace(index++), //ID = 7
                new OtherSpace(index++),
                new BlueSpace(index++),
                new BlueSpace(index++), //ID = 10
                new MoveEventSpace(index++, getMoveEventDestinationID(), true),
                new BlueSpace(index++),
                new BlueSpace(index++),
                new AllySpace(index++),
                new OtherSpace(index++), //ID = 15
                new BlueSpace(index++),
                new OtherSpace(index++),
                new BlueSpace(index++),
                new MoveEventSpace(index++, getMoveEventDestinationID(), true),
                new NonMovementSpace(index++), //ID = 20
                new MoveEventSpace(index++, getMoveEventDestinationID(), true),
                new BlueSpace(index++),
                new BlueSpace(index++),
                new LakituSpace(index++),
                new BlueSpace(index++),
                new BadLuckSpace(index++),
                new OtherSpace(index++), //ID = 27
                new EventSpace(index++),
                new NonMovementSpace(index++), //ID = 29
                new VSSpace(index++),
                new NonMovementSpace(index++), //ID = 31
                new BlueSpace(index++),
                new NonMovementSpace(index++),
                new LuckySpace(index++),
                new LuckySpace(index++),
                new BlueSpace(index++),
                new BlueSpace(index++),
                new AllySpace(index++), //ID = 38
                new BadLuckSpace(index++),
                new BlueSpace(index++), //ID = 40
                new NonMovementSpace(index++),
                new BlueSpace(index++)
        ).addEdgeChain(
                //Inner Loop
                new EventSpace(index++), //ID = 43
                new AllySpace(index++),
                new BlueSpace(index++),
                new LuckySpace(index++) //ID = 46
        ).addEdgeChain(
                //Side Path 1
                new BlueSpace(index++),  //ID = 47
                new NonMovementSpace(index++),
                new BlueSpace(index++),
                new ChooseTreasureChestEvent(index++),
                new BadLuckSpace(index++) //ID = 51
        ).addEdgeChain(
                //Side Path 2
                new RedSpace(index++), //ID = 52
                new BlueSpace(index++),
                new VSSpace(index++),
                new AllySpace(index++) //ID = 55
        ).addEdgeChain(
                //Side Path 3
                new EventSpace(index++), //ID = 56
                new OtherSpace(index++),
                new BlueSpace(index++),
                new BlueSpace(index++),
                new BadLuckSpace(index++),
                new BadLuckSpace(index++) //ID = 61
        ).addEdgeChain(
                //Side Path 4
                new RedSpace(index++), //ID = 62
                new BlueSpace(index++),
                new BlueSpace(index++),
                new OtherSpace(index) //ID = 65
        );

        board = graphBuilder.build();
        connectPaths();
    }

    private void connectPaths() {
        board.addEdge(board.getVertexById(42), board.getVertexById(1));

        board.addEdge(board.getVertexById(5), board.getVertexById(43));
        board.addEdge(board.getVertexById(46), board.getVertexById(40));

        board.addEdge(board.getVertexById(7), board.getVertexById(47));
        board.addEdge(board.getVertexById(51), board.getVertexById(14));

        board.addEdge(board.getVertexById(20), board.getVertexById(52));
        board.addEdge(board.getVertexById(55), board.getVertexById(27));

        board.addEdge(board.getVertexById(20), board.getVertexById(52));
        board.addEdge(board.getVertexById(55), board.getVertexById(27));

        board.addEdge(board.getVertexById(29), board.getVertexById(56));
        board.addEdge(board.getVertexById(61), board.getVertexById(55));

        board.addEdge(board.getVertexById(31), board.getVertexById(62));
        board.addEdge(board.getVertexById(65), board.getVertexById(38));
    }

    private int getMoveEventDestinationID() {
        return 10;
    }
}
