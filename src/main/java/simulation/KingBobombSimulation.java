package simulation;

import boards.KingBobombsPowderkegMine;
import org.apache.commons.collections4.CollectionUtils;
import partydice.BobombAlly;
import partydice.Dice;
import stattracker.GameStatTracker;
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

    @Override
    protected int rollAllies(GameStatTracker gameStatTracker) {
        int result = super.rollAllies(gameStatTracker);

        if (CollectionUtils.isNotEmpty(gameStatTracker.getBobombAllies())) {
            for (BobombAlly bobombAlly : gameStatTracker.getBobombAllies()) {
                result += bobombAlly.rollBobombAlly();
            }

            gameStatTracker.getBobombAllies().removeIf(BobombAlly::explode);
        }

        return result;
    }
}
