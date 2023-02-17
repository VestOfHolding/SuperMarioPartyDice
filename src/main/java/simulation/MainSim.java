package simulation;

import boards.KameksTantalizingTower;
import boards.KingBobombsPowderkegMine;
import boards.MegafruitParadise;
import boards.WhompsDominoRuins;

public class MainSim {
    public static void main(String[] args) {
        int simCount = 1000000;
        if (1 == args.length) {
            try {
                if (0 < Integer.parseInt(args[0])) {
                    simCount = Integer.parseInt(args[0]);
                }
            } catch (NumberFormatException ignored) { }
        }
        
        Thread whompsThread = new Thread(new Simulation(WhompsDominoRuins::new, WhompsDominoRuins.OUTPUT_NAME, simCount));
        Thread kingBobombsThread = new Thread(new Simulation(KingBobombsPowderkegMine::new, KingBobombsPowderkegMine.OUTPUT_NAME, simCount));
        Thread megafruitThread = new Thread(new Simulation(MegafruitParadise::new, MegafruitParadise.OUTPUT_NAME, simCount));
        Thread kameksThread = new Thread(new Simulation(KameksTantalizingTower::new, KameksTantalizingTower.OUTPUT_NAME, simCount));

        whompsThread.start();
        kingBobombsThread.start();
        megafruitThread.start();
        kameksThread.start();
    }
}
