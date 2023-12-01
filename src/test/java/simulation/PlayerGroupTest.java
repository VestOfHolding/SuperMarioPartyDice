package simulation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.voh.smp.partydice.Dice;
import org.voh.smp.simulation.Player;
import org.voh.smp.simulation.PlayerGroup;
import org.voh.smp.stattracker.GameStatTracker;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerGroupTest {

    @Nested
    public class IsDieInGroup {
        List<Dice> playerDice;
        PlayerGroup group;

        @BeforeEach
        void setUp() {
            playerDice = Arrays.asList(Dice.BOO, Dice.BOWSER, Dice.MARIO, Dice.LUIGI);
            group = new PlayerGroup(Arrays.asList(
                    new Player(playerDice.get(0), new GameStatTracker(5)),
                    new Player(playerDice.get(1), new GameStatTracker(5)),
                    new Player(playerDice.get(2), new GameStatTracker(5)),
                    new Player(playerDice.get(3), new GameStatTracker(5))
            ));
        }

        @Test
        void test_True() {
            assertTrue(group.isDieInGroup(Dice.BOO));
            assertTrue(group.isDieInGroup(Dice.BOWSER));
            assertTrue(group.isDieInGroup(Dice.MARIO));
        }

        @Test
        void test_False() {
            assertFalse(group.isDieInGroup(Dice.DAISY));
            assertFalse(group.isDieInGroup(Dice.DIDDY_KONG));
            assertFalse(group.isDieInGroup(Dice.GOOMBA));
        }
    }
}