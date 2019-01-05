package simulation;

import boards.WhompsDominoRuins;
import org.apache.commons.lang3.time.StopWatch;
import partydice.Dice;
import stattracker.SimulationStatTracker;

public class WhompsSimulation extends Simulation {

    @Override
    public void simulate() {
        WhompsDominoRuins whompsRuinsBoard = new WhompsDominoRuins();
        SimulationStatTracker simulationStatTracker;

        printTableHeaders(whompsRuinsBoard);

        for (Dice characterDie : Dice.values()) {
//            StopWatch stopWatch = StopWatch.createStarted();

            simulationStatTracker = new SimulationStatTracker(characterDie);

            for (int i = 0; i < SIM_COUNT; ++i) {
                simulationStatTracker.endGame(simulateGame(characterDie, whompsRuinsBoard, simulationStatTracker));
                whompsRuinsBoard.resetBoard();
            }

            printSimulationResult(characterDie, simulationStatTracker, whompsRuinsBoard.getTotalBoardSize());

//            stopWatch.split();
//            System.out.println("Time elapsed: " + stopWatch.toSplitString());
//            stopWatch.stop();
        }
    }
}
