package boards.spaces.events;

import boards.MPEdge;
import boards.layout.MPBoard;
import boards.spaces.BaseSpace;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import stattracker.GameStatTracker;
import utils.BadLuckEventTable;
import utils.LuckEvent;
import utils.RandomUtils;
import utils.SpaceUIClass;

import java.util.ArrayList;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class BadLuckSpace extends EventSpace {

    public BadLuckSpace(int spaceID) {
        super(spaceID);
    }

    @Override
    public boolean processEvent(MPBoard<BaseSpace, MPEdge> gameBoard,
                                GameStatTracker gameStatTracker) {
        BadLuckEventTable eventTable;
        boolean coinFlip = RandomUtils.isFlippedCoinHeads();

        if (gameStatTracker.isLastThreeTurns()) {
            eventTable = getExtraBadLuckTable(coinFlip);
        }
        else if (gameStatTracker.isHalfwayOver()) {
            eventTable = coinFlip ? BadLuckEventTable.SECOND_HALF_1ST_2ND : BadLuckEventTable.SECOND_HALF_3RD_4TH;
        }
        else {
            eventTable = coinFlip ? BadLuckEventTable.FIRST_HALF_1ST_2ND : BadLuckEventTable.FIRST_HALF_3RD_4TH;
        }

        return commonProcessEvent(eventTable, gameStatTracker);
    }

    @Override
    public boolean processKamekEvent(MPBoard<BaseSpace, MPEdge> gameBoard,
                                     GameStatTracker gameStatTracker) {
        BadLuckEventTable eventTable;
        boolean coinFlip = RandomUtils.isFlippedCoinHeads();

        if (gameStatTracker.isLastThreeTurns()) {
            eventTable = getExtraBadLuckTable(coinFlip);
        }
        else if (gameStatTracker.isHalfwayOver()) {
            eventTable = coinFlip ? BadLuckEventTable.KAMEK_SECOND_HALF_1ST_2ND : BadLuckEventTable.KAMEK_SECOND_HALF_3RD_4TH;
        }
        else {
            eventTable = coinFlip ? BadLuckEventTable.KAMEK_FIRST_HALF_1ST_2ND : BadLuckEventTable.KAMEK_FIRST_HALF_3RD_4TH;
        }

        return commonProcessEvent(eventTable, gameStatTracker);
    }

    private BadLuckEventTable getExtraBadLuckTable(boolean coinFlip) {
        return coinFlip ? BadLuckEventTable.SUPER_BAD_LUCK_1ST_2ND : BadLuckEventTable.SUPER_BAD_LUCK_3RD_4TH;
    }

    private boolean commonProcessEvent(BadLuckEventTable eventTable, GameStatTracker gameStatTracker) {
        LuckEvent chosenEvent = new ArrayList<>(BadLuckEventTable.buildEventList(eventTable)).get(RandomUtils.getRandomInt(4));

        if (chosenEvent.getCoinGain() == Integer.MIN_VALUE) {
            gameStatTracker.addCoins(-(gameStatTracker.getCoinTotal() / 2));
        }
        else {
            gameStatTracker.addCoins(chosenEvent.getCoinGain());
        }

        return false;
    }

    @Override
    public SpaceUIClass getNodeClass() {
        return SpaceUIClass.BADLUCK;
    }
}
