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
import boards.spaces.events.VSSpace;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.builder.GraphBuilder;

public class KameksTantalizingTower extends BaseBoard  {
    public KameksTantalizingTower() {
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
                //Main path to star
                new MoveEventSpace(index++, 23),
                newBlueKamekSpace(index++),
                newBlueKamekSpace(index++),
                newBlueKamekSpace(index++),
                new NonMovementSpace(index++),
                newBlueKamekSpace(index++),
                newBlueKamekSpace(index++),
                newBlueKamekSpace(index++),
                new NonMovementSpace(index++), //ID = 9
                new LuckySpace(index++),
                newBlueKamekSpace(index++),
                new NonMovementSpace(index++), //ID = 12
                newBlueKamekSpace(index++),
                newBlueKamekSpace(index++),
                new EventSpace(index++),
                new EventSpace(index++),
                newBlueKamekSpace(index++), //ID = 17
                new BadLuckSpace(index++),
                new EventSpace(index++),
                newBlueKamekSpace(index++),
                new MoveEventSpace(index++, 35) //ID = 21
        ).addEdgeChain(
                //Past the Thwomp
                newRedKamekSpace(index++), //ID = 22
                newBlueKamekSpace(index++),
                newBlueKamekSpace(index++),
                new MoveEventSpace(index++, 30), //ID = 25
                new NonMovementSpace(index++), //ID = 26
                new OtherSpace(index++) //ID = 27
        ).addEdgeChain(
                //Through the Thwomp
                new NonMovementSpace(index++), //ID = 28
                newBlueKamekSpace(index++) //ID = 29
        ).addEdgeChain(
                //Odd side path
                newBlueKamekSpace(index++), //ID = 30
                new NonMovementSpace(index++),
                new VSSpace(index++),
                new MoveEventSpace(index++, 35)  //ID = 34
        ).addEdgeChain(
                //And wrapping it back around
                newRedKamekSpace(index++), //ID = 35
                new OtherSpace(index++),
                new AllySpace(index++),
                new VSSpace(index) //ID = 38
        );
    }
    
    private BlueSpace newBlueKamekSpace(int index) {
        return new BlueSpace(index,6);
    }
    
    private RedSpace newRedKamekSpace(int index) {
        return new RedSpace(index,-6);
    }
}
