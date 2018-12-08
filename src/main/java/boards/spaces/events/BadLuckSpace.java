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
    // * Give 3 coins to all other players (2)
    // * Give 5 coins to all other players (5)
    // * Lose 5 coins (4)
    // * Lose 10 coins (7)
    // * The Star moves (6)
    // * Give 5 coins to the last-place player. (3)
    // * Give 10 coins to the last-place player. (4)
    // * Give 5 coins to a random player. (2)
    // * Raise the coin cost for a Star (2)

    public BadLuckSpace(int spaceID) {
        super(spaceID);
    }

    @Override
    public void processEvent(MPBoard<BaseSpace, DefaultEdge> gameBoard,
                             GameStatTracker gameStatTracker, BaseSpace space) {

    }
}
