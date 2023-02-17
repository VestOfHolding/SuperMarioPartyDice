package boards.layout;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class KingBobombsBoardTest {

    @Test
    public void testDecrementCountdown() {
        KingBobombsBoard board = new KingBobombsBoard();
        assertFalse(board.decrementCountdown());
        assertFalse(board.decrementCountdown());
        assertFalse(board.decrementCountdown());
        assertFalse(board.decrementCountdown());
        assertTrue(board.decrementCountdown());
        assertFalse(board.decrementCountdown());
    }

    @Test
    public void testResetCountdown() {
        KingBobombsBoard board = new KingBobombsBoard();
        board.decrementCountdown();
        board.decrementCountdown();
        board.resetCountdown();
        assertFalse(board.decrementCountdown());
    }
}