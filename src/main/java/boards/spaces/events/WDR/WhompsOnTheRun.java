package boards.spaces.events.WDR;

import boards.layout.MPBoard;
import boards.spaces.BaseSpace;
import boards.spaces.events.EventSpace;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.jgrapht.graph.DefaultEdge;
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
        super(spaceID);
        this.partnerID = partnerID;
        this.active = active;
    }

    @Override
    public boolean processEvent(MPBoard<BaseSpace, DefaultEdge> gameBoard,
                                GameStatTracker gameStatTracker, BaseSpace space) {
        if (!isActive()) {
            return false;
        }

        gameStatTracker.addCoins(-COST);

        whompSwitch(gameBoard);
        return true;
    }

    public void whompSwitch(MPBoard<BaseSpace, DefaultEdge> gameBoard) {
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
