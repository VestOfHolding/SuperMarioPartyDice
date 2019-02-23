package display;

import boards.BaseBoard;
import boards.MPEdge;
import boards.WhompsDominoRuins;
import boards.layout.MPBoard;
import boards.spaces.BaseSpace;
import boards.spaces.BlueSpace;
import boards.spaces.NonMovementSpace;
import boards.spaces.RedSpace;
import boards.spaces.StartSpace;
import boards.spaces.events.BadLuckSpace;
import boards.spaces.events.VSSpace;
import boards.spaces.events.WDR.WhompsOnTheRun;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.layout.Layout;
import org.graphstream.ui.layout.springbox.implementations.LinLog;
import org.graphstream.ui.view.Viewer;

public class GraphDisplay {
    public void display() {
        BaseBoard board = new WhompsDominoRuins();

        Graph gsGraph = new SingleGraph("Super Mario Party Board");
        gsGraph.setAttribute("ui.stylesheet", getStyleSheet());

        MPBoard<BaseSpace, MPEdge> mpBoard = board.getBoard();

        for (BaseSpace space : mpBoard.vertexSet()) {
            Node node = gsGraph.addNode(Integer.toString(space.getSpaceID()));

            if (space instanceof BlueSpace) {
                node.setAttribute("ui.class", "blue");
            }
            else if (space instanceof RedSpace) {
                node.setAttribute("ui.class", "red");
            }
            else if (space instanceof VSSpace) {
                node.setAttribute("ui.class", "yellow");
            }
            else if (space instanceof StartSpace) {
                node.setAttribute("ui.class", "green");
            }
            else if (space instanceof WhompsOnTheRun || space instanceof NonMovementSpace) {
                node.setAttribute("ui.class", "gray");
            }
            else if (space instanceof BadLuckSpace) {
                node.setAttribute("ui.class", "darkred");
            }
            else {
                node.setAttribute("ui.class", "green");
            }
            node.setAttribute("ui.label", Integer.toString(space.getSpaceID()));

            if (space.getX() > 0 && space.getY() > 0) {
                node.setAttribute("xyz", space.getX(), space.getY(), 0);
            }
        }

        for (MPEdge edge : mpBoard.edgeSet()) {
            gsGraph.addEdge(edge.getEdgeName(), edge.getSourceName(), edge.getTargetName());
        }

        Layout layout = new LinLog();
        layout.setQuality(3.9);
        layout.setStabilizationLimit(0.9);
        layout.setForce(0.9f);

        Viewer viewer = gsGraph.display();
        viewer.disableAutoLayout();
    }

    public String getStyleSheet() {
        return "node{ size: 20px; }" +
                "node.red { fill-color: red; }" +
                "node.yellow { fill-color: yellow; }" +
                "node.green { fill-color: green; }" +
                "node.blue { fill-color: blue; }" +
                "node.gray { fill-color: gray; }" +
                "node.darkred { fill-color: darkred; }";
    }
}
