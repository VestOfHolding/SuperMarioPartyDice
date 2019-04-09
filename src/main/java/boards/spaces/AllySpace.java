package boards.spaces;

import boards.MPEdge;
import boards.layout.MPBoard;
import boards.spaces.events.EventSpace;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import simulation.Player;
import utils.SpaceUIClass;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class AllySpace extends EventSpace {

    public AllySpace(int spaceID) {
        super(spaceID);
    }

    @Override
    public boolean processEvent(MPBoard<BaseSpace, MPEdge> gameBoard, Player currentPlayer, List<Player> allPlayers) {
        currentPlayer.getGameStatTracker().addAlly();
        return true;
    }

    @Override
    public SpaceUIClass getNodeClass() {
        return SpaceUIClass.ALLY;
    }
}
