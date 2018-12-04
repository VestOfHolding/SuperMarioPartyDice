package boards.spaces;

import boards.layout.Board;
import boards.layout.Edge;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;
import stattracker.GameStatTracker;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class BaseSpace {

    protected int spaceID;

    @EqualsAndHashCode.Exclude
    private Set<Edge> edges;

    public BaseSpace(int spaceID) {
        edges = new HashSet<>();
        this.spaceID = spaceID;
    }

    public int coinGain() {
        return 0;
    }

    public boolean addPartner() {
        return false;
    }

    public int moveToSpace() {
        return -1;
    }

    public void processEvent(Board gameBoard, GameStatTracker gameStatTracker, BaseSpace space) { }

    public boolean affectsMovement() {
        return true;
    }

    public boolean isPassingEvent() {
        return false;
    }

    public void addEdge(Edge edge) {
        if (CollectionUtils.isEmpty(edges)) {
            edges = new HashSet<>();
        }

        edges.add(edge);
    }

    public boolean containsEdge(Edge edge) {
        return edges.contains(edge);
    }

    public int getNeighborCount() {
        return edges.size();
    }
}
