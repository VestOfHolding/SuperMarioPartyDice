package simulation;

import boards.KameksTantalizingTower;
import boards.KingBobombsPowderkegMine;
import boards.MegafruitParadise;
import boards.WhompsDominoRuins;

public class MainSim {
    public static void main(String[] args) {
        Thread whompsThread = new Thread(new Simulation(new WhompsDominoRuins()));
        Thread kingBobombsThread = new Thread(new Simulation(new KingBobombsPowderkegMine()));
        Thread megafruitThread = new Thread(new Simulation(new MegafruitParadise()));
        Thread kameksThread = new Thread(new Simulation(new KameksTantalizingTower()));

        whompsThread.start();
        kingBobombsThread.start();
        megafruitThread.start();
        kameksThread.start();
    }
}
