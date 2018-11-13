package boards;

import boards.spaces.AllySpace;
import boards.spaces.BaseSpace;
import boards.spaces.BlueSpace;
import boards.spaces.EventSpace;
import boards.spaces.OtherSpace;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WhompsDominoRuinsTest {

    private static WhompsDominoRuins classUnderTest;

    @BeforeAll
    static void setup() {
        //Run the real initialization so we're dealing with the real board.
        classUnderTest = new WhompsDominoRuins();
    }

    @Test
    void getPossibleDestinations_simple() {
        BaseSpace start = classUnderTest.getStartSpace();

        List<BaseSpace> results = classUnderTest.getPossibleDestinations(start.getSpaceID(), 4);

        assertEquals(results.size(), 1);
        assertEquals(results.get(0).getSpaceID(), 4);
        assertTrue(results.get(0) instanceof EventSpace);
    }

    @Test
    void getPossibleDestinations_many() {
        BaseSpace start = classUnderTest.getStartSpace();

        List<BaseSpace> results = classUnderTest.getPossibleDestinations(start.getSpaceID(), 6);

        //The path branches twice, so three destinations should result from this.
        assertEquals(results.size(), 3);
        assertTrue(results.get(0) instanceof AllySpace);
        assertTrue(results.get(1) instanceof OtherSpace);
        assertTrue(results.get(2) instanceof BlueSpace);
    }

    @Test
    void travelToNextSpaces_simple() {
        BaseSpace start = classUnderTest.getStartSpace();

        List<BaseSpace> results = classUnderTest.travelToNextSpaces(start);

        assertEquals(results.size(), 1);
        assertEquals(results.get(0).getSpaceID(), 1);
        assertTrue(results.get(0) instanceof BlueSpace);
    }

    @Test
    void travelToNextSpaces_many() {
        //Get the first space that has more than one Edge.
        BaseSpace branchingSpace = classUnderTest.gameBoard.getNode(4);

        List<BaseSpace> results = classUnderTest.travelToNextSpaces(branchingSpace);

        assertEquals(results.size(), 2);
        assertTrue(results.get(0) instanceof EventSpace);
        assertTrue(results.get(1) instanceof BlueSpace);
    }
}