package boards.spaces.events;

import boards.layout.MPBoard;
import boards.spaces.BaseSpace;
import lombok.ToString;
import simulation.Player;
import simulation.PlayerGroup;
import stattracker.GameStatTracker;
import utils.LuckEvent;
import utils.LuckyEventTable;
import utils.RandomUtils;

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
            currentPlayer.getGameStatTracker().addAlly();
        }

        if (chosenEvent.getRivalCoinChange() != 0) {
            playerGroup.getRandomPlayerBesidesCurrent(currentPlayer).addCoins(chosenEvent.getRivalCoinChange());
        }

        return false;
    }

}
