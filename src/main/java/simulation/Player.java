package simulation;

import lombok.Data;
import partydice.Dice;
import results.DieResult;
import stattracker.GameStatTracker;

@Data
public class Player {
    private Dice characterDice;

    private GameStatTracker gameStatTracker;

    public DieResult rollCharacterDie() {
        return characterDice.roll();
    }
}
