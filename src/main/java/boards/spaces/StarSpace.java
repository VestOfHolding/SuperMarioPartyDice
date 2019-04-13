package boards.spaces;

import boards.MPEdge;
import boards.layout.MPBoard;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import simulation.Player;
import simulation.PlayerGroup;
import stattracker.GameStatTracker;
import utils.SpaceUIClass;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class StarSpace extends BlueSpace {

    @EqualsAndHashCode.Exclude
    private boolean starActive = false;

    public StarSpace(int spaceID) {
        super(spaceID);
        starActive = false;
    }

    public StarSpace(int spaceID, int coins) {
        super(spaceID, coins);
        starActive = false;
    }

    @Override
    public SpaceUIClass getNodeClass() {
        return SpaceUIClass.STAR;
    }

    @Override
    public int coinGain() {
        if (starActive) {
            return 0;
        }
        return super.coinGain();
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
    public boolean processEvent(MPBoard<BaseSpace, MPEdge> gameBoard, Player currentPlayer, PlayerGroup playerGroup) {
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
