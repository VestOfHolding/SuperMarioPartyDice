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
        int simCount = 10000;
        if (1 == args.length) {
            try {
                if (0 < Integer.parseInt(args[0])) {
                    simCount = Integer.parseInt(args[0]);
                }
            } catch (NumberFormatException ignored) { }
        }

        try (ExecutorService service = Executors.newVirtualThreadPerTaskExecutor()){
            Simulation whompsSim = new Simulation(WhompsDominoRuins::new, simCount);
            Simulation bobombSim = new Simulation(KingBobombsPowderkegMine::new, simCount);
            Simulation megafruitSim = new Simulation(MegafruitParadise::new, simCount);
            Simulation kameksSim = new Simulation(KameksTantalizingTower::new, simCount);

            List<Callable<String>> simulations = List.of(
                    whompsSim::runWithTimeInfo, bobombSim::runWithTimeInfo, megafruitSim::runWithTimeInfo, kameksSim::runWithTimeInfo);

            service.invokeAll(simulations);
        } catch (Exception e) {
            LOG.error("Exception thrown when trying to run threads.", e);
            throw new RuntimeException(e);
        }
    }
}
