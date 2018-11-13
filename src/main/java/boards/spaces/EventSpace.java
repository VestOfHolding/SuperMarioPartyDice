package boards.spaces;

import boards.layout.Edge;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class EventSpace extends BaseSpace {
    private Integer spaceToMoveToID;

    @Builder
    private EventSpace(int spaceID, Set<Edge> edges, Integer spaceToMoveToID) {
        super(spaceID, edges);
        this.spaceToMoveToID = Objects.requireNonNullElse(spaceToMoveToID, -1);
    }

    @Override
    public int moveToSpace() {
        return spaceToMoveToID;
    }
}
