package boards.layout;

import org.voh.smp.boards.layout.MPBoard;
import org.voh.smp.boards.spaces.BaseSpace;
import org.voh.smp.boards.spaces.StarSpace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MPBoardTest {
    private MPBoard board;
    private BaseSpace startSpace;
    private StarSpace starSpace;

    @BeforeEach
    public void setUp() {
        board = new MPBoard();
        startSpace = new BaseSpace(0);
        starSpace = new StarSpace(1, 20);
        board.addVertex(startSpace);
        board.addVertex(starSpace);
        board.addEdge(startSpace, starSpace);
    }

    @Test
    public void testAddVertex() {
        BaseSpace newSpace = new BaseSpace(2);
        assertTrue(board.addVertex(newSpace));
        assertEquals(newSpace, board.getVertexById(2));
    }

    @Test
    public void testAddVertex_Null() {
        assertFalse(board.addVertex(null));
    }

    @Test
    public void testAddVertex_AlreadyExists() {
        assertFalse(board.addVertex(startSpace));
    }

    @Test
    public void testRemoveVertex() {
        assertTrue(board.removeVertex(starSpace));
        assertNull(board.getVertexById(1));
    }

    @Test
    public void testRemoveVertex_Null() {
        assertFalse(board.removeVertex(null));
    }

    @Test
    public void testRemoveVertex_NotExists() {
        BaseSpace newSpace = new BaseSpace(2);
        assertFalse(board.removeVertex(newSpace));
    }

    @Test
    public void testGetStartSpace() {
        assertEquals(startSpace, board.getStartSpace());
    }

    @Test
    public void testGetVertexById() {
        assertEquals(startSpace, board.getVertexById(0));
    }

    @Test
    public void testGetVertexById_Null() {
        assertNull(board.getVertexById(2));
    }

    @Test
    public void testGetGraphSize() {
        assertEquals(2, board.getGraphSize());
    }

    @Test
    public void testInitStarDistances() {
        board.initStarDistances();
        assertEquals(2, board.getStarDistance(starSpace, startSpace));
    }
}
