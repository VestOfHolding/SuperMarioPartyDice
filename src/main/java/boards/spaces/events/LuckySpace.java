package boards.spaces.events;

import boards.MPEdge;
import boards.layout.MPBoard;
import boards.spaces.BaseSpace;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import stattracker.GameStatTracker;
import utils.LuckyEvent;
import utils.LuckyEventTable;
import utils.RandomUtils;
import utils.SpaceUIClass;

import java.util.ArrayList;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class LuckySpace extends EventSpace {

    private boolean allyOptionTriggered;

    public LuckySpace(int spaceID) {
        super(spaceID);
    }

    @Override
    public boolean processEvent(MPBoard<BaseSpace, MPEdge> gameBoard,
                                GameStatTracker gameStatTracker, BaseSpace space) {
        LuckyEventTable eventTable;
        boolean coinFlip = RandomUtils.isFlippedCoinHeads();

        if (gameStatTracker.isHalfwayOver()) {
            eventTable = coinFlip ? LuckyEventTable.SECOND_HALF_1ST_2ND : LuckyEventTable.SECOND_HALF_3RD_4TH;
        }
        else {
            eventTable = coinFlip ? LuckyEventTable.FIRST_HALF_1ST_2ND : LuckyEventTable.FIRST_HALF_3RD_4TH;
        }

        return commonProcessEvent(eventTable, gameStatTracker);
    }

    @Override
    public boolean processKamekEvent(MPBoard<BaseSpace, MPEdge> gameBoard,
                                     GameStatTracker gameStatTracker, BaseSpace space) {
        LuckyEventTable eventTable;
        boolean coinFlip = RandomUtils.isFlippedCoinHeads();

        if (gameStatTracker.isHalfwayOver()) {
            eventTable = coinFlip ? LuckyEventTable.KAMEK_SECOND_HALF_1ST_2ND : LuckyEventTable.KAMEK_SECOND_HALF_3RD_4TH;
        }
        else {
            eventTable = coinFlip ? LuckyEventTable.KAMEK_FIRST_HALF_1ST_2ND : LuckyEventTable.KAMEK_FIRST_HALF_3RD_4TH;
        }

        return commonProcessEvent(eventTable, gameStatTracker);
    }

    private boolean commonProcessEvent(LuckyEventTable eventTable, GameStatTracker gameStatTracker) {
        LuckyEvent chosenEvent = new ArrayList<>(LuckyEventTable.buildEventList(eventTable)).get(RandomUtils.getRandomInt(4));

        gameStatTracker.addCoins(chosenEvent.getCoinGain());

        //Set this here so that way when the general simulation logic checks
        // if an ally needs to be added it'll find out through the same
        // overridden method as usual.
        if (chosenEvent.isAddAlly()) {
            allyOptionTriggered = true;
        }

        return false;
    }

    @Override
    public boolean addAlly() {
        if (allyOptionTriggered) {
            allyOptionTriggered = false;
            return true;
        }
        return false;
    }

    @Override
    public SpaceUIClass getNodeClass() {
        return SpaceUIClass.LUCKY;
    }
}
