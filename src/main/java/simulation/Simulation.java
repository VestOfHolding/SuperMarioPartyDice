package simulation;

import boards.BaseBoard;
import boards.WhompsDominoRuins;
import org.apache.commons.lang3.time.StopWatch;
import partydice.Dice;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class Simulation implements Runnable {
    protected final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("####.#######");

    protected int SIM_COUNT;

    protected Supplier<BaseBoard> gameBoard;

    protected String fileOutputName;

    public Simulation() {
        this.gameBoard = WhompsDominoRuins::new;
    }

    public Simulation(Supplier<BaseBoard> gameBoard, String fileOutputName, int simCount) {
        SIM_COUNT = simCount;
        this.gameBoard = gameBoard;
        this.fileOutputName = "output/" + fileOutputName;
    }

    @Override
    public void run() {
        StopWatch stopWatch = StopWatch.createStarted();
        System.out.println(fileOutputName + " time start!\t\t" + LocalDateTime.now());
        printTableHeaders();

        CompletableFuture<?>[] characterSims = Arrays.stream(Dice.values())
                .map(characterDie -> new CharacterSimulation(gameBoard.get(), characterDie, SIM_COUNT))
                .map(CompletableFuture::runAsync).toList()
                .toArray(CompletableFuture<?>[]::new);

        try {
            while(!CompletableFuture.allOf(characterSims).isDone()) {
                Thread.sleep(100);
            }
        }
        catch (InterruptedException e) {
            System.out.println("Error waiting for threads to complete.");
        }

        stopWatch.split();
        System.out.println(fileOutputName + " time elapsed: " + stopWatch.getTime() + " ms\t\t" + LocalDateTime.now());
        stopWatch.stop();
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
            System.out.println("ERROR: Could not write table headers to file: " + fileOutputName);
        }
        System.out.println();
    }
}
