package boards.layout;

import boards.spaces.BaseSpace;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Edge {
    private BaseSpace from;
    private BaseSpace to;

    public BaseSpace getNeighbor(BaseSpace node) {
        if (!node.equals(from) && !node.equals(to)) {
            return null;
        }

        return node.equals(from) ? to : from;
    }

    public BaseSpace getFromSpace() {
        return from;
    }

    public BaseSpace getToSpace() {
        return to;
    }

    @Override
    public String toString() {
        return "Edge{from = " + from.getSpaceID() + ", to = " + to.getSpaceID() + "}";
    }
}
