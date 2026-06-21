package org.voh.smp.utils;

import java.util.SplittableRandom;
import java.util.concurrent.ThreadLocalRandom;

public final class RandomUtils {
    private RandomUtils() { }

    private static final ThreadLocal<SplittableRandom> RNG =
            ThreadLocal.withInitial(SplittableRandom::new);

    public static void newStream() {
        RNG.set(new SplittableRandom());
    }

    /**
     * @param max
     * @return An integer between 0 and the max parameter given.
     */
    public static int getRandomInt(int max) {
        return RNG.get().nextInt(max + 1);
    }

    public static int getRandomInt(int min, int max) {
        return RNG.get().nextInt((max - min) + 1) + min;
    }

    /**
     * @return an int in [0, boundExclusive) -- the standard exclusive-bound form.
     * Prefer this in new hot-path code (e.g. the CSR junction picker) to avoid the +1 footgun.
     */
    public static int nextIntExclusive(int boundExclusive) {
        return RNG.get().nextInt(boundExclusive);
    }

    public static boolean isFlippedCoinHeads() {
        return RNG.get().nextBoolean();
    }
}
