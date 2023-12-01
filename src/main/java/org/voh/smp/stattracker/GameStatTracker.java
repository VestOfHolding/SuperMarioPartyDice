package org.voh.smp.stattracker;

import org.voh.smp.boards.spaces.BaseSpace;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.voh.smp.partydice.BobombAlly;
import org.voh.smp.partydice.Dice;
import org.voh.smp.results.CoinResult;
import org.voh.smp.results.DieResult;
import org.voh.smp.results.MoveResult;
import org.voh.smp.simulation.PlayerGroup;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GameStatTracker {
    private int turnNumber;
    private int turnMax;

    private List<String> allies;

    private int distanceTotal;
    private int coinTotal;
    private int maxCoins;
    private int starCount;
    private int miniGameWins;
    private int eventActivations;
    private int badLuckCount;

    private List<BobombAlly> bobombAllies;

    private Int2IntOpenHashMap allyGainOnTurn;

    private Int2IntOpenHashMap landedSpacesAmounts;

    public GameStatTracker(int initialTurnCount) {
        turnNumber = 1;
        turnMax = initialTurnCount;
        distanceTotal = 0;
        coinTotal = 5;
        allies = new ArrayList<>(5);
        landedSpacesAmounts = createNewInt2IntOpenHashMap();

        allyGainOnTurn = createNewInt2IntOpenHashMap();
        //No allies "gained" on the first turn.
        allyGainOnTurn.put(0, 1);
    }

    public void addCoins(int coins) {
        coinTotal = Math.max(0, coinTotal + coins);
    }

    public void addStar() {
        ++starCount;
    }

    public void loseStar() {
        starCount = Math.max(0, starCount - 1);
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

    public int getTrueAllyCount() {
        return null == bobombAllies ? allies.size() : allies.size() + bobombAllies.size();
    }

    public void addAlly(PlayerGroup playerGroup) {
        if (4 <= getTrueAllyCount()) {
            return;
        }
        allies.add(Dice.getRandomCharacterDieNotInGroup(playerGroup).getName());

        allyGainOnTurn.put(allies.size(), turnNumber);
    }

    public void addBobombAlly() {
        //Most of the time it's not worth initializing, so only do it
        // when this actually comes up.
        if (null == bobombAllies) {
            bobombAllies = new ArrayList<>(1);
        }

        bobombAllies.add(new BobombAlly());

        //Bo-bomb allies can replace real allies.
        if (4 <= allies.size()) {
            allies.remove(allies.size() - 1);
        }
    }

    public void removeBobombAlly() {
        if (CollectionUtils.isEmpty(bobombAllies)) {
            return;
        }

        bobombAllies.remove(bobombAllies.size() - 1);
    }

    public void addLandedSpace(BaseSpace baseSpace) {
        int amount = landedSpacesAmounts.get(baseSpace.getSpaceID());
        amount = 0 < amount ? 1 : amount + 1;

        landedSpacesAmounts.put(baseSpace.getSpaceID(), amount);
    }

    private Int2IntOpenHashMap createNewInt2IntOpenHashMap() {
        Int2IntOpenHashMap result = new Int2IntOpenHashMap();
        result.defaultReturnValue(-1);
        return result;
    }

    public void incrementTurn() {
        turnNumber = Math.min(turnMax, turnNumber + 1);
    }

    public boolean isHalfwayOver() {
        return 0.5 <= (double) turnNumber / (double) turnMax;
    }

    public boolean isLastThreeTurns() {
        return 3 >= turnMax - turnNumber;
    }

    public int getAllyTotal() {
        return CollectionUtils.size(allies);
    }
}
