package process_scheduling.algorithms;

import process_scheduling.SimulationData;
import process_scheduling.process.Process;
import process_scheduling.process.ProcessQueue;

public abstract class SchedulingAlgorithm {

    protected ProcessQueue queue;
    protected SimulationData data;

    protected int cycles = 0;
    /**
     * Process that is being currently executed by the processor.
     */
    Process current = null;

    /**
     * @return {@link ProcessQueue} {@code queue} a process queue associated with this scheduling algorithm
     */
    public ProcessQueue getQueue() {
        return queue;
    }

    /**
     * @return current cycle number
     */
    public int getCycles() {
        return cycles;
    }

    /**
     * @param queue a new {@link ProcessQueue}
     */
    public void setQueue(ProcessQueue queue) {
        this.queue = queue;
    }

    /**
     * @return {@link SimulationData} {@code data} a simulation data associated with this scheduling algorithm
     */
    public SimulationData getData() {
        return data;
    }

    public Process getCurrent() {
        return current;
    }

    public void run(float delta) {
        cycles++;

        if (getCycles() >= SimulationData.CYCLES_COUNT * (getData().processesFinishedInTime.size() + 1) && !finished()) {
            getData().addProcessFinished();
        }
    }

    public boolean finished() {
        return current == null && getQueue().isEmpty();
    }

    /**
     * Constructor for {@link SchedulingAlgorithm} that creates an empty {@link ProcessQueue} with a custom sorting algorithm declared in {@code algorithmSort()} method.
     */
    public SchedulingAlgorithm() {
        data = new SimulationData();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
