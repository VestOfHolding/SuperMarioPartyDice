package stattracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import partydice.Dice;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class GameStatTrackerTest {

    GameStatTracker classUnderTest;

    @BeforeEach
    void setUp() {
        classUnderTest = new GameStatTracker(Dice.BOWSER);
    }

    @Test
    void addAlly() {
        //At first, there shouldn't be anything there.
        assertEquals(0, classUnderTest.getAllyTotal());
        assertEquals(1, classUnderTest.getAllyGainOnTurn().size());
        assertTrue(Optional.ofNullable(classUnderTest.getAllyGainOnTurn().get(0)).isPresent());
        assertEquals(Optional.of(1), Optional.ofNullable(classUnderTest.getAllyGainOnTurn().get(0)));

        int turnGainedFirstAlly = 5;

        classUnderTest.addAlly(turnGainedFirstAlly);

        assertEquals(1, classUnderTest.getAllyTotal());
        assertEquals(2, classUnderTest.getAllyGainOnTurn().size());
        assertTrue(Optional.ofNullable(classUnderTest.getAllyGainOnTurn().get(1)).isPresent());
        assertEquals(Optional.of(turnGainedFirstAlly), Optional.ofNullable(classUnderTest.getAllyGainOnTurn().get(1)));
    }
}