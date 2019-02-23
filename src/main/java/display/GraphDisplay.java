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
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;
import org.graphstream.ui.view.Viewer;

public class GraphDisplay {
    public void display() {
        BaseBoard board = new WhompsDominoRuins();

        Graph gsGraph = new SingleGraph("Super Mario Party Board");
        gsGraph.setAttribute("ui.stylesheet", getStyleSheet());

        MPBoard<BaseSpace, MPEdge> mpBoard = board.getBoard();

        for (BaseSpace space : mpBoard.vertexSet()) {
            Node node = gsGraph.addNode(Integer.toString(space.getSpaceID()));

            node.setAttribute("ui.class", space.getNodeClass().name().toLowerCase());
//            node.setAttribute("ui.label", Integer.toString(space.getSpaceID()));

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
        return "node {\n" +
                "\tsize: 34px;\n" +
                "\tfill-mode: image-scaled-ratio-max;\n" +
                "\tfill-image: url('images/OtherSpace.png');\n" +
                "}\n" +
                "node.red {\n" +
                "\tfill-image: url('images/RedSpace.png');\n" +
                "}\n" +
                "node.blue {\n" +
                "\tfill-image: url('images/BlueSpace.png');\n" +
                "}\n" +
                "node.event {\n" +
                "\tfill-image: url('images/EventSpace.png');\n" +
                "}\n" +
                "node.badluck {\n" +
                "\tsize: 40px;\n" +
                "\tfill-image: url('images/BadLuckSpace.png');\n" +
                "}\n" +
                "node.item {\n" +
                "\tfill-image: url('images/ItemSpace.png');\n" +
                "}\n" +
                "node.lucky {\n" +
                "\tfill-image: url('images/LuckySpace.png');\n" +
                "}\n" +
                "node.nonmovement {\n" +
                "\tsize: 6px;\n" +
                "\tstroke-mode: plain;\n" +
                "\tstroke-color: #999;\n" +
                "\tstroke-width: 1px;\n" +
                "\tfill-image: url('images/NonMovementSpace.png');\n" +
                "}\n" +
                "node.other {\n" +
                "\tfill-image: url('images/OtherSpace.png');\n" +
                "}\n" +
                "node.vs {\n" +
                "\tfill-image: url('images/VSSpace.png');\n" +
                "}";
    }
}
