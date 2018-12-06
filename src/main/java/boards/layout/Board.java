package boards.layout;

import boards.spaces.BaseSpace;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Board {
    private Map<Integer, BaseSpace> nodes;
    private Set<Edge> edges;

    public Board() {
        nodes = new HashMap<>();
        edges = new HashSet<>();
    }

}
