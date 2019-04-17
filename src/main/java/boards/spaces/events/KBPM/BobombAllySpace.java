package boards.spaces.events.KBPM;

import boards.MPEdge;
import boards.layout.MPBoard;
import boards.spaces.BaseSpace;
import boards.spaces.events.EventSpace;
import lombok.NoArgsConstructor;
import lombok.ToString;
import simulation.Player;
import simulation.PlayerGroup;

@NoArgsConstructor
@ToString(callSuper = true)
public class BobombAllySpace extends EventSpace {
    public BobombAllySpace(int spaceID) {
        super(spaceID);
    }

    public BobombAllySpace(int spaceID, int x, int y) {
        super(spaceID, x, y);
    }

    @Override
    public boolean isPassingEvent() {
        return true;
    }

    @Override
    public boolean processEvent(MPBoard<BaseSpace, MPEdge> gameBoard,
                                Player currentPlayer, PlayerGroup playerGroup) {
        currentPlayer.getGameStatTracker().addBobombAlly();
        return true;
    }
}
