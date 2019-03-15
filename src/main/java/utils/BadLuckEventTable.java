package utils;

import org.apache.commons.lang3.Range;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public enum BadLuckEventTable {
    FIRST_HALF_1ST_2ND(Arrays.asList(
            new LuckEvent(-5, Range.between(1, 17)),
            new LuckEvent(-10, Range.between(18, 34)),
            LuckEvent.buildEmptyEvent(Range.between(35, 51)),
            LuckEvent.buildEmptyEvent(Range.between(52, 68)),
            LuckEvent.buildEmptyEvent(Range.between(69, 84)),
            LuckEvent.buildEmptyEvent(Range.between(85, 100))
    )),
    FIRST_HALF_3RD_4TH(Arrays.asList(
            new LuckEvent(-5, Range.between(1, 16)),
            new LuckEvent(-10, Range.between(17, 32)),
            LuckEvent.buildEmptyEvent(Range.between(33, 50)),
            LuckEvent.buildEmptyEvent(Range.between(51, 68)),
            LuckEvent.buildEmptyEvent(Range.between(69, 84)),
            LuckEvent.buildEmptyEvent(Range.between(85, 100))
    )),
    SECOND_HALF_1ST_2ND(Arrays.asList(
            new LuckEvent(-5, Range.between(1, 16)),
            new LuckEvent(-10, Range.between(17, 32)),
            LuckEvent.buildEmptyEvent(Range.between(33, 48)),
            LuckEvent.buildEmptyEvent(Range.between(49, 66)),
            LuckEvent.buildEmptyEvent(Range.between(67, 84)),
            LuckEvent.buildEmptyEvent(Range.between(85, 100))
    )),
    SECOND_HALF_3RD_4TH(Arrays.asList(
            new LuckEvent(-5, Range.between(1, 17)),
            new LuckEvent(-10, Range.between(18, 34)),
            LuckEvent.buildEmptyEvent(Range.between(35, 51)),
            LuckEvent.buildEmptyEvent(Range.between(52, 68)),
            LuckEvent.buildEmptyEvent(Range.between(69, 84)),
            LuckEvent.buildEmptyEvent(Range.between(85, 100))
    )),
    KAMEK_FIRST_HALF_1ST_2ND(Arrays.asList(
            new LuckEvent(-5, Range.between(1, 17)),
            new LuckEvent(-10, Range.between(18, 34)),
            LuckEvent.buildEmptyEvent(Range.between(35, 51)),
            LuckEvent.buildEmptyEvent(Range.between(52, 68)),
            LuckEvent.buildEmptyEvent(Range.between(69, 84)),
            LuckEvent.buildEmptyEvent(Range.between(85, 100))
    )),
    KAMEK_FIRST_HALF_3RD_4TH(Arrays.asList(
            new LuckEvent(-5, Range.between(1, 16)),
            new LuckEvent(-10, Range.between(17, 32)),
            LuckEvent.buildEmptyEvent(Range.between(33, 50)),
            LuckEvent.buildEmptyEvent(Range.between(51, 68)),
            LuckEvent.buildEmptyEvent(Range.between(69, 84)),
            LuckEvent.buildEmptyEvent(Range.between(85, 100))
    )),
    KAMEK_SECOND_HALF_1ST_2ND(Arrays.asList(
            new LuckEvent(-5, Range.between(1, 16)),
            new LuckEvent(-10, Range.between(17, 32)),
            LuckEvent.buildEmptyEvent(Range.between(33, 48)),
            LuckEvent.buildEmptyEvent(Range.between(49, 66)),
            LuckEvent.buildEmptyEvent(Range.between(67, 84)),
            LuckEvent.buildEmptyEvent(Range.between(85, 100))
    )),
    KAMEK_SECOND_HALF_3RD_4TH(Arrays.asList(
            new LuckEvent(-5, Range.between(1, 17)),
            new LuckEvent(-10, Range.between(18, 34)),
            LuckEvent.buildEmptyEvent(Range.between(35, 51)),
            LuckEvent.buildEmptyEvent(Range.between(52, 68)),
            LuckEvent.buildEmptyEvent(Range.between(69, 84)),
            LuckEvent.buildEmptyEvent(Range.between(85, 100))
    )),
    SUPER_BAD_LUCK_1ST_2ND(Arrays.asList(
            new LuckEvent(-20, Range.between(1, 13)),
            new LuckEvent(Integer.MIN_VALUE, Range.between(14, 26)),
            LuckEvent.buildEmptyEvent(Range.between(27, 39)),
            LuckEvent.buildEmptyEvent(Range.between(40, 52)),
            LuckEvent.buildEmptyEvent(Range.between(53, 64)),
            LuckEvent.buildEmptyEvent(Range.between(65, 76)),
            LuckEvent.buildEmptyEvent(Range.between(77, 88)),
            LuckEvent.buildEmptyEvent(Range.between(89, 100))
    )),
    SUPER_BAD_LUCK_3RD_4TH(Arrays.asList(
            new LuckEvent(-5, Range.between(1, 12)),
            new LuckEvent(-10, Range.between(13, 24)),
            new LuckEvent(-20, Range.between(25, 36)),
            LuckEvent.buildEmptyEvent(Range.between(37, 50)),
            LuckEvent.buildEmptyEvent(Range.between(51, 64)),
            LuckEvent.buildEmptyEvent(Range.between(65, 76)),
            LuckEvent.buildEmptyEvent(Range.between(77, 88)),
            new LuckEvent(10, Range.between(89, 100))
    ));

    List<LuckEvent> events;

    BadLuckEventTable(List<LuckEvent> events) {
        this.events = events;
    }

    public static Set<LuckEvent> buildEventList(BadLuckEventTable eventTable) {
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
