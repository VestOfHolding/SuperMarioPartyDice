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
public class BlueSpace extends BaseSpace{

    @Builder
    private BlueSpace(int spaceID, Set<Edge> edges) {
        super(spaceID, edges);
    }

    @Override
    public int coinGain() {
        return 3;
    }
}
