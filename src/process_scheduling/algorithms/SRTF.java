package process_scheduling.algorithms;

import process_scheduling.process.Process;
import process_scheduling.process.ProcessQueue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class SRTF extends SchedulingAlgorithm {

    public SRTF() {
        super();
        setQueue(new ProcessQueue(this) {

            @Override
            public void sort() {

                ArrayList<Process> array = new ArrayList<>();

                // Add all processes to temporal array
                Process p = getAndRemoveProcess();
                while (p != null) {
                    array.add(p);
                    p = getAndRemoveProcess();
                }

                // Sort using Java Comparator
                Comparator<Process> comparator = new Comparator<Process>() {

                    @Override
                    public int compare(Process p1, Process p2) {
                        return (int) ( p1.getTimeLeft() - p2.getTimeLeft() );
                    }

                };

                array.sort(comparator);

                // Add sorted processes to main queue
                for (Process process : array) {
                    addProcessWithoutSorting(process);
                }

            }

        });
    }

    @Override
    public void run(float delta) {

        if (getQueue().isEmpty())
            return;

        current = getQueue().getProcess();

        current.execute(delta);

        if (current.hasCompleted()) {
            getQueue().removeProcess(current.getPID());
        }

        getData().totalExecutionTime += delta;

    }

}
