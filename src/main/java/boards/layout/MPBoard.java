package boards.layout;

import boards.MPEdge;
import boards.spaces.BaseSpace;
import boards.spaces.StarSpace;
import lombok.Getter;
import lombok.Setter;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MPBoard<V extends BaseSpace, E extends MPEdge> extends SimpleDirectedWeightedGraph<V, E> {
    Map<Integer, V> VERTEX_MAP;

    private static final int kingBobombCountdownStart = 5;

    public final int INIT_STAR_COST = 10;

    private int kingBobombCountdown = 5;

    @Getter
    @Setter
    private int starCost = INIT_STAR_COST;

    @Getter
    @Setter
    private boolean needToMoveStar = false;

    @Getter
    @Setter
    private boolean isKamekBoard;

    public MPBoard(Class<? extends E> edgeClass) {
        super(edgeClass);

        VERTEX_MAP = new HashMap<>();
    }

    @Override
    public boolean addVertex(V v) {
        if (v == null || VERTEX_MAP.containsKey(v.getSpaceID())) {
            return false;
        }

        VERTEX_MAP.put(v.getSpaceID(), v);
        return super.addVertex(v);
    }

    @Override
    public boolean removeVertex(V v) {
        if (v == null || !VERTEX_MAP.containsKey(v.getSpaceID())) {
            return false;
        }

        VERTEX_MAP.remove(v.getSpaceID());
        return super.removeVertex(v);
    }

    /**
     * Star spaces only don't count towards movement, and therefore don't have an incoming edge weight,
     *  if the star is currently active, which can obviously change over the course of the game.
     *
     *  Therefore, if the target of this edge is a star space, check again if it currently affects movement.
     */
    @Override
    public double getEdgeWeight(E e) {
        double result = super.getEdgeWeight(e);

        return e.getTarget() instanceof StarSpace && !e.getTarget().affectsMovement()
                ? 0.0 : result;
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

        //Need to preserve a copy of these before we remove the vertex.
        Set<E> outgoingEdges = new HashSet<>(outgoingEdgesOf(previous));
        Set<E> incomingEdges = new HashSet<>(incomingEdgesOf(previous));

        removeVertex(previous);
        addVertex(newVertex);

        for (E edge : outgoingEdges) {
            addEdge(newVertex, getEdgeTarget(edge), edge);
        }
        for (E edge : incomingEdges)  {
            addEdge(getEdgeSource(edge), newVertex, edge);
        }
    }

    public boolean decrementCountdown() {
        kingBobombCountdown--;

        if (kingBobombCountdown <= 0) {
            kingBobombCountdown = kingBobombCountdownStart;
            return true;
        }
        return false;
    }

    public void resetCountdown() {
        kingBobombCountdown = kingBobombCountdownStart;
    }
}
