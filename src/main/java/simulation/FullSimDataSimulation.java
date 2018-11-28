package simulation;

import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import partydice.Dice;
import stattracker.GameStatTracker;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;

public class FullSimDataSimulation extends Simulation {
    protected static final int SIM_COUNT = 10000000;

    @Override
    public void simulate() throws Exception {
        Int2IntOpenHashMap distanceFrequencyMap;

        GameStatTracker gameStatTracker;

        FileOutputStream fo;

        for (Dice characterDie : Dice.values()) {
            String filePath = "output/" + characterDie.getName() + ".txt";

            Files.deleteIfExists(new File(filePath).toPath());
            fo = new FileOutputStream(filePath);
            distanceFrequencyMap = new Int2IntOpenHashMap();

            for (int i = 0; i < SIM_COUNT; ++i) {
                gameStatTracker = new GameStatTracker();

                for (int j = 0; j < TURN_COUNT; ++j) {
                    gameStatTracker.addDiceResult(characterDie.roll());
                }

                if (distanceFrequencyMap.get(gameStatTracker.getDistanceTotal()) < 1) {
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
        }
    }
}
