package boards.spaces;

import boards.layout.CustomSimpleDirectedGraph;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.jgrapht.graph.DefaultEdge;
import stattracker.GameStatTracker;

@NoArgsConstructor
@Data
@ToString
public class BaseSpace {

    protected int spaceID;

    public BaseSpace(int spaceID) {
        this.spaceID = spaceID;
    }

    public int coinGain() {
        return 0;
    }

    public boolean addPartner() {
        return false;
    }

    public int moveToSpace() {
        return -1;
    }

    public void processEvent(CustomSimpleDirectedGraph<BaseSpace, DefaultEdge> gameBoard, GameStatTracker gameStatTracker, BaseSpace space) { }

    public boolean affectsMovement() {
        return true;
    }

    public boolean isPassingEvent() {
        return false;
    }
}
