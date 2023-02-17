package partydice;

import utils.RandomUtils;

public class BobombAlly {
    private static final int MAX_TURNS = 3;

    private int countdown;

    public BobombAlly() {
        countdown = MAX_TURNS;
    }

    /**
     * Bo-bomb allies roll either 0 or -1.
     */
    public int rollBobombAlly() {
        countdown--;
        return RandomUtils.getRandomInt(1) * -1;
    }

    public boolean explode() {
        return 0 >= countdown;
    }
}
