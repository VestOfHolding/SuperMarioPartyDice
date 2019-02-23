package boards.spaces.events.WDR;

import boards.MPEdge;
import boards.layout.MPBoard;
import boards.spaces.BaseSpace;
import boards.spaces.events.EventSpace;
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
public class WhompsOnTheRun extends EventSpace {
    private static int COST = 3;

    private int partnerID;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private boolean active;

    public WhompsOnTheRun(int spaceID, int partnerID, boolean active) {
        this(spaceID, partnerID, active, -1, -1);
    }

    public WhompsOnTheRun(int spaceID, int partnerID, boolean active, int x, int y) {
        super(spaceID);
        this.partnerID = partnerID;
        this.active = active;
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean processEvent(MPBoard<BaseSpace, MPEdge> gameBoard,
                                GameStatTracker gameStatTracker, BaseSpace space) {
        if (!isActive()) {
            return false;
        }

        gameStatTracker.addCoins(-COST);

        whompSwitch(gameBoard);
        return true;
    }

    public void whompSwitch(MPBoard<BaseSpace, MPEdge> gameBoard) {
        // Let's go with a 10% chance that some other player has come along and also swapped the Whomps
        // so you just swap them back.
        // I fully acknowledge this is absolutely nothing more than a "better than nothing" approach
        // that isn't the most accurate.
        if (RandomUtils.getRandomInt(0, 9) == 0) {
            return;
        }

        active = !active;

        WhompsOnTheRun secondWhomp = (WhompsOnTheRun)gameBoard.getVertexById(partnerID);
        secondWhomp.setActive(!secondWhomp.isActive());
    }

    @Override
    public boolean affectsMovement() {
        return false;
    }

    @Override
    public boolean isPassingEvent() {
        return true;
    }

    @Override
    public boolean hasToll() {
        return isActive();
    }

    @Override
    public boolean canCross(GameStatTracker gameStatTracker) {
        return gameStatTracker.getCoinTotal() - COST >= 0;
    }
}
