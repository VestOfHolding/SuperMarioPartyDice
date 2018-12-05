package boards.spaces.events;

import boards.layout.CustomSimpleDirectedGraph;
import boards.spaces.BaseSpace;
import boards.spaces.BlueSpace;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.jgrapht.graph.DefaultEdge;
import stattracker.GameStatTracker;

import java.util.Objects;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class MoveEventSpace extends EventSpace {
    private Integer spaceToMoveToID;

    private boolean turnsBlue;

    public MoveEventSpace(int spaceID, Integer spaceToMoveToID) {
        super(spaceID);
        this.spaceToMoveToID = Objects.requireNonNullElse(spaceToMoveToID, -1);
        turnsBlue = true;
    }

    public MoveEventSpace(int spaceID, Integer spaceToMoveToID, boolean turnsBlue) {
        super(spaceID);
        this.spaceToMoveToID = Objects.requireNonNullElse(spaceToMoveToID, -1);
        this.turnsBlue = turnsBlue;
    }

    @Override
    public int moveToSpace() {
        return spaceToMoveToID;
    }

    @Override
    public void processEvent(CustomSimpleDirectedGraph<BaseSpace, DefaultEdge> gameBoard,
                             GameStatTracker gameStatTracker, BaseSpace space) {
        if (turnsBlue) {
            //For now, just handle the fact that this space becomes a Blue Space once it's used.
            gameBoard.setOrReplaceVertex(space.getSpaceID(), new BlueSpace());
        }
    }
}
