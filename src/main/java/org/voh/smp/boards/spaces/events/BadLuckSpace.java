package org.voh.smp.boards.spaces.events;

import org.voh.smp.boards.layout.MPBoard;
import org.voh.smp.boards.spaces.BaseSpace;
import org.voh.smp.boards.spaces.SpaceColor;
import lombok.ToString;
import org.voh.smp.simulation.Player;
import org.voh.smp.simulation.PlayerGroup;
import org.voh.smp.stattracker.GameStatTracker;
import org.voh.smp.utils.BadLuckEventTable;
import org.voh.smp.utils.LuckEvent;
import org.voh.smp.utils.RandomUtils;

import java.util.ArrayList;
import java.util.HashSet;

@ToString(callSuper = true)
public class BadLuckSpace extends BaseSpace {

    public BadLuckSpace(int spaceID) {
        super(spaceID);
    }

    @Override
    public boolean processEvent(MPBoard gameBoard,
                                Player currentPlayer, PlayerGroup playerGroup) {
        BadLuckEventTable eventTable;
        GameStatTracker gameStatTracker = currentPlayer.getGameStatTracker();

        if (gameStatTracker.isLastThreeTurns()) {
            eventTable = getExtraBadLuckTable(currentPlayer);
        }
        else if (gameStatTracker.isHalfwayOver()) {
            if (gameBoard.isKamekBoard()) {
                eventTable = currentPlayer.isFirstOrSecond() ? BadLuckEventTable.KAMEK_SECOND_HALF_1ST_2ND : BadLuckEventTable.KAMEK_SECOND_HALF_3RD_4TH;
            }
            else {
                eventTable = currentPlayer.isFirstOrSecond() ? BadLuckEventTable.SECOND_HALF_1ST_2ND : BadLuckEventTable.SECOND_HALF_3RD_4TH;
            }
        }
        else {
            if (gameBoard.isKamekBoard()) {
                eventTable = currentPlayer.isFirstOrSecond() ? BadLuckEventTable.KAMEK_FIRST_HALF_1ST_2ND : BadLuckEventTable.KAMEK_FIRST_HALF_3RD_4TH;
            }
            else {
                eventTable = currentPlayer.isFirstOrSecond() ? BadLuckEventTable.FIRST_HALF_1ST_2ND : BadLuckEventTable.FIRST_HALF_3RD_4TH;
            }
        }

        return processBadLuckEvent(gameBoard, eventTable, currentPlayer, playerGroup);
    }

    private BadLuckEventTable getExtraBadLuckTable(Player currentPlayer) {
        return currentPlayer.isFirstOrSecond() ? BadLuckEventTable.SUPER_BAD_LUCK_1ST_2ND : BadLuckEventTable.SUPER_BAD_LUCK_3RD_4TH;
    }

    private boolean processBadLuckEvent(MPBoard gameBoard, BadLuckEventTable eventTable, Player currentPlayer, PlayerGroup playerGroup) {
        LuckEvent chosenEvent = new ArrayList<>(BadLuckEventTable.buildEventList(eventTable)).get(RandomUtils.getRandomInt(4));

        //Example: The player has to give 5 coins to all players, but doesn't actually have 15 coins to give.
        // Only 12, so we need to do the math based on that.
        //This will be a negative number.
        int actualCoinChange = 0 >= currentPlayer.getCoinTotal() + chosenEvent.getCoinChange()
                ? -currentPlayer.getCoinTotal()
                : chosenEvent.getCoinChange();

        //Chosen MIN_VALUE to represent when we lose half our coins.
        if (Integer.MIN_VALUE == chosenEvent.getCoinChange()) {
            currentPlayer.addCoins(-(currentPlayer.getCoinTotal() / 2));
        }
        else {
            currentPlayer.addCoins(actualCoinChange);
        }

        if (0 < actualCoinChange) {
            if (chosenEvent.isGiveCoinsToLast()) {
                playerGroup.getLastPlacePlayer().addCoins(-actualCoinChange);
            }
            else if (chosenEvent.isGiveCoinsToRandom()) {
                playerGroup.getRandomPlayerBesidesCurrent(currentPlayer).addCoins(-actualCoinChange);
            }
            else if (chosenEvent.isGiveCoinsToAll()) {
                int coinsPerPlayer = actualCoinChange / 3;
                int leftoverAmount = Math.abs(coinsPerPlayer * 3 - actualCoinChange);

                for (Player player : new HashSet<>(playerGroup.getAllPlayersExceptCurrent(currentPlayer))) {
                    player.addCoins(-coinsPerPlayer);

                    if (0 < leftoverAmount) {
                        player.addCoins(1);
                        --leftoverAmount;
                    }
                }
            }
        }

        if (chosenEvent.isMoveStar()) {
            gameBoard.setNeedToMoveStar(true);
        }
        if (chosenEvent.isLoseStar()) {
            currentPlayer.loseStar();
        }
        if (chosenEvent.isDoubleStarCost()) {
            gameBoard.setStarCost(gameBoard.INIT_STAR_COST * 2);
        }
        //The player is pitied and given coins if they have no stars to lose.
        if (chosenEvent.isLoseStarOrGainCoins()) {
            if (0 >= currentPlayer.getStarCount()) {
                currentPlayer.addCoins(20);
            }
            else {
                currentPlayer.loseStar();
            }
        }

        return false;
    }

    @Override
    public SpaceColor getSpaceColor() {
        return SpaceColor.RED;
    }
}
