package boards.spaces;

import boards.MPEdge;
import boards.layout.MPBoard;
import boards.spaces.events.EventSpace;
import lombok.ToString;
import simulation.Player;
import simulation.PlayerGroup;
import utils.SpaceUIClass;

@ToString(callSuper = true)
public class AllySpace extends EventSpace {

    public AllySpace(int spaceID) {
        super(spaceID);
    }

    @Override
    public boolean processEvent(MPBoard<BaseSpace, MPEdge> gameBoard, Player currentPlayer, PlayerGroup playerGroup) {
        currentPlayer.getGameStatTracker().addAlly();
        return true;
    }

    @Override
    public SpaceUIClass getNodeClass() {
        return SpaceUIClass.ALLY;
    }
}
