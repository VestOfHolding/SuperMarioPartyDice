package org.voh.smp.simulation;

import org.voh.smp.boards.BaseBoard;
import org.voh.smp.boards.WhompsDominoRuins;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.voh.smp.partydice.Dice;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class Simulation {

    private static final Logger LOG = LoggerFactory.getLogger(Simulation.class);
    protected final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("####.#######");

    protected int SIM_COUNT;

    protected final Supplier<BaseBoard> gameBoard;

    protected String fileOutputName;

    public Simulation() {
        gameBoard = WhompsDominoRuins::new;
    }

    public Simulation(Supplier<BaseBoard> gameBoard, int simCount) {
        SIM_COUNT = simCount;
        this.gameBoard = gameBoard;
        this.fileOutputName = "output/" + gameBoard.get().getFileOutputName();
    }

    public String runWithTimeInfo() {
        String shortOutputName = fileOutputName.substring(fileOutputName.indexOf('/') + 1, fileOutputName.indexOf('.'));

        StopWatch stopWatch = StopWatch.createStarted();
        LOG.atDebug().setMessage(shortOutputName + " time start!")
                .addKeyValue("Start Time", LocalDateTime.now())
                .log();
        printTableHeaders();

        run();

        stopWatch.split();
        LOG.atDebug().setMessage(shortOutputName + " simulation complete.")
                .addKeyValue("End Time", LocalDateTime.now())
                .addKeyValue("Time Elapsed", stopWatch.formatSplitTime())
                .log();
        stopWatch.stop();

        return "" + stopWatch.getTime();
    }

    public void run() {
        List<CompletableFuture<Void>> characterSims = Arrays.stream(Dice.values())
                .map(characterDie -> new CharacterSimulation(gameBoard.get(), characterDie, SIM_COUNT))
                .map(CompletableFuture::runAsync)
                .toList();

        characterSims.forEach(CompletableFuture::join);
    }

    protected void printTableHeaders() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileOutputName, false))) {

            writer.write("Character\tAllyCount\tFrequency\t" +
                    "Min Turn Gained\t1st Quartile Turn Gained\tAvg Turn Gained\t3rd Quartile Turn Gained\tMax Turn Gained\tTurn Gained SD\t" +
                    "Min Distance\t1st Quartile Distance\tDistance Avg\t3rd Quartile Distance\tMax Distance\tDistance SD\t" +
                    "Min Coins\t1st Quartile Coins\tCoin Avg\t3rd Quartile Coins\tMax Coins\tCoin SD\t" +
                    "Min Stars\t1st Quartile Stars\tStar Avg\t3rd Quartile Stars\tMax Stars\tStars SD\t" +
                    "Average Place");
            writer.newLine();
    //        for (int i = 0; i < gameBoard.getTotalBoardSize(); ++i) {
    //            System.out.print("Space" + i + "\t");
    //        }
        } catch (IOException exception) {
            LOG.atError().setMessage("Could not write table headers to file.")
                    .addKeyValue("Output File", fileOutputName)
                    .log();
        }
    }
}
