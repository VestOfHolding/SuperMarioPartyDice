package boards.spaces.events.KTT;

import boards.MPEdge;
import boards.layout.MPBoard;
import boards.spaces.BaseSpace;
import boards.spaces.NonMovementSpace;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import stattracker.GameStatTracker;
import utils.RandomUtils;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class ThwompShortcutSpace extends NonMovementSpace {
    @EqualsAndHashCode.Exclude
    private int cost;

    @EqualsAndHashCode.Exclude
    private int nextCost;

    public ThwompShortcutSpace(int spaceID) {
        super(spaceID);
    }

    public ThwompShortcutSpace(int spaceID, int x, int y) {
        super(spaceID, x, y);
    }

    @Override
    public boolean isPassingEvent() {
        return true;
    }

    @Override
    public boolean processEvent(MPBoard<BaseSpace, MPEdge> gameBoard,
                                GameStatTracker gameStatTracker, BaseSpace space) {
        if (nextCost < 0) {
            nextCost = calculateNextCost();
        }

        gameStatTracker.addCoins(-cost);

        nextCost = -1;
        return true;
    }

    @Override
    public boolean hasToll() {
        return true;
    }

    @Override
    public boolean canCross(GameStatTracker gameStatTracker) {
        if (nextCost < 0) {
            nextCost = calculateNextCost();
        }
        return gameStatTracker.getCoinTotal() - nextCost >= 0;
    }

    private int calculateNextCost() {
        int result = RandomUtils.getRandomInt(1, 20);
        //Every time you pass Thwomp, he wants at least one more coin than before.
        int tempNextCost = cost + 1;

        //5% chance two players also used Thwomp.
        if (result <= 1) {
            tempNextCost += 2;
        }
        //25% chance one other player also used Thwomp.
        else if (result <= 6) {
            tempNextCost++;
        }

        return tempNextCost;
    }
}
