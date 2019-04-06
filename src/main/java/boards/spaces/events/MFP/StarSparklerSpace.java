package boards.spaces.events.MFP;

import boards.MPEdge;
import boards.layout.MPBoard;
import boards.spaces.BaseSpace;
import boards.spaces.events.EventSpace;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import stattracker.GameStatTracker;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class StarSparklerSpace extends EventSpace {
    @EqualsAndHashCode.Exclude
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
                                GameStatTracker gameStatTracker) {
        countdown = Math.max(0, countdown - 1);

        if (countdown <= 0) {
            gameStatTracker.addStar();
            countdown = 2;
        }
        return true;
    }
}
