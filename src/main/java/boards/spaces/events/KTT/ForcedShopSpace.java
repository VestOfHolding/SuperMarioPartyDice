package boards.spaces.events.KTT;

import boards.layout.MPBoard;
import boards.spaces.NonMovementSpace;
import lombok.ToString;
import simulation.Player;
import simulation.PlayerGroup;

@ToString(callSuper = true)
public class ForcedShopSpace extends NonMovementSpace {

    public ForcedShopSpace(int spaceID, int x, int y) {
        super(spaceID, x, y);
    }

    @Override
    public boolean isPassingEvent() {
        return true;
    }

    @Override
    public boolean processEvent(MPBoard gameBoard,
                                Player currentPlayer, PlayerGroup playerGroup) {
        //This is a shop that sells everything for 6 coins, and you MUST buy something.
        currentPlayer.addCoins(-6);
        return true;
    }
}
