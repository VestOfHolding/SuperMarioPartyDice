package boards.layout;

import boards.MPEdge;
import boards.spaces.BaseSpace;
import boards.spaces.StarSpace;
import lombok.Getter;
import lombok.Setter;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.util.HashMap;
import java.util.Map;

public class MPBoard extends SimpleDirectedWeightedGraph<BaseSpace, MPEdge> {
    Map<Integer, BaseSpace> VERTEX_MAP;

    public final int INIT_STAR_COST = 10;

    @Getter
    @Setter
    private int starCost = INIT_STAR_COST;

    @Getter
    @Setter
    private boolean needToMoveStar = false;

    @Getter
    @Setter
    private boolean isKamekBoard;

    public MPBoard() {
        super(MPEdge.class);

        VERTEX_MAP = new HashMap<>();
    }

    @Override
    public boolean addVertex(BaseSpace v) {
        if (v == null || VERTEX_MAP.containsKey(v.getSpaceID())) {
            return false;
        }

        VERTEX_MAP.put(v.getSpaceID(), v);
        return super.addVertex(v);
    }

    @Override
    public boolean removeVertex(BaseSpace v) {
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
    public double getEdgeWeight(MPEdge e) {
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

    public void resetStarDistanceCounts(BaseSpace starSpace) {
        VERTEX_MAP.values().parallelStream()
                .filter(vertex -> vertex.getDistanceToStar() != Integer.MAX_VALUE)
                .forEach(vertex -> vertex.setDistanceToStar(Integer.MAX_VALUE));

        setDistanceToStar(starSpace, 1);
    }

    /**
     * This method is the CPU bottleneck of the program. Refactoring this method
     *  is the key to better CPU performance right now.
     */
    public void setDistanceToStar(BaseSpace currentSpace, int distance) {
        //Base case is we arrive at a space where a better route is already documented.
        if (currentSpace.getDistanceToStar() <= distance) {
            return;
        }
        currentSpace.setDistanceToStar(distance);

        for (MPEdge edge : incomingEdgesOf(currentSpace)) {
            setDistanceToStar(edge.getSource(),
                    currentSpace.affectsMovement() ? distance + 1 : distance);
        }
    }
}
