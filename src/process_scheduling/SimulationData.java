package process_scheduling;

public class SimulationData {

    /**
     * How much total waiting time is considered for a process to starve.
     */
    public static final float STARVATION_TIME = 500f;

    public float totalWaitingTime;
    public float totalExecutionTime;
    public float totalProcessorAccessTime;

    public int starvedProcesses;

    public SimulationData() {
        totalWaitingTime = 0;
        totalExecutionTime = 0;
        totalProcessorAccessTime = 0;
        starvedProcesses = 0;
    }

}
