package boards.spaces.events.KTT;

import boards.layout.MPBoard;
import boards.spaces.BaseSpace;
import boards.spaces.NonMovementSpace;
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
public class ThwompShortcutSpace extends NonMovementSpace {
    @EqualsAndHashCode.Exclude
    private int cost;

    public ThwompShortcutSpace(int spaceID) {
        super(spaceID);
    }

    @Override
    public boolean isPassingEvent() {
        return true;
    }

    @Override
    public boolean processEvent(MPBoard<BaseSpace, DefaultEdge> gameBoard,
                                GameStatTracker gameStatTracker, BaseSpace space) {
        //Every time you pass Thwomp, he wants at least one more coin than before.

        int result = RandomUtils.getRandomInt(1, 20);

        //5% chance two players also used Thwomp.
        if (result <= 1) {
            cost += 2;
        }
        //25% chance one other player also used Thwomp.
        else if (result <= 6) {
            cost++;
        }

        gameStatTracker.addCoins(-cost);
        cost++;
        return true;
    }
}