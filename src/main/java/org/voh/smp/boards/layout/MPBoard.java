package org.voh.smp.boards.layout;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.jgrapht.Graphs;
import org.voh.smp.boards.MPEdge;
import org.voh.smp.boards.spaces.BaseSpace;
import org.voh.smp.boards.spaces.StarSpace;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.MapUtils;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.voh.smp.utils.RandomUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MPBoard extends SimpleDirectedWeightedGraph<BaseSpace, MPEdge> {
    private final Map<Integer, BaseSpace> VERTEX_MAP;

    private final Map<Integer, Map<Integer, Integer>> starDistanceMap = new HashMap<>(64);

    private final Int2ObjectOpenHashMap<List<BaseSpace>> successorCache = new Int2ObjectOpenHashMap<>(128);

    public static final int INIT_STAR_COST = 10;
    private static final int[] KAMEK_STAR_PRICES = {5, 10, 15};

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

    public List<BaseSpace> successorsOf(BaseSpace space) {
        int id = space.getSpaceID();
        List<BaseSpace> cached = successorCache.get(id);
        if (null == cached) {
            cached = Graphs.successorListOf(this, space);
            successorCache.put(id, cached);
        }
        return cached;
    }

    @Override
    public MPEdge addEdge(BaseSpace sourceVertex, BaseSpace targetVertex) {
        MPEdge edge = super.addEdge(sourceVertex, targetVertex);
        if (null != sourceVertex) {
            successorCache.remove(sourceVertex.getSpaceID());
        }
        return edge;
    }

    @Override
    public boolean addEdge(BaseSpace sourceVertex, BaseSpace targetVertex, MPEdge edge) {
        boolean added = super.addEdge(sourceVertex, targetVertex, edge);
        if (added && null != sourceVertex) {
            successorCache.remove(sourceVertex.getSpaceID());
        }
        return added;
    }

    @Override
    public MPEdge removeEdge(BaseSpace sourceVertex, BaseSpace targetVertex) {
        MPEdge edge = super.removeEdge(sourceVertex, targetVertex);
        if (null != sourceVertex) {
            successorCache.remove(sourceVertex.getSpaceID());
        }
        return edge;
    }

    @Override
    public boolean removeEdge(MPEdge edge) {
        if (null != edge) {
            BaseSpace source = getEdgeSource(edge);
            boolean removed = super.removeEdge(edge);
            if (removed && null != source) {
                successorCache.remove(source.getSpaceID());
            }
            return removed;
        }
        return super.removeEdge(edge);
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

    public void reetStarDistances() {
        starDistanceMap.clear();
        initStarDistances();
    }

    /**
     * On Kamek's board the Star never moves; instead its price changes between
     * 5, 10, and 15 coins, and the new price is never the same as the old one.
     */
    public void rollNewKamekStarPrice() {
        int nextPrice;
        do {
            nextPrice = KAMEK_STAR_PRICES[RandomUtils.nextIntExclusive(KAMEK_STAR_PRICES.length)];
        } while (nextPrice == starCost);

        setStarCost(nextPrice);
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
