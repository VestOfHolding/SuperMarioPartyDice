package simulation;

import display.GraphDisplay;
import org.apache.commons.lang3.time.StopWatch;

public class MainSim {
    public static void main(String[] args) throws Exception {
        System.setProperty("org.graphstream.ui", "swing");

        StopWatch stopWatch = StopWatch.createStarted();

//        GraphDisplay display = new GraphDisplay();
//        display.display();

        Simulation simulation = new WhompsSimulation();
        simulation.simulate();

//        ShortestAllyPath shortestAllyPath = new ShortestAllyPath();
//        shortestAllyPath.shortestPathToFirstAlly();

        stopWatch.split();
        System.out.println("\n\nTime elapsed: " + stopWatch.toSplitString());
        stopWatch.stop();
    }
}
