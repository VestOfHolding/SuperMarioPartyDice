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

import java.util.Arrays;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class ChangeStarPriceSpace extends EventSpace {
    private List<Integer> possibleStarPrices = Arrays.asList(5, 10, 15);

    public ChangeStarPriceSpace(int spaceID) {
        super(spaceID);
    }

    public ChangeStarPriceSpace(int spaceID, int x, int y) {
        super(spaceID, x, y);
    }

    @Override
    public boolean processKamekEvent(MPBoard<BaseSpace, MPEdge> gameBoard,
                                GameStatTracker gameStatTracker) {
        //The next price of the star must be different from the current price.
        int nextPrice;
        do {
            nextPrice = possibleStarPrices.get(RandomUtils.getRandomInt(possibleStarPrices.size() - 1));
        } while (nextPrice == gameBoard.getStarCost());

        gameBoard.setStarCost(nextPrice);
        return true;
    }
}