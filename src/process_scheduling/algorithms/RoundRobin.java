package process_scheduling.algorithms;

import process_scheduling.process.ProcessQueue;

public class RoundRobin extends SchedulingAlgorithm {

    /**
     * A time period in which process is allowed to be executed by CPU.
     */
    public static final float QUANTA = 5f;

    protected float frameTime = 0f;

    protected int currentProcessIndex = 0;

    public RoundRobin() {
        super();
        setQueue(new ProcessQueue(this) {

            @Override
            public void sort() {

            }

        });

    }

    @Override
    public void run(float delta) {

        current = getQueue().getProcessByIndex(currentProcessIndex);

        if (current.hasCompleted()) {
            getQueue().removeProcess(current.getPID());
            current = null;
            frameTime = 0f;
            currentProcessIndex++;
        } else {
            current.execute(delta);
        }

        frameTime += delta;

        if (frameTime >= QUANTA) {

            getData().totalProcessorAccessTime += frameTime;

            frameTime = 0f;
            currentProcessIndex++;
        }

        if (currentProcessIndex >= getQueue().getProcessCount())
            currentProcessIndex = 0;

        getData().totalExecutionTime += delta;

    }

}
