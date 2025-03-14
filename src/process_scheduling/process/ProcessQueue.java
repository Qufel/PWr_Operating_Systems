package process_scheduling.process;

import process_scheduling.algorithms.SchedulingAlgorithm;

import java.util.ArrayList;
import java.util.function.Predicate;

/**
 * {@link ProcessQueue} handles scheduling and access to processes.
 */
public class ProcessQueue {

    /**
     * Processes in queue. {@code private} and {@code final} attributes ensure that it cannot be accessed or modified in any other way than through methods.
     */
    private final ArrayList<Process> PROCESSES;
    private final SchedulingAlgorithm ALGORITHM;

    private int processesCount;

    /**
     * Initializes a new {@link ProcessQueue} with an empty processes array.
     * @param algorithm {@link SchedulingAlgorithm} algorithm that is used to schedule this queue to processor
     */
    public ProcessQueue(SchedulingAlgorithm algorithm) {
        PROCESSES = new ArrayList<>();
        ALGORITHM = algorithm;
    }

    /**
     * Sorts processes in the queue.
     * The function is meant to be overwritten by individual scheduling algorithms.
     */
    protected void sort() {}

    //region Process Queue Handling

    /**
     * Adds a new {@link Process} to a queue and assigns a new PID to the added process. That means the process was registered by the queue and is now waiting for execution.
     * @param process a process that will be added to a queue
     * @return {@code pid} a process ID of an added process
    */
    public int addProcess(Process process) {

        int newPid;

        if (process.pid != -1) {
            newPid = process.pid;
        } else {
            newPid = getLargestPID() + 1;
        }

        process.setPID(newPid);

        PROCESSES.add(process);
        processesCount++;

        // After adding new process sort the queue
        this.sort();

        ALGORITHM.getData().totalWaitingTime += getProcessWaitingTime(newPid);

        return newPid;
    }

    protected void addProcessWithoutSorting(Process process) {
        PROCESSES.add(process);
    }

    /**
     * Returns a first process from the queue.
     * @return {@link Process} {@code process}
     */
    public Process getProcess() {
        if (PROCESSES.isEmpty())
            return new Process(0,0f);

        return PROCESSES.getFirst();
    }

    /**
     * @return {@code true} if queue is empty
     */
    public boolean isEmpty() {
        return PROCESSES.isEmpty();
    }

    /**
     * Clears a process queue.
     */
    public void clear() {
        PROCESSES.clear();
    }

    /**
     * @param pid a pid of a process to be removed
     * @return {@link Process} {@code process} a process removed from the queue
     */
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public Process removeProcess(int pid) {
        if (pid == -1)
            return null;

        Predicate<Process> filter = process -> process.getPID() == pid;
        int index = PROCESSES.indexOf(PROCESSES.stream().filter(filter).findFirst().get());
        return PROCESSES.remove(index);
    }

    /**
     * Returns a first process in queue and deletes it from the list. The process should be immediately send to execution.
     * @return {@link Process} {@code process} a removed process
     */
    public Process getAndRemoveProcess() {
        return removeProcess(getProcess().getPID());
    }

    //endregion

    /**
     * @return {@code pid} a process ID of the last process in queue or {@code 0} if queue is empty
     */
    public int getLargestPID() {
        // Gets a last process PID
        if (PROCESSES.isEmpty())
            return 0;

        int largestPID = PROCESSES.getFirst().getPID();

        for (int i = 0; i < PROCESSES.size(); i++) {
            if (PROCESSES.get(i).getPID() > largestPID)
                largestPID = PROCESSES.get(i).getPID();
        }

        return largestPID;
    }

    /**
     * @return amount of processes
     */
    public int getProcessCount() {
        return PROCESSES.size();
    }

    /**
     * Calculates a waiting time for a process of given process ID.
     * @param pid a process ID
     * @return {@code waitingTime} an amount of time for which process has to wait for execution
     */
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private float getProcessWaitingTime(int pid) {
        Predicate<Process> filter = process -> process.getPID() == pid;
        int index = PROCESSES.indexOf(PROCESSES.stream().filter(filter).findFirst().get());

        float waitingTime = 0;

        for (int i = 0; i < index; i++) {
            waitingTime += PROCESSES.get(i).getTimeLeft();
        }

        return waitingTime;
    }

    /**
     * Lists all processes in queue.
     * @return {@link String} containing a description of all processes in a queue
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Processes:\n");

        for (int i = 1; i <= PROCESSES.size(); i++) {
            sb.append(i).append(". ").append(PROCESSES.get(i - 1).toString()).append("\n");
        }


        return sb.toString();

    }
}
