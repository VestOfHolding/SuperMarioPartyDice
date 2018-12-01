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

    public Board(List<BaseSpace> nodeList) {
        nodes = new HashMap<>();
        edges = new HashSet<>();

        for (BaseSpace node : nodeList) {
            nodes.put(node.getSpaceID(), node);
        }
    }

    public void addNode(BaseSpace node) {
        nodes.putIfAbsent(node.getSpaceID(), node);
    }

    public boolean containsNode(BaseSpace node) {
        return nodes.get(node.getSpaceID()) != null;
    }

    public BaseSpace getNode(int index) {
        return nodes.get(index);
    }

    public void setNode(int spaceId, BaseSpace newSpace) {
        BaseSpace previous = nodes.getOrDefault(spaceId, null);

        if (previous == null) {
            addNode(newSpace);
            return;
        }

        newSpace.setSpaceID(previous.getSpaceID());
        newSpace.setEdges(previous.getEdges());

        //With the new space all set up, replace it in the graph.
        getEdgesByDestination(spaceId).forEach(e -> e.setDestination(newSpace));
        nodes.put(spaceId, newSpace);
    }

    public BaseSpace getStartNode() {
        return nodes.get(0);
    }

    public int getNodesSize() {
        return nodes.size();
    }

    public void addEdge(BaseSpace from, BaseSpace to) {
        if (from.equals(to)) {
            return;
        }

        Edge edge = new Edge(from, to);
        edges.add(edge);

        from.addEdge(edge);
    }

    public boolean containsEdge(Edge edge) {
        return edge.getFrom() != null && edge.getDestination() != null && edges.contains(edge);
    }

    public Set<Edge> getEdgesByDestination(int destinationID) {
        return edges.stream()
                .filter(e -> e.getDestination().getSpaceID() == destinationID)
                .collect(Collectors.toSet());
    }
}
