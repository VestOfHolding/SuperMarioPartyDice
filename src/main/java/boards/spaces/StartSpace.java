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
public class StartSpace extends BaseSpace {
    @Builder
    public StartSpace(int spaceID, Set<Edge> edges) {
        super(spaceID, edges);
    }
}
