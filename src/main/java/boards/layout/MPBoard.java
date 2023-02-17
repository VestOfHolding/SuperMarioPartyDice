package boards.layout;

import boards.MPEdge;
import boards.spaces.BaseSpace;
import boards.spaces.StarSpace;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.MapUtils;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.util.HashMap;
import java.util.Map;

public class MPBoard extends SimpleDirectedWeightedGraph<BaseSpace, MPEdge> {
    private final Map<Integer, BaseSpace> VERTEX_MAP;

    private final Map<Integer, Map<Integer, Integer>> starDistanceMap = new HashMap<>(64);

    public final int INIT_STAR_COST = 10;

    @Getter
    @Setter
    private int starCost = INIT_STAR_COST;

    @Getter
    @Setter
    private boolean needToMoveStar;

    @Getter
    @Setter
    private boolean isKamekBoard;

    public MPBoard() {
        super(MPEdge.class);

        VERTEX_MAP = new HashMap<>(64);
    }

    @Override
    public boolean addVertex(BaseSpace v) {
        if (null == v || VERTEX_MAP.containsKey(v.getSpaceID())) {
            return false;
        }

        VERTEX_MAP.put(v.getSpaceID(), v);
        return super.addVertex(v);
    }

    @Override
    public boolean removeVertex(BaseSpace v) {
        if (null == v || !VERTEX_MAP.containsKey(v.getSpaceID())) {
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

    public void initStarDistances() {
        if (MapUtils.isNotEmpty(starDistanceMap)) {
            return;
        }
        for (StarSpace starSpace : VERTEX_MAP.values().stream()
                .filter(s -> s instanceof StarSpace)
                .map(s -> (StarSpace)s)
                .toList()) {
            Map<Integer, Integer> distanceMap = new HashMap<>(64);

            VERTEX_MAP.values().forEach(vertex -> distanceMap.put(vertex.getSpaceID(), Integer.MAX_VALUE));

            setDistanceToStar(distanceMap, starSpace, 1);
            starDistanceMap.put(starSpace.getSpaceID(), distanceMap);
        }
    }
    
    private void setDistanceToStar(Map<Integer, Integer> distanceMap, BaseSpace currentSpace, int distance) {
        //Base case is we arrive at a space where a better route is already documented.
        if (distanceMap.get(currentSpace.getSpaceID()) <= distance) {
            return;
        }
        distanceMap.put(currentSpace.getSpaceID(), distance);

        for (MPEdge edge : incomingEdgesOf(currentSpace)) {
            setDistanceToStar(distanceMap, edge.getSource(),
                    currentSpace.affectsMovement() ? distance + 1 : distance);
        }
    }

    public int getStarDistance(StarSpace currentStar, BaseSpace currentSpace) {
        return starDistanceMap.get(currentStar.getSpaceID()).get(currentSpace.getSpaceID());
    }
}
