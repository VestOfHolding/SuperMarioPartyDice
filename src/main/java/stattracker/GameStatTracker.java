package stattracker;

import boards.spaces.BaseSpace;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import partydice.Dice;
import results.CoinResult;
import results.DieResult;
import results.MoveResult;

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

    private Int2IntOpenHashMap allyGainOnTurn;

    private Int2IntOpenHashMap landedSpacesAmounts;

    public GameStatTracker(Dice characterDie) {
        this.characterDie = characterDie;
        allyTotal = 0;
        distanceTotal = 0;
        coinTotal = 0;
        landedSpacesAmounts = createNewInt2IntOpenHashMap();

        allyGainOnTurn = createNewInt2IntOpenHashMap();
        //No allies "gained" on the first turn.
        allyGainOnTurn.put(0, 1);
    }

    public void addCoins(int coins) {
        coinTotal += coins;
    }

    public void addDistance(int distanceTraveled) {
        distanceTotal += distanceTraveled;
    }

    public void addDiceResult(DieResult dieResult) {
        if (dieResult instanceof MoveResult) {
            addDistance(dieResult.getResult());
        }
        else if (dieResult instanceof CoinResult) {
            addCoins(dieResult.getResult());
        }
    }

    public void addAlly(int turnNumber) {
        if (allyTotal >= 4) {
            return;
        }
        allyTotal = Math.min(allyTotal + 1, 4);
        allyGainOnTurn.put(allyTotal, turnNumber);
    }

    public void addLandedSpace(BaseSpace baseSpace) {
        int amount = landedSpacesAmounts.get(baseSpace.getSpaceID());
        amount = amount > 0 ? 1 : amount + 1;

        landedSpacesAmounts.put(baseSpace.getSpaceID(), amount);
    }

    private Int2IntOpenHashMap createNewInt2IntOpenHashMap() {
        Int2IntOpenHashMap result = new Int2IntOpenHashMap();
        result.defaultReturnValue(-1);
        return result;
    }
}
