package boards.spaces.events;

import boards.MPEdge;
import boards.layout.MPBoard;
import boards.spaces.BaseSpace;
import boards.spaces.BlueSpace;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import simulation.Player;

import java.util.List;
import java.util.Objects;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class MoveEventSpace extends EventSpace {
    protected Integer spaceToMoveToID;

    protected boolean turnsBlue;

    public MoveEventSpace(int spaceID, Integer spaceToMoveToID, int x, int y) {
        super(spaceID, x, y);
        this.spaceToMoveToID = Objects.requireNonNullElse(spaceToMoveToID, -1);
        turnsBlue = false;
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
    public boolean processEvent(MPBoard<BaseSpace, MPEdge> gameBoard,
                                Player currentPlayer, List<Player> allPlayers) {
        if (turnsBlue) {
            //For now, just handle the fact that this space becomes a Blue Space once it's used.
            gameBoard.setOrReplaceVertex(spaceID, new BlueSpace());
            return true;
        }
        return false;
    }
}
