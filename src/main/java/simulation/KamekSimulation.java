package simulation;

import boards.BaseBoard;
import boards.KameksTantalizingTower;
import partydice.Dice;
import stattracker.SimulationStatTracker;

public class KamekSimulation extends Simulation {
    @Override
    public void simulate() {
        KameksTantalizingTower kamekBoard = new KameksTantalizingTower();
        SimulationStatTracker simulationStatTracker;

        printTableHeaders(kamekBoard);

        for (Dice characterDie : Dice.values()) {
            simulationStatTracker = new SimulationStatTracker(characterDie);

            for (int i = 0; i < SIM_COUNT; ++i) {
                simulationStatTracker.endGame(simulateGame(characterDie, kamekBoard, simulationStatTracker));
                kamekBoard.resetBoard();
            }

            printSimulationResult(characterDie, simulationStatTracker, kamekBoard.getTotalBoardSize());
        }
    }

    @Override
    protected void lastThreeTurns(BaseBoard gameBoard) {
        gameBoard.lastThreeTurns(4);
    }
}
