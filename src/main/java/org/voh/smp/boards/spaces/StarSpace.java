package org.voh.smp.boards.spaces;

import org.voh.smp.boards.layout.MPBoard;
import lombok.Getter;
import lombok.ToString;
import org.voh.smp.simulation.Player;
import org.voh.smp.simulation.PlayerGroup;

@Getter
@ToString(callSuper = true)
public class StarSpace extends BlueSpace {

    private boolean starActive;

    public StarSpace(int spaceID, int coins) {
        super(spaceID, coins);
        starActive = false;
    }

    @Override
    public boolean affectsMovement() {
        return !starActive;
    }

    @Override
    public boolean isPassingEvent() {
        return starActive;
    }

    @Override
    public boolean processEvent(MPBoard gameBoard, Player currentPlayer, PlayerGroup playerGroup) {
        if (gameBoard.isKamekBoard()) {
            //The star is always active on the same space on this board.
            if (currentPlayer.getCoinTotal() >= gameBoard.getStarCost()) {
                currentPlayer.addCoins(-1 * gameBoard.getStarCost());
                currentPlayer.addStar();

                //If we have the coin, we can buy a second star while we're here.
                if (currentPlayer.getCoinTotal() >= gameBoard.getStarCost()) {
                    currentPlayer.addCoins(-1 * gameBoard.getStarCost());
                    currentPlayer.addStar();
                }

                gameBoard.setNeedToMoveStar(true);

                return true;
            }
        }
        else {
            if (starActive && currentPlayer.getCoinTotal() >= gameBoard.getStarCost()) {
                currentPlayer.addCoins(-1 * gameBoard.getStarCost());
                currentPlayer.addStar();

                gameBoard.setNeedToMoveStar(true);

                return true;
            }
            //When the star is inactive, it's just a blue space.
            else if (!starActive) {
                return super.processEvent(gameBoard, currentPlayer, playerGroup);
            }
        }

        return false;
    }

    public void activateStar() {
        starActive = true;
    }

    public void deactivateStar() {
        starActive = false;
    }
}
