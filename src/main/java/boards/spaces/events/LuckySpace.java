package boards.spaces.events;

import boards.layout.CustomSimpleDirectedGraph;
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
    // * Receive a Dash Mushroom (10)
    // * Get several Dash Mushrooms (4)
    // * Receive a Golden Dash Mushroom (6)
    // * Get several Golden Dash Mushrooms (3)
    // * Make a rival lose 5 coins (seen twice at once) (12)
    // * Make a rival lose 10 coins (1)
    // * Receive 3 coins (6)
    // * Receive 5 coins (13)
    // * Steal one ally from a rival (1)

    public LuckySpace(int spaceID) {
        super(spaceID);
    }

    @Override
    public void processEvent(CustomSimpleDirectedGraph<BaseSpace, DefaultEdge> gameBoard,
                             GameStatTracker gameStatTracker, BaseSpace space) {

    }
}
