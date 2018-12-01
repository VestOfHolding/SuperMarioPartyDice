package simulation;

import boards.WhompsDominoRuins;
import partydice.Dice;
import stattracker.SimulationStatTracker;

public class WhompsSimulation extends Simulation {

    @Override
    public void simulate() {
        WhompsDominoRuins whompsRuinsBoard = new WhompsDominoRuins();
        SimulationStatTracker simulationStatTracker;

        printTableHeaders(whompsRuinsBoard);

        for (Dice characterDie : Dice.values()) {
            simulationStatTracker = new SimulationStatTracker(characterDie);

            for (int i = 0; i < SIM_COUNT; ++i) {
                simulationStatTracker.endGame(simulateGame(characterDie, whompsRuinsBoard, simulationStatTracker));
            }

            printSimulationResult(characterDie, simulationStatTracker, whompsRuinsBoard.getTotalBoardSize());
        }
    }
}
