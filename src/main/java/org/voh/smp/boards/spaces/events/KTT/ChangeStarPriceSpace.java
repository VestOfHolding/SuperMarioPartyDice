package org.voh.smp.boards.spaces.events.KTT;

import org.voh.smp.boards.layout.MPBoard;
import org.voh.smp.boards.spaces.BaseSpace;
import lombok.ToString;
import org.voh.smp.simulation.Player;
import org.voh.smp.simulation.PlayerGroup;
import org.voh.smp.utils.RandomUtils;

import java.util.Arrays;
import java.util.List;

@ToString(callSuper = true)
public class ChangeStarPriceSpace extends BaseSpace {
    private final List<Integer> possibleStarPrices = Arrays.asList(5, 10, 15);

    public ChangeStarPriceSpace(int spaceID, int x, int y) {
        super(spaceID, x, y);
    }

    @Override
    public boolean processEvent(MPBoard gameBoard,
                                Player currentPlayer, PlayerGroup playerGroup) {
        //The next price of the star must be different from the current price.
        int nextPrice;
        do {
            nextPrice = possibleStarPrices.get(RandomUtils.getRandomInt(possibleStarPrices.size() - 1));
        } while (nextPrice == gameBoard.getStarCost());

        gameBoard.setStarCost(nextPrice);
        return true;
    }
}
