package simulation;

import org.apache.commons.lang3.time.StopWatch;

public class MainSim {
    public static void main(String[] args) throws Exception {
        StopWatch stopWatch = StopWatch.createStarted();

        Simulation simulation = new WhompsSimulation();
        simulation.simulate();

//        ShortestAllyPath shortestAllyPath = new ShortestAllyPath();
//        shortestAllyPath.shortestPathToFirstAlly();

        stopWatch.stop();

        System.out.println("\n\nTime elapsed: " + stopWatch.toSplitString());
    }
}
