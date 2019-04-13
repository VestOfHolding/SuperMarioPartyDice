package utils;

import org.apache.commons.lang3.Range;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public enum LuckyEventTable {
    FIRST_HALF_1ST_2ND(Arrays.asList(
            new LuckEvent(3, Range.between(1, 18)),
            new LuckEvent(3, Range.between(19, 36)),
            new LuckEvent(5, Range.between(37, 52)),
            LuckEvent.builder().rivalCoinChange(-5).chanceRange(Range.between(53, 68)).build(),
            LuckEvent.buildEmptyEvent(Range.between(69, 84)),
            LuckEvent.buildEmptyEvent(Range.between(85, 100))
    )),
    FIRST_HALF_3RD_4TH(Arrays.asList(
            new LuckEvent(5, Range.between(1, 17)),
            new LuckEvent(5, Range.between(18, 34)),
            LuckEvent.builder().rivalCoinChange(-5).chanceRange(Range.between(35, 50)).build(),
            LuckEvent.buildEmptyEvent(Range.between(51, 66)),
            LuckEvent.buildEmptyEvent(Range.between(67, 83)),
            LuckEvent.buildEmptyEvent(Range.between(84, 100))
    )),
    SECOND_HALF_1ST_2ND(Arrays.asList(
            new LuckEvent(3, Range.between(1, 17)),
            new LuckEvent(5, Range.between(18, 34)),
            LuckEvent.builder().rivalCoinChange(-5).chanceRange(Range.between(35, 51)).build(),
            LuckEvent.buildEmptyEvent(Range.between(52, 68)),
            LuckEvent.buildEmptyEvent(Range.between(69, 84)),
            LuckEvent.buildEmptyEvent(Range.between(85, 100))
    )),
    SECOND_HALF_3RD_4TH(Arrays.asList(
            new LuckEvent(5, Range.between(1, 17)),
            LuckEvent.builder().rivalCoinChange(-5).chanceRange(Range.between(18, 34)).build(),
            LuckEvent.builder().rivalCoinChange(-10).chanceRange(Range.between(35, 51)).build(),
            LuckEvent.buildEmptyEvent(Range.between(52, 68)),
            LuckEvent.buildEmptyEvent(Range.between(69, 84)),
            LuckEvent.buildEmptyEvent(Range.between(85, 100))
    )),
    KAMEK_FIRST_HALF_1ST_2ND(Arrays.asList(
            new LuckEvent(3, Range.between(1, 18)),
            new LuckEvent(3, Range.between(19, 36)),
            new LuckEvent(5, Range.between(37, 52)),
            LuckEvent.builder().rivalCoinChange(-5).chanceRange(Range.between(53, 68)).build(),
            LuckEvent.buildEmptyEvent(Range.between(69, 84)),
            LuckEvent.buildEmptyEvent(Range.between(85, 100))
    )),
    KAMEK_FIRST_HALF_3RD_4TH(Arrays.asList(
            new LuckEvent(5, Range.between(1, 17)),
            new LuckEvent(5, Range.between(18, 34)),
            LuckEvent.builder().rivalCoinChange(-5).chanceRange(Range.between(35, 50)).build(),
            LuckEvent.buildEmptyEvent(Range.between(51, 66)),
            LuckEvent.buildEmptyEvent(Range.between(67, 83)),
            LuckEvent.buildEmptyEvent(Range.between(84, 100))
    )),
    KAMEK_SECOND_HALF_1ST_2ND(Arrays.asList(
            new LuckEvent(3, Range.between(1, 17)),
            new LuckEvent(5, Range.between(18, 34)),
            LuckEvent.builder().rivalCoinChange(-5).chanceRange(Range.between(35, 51)).build(),
            LuckEvent.buildEmptyEvent(Range.between(52, 68)),
            LuckEvent.buildEmptyEvent(Range.between(69, 84)),
            new LuckEvent(7, Range.between(85, 100))
    )),
    KAMEK_SECOND_HALF_3RD_4TH(Arrays.asList(
            new LuckEvent(5, Range.between(1, 17)),
            LuckEvent.builder().rivalCoinChange(-5).chanceRange(Range.between(18, 34)).build(),
            LuckEvent.builder().rivalCoinChange(-10).chanceRange(Range.between(35, 51)).build(),
            LuckEvent.buildEmptyEvent(Range.between(52, 68)),
            new LuckEvent(10, Range.between(69, 84)),
            LuckEvent.builder().addAlly(true).chanceRange(Range.between(85, 100)).build()
    ));

    List<LuckEvent> events;

    LuckyEventTable(List<LuckEvent> events) {
        this.events = events;
    }

    public static Set<LuckEvent> buildEventList(LuckyEventTable eventTable) {
        Set<LuckEvent> result = new HashSet<>();

        do {
            int random = RandomUtils.getRandomInt(1, 100);

            LuckEvent luckEvent = eventTable.events.stream()
                    .filter(event -> event.getChanceRange().contains(random))
                    .findFirst()
                    //This can never happen, given our number ranges.
                    .orElse(null);

            result.add(luckEvent);
        } while (result.size() < 5);

        return result;
    }
}
