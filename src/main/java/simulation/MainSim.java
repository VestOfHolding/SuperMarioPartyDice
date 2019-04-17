package simulation;

import boards.BaseBoard;
import boards.KameksTantalizingTower;
import boards.KingBobombsPowderkegMine;
import boards.MegafruitParadise;
import boards.WhompsDominoRuins;
import org.apache.commons.lang3.time.StopWatch;

public class MainSim {
    public static void main(String[] args) {
        StopWatch stopWatch = StopWatch.createStarted();
        BaseBoard board = new WhompsDominoRuins();

        Simulation simulation = new Simulation(board);
        simulation.simulate();

        stopWatch.split();
        System.out.println("\n\nTime elapsed: " + stopWatch.toSplitString());
        stopWatch.stop();
    }
}
