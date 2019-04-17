package boards.spaces.events.KTT;

import boards.MPEdge;
import boards.layout.MPBoard;
import boards.spaces.BaseSpace;
import boards.spaces.NonMovementSpace;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import simulation.Player;
import simulation.PlayerGroup;
import stattracker.GameStatTracker;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class ThwompShortcutSpace extends NonMovementSpace {
    private int cost;

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
                                Player currentPlayer, PlayerGroup playerGroup) {
        if (nextCost < 0) {
            nextCost = calculateNextCost();
        }

        currentPlayer.addCoins(-cost);

        nextCost = -1;
        return true;
    }

    @Override
    public boolean hasToll() {
        return true;
    }

    @Override
    public boolean canCross(GameStatTracker gameStatTracker, int starCost) {
        if (nextCost < 0) {
            nextCost = calculateNextCost();
        }
        return gameStatTracker.getCoinTotal() - nextCost - starCost >= 0;
    }

    private int calculateNextCost() {
        //Every time you pass Thwomp, he wants at least one more coin than before.
        return cost + 1;
    }
}
