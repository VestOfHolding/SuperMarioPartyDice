package org.voh.smp.utils;

import org.apache.commons.lang3.Range;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public enum BadLuckEventTable {
    FIRST_HALF_1ST_2ND(Arrays.asList(
            new LuckEvent(-5, Range.of(1, 17)),
            new LuckEvent(-10, Range.of(18, 34)),
            LuckEvent.builder().coinChange(-5).giveCoinsToLast(true).chanceRange(Range.of(35, 51)).build(),
            LuckEvent.builder().coinChange(-10).giveCoinsToLast(true).chanceRange(Range.of(52, 68)).build(),
            LuckEvent.builder().coinChange(-5).giveCoinsToAll(true).chanceRange(Range.of(69, 84)).build(),
            new LuckEvent(true, Range.of(85, 100))
    )),
    FIRST_HALF_3RD_4TH(Arrays.asList(
            new LuckEvent(-5, Range.of(1, 16)),
            new LuckEvent(-10, Range.of(17, 32)),
            LuckEvent.builder().coinChange(-5).giveCoinsToRandom(true).chanceRange(Range.of(33, 50)).build(),
            LuckEvent.builder().coinChange(-5).giveCoinsToRandom(true).chanceRange(Range.of(51, 68)).build(),
            LuckEvent.builder().coinChange(-3).giveCoinsToAll(true).chanceRange(Range.of(69, 84)).build(),
            new LuckEvent(true, Range.of(85, 100))
    )),
    SECOND_HALF_1ST_2ND(Arrays.asList(
            new LuckEvent(-5, Range.of(1, 16)),
            new LuckEvent(-10, Range.of(17, 32)),
            LuckEvent.builder().coinChange(-10).giveCoinsToLast(true).chanceRange(Range.of(33, 48)).build(),
            LuckEvent.builder().coinChange(-5).giveCoinsToAll(true).chanceRange(Range.of(49, 66)).build(),
            LuckEvent.builder().coinChange(-5).giveCoinsToAll(true).chanceRange(Range.of(67, 84)).build(),
            new LuckEvent(true, Range.of(85, 100))
    )),
    SECOND_HALF_3RD_4TH(Arrays.asList(
            new LuckEvent(-5, Range.of(1, 17)),
            new LuckEvent(-10, Range.of(18, 34)),
            LuckEvent.builder().coinChange(-5).giveCoinsToRandom(true).chanceRange(Range.of(35, 51)).build(),
            LuckEvent.builder().coinChange(-3).giveCoinsToAll(true).chanceRange(Range.of(52, 68)).build(),
            LuckEvent.builder().doubleStarCost(true).chanceRange(Range.of(69, 84)).build(),
            new LuckEvent(true, Range.of(85, 100))
    )),
    KAMEK_FIRST_HALF_1ST_2ND(Arrays.asList(
            new LuckEvent(-5, Range.of(1, 17)),
            new LuckEvent(-10, Range.of(18, 34)),
            LuckEvent.builder().coinChange(-5).giveCoinsToLast(true).chanceRange(Range.of(35, 51)).build(),
            LuckEvent.builder().coinChange(-10).giveCoinsToLast(true).chanceRange(Range.of(52, 68)).build(),
            LuckEvent.builder().coinChange(-5).giveCoinsToAll(true).chanceRange(Range.of(69, 84)).build(),
            LuckEvent.buildEmptyEvent(Range.of(85, 100))
    )),
    KAMEK_FIRST_HALF_3RD_4TH(Arrays.asList(
            new LuckEvent(-5, Range.of(1, 16)),
            new LuckEvent(-10, Range.of(17, 32)),
            LuckEvent.builder().coinChange(-5).giveCoinsToRandom(true).chanceRange(Range.of(33, 50)).build(),
            LuckEvent.builder().coinChange(-5).giveCoinsToRandom(true).chanceRange(Range.of(51, 68)).build(),
            LuckEvent.builder().coinChange(-3).giveCoinsToAll(true).chanceRange(Range.of(69, 84)).build(),
            LuckEvent.buildEmptyEvent(Range.of(85, 100))
    )),
    KAMEK_SECOND_HALF_1ST_2ND(Arrays.asList(
            new LuckEvent(-5, Range.of(1, 16)),
            new LuckEvent(-10, Range.of(17, 32)),
            LuckEvent.builder().coinChange(-10).giveCoinsToLast(true).chanceRange(Range.of(33, 48)).build(),
            LuckEvent.builder().coinChange(-5).giveCoinsToAll(true).chanceRange(Range.of(49, 66)).build(),
            LuckEvent.builder().coinChange(-5).giveCoinsToAll(true).chanceRange(Range.of(67, 84)).build(),
            LuckEvent.buildEmptyEvent(Range.of(85, 100))
    )),
    KAMEK_SECOND_HALF_3RD_4TH(Arrays.asList(
            new LuckEvent(-5, Range.of(1, 17)),
            new LuckEvent(-10, Range.of(18, 34)),
            LuckEvent.builder().coinChange(-5).giveCoinsToRandom(true).chanceRange(Range.of(35, 51)).build(),
            LuckEvent.builder().coinChange(-3).giveCoinsToAll(true).chanceRange(Range.of(52, 68)).build(),
            LuckEvent.buildEmptyEvent(Range.of(69, 84)),
            LuckEvent.buildEmptyEvent(Range.of(85, 100))
    )),
    SUPER_BAD_LUCK_1ST_2ND(Arrays.asList(
            new LuckEvent(-20, Range.of(1, 13)),
            new LuckEvent(Integer.MIN_VALUE, Range.of(14, 26)),
            LuckEvent.builder().coinChange(-10).giveCoinsToLast(true).chanceRange(Range.of(27, 39)).build(),
            LuckEvent.builder().coinChange(-5).giveCoinsToAll(true).chanceRange(Range.of(40, 52)).build(),
            LuckEvent.builder().coinChange(-10).giveCoinsToAll(true).chanceRange(Range.of(53, 64)).build(),
            LuckEvent.buildEmptyEvent(Range.of(65, 76)),
            LuckEvent.builder().loseStar(true).chanceRange(Range.of(77, 88)).build(),
            LuckEvent.buildEmptyEvent(Range.of(89, 100))
    )),
    SUPER_BAD_LUCK_3RD_4TH(Arrays.asList(
            new LuckEvent(-5, Range.of(1, 12)),
            new LuckEvent(-10, Range.of(13, 24)),
            new LuckEvent(-20, Range.of(25, 36)),
            LuckEvent.builder().coinChange(-10).giveCoinsToRandom(true).chanceRange(Range.of(37, 50)).build(),
            LuckEvent.builder().coinChange(-10).giveCoinsToRandom(true).chanceRange(Range.of(51, 64)).build(),
            LuckEvent.builder().coinChange(-5).giveCoinsToAll(true).chanceRange(Range.of(65, 76)).build(),
            LuckEvent.builder().loseStarOrGainCoins(true).chanceRange(Range.of(77, 88)).build(),
            new LuckEvent(10, Range.of(89, 100))
    ));

    final Map<Integer, LuckEvent> eventMap = new HashMap<>();

    BadLuckEventTable(List<LuckEvent> events) {
        for (LuckEvent event : events) {
            for (int i = event.getChanceRange().getMinimum(); i <= event.getChanceRange().getMaximum(); i++) {
                eventMap.put(i, event);
            }
        }
    }

    public static Set<LuckEvent> buildEventList(BadLuckEventTable eventTable) {
        Set<LuckEvent> result = new HashSet<>(5);

        do {
            result.add(eventTable.eventMap.get(RandomUtils.getRandomInt(1, 100)));
        } while (5 > result.size());

        return result;
    }
}
