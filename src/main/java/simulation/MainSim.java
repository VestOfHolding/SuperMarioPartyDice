package simulation;

import boards.BaseBoard;
import boards.KingBobombsPowderkegMine;
import boards.MegafruitParadise;
import boards.WhompsDominoRuins;
import display.GraphDisplay;
import org.apache.commons.lang3.time.StopWatch;

public class MainSim {
    public static void main(String[] args) throws Exception {
        System.setProperty("org.graphstream.ui", "swing");

        StopWatch stopWatch = StopWatch.createStarted();
        BaseBoard board = new MegafruitParadise();

//        GraphDisplay display = new GraphDisplay();
//        display.display(board);

        Simulation simulation = new MegafruitSimulation();
        simulation.simulate();

//        ShortestPath shortestAllyPath = new ShortestPath();
//        shortestAllyPath.shortestPathToFirstAlly(board);

        stopWatch.split();
        System.out.println("\n\nTime elapsed: " + stopWatch.toSplitString());
        stopWatch.stop();
    }
}
