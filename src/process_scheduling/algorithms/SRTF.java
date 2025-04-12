package process_scheduling.algorithms;

import process_scheduling.process.Process;
import process_scheduling.process.ProcessQueue;

import java.util.ArrayList;
import java.util.Comparator;

public class SRTF extends SchedulingAlgorithm {

    public SRTF() {
        super();
        setQueue(new ProcessQueue(this) {

            @Override
            public void sort() {

                // Sort using Java Comparator
                Comparator<Process> comparator = (p1, p2) -> Float.compare(p1.getTimeLeft(), p2.getTimeLeft());

                PROCESSES.sort(comparator);

            }

        });
    }

    @Override
    public void run(float delta) {

        if (getQueue().isEmpty()) {
            super.run(delta);
            return;
        }

        current = getQueue().getProcess();

        current.execute(delta);

        if (current.hasCompleted()) {
            getQueue().removeProcess(current.getPID());

            getData().switches++;
            getData().processesFinished++;

            current = null;
        }

        super.run(delta);
    }

}
