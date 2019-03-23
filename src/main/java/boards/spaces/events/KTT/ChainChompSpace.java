package boards.spaces.events.KTT;

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
public class ChainChompSpace extends EventSpace {

    public ChainChompSpace(int spaceID) {
        super(spaceID);
    }

    public ChainChompSpace(int spaceID, int x, int y) {
        super(spaceID, x, y);
    }

    @Override
    public boolean processEvent(MPBoard<BaseSpace, MPEdge> gameBoard,
                                GameStatTracker gameStatTracker) {
        //The Chain Chomp comes and steals coins.
        // Not fully sure what the range of possibilities is here yet.
        // I've seen both 5 and 7, though not sure what the percentage chance is.
        gameStatTracker.addCoins(RandomUtils.isFlippedCoinHeads() ? -5 : -7);
        return true;
    }
}
