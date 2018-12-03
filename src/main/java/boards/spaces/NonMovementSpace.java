package boards.spaces;

import boards.layout.Edge;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class NonMovementSpace extends BaseSpace {

    @Builder
    public NonMovementSpace(int spaceID, Set<Edge> edges) {
        super(spaceID, edges);
    }

    @Override
    public boolean affectsMovement() {
        return false;
    }
}
