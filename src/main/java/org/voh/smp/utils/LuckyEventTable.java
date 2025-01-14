package org.voh.smp.utils;

import org.apache.commons.lang3.Range;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public enum LuckyEventTable {
    FIRST_HALF_1ST_2ND(Arrays.asList(
            new LuckEvent(3, Range.of(1, 18)),
            new LuckEvent(3, Range.of(19, 36)),
            new LuckEvent(5, Range.of(37, 52)),
            LuckEvent.builder().rivalCoinChange(-5).chanceRange(Range.of(53, 68)).build(),
            LuckEvent.buildEmptyEvent(Range.of(69, 84)),
            LuckEvent.buildEmptyEvent(Range.of(85, 100))
    )),
    FIRST_HALF_3RD_4TH(Arrays.asList(
            new LuckEvent(5, Range.of(1, 17)),
            new LuckEvent(5, Range.of(18, 34)),
            LuckEvent.builder().rivalCoinChange(-5).chanceRange(Range.of(35, 50)).build(),
            LuckEvent.buildEmptyEvent(Range.of(51, 66)),
            LuckEvent.buildEmptyEvent(Range.of(67, 83)),
            LuckEvent.buildEmptyEvent(Range.of(84, 100))
    )),
    SECOND_HALF_1ST_2ND(Arrays.asList(
            new LuckEvent(3, Range.of(1, 17)),
            new LuckEvent(5, Range.of(18, 34)),
            LuckEvent.builder().rivalCoinChange(-5).chanceRange(Range.of(35, 51)).build(),
            LuckEvent.buildEmptyEvent(Range.of(52, 68)),
            LuckEvent.buildEmptyEvent(Range.of(69, 84)),
            LuckEvent.buildEmptyEvent(Range.of(85, 100))
    )),
    SECOND_HALF_3RD_4TH(Arrays.asList(
            new LuckEvent(5, Range.of(1, 17)),
            LuckEvent.builder().rivalCoinChange(-5).chanceRange(Range.of(18, 34)).build(),
            LuckEvent.builder().rivalCoinChange(-10).chanceRange(Range.of(35, 51)).build(),
            LuckEvent.buildEmptyEvent(Range.of(52, 68)),
            LuckEvent.buildEmptyEvent(Range.of(69, 84)),
            LuckEvent.buildEmptyEvent(Range.of(85, 100))
    )),
    KAMEK_FIRST_HALF_1ST_2ND(Arrays.asList(
            new LuckEvent(3, Range.of(1, 18)),
            new LuckEvent(3, Range.of(19, 36)),
            new LuckEvent(5, Range.of(37, 52)),
            LuckEvent.builder().rivalCoinChange(-5).chanceRange(Range.of(53, 68)).build(),
            LuckEvent.buildEmptyEvent(Range.of(69, 84)),
            LuckEvent.buildEmptyEvent(Range.of(85, 100))
    )),
    KAMEK_FIRST_HALF_3RD_4TH(Arrays.asList(
            new LuckEvent(5, Range.of(1, 17)),
            new LuckEvent(5, Range.of(18, 34)),
            LuckEvent.builder().rivalCoinChange(-5).chanceRange(Range.of(35, 50)).build(),
            LuckEvent.buildEmptyEvent(Range.of(51, 66)),
            LuckEvent.buildEmptyEvent(Range.of(67, 83)),
            LuckEvent.buildEmptyEvent(Range.of(84, 100))
    )),
    KAMEK_SECOND_HALF_1ST_2ND(Arrays.asList(
            new LuckEvent(3, Range.of(1, 17)),
            new LuckEvent(5, Range.of(18, 34)),
            LuckEvent.builder().rivalCoinChange(-5).chanceRange(Range.of(35, 51)).build(),
            LuckEvent.buildEmptyEvent(Range.of(52, 68)),
            LuckEvent.buildEmptyEvent(Range.of(69, 84)),
            new LuckEvent(7, Range.of(85, 100))
    )),
    KAMEK_SECOND_HALF_3RD_4TH(Arrays.asList(
            new LuckEvent(5, Range.of(1, 17)),
            LuckEvent.builder().rivalCoinChange(-5).chanceRange(Range.of(18, 34)).build(),
            LuckEvent.builder().rivalCoinChange(-10).chanceRange(Range.of(35, 51)).build(),
            LuckEvent.buildEmptyEvent(Range.of(52, 68)),
            new LuckEvent(10, Range.of(69, 84)),
            LuckEvent.builder().addAlly(true).chanceRange(Range.of(85, 100)).build()
    ));

    final Map<Integer, LuckEvent> eventMap = new HashMap<>();

    LuckyEventTable(List<LuckEvent> events) {
        for (LuckEvent event : events) {
            for (int i = event.getChanceRange().getMinimum(); i <= event.getChanceRange().getMaximum(); i++) {
                eventMap.put(i, event);
            }
        }
    }

    public static Set<LuckEvent> buildEventList(LuckyEventTable eventTable) {
        Set<LuckEvent> result = new HashSet<>(5);

        do {
            result.add(eventTable.eventMap.get(RandomUtils.getRandomInt(1, 100)));
        } while (5 > result.size());

        return result;
    }
}
