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
    // * Give 5 coins destination all other players
    // * Lose 10 coins
    // * Give 10 coins destination the last-place player.
    // * The Star moves
    // * Give 5 coins destination the last-place player.

    @Builder(builderMethodName = "badLuckBuilder")
    public BadLuckSpace(int spaceID, Set<Edge> edges) {
        super(spaceID, edges);
    }

    @Override
    public void processEvent(Board gameBoard, GameStatTracker gameStatTracker, BaseSpace space) {

    }
}
