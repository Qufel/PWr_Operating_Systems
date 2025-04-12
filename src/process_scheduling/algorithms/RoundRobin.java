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

        if (currentProcessIndex >= getQueue().getProcessCount()) {
            currentProcessIndex = 0;
        }

        current = getQueue().getProcessByIndex(currentProcessIndex);

        if (current == null) {
            return;
        }

        current.execute(delta);

        if (current.hasCompleted()) {

            getQueue().removeProcessByIndex(currentProcessIndex);

            frameTime = 0f;
            currentProcessIndex++;

            getData().switches++;
            getData().processesFinished++;

        }

        frameTime += delta;

        if (frameTime >= QUANTA) {
            currentProcessIndex++;

            getData().switches++;
            getData().totalProcessorAccessTime += frameTime;

            frameTime = 0f;
        }

        super.run(delta);

    }

}
