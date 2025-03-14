package process_scheduling.process;

import java.util.Random;

public class Process {

    //region Random Process Bounds

    static final float LOWER_TIME_BOUND = 1.0f;
    static final float UPPER_TIME_BOUND = 20.0f;

    //endregion

    int pid = -1;
    int priority;

    float time;
    float timeLeft;

    float waitingTime;

    boolean done = false;

    /**
     * Creates a new {@link Process}. This constructor sets its priority and time.
     * @param priority a priority of a process from 0 {@code high} to 2 {@code low}
     * @param time how long a process has to be executed
     */
    public Process(int priority, float time) {
        this.priority = priority;
        this.time = time;
        this.timeLeft = time;
    }

    public void execute(float delta) {
        timeLeft -= delta;

        if (timeLeft <= 0) {
            done = true;
        }
    }

    /**
     * @param pid a new process ID
     */
    public void setPID(int pid) {
        this.pid = pid;
    }

    /**
     * @return {@code pid} a process ID
     */
    public int getPID() {
        return pid;
    }

    /**
     * @return {@code priority} a priority of a process
     */
    public int getPriority() {
        return priority;
    }

    /**
     * @return {@code time} a total time of execution of a process
     */
    public float getTime() {
        return time;
    }

    /**
     * @return {@code timeLeft} a time left to finish the execution of a process
     */
    public float getTimeLeft() {
        return timeLeft;
    }

    /**
     * @return {@code done} a boolean value indicating if process has finished
     */
    public boolean hasCompleted() {
        return done;
    }

    /**
     * Generates a random {@link Process} with randomized time and priority. PID is assigned to a process while adding to a queue.
     * @return {@link Process} {@code process} a random process
     */
    public static Process randomProcess(int seed) {
        Random r = new Random(seed);

        // Gets random priority of a process
        // TODO Make it able to choose what will be more common
        int rPriotity = (int) r.nextInt(0, 3);

        // Gets random time of a process from range [0.0, 20.0)
        float rTime = (float) Math.round(r.nextFloat(LOWER_TIME_BOUND, UPPER_TIME_BOUND) * 10) / 10;

        return new Process(rPriotity, rTime);
    }

    /**
     * @return A description of a {@link Process}
     */
    @Override
    public String toString() {
        return "Process [pid=" + pid + ", priority=" + priority + ", time=" + time + ", timeLeft=" + timeLeft + "]";
    }

}
