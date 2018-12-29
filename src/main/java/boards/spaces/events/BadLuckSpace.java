package boards.spaces.events;

import boards.layout.MPBoard;
import boards.spaces.BaseSpace;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.jgrapht.graph.DefaultEdge;
import stattracker.GameStatTracker;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class BadLuckSpace extends EventSpace {
    //List of known possible events on Bad Luck Spaces:
    // * Give 3 coins to all other players (5)
    // * Give 5 coins to all other players (8)
    // * Lose 5 coins (10)
    // * Lose 10 coins (15)
    // * The Star moves (12)
    // * Give 5 coins to the last-place player. (5)
    // * Give 10 coins to the last-place player. (8)
    // * Give 5 coins to a random player. (5)
    // * Raise the coin cost for a Star (2)
    // * Lose one item (0)

    public BadLuckSpace(int spaceID) {
        super(spaceID);
    }

    @Override
    public boolean processEvent(MPBoard<BaseSpace, DefaultEdge> gameBoard,
                                GameStatTracker gameStatTracker, BaseSpace space) {
        return false;
    }
}
