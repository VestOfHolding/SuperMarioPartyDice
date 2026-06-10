package org.voh.smp.utils;

import java.util.concurrent.ThreadLocalRandom;

public final class RandomUtils {
    private RandomUtils() { }

    /**
     * @param max
     * @return An integer between 0 and the max parameter given.
     */
    public static int getRandomInt(int max) {
        return ThreadLocalRandom.current().nextInt(max + 1);
    }

    public static int getRandomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt((max - min) + 1) + min;
    }

    /**
     * @return an int in [0, boundExclusive) -- the standard exclusive-bound form.
     * Prefer this in new hot-path code (e.g. the CSR junction picker) to avoid the +1 footgun.
     */
    public static int nextIntExclusive(int boundExclusive) {
        return ThreadLocalRandom.current().nextInt(boundExclusive);
    }

    public static boolean isFlippedCoinHeads() {
        return ThreadLocalRandom.current().nextBoolean();
    }
}
