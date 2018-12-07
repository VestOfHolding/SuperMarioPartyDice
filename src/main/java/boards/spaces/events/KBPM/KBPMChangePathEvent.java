package boards.spaces.events.KBPM;

import boards.layout.MPBoard;
import boards.spaces.BaseSpace;
import boards.spaces.BlueSpace;
import boards.spaces.events.EventSpace;
import boards.spaces.events.LuckySpace;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import stattracker.GameStatTracker;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class KBPMChangePathEvent extends EventSpace {
    public KBPMChangePathEvent(int spaceID) {
        super(spaceID);
    }

    @Override
    public void processEvent(MPBoard<BaseSpace, DefaultEdge> gameBoard,
                             GameStatTracker gameStatTracker, BaseSpace space) {

        KBPMChangePathEvent firstEventSpace = (KBPMChangePathEvent)gameBoard.getVertexById(27);

        BaseSpace nextSpace = Graphs.successorListOf(gameBoard, firstEventSpace).get(0);

        if (nextSpace instanceof LuckySpace) {
            gameBoard.removeEdge(firstEventSpace, gameBoard.getVertexById(28));
            gameBoard.addEdge(firstEventSpace, gameBoard.getVertexById(38));

            gameBoard.removeEdge(gameBoard.getVertexById(30), gameBoard.getVertexById(31));
            gameBoard.addEdge(gameBoard.getVertexById(40), gameBoard.getVertexById(31));
        }
        else if (nextSpace instanceof BlueSpace) {
            gameBoard.removeEdge(firstEventSpace, gameBoard.getVertexById(38));
            gameBoard.addEdge(firstEventSpace, gameBoard.getVertexById(28));

            gameBoard.removeEdge(gameBoard.getVertexById(40), gameBoard.getVertexById(31));
            gameBoard.addEdge(gameBoard.getVertexById(30), gameBoard.getVertexById(31));
        }
    }
}
