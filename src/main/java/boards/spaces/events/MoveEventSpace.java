package boards.spaces.events;

import boards.layout.Board;
import boards.layout.Edge;
import boards.spaces.BaseSpace;
import boards.spaces.BlueSpace;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import stattracker.GameStatTracker;

import java.util.Objects;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class MoveEventSpace extends EventSpace {
    private Integer spaceToMoveToID;

    @Builder(builderMethodName = "moveBuilder")
    private MoveEventSpace(int spaceID, Set<Edge> edges, Integer spaceToMoveToID) {
        super(spaceID, edges);
        this.spaceToMoveToID = Objects.requireNonNullElse(spaceToMoveToID, -1);
    }

    @Override
    public int moveToSpace() {
        return spaceToMoveToID;
    }

    @Override
    public void processEvent(Board gameBoard, GameStatTracker gameStatTracker, BaseSpace space) {
        //For now, just handle the fact that this space becomes a Blue Space once it's used.
        gameBoard.setNode(space.getSpaceID(), new BlueSpace());
    }
}
