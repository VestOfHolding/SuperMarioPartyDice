package simulation;

import org.apache.commons.lang3.time.StopWatch;

public class MainSim {
    public static void main(String[] args) throws Exception {
        StopWatch stopWatch = StopWatch.createStarted();

        Simulation simulation = new KamekSimulation();
        simulation.simulate();

//        ShortestAllyPath shortestAllyPath = new ShortestAllyPath();
//        shortestAllyPath.shortestPathToFirstAlly();

        stopWatch.split();
        System.out.println("\n\nTime elapsed: " + stopWatch.toSplitString());
        stopWatch.stop();
    }
}
