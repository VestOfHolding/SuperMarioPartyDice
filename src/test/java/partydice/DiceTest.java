package partydice;

import org.junit.jupiter.api.Test;
import org.voh.smp.partydice.Dice;
import org.voh.smp.simulation.Player;
import org.voh.smp.simulation.PlayerGroup;
import org.voh.smp.stattracker.GameStatTracker;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DiceTest {

    @Test
    void getRandomCharacterDieNotInGroup() {
        List<Dice> playerDice = Arrays.asList(Dice.BOO, Dice.BOWSER, Dice.MARIO, Dice.LUIGI);
        PlayerGroup group = new PlayerGroup(Arrays.asList(
                new Player(playerDice.get(0), new GameStatTracker(5)),
                new Player(playerDice.get(1), new GameStatTracker(5)),
                new Player(playerDice.get(2), new GameStatTracker(5)),
                new Player(playerDice.get(3), new GameStatTracker(5))
        ));

        for (int i = 0; 40 > i; ++i) {
            Dice result = Dice.getRandomCharacterDieNotInGroup(group);
            assertFalse(playerDice.contains(result));
        }
    }
}