package paging.algorithms;

import paging.Main;
import paging.process.Process;

public class LRU extends PagingAlgorithm {

    /**
     * Currently served page.
     */
    int page;
    /**
     * Candidate frame to which new page will be allocated.
     */
    int candidate;

    public LRU() {
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
        int frame = getEmptyFrame();
        if (frame != -1) {
            return frame;
        }

        int max = 0;

        for (int f = 0; f < Main.FRAMES; f++) {

            for (int i = 1; process.getIndex() - i >= 0 ; i++) {

                int index = process.getIndex() - i;

                if (process.getSequence()[index] == frames[f]) {

                    if (i > max) {
                        max = i;
                        frame = f;
                    }

                    break;

                }

            }

        }

        return frame;

    }

    @Override
    public void clear() {
        super.clear();
    }

}
