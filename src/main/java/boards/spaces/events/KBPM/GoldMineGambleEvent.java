package boards.spaces.events.KBPM;

import boards.layout.MPBoard;
import boards.spaces.BaseSpace;
import lombok.ToString;
import simulation.Player;
import simulation.PlayerGroup;
import utils.RandomUtils;

@ToString(callSuper = true)
public class GoldMineGambleEvent extends BaseSpace {

    public GoldMineGambleEvent(int spaceID, int x, int y) {
        super(spaceID, x, y);
    }

    /**
     * Mine three times in a row. Player has a random chance each time of gaining coins or losing everything.
     * After the first two, the player has the choice to take the current coins earned and leave.
     * The third time will be more coins than the first two.
     *
     * TODO: Saw it happen once with 5 and 10. Need to see more.
     */
    @Override
    public boolean processEvent(MPBoard gameBoard,
                                Player currentPlayer, PlayerGroup playerGroup) {
        int coinsEarned = 0;

        if (RandomUtils.isFlippedCoinHeads()) {
            coinsEarned += 5;
        }
        else {
            return true;
        }
        //Random chance the player decides to take the current coins and leave.
        if (RandomUtils.isFlippedCoinHeads()) {
            currentPlayer.addCoins(coinsEarned);
            return true;
        }

        if (RandomUtils.isFlippedCoinHeads()) {
            coinsEarned += 5;
        }
        else {
            return true;
        }
        //Random chance the player decides to take the current coins and leave.
        if (RandomUtils.isFlippedCoinHeads()) {
            currentPlayer.addCoins(coinsEarned);
            return true;
        }

        if (RandomUtils.isFlippedCoinHeads()) {
            coinsEarned += 10;
        }
        else {
            return true;
        }
        currentPlayer.addCoins(coinsEarned);
        return true;
    }
}
