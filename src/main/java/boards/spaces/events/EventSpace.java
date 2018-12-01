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

import java.util.Objects;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class EventSpace extends BaseSpace {

    @Builder
    protected EventSpace(int spaceID, Set<Edge> edges) {
        super(spaceID, edges);
    }
}
