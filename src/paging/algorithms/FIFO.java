package paging.algorithms;

import paging.Main;
import paging.process.Process;

public class FIFO extends PagingAlgorithm {

    protected int frameToSwap = 0;

    /**
     * Currently served page.
     */
    int page;
    /**
     * Candidate frame to which new page will be allocated.
     */
    int candidate;

    int i = 1;

    public FIFO() {
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

                candidate = getNextFrame();

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

    //region Frame allocation

    @Override
    public int getNextFrame() {
        int frame = getEmptyFrame();
        if (frame != -1) {
            return frame;
        }
        return (frameToSwap++ % Main.FRAMES);
    }

    //endregion
}
