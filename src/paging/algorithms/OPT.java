package paging.algorithms;

import paging.Main;
import paging.process.Process;

public class OPT extends PagingAlgorithm {

    /**
     * Currently served page.
     */
    int page;
    /**
     * Candidate frame to which new page will be allocated.
     */
    int candidate;

    public OPT() {
        super();
    }

    @Override
    public void step() {
        for (Process process : getProcesses()) {

            page = process.getNextPage();

            if (isPageInFrames(page)) {
                // Access address at this page. In other words, do work and do not change anything.
                process.pushFaultState(false); // Indicate that there was no page fault
            } else {

                // Page fault occurs

                candidate = getNextFrame(process);

                setPageAtFrame(candidate, page);

                process.updatePageFaults();
                process.pushFaultState(true); // Indicate there was page fault

            }

            if (process.thrashing()) {
                process.updateThrashing();
            }

            if (process.isFinished()) {
                finishProcess(process);
            }

        }
    }

    public int getNextFrame(Process process) {
        int frame = getEmptyFrame(process);
        if (frame != -1) {
            return frame;
        }

        int max = -1;
        for (int f = 0; f < Main.FRAMES; f++) {

            int reference = frames[f];
            int index;
            boolean foundNext = false;

            for (int i = 0; process.getIndex() + i < process.getSequence().length; i++) {
                index = process.getIndex() + i;

                if (process.getSequence()[index] == reference) {
                    if (i > max) {
                        max = i;
                        frame = f;
                    }
                    foundNext = true;
                    break;
                }

            }

            if (!foundNext) {
                return f;
            }

        }

        return frame;

    }

    @Override
    public void clear() {
        super.clear();
    }

}
