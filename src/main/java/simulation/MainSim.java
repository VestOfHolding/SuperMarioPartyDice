package simulation;

import boards.KameksTantalizingTower;
import boards.KingBobombsPowderkegMine;
import boards.MegafruitParadise;
import boards.WhompsDominoRuins;

public class MainSim {
    public static void main(String[] args) {
        int simCount = 7000000;
        if (args.length == 1) {
            try {
                if (Integer.valueOf(args[0]) > 0) {
                    simCount = Integer.valueOf(args[0]);
                }
            } catch (NumberFormatException ignored) { }
        }
        
        Thread whompsThread = new Thread(new Simulation(new WhompsDominoRuins(), simCount));
        Thread kingBobombsThread = new Thread(new Simulation(new KingBobombsPowderkegMine(), simCount));
        Thread megafruitThread = new Thread(new Simulation(new MegafruitParadise(), simCount));
        Thread kameksThread = new Thread(new Simulation(new KameksTantalizingTower(), simCount));

        whompsThread.start();
        kingBobombsThread.start();
        megafruitThread.start();
        kameksThread.start();
    }
}
