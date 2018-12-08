package simulation;

import boards.KingBobombsPowderkegMine;
import partydice.Dice;
import stattracker.SimulationStatTracker;

public class KingBobombSimulation extends Simulation  {
    @Override
    public void simulate() {
        KingBobombsPowderkegMine kingBobombsBoard = new KingBobombsPowderkegMine();
        SimulationStatTracker simulationStatTracker;

        printTableHeaders(kingBobombsBoard);

        for (Dice characterDie : Dice.values()) {
            simulationStatTracker = new SimulationStatTracker(characterDie);

            for (int i = 0; i < SIM_COUNT; ++i) {
                simulationStatTracker.endGame(simulateGame(characterDie, kingBobombsBoard, simulationStatTracker));
                kingBobombsBoard.resetBoard();
            }

            printSimulationResult(characterDie, simulationStatTracker, kingBobombsBoard.getTotalBoardSize());
        }
    }
}
