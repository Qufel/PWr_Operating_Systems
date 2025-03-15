package process_scheduling.algorithms;

import process_scheduling.SimulationData;
import process_scheduling.process.Process;
import process_scheduling.process.ProcessQueue;

public class SchedulingAlgorithm {

    protected ProcessQueue queue;
    protected SimulationData data;

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


    public void run(float delta) {

    }

    /**
     * Constructor for {@link SchedulingAlgorithm} that creates an empty {@link ProcessQueue} with a custom sorting algorithm declared in {@code algorithmSort()} method.
     */
    public SchedulingAlgorithm() {
        data = new SimulationData();
    }

}
