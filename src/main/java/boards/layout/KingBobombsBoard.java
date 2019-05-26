package boards.layout;

public class KingBobombsBoard extends MPBoard {
    private static final int kingBobombCountdownStart = 5;

    private int kingBobombCountdown = 5;

    public KingBobombsBoard() {
        super();
        setKamekBoard(false);
    }

    public boolean decrementCountdown() {
        kingBobombCountdown--;

        if (kingBobombCountdown <= 0) {
            kingBobombCountdown = kingBobombCountdownStart;
            return true;
        }
        return false;
    }

    public void resetCountdown() {
        kingBobombCountdown = kingBobombCountdownStart;
    }
}
