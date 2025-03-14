package process_scheduling;

public class SimulationData {

    public float totalWaitingTime;
    public float totalExecutionTime;

    public int starvedProcesses;

    public SimulationData() {
        totalWaitingTime = 0;
        totalExecutionTime = 0;
        starvedProcesses = 0;
    }

}
