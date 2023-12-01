package org.voh.smp.utils;

import org.apache.commons.lang3.Range;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public enum BadLuckEventTable {
    FIRST_HALF_1ST_2ND(Arrays.asList(
            new LuckEvent(-5, Range.between(1, 17)),
            new LuckEvent(-10, Range.between(18, 34)),
            LuckEvent.builder().coinChange(-5).giveCoinsToLast(true).chanceRange(Range.between(35, 51)).build(),
            LuckEvent.builder().coinChange(-10).giveCoinsToLast(true).chanceRange(Range.between(52, 68)).build(),
            LuckEvent.builder().coinChange(-5).giveCoinsToAll(true).chanceRange(Range.between(69, 84)).build(),
            new LuckEvent(true, Range.between(85, 100))
    )),
    FIRST_HALF_3RD_4TH(Arrays.asList(
            new LuckEvent(-5, Range.between(1, 16)),
            new LuckEvent(-10, Range.between(17, 32)),
            LuckEvent.builder().coinChange(-5).giveCoinsToRandom(true).chanceRange(Range.between(33, 50)).build(),
            LuckEvent.builder().coinChange(-5).giveCoinsToRandom(true).chanceRange(Range.between(51, 68)).build(),
            LuckEvent.builder().coinChange(-3).giveCoinsToAll(true).chanceRange(Range.between(69, 84)).build(),
            new LuckEvent(true, Range.between(85, 100))
    )),
    SECOND_HALF_1ST_2ND(Arrays.asList(
            new LuckEvent(-5, Range.between(1, 16)),
            new LuckEvent(-10, Range.between(17, 32)),
            LuckEvent.builder().coinChange(-10).giveCoinsToLast(true).chanceRange(Range.between(33, 48)).build(),
            LuckEvent.builder().coinChange(-5).giveCoinsToAll(true).chanceRange(Range.between(49, 66)).build(),
            LuckEvent.builder().coinChange(-5).giveCoinsToAll(true).chanceRange(Range.between(67, 84)).build(),
            new LuckEvent(true, Range.between(85, 100))
    )),
    SECOND_HALF_3RD_4TH(Arrays.asList(
            new LuckEvent(-5, Range.between(1, 17)),
            new LuckEvent(-10, Range.between(18, 34)),
            LuckEvent.builder().coinChange(-5).giveCoinsToRandom(true).chanceRange(Range.between(35, 51)).build(),
            LuckEvent.builder().coinChange(-3).giveCoinsToAll(true).chanceRange(Range.between(52, 68)).build(),
            LuckEvent.builder().doubleStarCost(true).chanceRange(Range.between(69, 84)).build(),
            new LuckEvent(true, Range.between(85, 100))
    )),
    KAMEK_FIRST_HALF_1ST_2ND(Arrays.asList(
            new LuckEvent(-5, Range.between(1, 17)),
            new LuckEvent(-10, Range.between(18, 34)),
            LuckEvent.builder().coinChange(-5).giveCoinsToLast(true).chanceRange(Range.between(35, 51)).build(),
            LuckEvent.builder().coinChange(-10).giveCoinsToLast(true).chanceRange(Range.between(52, 68)).build(),
            LuckEvent.builder().coinChange(-5).giveCoinsToAll(true).chanceRange(Range.between(69, 84)).build(),
            LuckEvent.buildEmptyEvent(Range.between(85, 100))
    )),
    KAMEK_FIRST_HALF_3RD_4TH(Arrays.asList(
            new LuckEvent(-5, Range.between(1, 16)),
            new LuckEvent(-10, Range.between(17, 32)),
            LuckEvent.builder().coinChange(-5).giveCoinsToRandom(true).chanceRange(Range.between(33, 50)).build(),
            LuckEvent.builder().coinChange(-5).giveCoinsToRandom(true).chanceRange(Range.between(51, 68)).build(),
            LuckEvent.builder().coinChange(-3).giveCoinsToAll(true).chanceRange(Range.between(69, 84)).build(),
            LuckEvent.buildEmptyEvent(Range.between(85, 100))
    )),
    KAMEK_SECOND_HALF_1ST_2ND(Arrays.asList(
            new LuckEvent(-5, Range.between(1, 16)),
            new LuckEvent(-10, Range.between(17, 32)),
            LuckEvent.builder().coinChange(-10).giveCoinsToLast(true).chanceRange(Range.between(33, 48)).build(),
            LuckEvent.builder().coinChange(-5).giveCoinsToAll(true).chanceRange(Range.between(49, 66)).build(),
            LuckEvent.builder().coinChange(-5).giveCoinsToAll(true).chanceRange(Range.between(67, 84)).build(),
            LuckEvent.buildEmptyEvent(Range.between(85, 100))
    )),
    KAMEK_SECOND_HALF_3RD_4TH(Arrays.asList(
            new LuckEvent(-5, Range.between(1, 17)),
            new LuckEvent(-10, Range.between(18, 34)),
            LuckEvent.builder().coinChange(-5).giveCoinsToRandom(true).chanceRange(Range.between(35, 51)).build(),
            LuckEvent.builder().coinChange(-3).giveCoinsToAll(true).chanceRange(Range.between(52, 68)).build(),
            LuckEvent.buildEmptyEvent(Range.between(69, 84)),
            LuckEvent.buildEmptyEvent(Range.between(85, 100))
    )),
    SUPER_BAD_LUCK_1ST_2ND(Arrays.asList(
            new LuckEvent(-20, Range.between(1, 13)),
            new LuckEvent(Integer.MIN_VALUE, Range.between(14, 26)),
            LuckEvent.builder().coinChange(-10).giveCoinsToLast(true).chanceRange(Range.between(27, 39)).build(),
            LuckEvent.builder().coinChange(-5).giveCoinsToAll(true).chanceRange(Range.between(40, 52)).build(),
            LuckEvent.builder().coinChange(-10).giveCoinsToAll(true).chanceRange(Range.between(53, 64)).build(),
            LuckEvent.buildEmptyEvent(Range.between(65, 76)),
            LuckEvent.builder().loseStar(true).chanceRange(Range.between(77, 88)).build(),
            LuckEvent.buildEmptyEvent(Range.between(89, 100))
    )),
    SUPER_BAD_LUCK_3RD_4TH(Arrays.asList(
            new LuckEvent(-5, Range.between(1, 12)),
            new LuckEvent(-10, Range.between(13, 24)),
            new LuckEvent(-20, Range.between(25, 36)),
            LuckEvent.builder().coinChange(-10).giveCoinsToRandom(true).chanceRange(Range.between(37, 50)).build(),
            LuckEvent.builder().coinChange(-10).giveCoinsToRandom(true).chanceRange(Range.between(51, 64)).build(),
            LuckEvent.builder().coinChange(-5).giveCoinsToAll(true).chanceRange(Range.between(65, 76)).build(),
            LuckEvent.builder().loseStarOrGainCoins(true).chanceRange(Range.between(77, 88)).build(),
            new LuckEvent(10, Range.between(89, 100))
    ));

    final List<LuckEvent> events;

    BadLuckEventTable(List<LuckEvent> events) {
        this.events = events;
    }

    public static Set<LuckEvent> buildEventList(BadLuckEventTable eventTable) {
        Set<LuckEvent> result = new HashSet<>(5);

        do {
            int random = RandomUtils.getRandomInt(1, 100);
            result.add(eventTable.events.stream()
                    .filter(event -> event.getChanceRange().contains(random))
                    .findFirst()
                    //This can never happen, given our number ranges.
                    .orElse(null));
        } while (5 > result.size());

        return result;
    }
}
