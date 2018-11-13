package stattracker;

import boards.spaces.BaseSpace;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import partydice.Dice;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameStatTracker {
    private Dice characterDie;

    private int allyTotal;
    private int distanceTotal;
    private int coinTotal;

    private Map<Integer, Integer> allyGainOnTurn;

    private Map<Integer, Integer> landedSpacesAmounts;

    public GameStatTracker(Dice characterDie) {
        this.characterDie = characterDie;
        allyTotal = 0;
        distanceTotal = 0;
        coinTotal = 0;
        landedSpacesAmounts = new HashMap<>();

        allyGainOnTurn = new HashMap<>();
        //No allies "gained" on the first turn.
        allyGainOnTurn.put(0, 1);
    }

    public void addAlly(int turnNumber) {
        if (allyTotal >= 4) {
            return;
        }
        allyTotal = Math.min(allyTotal + 1, 4);
        allyGainOnTurn.put(allyTotal, turnNumber);
    }

    public void addLandedSpace(BaseSpace baseSpace) {
        Integer amount = landedSpacesAmounts.get(baseSpace.getSpaceID());
        amount = amount == null ? 1 : amount + 1;

        landedSpacesAmounts.put(baseSpace.getSpaceID(), amount);
    }
}
