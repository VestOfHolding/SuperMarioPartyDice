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
public class LuckySpace extends EventSpace {
    //List of known possible events on Lucky Spaces:
    // * Receive a Dash Mushroom (12)
    // * Get several Dash Mushrooms (7)
    // * Receive a Golden Dash Mushroom (6)
    // * Get several Golden Dash Mushrooms (4)
    // * Make a rival lose 5 coins (seen twice at once) (15)
    // * Make a rival lose 10 coins (2)
    // * Receive 3 coins (7)
    // * Receive 5 coins (16)
    // * Steal one ally from a rival (2)

    public LuckySpace(int spaceID) {
        super(spaceID);
    }

    @Override
    public boolean processEvent(MPBoard<BaseSpace, DefaultEdge> gameBoard,
                                GameStatTracker gameStatTracker, BaseSpace space) {
        return false;
    }
}
