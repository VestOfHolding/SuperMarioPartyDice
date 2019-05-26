package boards.spaces;

import boards.layout.MPBoard;
import lombok.ToString;
import simulation.Player;
import simulation.PlayerGroup;

@ToString(callSuper = true)
public class AllySpace extends BaseSpace {

    public AllySpace(int spaceID) {
        super(spaceID);
    }

    @Override
    public boolean processEvent(MPBoard gameBoard, Player currentPlayer, PlayerGroup playerGroup) {
        currentPlayer.getGameStatTracker().addAlly();
        return true;
    }
}
