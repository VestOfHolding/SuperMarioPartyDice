package boards.spaces;

import boards.MPEdge;
import boards.layout.MPBoard;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import stattracker.GameStatTracker;
import utils.SpaceUIClass;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class StarSpace extends BlueSpace {

    @EqualsAndHashCode.Exclude
    private boolean starActive;

    public StarSpace(int spaceID) {
        super(spaceID);
    }

    public StarSpace(int spaceID, int coins) {
        super(spaceID, coins);
    }

    @Override
    public SpaceUIClass getNodeClass() {
        return SpaceUIClass.BLUE;
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
    public boolean processEvent(MPBoard<BaseSpace, MPEdge> gameBoard, GameStatTracker gameStatTracker) {
        if (starActive && gameStatTracker.getCoinTotal() >= 10) {
            gameStatTracker.addCoins(-10);
            gameStatTracker.addStar();

            gameBoard.setNeedToMoveStar(true);

            return true;
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
