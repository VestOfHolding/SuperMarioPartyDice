package boards.spaces.events;

import boards.layout.Board;
import boards.layout.Edge;
import boards.spaces.BaseSpace;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import stattracker.GameStatTracker;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class BadLuckSpace extends EventSpace {
    //List of known possible events on Bad Luck Spaces:
    // * Give 3 coins to all other players (1)
    // * Give 5 coins to all other players (2)
    // * Lose 10 coins (3)
    // * The Star moves (3)
    // * Give 5 coins to the last-place player. (2)
    // * Give 10 coins to the last-place player. (2)
    // * Give 5 coins to a random player. (1)
    // * Raise the coin cost for a Star (1)

    @Builder(builderMethodName = "badLuckBuilder")
    public BadLuckSpace(int spaceID, Set<Edge> edges) {
        super(spaceID, edges);
    }

    @Override
    public void processEvent(Board gameBoard, GameStatTracker gameStatTracker, BaseSpace space) {

    }
}
