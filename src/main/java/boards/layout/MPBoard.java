package boards.layout;

import boards.spaces.BaseSpace;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import java.util.HashMap;
import java.util.Map;

public class MPBoard<V extends BaseSpace, E extends DefaultEdge> extends SimpleDirectedGraph<V, E> {
    Map<Integer, V> VERTEX_MAP;

    public MPBoard(Class<? extends E> edgeClass) {
        super(edgeClass);

        VERTEX_MAP = new HashMap<>();
    }

    @Override
    public boolean addVertex(V vertex) {
        if (vertex == null || VERTEX_MAP.containsKey(vertex.getSpaceID())) {
            return false;
        }

        VERTEX_MAP.put(vertex.getSpaceID(), vertex);

        return super.addVertex(vertex);
    }

    public BaseSpace getStartSpace() {
        return getVertexById(0);
    }

    public BaseSpace getVertexById(int id) {
        return VERTEX_MAP.getOrDefault(id, null);
    }

    public int getGraphSize() {
        return vertexSet().size();
    }

    public void setOrReplaceVertex(int spaceId, V newVertex) {
        newVertex.setSpaceID(spaceId);

        V previous = VERTEX_MAP.getOrDefault(spaceId, null);

        if (previous == null) {
            addVertex(newVertex);
            return;
        }

        addVertex(newVertex);
        for (E edge : outgoingEdgesOf(previous)) {
            addEdge(newVertex, getEdgeTarget(edge), edge);
        }
        for (E edge : incomingEdgesOf(previous))  {
            addEdge(getEdgeSource(edge), newVertex, edge);
        }
        removeVertex(previous);

        VERTEX_MAP.put(spaceId, newVertex);
    }
}
