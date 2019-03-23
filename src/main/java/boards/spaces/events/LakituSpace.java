package boards.spaces.events;

import boards.MPEdge;
import boards.layout.MPBoard;
import boards.spaces.BaseSpace;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import stattracker.GameStatTracker;
import utils.SpaceUIClass;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class LakituSpace extends EventSpace {
    public LakituSpace(int spaceID) {
        this(spaceID, -1, -1);
    }

    public LakituSpace(int spaceID, int x, int y) {
        super(spaceID, x, y);
    }

    @Override
    public boolean isPassingEvent() {
        return true;
    }

    @Override
    public boolean processEvent(MPBoard<BaseSpace, MPEdge> gameBoard,
                                GameStatTracker gameStatTracker) {
//        gameStatTracker.addCoins(RandomUtils.getRandomInt(5, 10));
        return true;
    }

    @Override
    public SpaceUIClass getNodeClass() {
        return SpaceUIClass.NONMOVEMENT;
    }
}
