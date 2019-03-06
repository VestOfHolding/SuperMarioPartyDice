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

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class ForcedShopSpace extends NonMovementSpace {
    public ForcedShopSpace(int spaceID) {
        super(spaceID);
    }

    public ForcedShopSpace(int spaceID, int x, int y) {
        super(spaceID, x, y);
    }

    @Override
    public boolean isPassingEvent() {
        return true;
    }

    @Override
    public boolean processKamekEvent(MPBoard<BaseSpace, MPEdge> gameBoard,
                                GameStatTracker gameStatTracker, BaseSpace space) {
        //This is a shop that sells everything for 6 coins, and you MUST buy something.
        gameStatTracker.addCoins(-6);
        return true;
    }
}
