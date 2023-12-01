package stattracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.voh.smp.simulation.PlayerGroup;
import org.voh.smp.stattracker.GameStatTracker;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameStatTrackerTest {

    GameStatTracker classUnderTest;

    @BeforeEach
    void setUp() {
        classUnderTest = new GameStatTracker(20);
    }

    @Test
    void addAlly() {
        //At first, there shouldn't be anything there.
        assertEquals(0, classUnderTest.getAllyTotal());
        assertEquals(1, classUnderTest.getAllyGainOnTurn().size());

        assertEquals(1, classUnderTest.getAllyGainOnTurn().get(0));

        classUnderTest.setTurnNumber(5);
        classUnderTest.addAlly(new PlayerGroup(Collections.emptyList()));

        assertEquals(1, classUnderTest.getAllyTotal());
        assertEquals(2, classUnderTest.getAllyGainOnTurn().size());
        assertEquals(5, classUnderTest.getAllyGainOnTurn().get(1));
    }
}