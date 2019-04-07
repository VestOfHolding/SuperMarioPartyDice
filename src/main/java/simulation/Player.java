package simulation;

import boards.spaces.BaseSpace;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import partydice.Dice;
import results.DieResult;
import stattracker.GameStatTracker;

@Data
@AllArgsConstructor
public class Player {
    private Dice characterDice;

    @EqualsAndHashCode.Exclude
    private GameStatTracker gameStatTracker;

    @EqualsAndHashCode.Exclude
    private BaseSpace currentSpace;

    public Player(Dice characterDice) {
        this(characterDice, null);
    }

    public Player(Dice characterDice, GameStatTracker gameStatTracker) {
        this.characterDice = characterDice;
        this.gameStatTracker = gameStatTracker;
        this.currentSpace = null;
    }

    public DieResult rollCharacterDie() {
        return characterDice.roll();
    }
}
