package process_scheduling;

import java.util.ArrayList;

public class SimulationData {

    /**
     * Used with {@code processesFinishedInTime} array to determine how many processes have finished in this amount of cycles.
     */
    public static final int CYCLES_COUNT = 100;

    /**
     * How much total waiting time is considered for a process to starve.
     */
    public static final float STARVATION_TIME = 1000f;

    public float totalWaitingTime;
    public float totalExecutionTime;
    public float totalProcessorAccessTime;

    public int switches;
    public int starvedProcesses;
    public ArrayList<Integer> processesFinishedInTime = new ArrayList<>();

    public int processesFinished = 0;

    public SimulationData() {
        totalWaitingTime = 0;
        totalExecutionTime = 0;
        totalProcessorAccessTime = 0;
        starvedProcesses = 0;
        switches = 0;
    }

    public void addProcessFinished() {
        processesFinishedInTime.add(processesFinished);
        processesFinished = 0;
    }

    public float getAverageFinishedProcessAmount() {
        int count = 0;
        for (int c : processesFinishedInTime) {
            count += c;
        }

        return (float) count / processesFinishedInTime.size();
    }

}
