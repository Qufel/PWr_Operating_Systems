package process_scheduling.algorithms;

import process_scheduling.process.ProcessQueue;

public class FCFS extends SchedulingAlgorithm {

    public FCFS() {
        super();
        setQueue(new ProcessQueue(this) {

            @Override
            public void sort() {

            }

        });
    }

    @Override
    public void run(float delta) {

        // Check if any process is being currently being executed, if not assign first process from the queue
        if (current == null) {
            current = getQueue().getProcess();
        }

        current.execute(delta);

        if (current.hasCompleted()) {

            getQueue().removeProcess(current.getPID());
            current = getQueue().getProcess();

            getData().switches++;
            getData().processesFinished++;

        }

        super.run(delta);
    }

}
