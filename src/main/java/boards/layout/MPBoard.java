package boards.layout;

import boards.MPEdge;
import boards.spaces.BaseSpace;
import boards.spaces.StarSpace;
import lombok.Getter;
import lombok.Setter;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.util.HashMap;
import java.util.Map;

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

    public void resetStarDistanceCounts(V starSpace) {
        for (V vertex : VERTEX_MAP.values()) {
            vertex.setDistanceToStar(Integer.MAX_VALUE);
        }

        setDistanceToStar(starSpace, 1);
    }

    public void setDistanceToStar(V currentSpace, int distance) {
        if (currentSpace.getDistanceToStar() != Integer.MAX_VALUE) {
            return;
        }
        currentSpace.setDistanceToStar(distance);

        for (MPEdge edge : incomingEdgesOf(currentSpace)) {
            setDistanceToStar((V)edge.getSource(),
                    currentSpace.affectsMovement() ? distance + 1 : distance);
        }
    }
}
