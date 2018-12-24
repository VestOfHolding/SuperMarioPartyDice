package simulation;

import boards.MegafruitParadise;
import partydice.Dice;
import stattracker.SimulationStatTracker;

public class MegafruitSimulation extends Simulation {
    @Override
    public void simulate() {
        MegafruitParadise megafruitParadiseBoard = new MegafruitParadise();
        SimulationStatTracker simulationStatTracker;

        printTableHeaders(megafruitParadiseBoard);

        for (Dice characterDie : Dice.values()) {
            simulationStatTracker = new SimulationStatTracker(characterDie);

            for (int i = 0; i < SIM_COUNT; ++i) {
                simulationStatTracker.endGame(simulateGame(characterDie, megafruitParadiseBoard, simulationStatTracker));
                megafruitParadiseBoard.resetBoard();
            }

            printSimulationResult(characterDie, simulationStatTracker, megafruitParadiseBoard.getTotalBoardSize());
        }
    }
}
