package boards.spaces.events;

import boards.layout.Board;
import boards.spaces.BaseSpace;
import boards.spaces.BlueSpace;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import stattracker.GameStatTracker;

import java.util.Objects;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class MoveEventSpace extends EventSpace {
    private Integer spaceToMoveToID;

    public MoveEventSpace(int spaceID, Integer spaceToMoveToID) {
        super(spaceID);
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
