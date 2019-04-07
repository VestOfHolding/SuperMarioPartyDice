package simulation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import partydice.Dice;
import results.DieResult;
import stattracker.GameStatTracker;

@Data
@AllArgsConstructor
public class Player {
    private Dice characterDice;

    @EqualsAndHashCode.Exclude
    private GameStatTracker gameStatTracker;

    public Player(Dice characterDice) {
        this.characterDice = characterDice;
    }

    public DieResult rollCharacterDie() {
        return characterDice.roll();
    }
}
