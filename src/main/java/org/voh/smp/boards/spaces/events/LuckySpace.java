package org.voh.smp.boards.spaces.events;

import org.voh.smp.boards.layout.MPBoard;
import org.voh.smp.boards.spaces.BaseSpace;
import lombok.ToString;
import org.voh.smp.simulation.Player;
import org.voh.smp.simulation.PlayerGroup;
import org.voh.smp.stattracker.GameStatTracker;
import org.voh.smp.utils.LuckEvent;
import org.voh.smp.utils.LuckyEventTable;
import org.voh.smp.utils.RandomUtils;

import java.util.ArrayList;

@ToString(callSuper = true)
public class LuckySpace extends BaseSpace {

    public LuckySpace(int spaceID) {
        super(spaceID);
    }

    @Override
    public boolean processEvent(MPBoard gameBoard,
                                Player currentPlayer, PlayerGroup playerGroup) {
        LuckyEventTable eventTable;
        GameStatTracker gameStatTracker = currentPlayer.getGameStatTracker();

        if (gameBoard.isKamekBoard()) {
            if (gameStatTracker.isHalfwayOver()) {
                eventTable = currentPlayer.isFirstOrSecond() ? LuckyEventTable.KAMEK_SECOND_HALF_1ST_2ND : LuckyEventTable.KAMEK_SECOND_HALF_3RD_4TH;
            }
            else {
                eventTable = currentPlayer.isFirstOrSecond() ? LuckyEventTable.KAMEK_FIRST_HALF_1ST_2ND : LuckyEventTable.KAMEK_FIRST_HALF_3RD_4TH;
            }
        }
        else {
            if (gameStatTracker.isHalfwayOver()) {
                eventTable = currentPlayer.isFirstOrSecond() ? LuckyEventTable.SECOND_HALF_1ST_2ND : LuckyEventTable.SECOND_HALF_3RD_4TH;
            }
            else {
                eventTable = currentPlayer.isFirstOrSecond() ? LuckyEventTable.FIRST_HALF_1ST_2ND : LuckyEventTable.FIRST_HALF_3RD_4TH;
            }
        }

        return processLuckyEvent(eventTable, currentPlayer, playerGroup);
    }

    private boolean processLuckyEvent(LuckyEventTable eventTable, Player currentPlayer, PlayerGroup playerGroup) {
        LuckEvent chosenEvent = new ArrayList<>(LuckyEventTable.buildEventList(eventTable)).get(RandomUtils.getRandomInt(4));

        currentPlayer.addCoins(chosenEvent.getCoinChange());

        if (chosenEvent.isAddAlly()) {
            currentPlayer.getGameStatTracker().addAlly(playerGroup);
        }

        if (0 != chosenEvent.getRivalCoinChange()) {
            playerGroup.getRandomPlayerBesidesCurrent(currentPlayer).addCoins(chosenEvent.getRivalCoinChange());
        }

        return false;
    }

}
