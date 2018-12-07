package simulation;

import boards.KingBobombsPowderkegMine;
import partydice.Dice;
import stattracker.SimulationStatTracker;

public class KingBobombSimulation extends Simulation  {
    @Override
    public void simulate() {
        KingBobombsPowderkegMine whompsRuinsBoard = new KingBobombsPowderkegMine();
        SimulationStatTracker simulationStatTracker;

        printTableHeaders(whompsRuinsBoard);

        for (Dice characterDie : Dice.values()) {
            simulationStatTracker = new SimulationStatTracker(characterDie);

            for (int i = 0; i < SIM_COUNT; ++i) {
                simulationStatTracker.endGame(simulateGame(characterDie, whompsRuinsBoard, simulationStatTracker));
                whompsRuinsBoard.resetBoard();
            }

            printSimulationResult(characterDie, simulationStatTracker, whompsRuinsBoard.getTotalBoardSize());
        }
    }
}
