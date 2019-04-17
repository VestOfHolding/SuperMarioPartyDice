package boards.spaces.events.MFP;

import boards.MPEdge;
import boards.layout.MPBoard;
import boards.spaces.BaseSpace;
import boards.spaces.events.EventSpace;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import simulation.Player;
import simulation.PlayerGroup;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class StarSparklerSpace extends EventSpace {

    @ToString.Exclude
    int countdown = 2;

    public StarSparklerSpace(int spaceID) {
        this(spaceID, -1, -1);
    }

    public StarSparklerSpace(int spaceID, int x, int y) {
        super(spaceID);
        this.x = x;
        this.y = y;

        countdown = 2;
    }

    @Override
    public boolean processEvent(MPBoard<BaseSpace, MPEdge> gameBoard,
                                Player currentPlayer, PlayerGroup playerGroup) {
        countdown = Math.max(0, countdown - 1);

        if (countdown <= 0) {
            currentPlayer.getGameStatTracker().addStar();
            countdown = 2;
        }
        return true;
    }
}
