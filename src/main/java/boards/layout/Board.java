package boards.layout;

import boards.spaces.BaseSpace;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Board {
    private Map<Integer, BaseSpace> nodes;
    private Set<Edge> edges;

    public Board() {
        nodes = new HashMap<>();
        edges = new HashSet<>();
    }

    public void addNode(BaseSpace node) {
        nodes.putIfAbsent(node.getSpaceID(), node);
    }

    public BaseSpace getNode(int index) {
        return nodes.get(index);
    }

    public void addEdge(BaseSpace from, BaseSpace to) {
        if (from.equals(to)) {
            return;
        }

        Edge edge = new Edge(from, to);
        edges.add(edge);

        from.addEdge(edge);
    }
}
