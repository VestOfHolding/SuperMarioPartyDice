package utils;

import java.util.Random;

public class RandomUtils {
    public static final Random RANDOM = new Random();

    public static int getRandomInt(int max) {
        return RANDOM.nextInt(max + 1);
    }

    public static int getRandomInt(int min, int max) {
        return RANDOM.nextInt((max - min) + 1) + min;
    }

    public static boolean isFlippedCoinHeads() {
        return getRandomInt(1) == 1;
    }
}
