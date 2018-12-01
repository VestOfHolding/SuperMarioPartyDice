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
    private BaseSpace destination;

    public BaseSpace getNeighbor(BaseSpace node) {
        if (!node.equals(from) && !node.equals(destination)) {
            return null;
        }

        return node.equals(from) ? destination : from;
    }

    public BaseSpace getFromSpace() {
        return from;
    }

    public BaseSpace getToSpace() {
        return destination;
    }

    @Override
    public String toString() {
        return "Edge{from = " + from.getSpaceID() + ", destination = " + destination.getSpaceID() + "}";
    }
}
