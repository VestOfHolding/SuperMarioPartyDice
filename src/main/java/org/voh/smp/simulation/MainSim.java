package org.voh.smp.simulation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.voh.smp.boards.KameksTantalizingTower;
import org.voh.smp.boards.KingBobombsPowderkegMine;
import org.voh.smp.boards.MegafruitParadise;
import org.voh.smp.boards.WhompsDominoRuins;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainSim {
    private static final Logger LOG = LoggerFactory.getLogger(MainSim.class);

    public static void main(String[] args) {
        int simCount = 100000;
        if (1 == args.length) {
            try {
                if (0 < Integer.parseInt(args[0])) {
                    simCount = Integer.parseInt(args[0]);
                }
            } catch (NumberFormatException ignored) { }
        }

        ExecutorService service = Executors.newWorkStealingPool();
        Simulation whompsSim = new Simulation(WhompsDominoRuins::new, WhompsDominoRuins.OUTPUT_NAME, simCount);
        Simulation bobombSim = new Simulation(KingBobombsPowderkegMine::new, KingBobombsPowderkegMine.OUTPUT_NAME, simCount);
        Simulation megafruitSim = new Simulation(MegafruitParadise::new, MegafruitParadise.OUTPUT_NAME, simCount);
        Simulation kameksSim = new Simulation(KameksTantalizingTower::new, KameksTantalizingTower.OUTPUT_NAME, simCount);

        try {
            List<Callable<String>> simulations = List.of(
                    whompsSim::runWithTimeInfo, bobombSim::runWithTimeInfo, megafruitSim::runWithTimeInfo, kameksSim::runWithTimeInfo);

            service.invokeAll(simulations);
        } catch (Exception e) {
            LOG.error("Exception thrown when trying to run threads.", e);
            throw new RuntimeException(e);
        } finally {
            service.shutdown();
        }
    }
}
