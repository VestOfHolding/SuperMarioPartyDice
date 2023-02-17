package boards.spaces.events.MFP;

import boards.layout.MPBoard;
import boards.spaces.BaseSpace;
import lombok.ToString;
import simulation.Player;
import simulation.PlayerGroup;

@ToString(callSuper = true)
public class StarSparklerSpace extends BaseSpace {

    @ToString.Exclude
    int countdown;

    public StarSparklerSpace(int spaceID, int x, int y) {
        super(spaceID);
        this.x = x;
        this.y = y;

        countdown = 2;
    }

    @Override
    public boolean processEvent(MPBoard gameBoard,
                                Player currentPlayer, PlayerGroup playerGroup) {
        countdown = Math.max(0, countdown - 1);

        if (0 == countdown) {
            currentPlayer.getGameStatTracker().addStar();
            countdown = 2;
        }
        return true;
    }

    @Override
    public void reset() {
        countdown = 2;
    }
}
