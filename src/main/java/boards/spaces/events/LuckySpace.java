package boards.spaces.events;

import boards.MPEdge;
import boards.layout.MPBoard;
import boards.spaces.BaseSpace;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import simulation.Player;
import simulation.PlayerGroup;
import stattracker.GameStatTracker;
import utils.LuckEvent;
import utils.LuckyEventTable;
import utils.RandomUtils;
import utils.SpaceUIClass;

import java.util.ArrayList;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class LuckySpace extends EventSpace {

    public LuckySpace(int spaceID) {
        super(spaceID);
    }

    @Override
    public boolean processEvent(MPBoard<BaseSpace, MPEdge> gameBoard,
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

        return processLuckyEvent(eventTable, gameStatTracker);
    }

    private boolean processLuckyEvent(LuckyEventTable eventTable, GameStatTracker gameStatTracker) {
        LuckEvent chosenEvent = new ArrayList<>(LuckyEventTable.buildEventList(eventTable)).get(RandomUtils.getRandomInt(4));

        gameStatTracker.addCoins(chosenEvent.getCoinGain());

        if (chosenEvent.isAddAlly()) {
            gameStatTracker.addAlly();
        }

        return false;
    }

    @Override
    public SpaceUIClass getNodeClass() {
        return SpaceUIClass.LUCKY;
    }
}
