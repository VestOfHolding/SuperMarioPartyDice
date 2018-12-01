package boards.spaces.events;

import boards.layout.Edge;
import boards.spaces.BaseSpace;
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
public class EventSpace extends BaseSpace {

    //Three treasures event space. Known rewards:
    //1. Golden Dash Mushroom
    //2. 10 coins
    //3. ?????????

    @Builder
    protected EventSpace(int spaceID, Set<Edge> edges) {
        super(spaceID, edges);
    }
}
