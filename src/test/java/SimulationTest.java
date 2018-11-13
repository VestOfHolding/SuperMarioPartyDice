import org.junit.jupiter.api.Test;
import partydice.Simulation;

class SimulationTest {

    private Simulation simulation;

    @Test
    void simulateBasic() {
        simulation = new Simulation();

        simulation.simulateBasic();
    }

    @Test
    void simulateWhomps() {
        simulation = new Simulation();

        simulation.simulateWhomps();
    }
}