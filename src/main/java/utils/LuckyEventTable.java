package utils;

import org.apache.commons.lang3.Range;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public enum LuckyEventTable {
    FIRST_HALF_1ST_2ND(Arrays.asList(
            new LuckyEvent(3, Range.between(0, 18)),
            new LuckyEvent(3, Range.between(19, 36)),
            new LuckyEvent(5, Range.between(37, 52)),
            LuckyEvent.buildEmptyEvent(Range.between(53, 68)),
            LuckyEvent.buildEmptyEvent(Range.between(69, 84)),
            LuckyEvent.buildEmptyEvent(Range.between(85, 99))
    )),
    FIRST_HALF_3RD_4TH(Arrays.asList(
            new LuckyEvent(5, Range.between(0, 17)),
            new LuckyEvent(5, Range.between(18, 34)),
            LuckyEvent.buildEmptyEvent(Range.between(35, 50)),
            LuckyEvent.buildEmptyEvent(Range.between(50, 66)),
            LuckyEvent.buildEmptyEvent(Range.between(67, 83)),
            LuckyEvent.buildEmptyEvent(Range.between(84, 99))
    )),
    SECOND_HALF_1ST_2ND(Arrays.asList(
            new LuckyEvent(3, Range.between(0, 17)),
            new LuckyEvent(5, Range.between(18, 34)),
            LuckyEvent.buildEmptyEvent(Range.between(35, 51)),
            LuckyEvent.buildEmptyEvent(Range.between(52, 68)),
            LuckyEvent.buildEmptyEvent(Range.between(69, 84)),
            LuckyEvent.buildEmptyEvent(Range.between(85, 99))
    )),
    SECOND_HALF_3RD_4TH(Arrays.asList(
            new LuckyEvent(5, Range.between(0, 17)),
            LuckyEvent.buildEmptyEvent(Range.between(18, 34)),
            LuckyEvent.buildEmptyEvent(Range.between(35, 51)),
            LuckyEvent.buildEmptyEvent(Range.between(52, 68)),
            LuckyEvent.buildEmptyEvent(Range.between(69, 84)),
            LuckyEvent.buildEmptyEvent(Range.between(85, 99))
    )),
    KAMEK_FIRST_HALF_1ST_2ND(Arrays.asList(
            new LuckyEvent(3, Range.between(0, 18)),
            new LuckyEvent(3, Range.between(19, 36)),
            new LuckyEvent(5, Range.between(37, 52)),
            LuckyEvent.buildEmptyEvent(Range.between(53, 68)),
            LuckyEvent.buildEmptyEvent(Range.between(69, 84)),
            LuckyEvent.buildEmptyEvent(Range.between(85, 99))
    )),
    KAMEK_FIRST_HALF_3RD_4TH(Arrays.asList(
            new LuckyEvent(5, Range.between(0, 17)),
            new LuckyEvent(5, Range.between(18, 34)),
            LuckyEvent.buildEmptyEvent(Range.between(35, 50)),
            LuckyEvent.buildEmptyEvent(Range.between(50, 66)),
            LuckyEvent.buildEmptyEvent(Range.between(67, 83)),
            LuckyEvent.buildEmptyEvent(Range.between(84, 99))
    )),
    KAMEK_SECOND_HALF_1ST_2ND(Arrays.asList(
            new LuckyEvent(3, Range.between(0, 17)),
            new LuckyEvent(5, Range.between(18, 34)),
            new LuckyEvent(7, Range.between(35, 51)),
            LuckyEvent.buildEmptyEvent(Range.between(52, 68)),
            LuckyEvent.buildEmptyEvent(Range.between(69, 84)),
            LuckyEvent.buildEmptyEvent(Range.between(85, 99))
    )),
    KAMEK_SECOND_HALF_3RD_4TH(Arrays.asList(
            new LuckyEvent(5, Range.between(0, 17)),
            new LuckyEvent(10, Range.between(18, 34)),
            new LuckyEvent(true, Range.between(35, 51)),
            LuckyEvent.buildEmptyEvent(Range.between(52, 68)),
            LuckyEvent.buildEmptyEvent(Range.between(69, 84)),
            LuckyEvent.buildEmptyEvent(Range.between(85, 99))
    ));

    List<LuckyEvent> events;

    LuckyEventTable(List<LuckyEvent> events) {
        this.events = events;
    }

    public static Set<LuckyEvent> buildEventList(LuckyEventTable eventTable) {
        Set<LuckyEvent> result = new HashSet<>();

        do {
            int random = RandomUtils.getRandomInt(0, 99);

            LuckyEvent luckyEvent = eventTable.events.stream()
                    .filter(event -> event.getChanceRange().contains(random))
                    .findFirst()
                    //This can never happen, given our number ranges.
                    .orElse(null);

            result.add(luckyEvent);
        } while (result.size() < 5);

        return result;
    }
}
