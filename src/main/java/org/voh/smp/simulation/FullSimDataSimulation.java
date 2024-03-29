package org.voh.smp.simulation;

import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import org.voh.smp.partydice.Dice;
import org.voh.smp.stattracker.GameStatTracker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

public class FullSimDataSimulation extends Simulation {

    protected static final int TURN_COUNT = 20;
    protected static final int SIM_COUNT = 10000000;

    @Override
    public void run() {
        Int2IntOpenHashMap distanceFrequencyMap;

        GameStatTracker gameStatTracker;

        FileOutputStream fo;

        for (Dice characterDie : Dice.values()) {
            String filePath = "output/" + characterDie.getName() + ".txt";

            try {
                Files.deleteIfExists(new File(filePath).toPath());

                try {
                    fo = new FileOutputStream(filePath);
                } catch (FileNotFoundException ignored) {
                    continue;
                }

                distanceFrequencyMap = new Int2IntOpenHashMap();

                for (int i = 0; SIM_COUNT > i; ++i) {
                    gameStatTracker = new GameStatTracker(TURN_COUNT);

                    for (int j = 0; TURN_COUNT > j; ++j) {
                        gameStatTracker.addDiceResult(characterDie.roll());
                    }

                    if (1 > distanceFrequencyMap.get(gameStatTracker.getDistanceTotal())) {
                        distanceFrequencyMap.put(gameStatTracker.getDistanceTotal(), 1);
                    }
                    else {
                        distanceFrequencyMap.put(gameStatTracker.getDistanceTotal(), distanceFrequencyMap.get(gameStatTracker.getDistanceTotal()) + 1);
                    }
                }

                fo.write("Distance\tPercentage\tAmount\n".getBytes());

                for (int key : distanceFrequencyMap.keySet()) {
                    fo.write((key + "\t" + DECIMAL_FORMAT.format((double)distanceFrequencyMap.get(key) / SIM_COUNT) + "\t" + distanceFrequencyMap.get(key) + "\n").getBytes());
                }
                fo.close();

            } catch (IOException ignored) { }
        }
    }
}
