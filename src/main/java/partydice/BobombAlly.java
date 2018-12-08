package partydice;

import java.util.Random;

public class BobombAlly {
    private static final Random RANDOM = new Random();
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
        return RANDOM.nextInt(2) * -1;
    }

    public boolean explode() {
        return countdown <= 0;
    }
}
