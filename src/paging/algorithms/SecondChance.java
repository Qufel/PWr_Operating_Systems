package paging.algorithms;

import paging.Main;
import paging.process.Process;

import java.lang.reflect.Array;
import java.util.Arrays;

public class SecondChance extends  PagingAlgorithm {

    /**
     * Currently served page.
     */
    int page;
    /**
     * Candidate frame to which new page will be allocated.
     */
    int candidate;

    int victim = 0;
    boolean[] referenceBits;

    public SecondChance() {
        super();
        this.referenceBits = new boolean[Main.FRAMES];
    }

    @Override
    public void step() {
        for (Process process : getProcesses()) {

            page = process.getNextPage();

            if (isPageInFrames(page)) {
                // Access address at this page. In other words, do work and do not change anything.
                process.pushFaultState(false); // Indicate that there was no page fault
                setReferenceBitFor(page);

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
            referenceBits[frame] = true;
            return frame;
        }

        while(referenceBits[victim]) {
            referenceBits[victim] = false;
            victim = (victim + 1) % Main.FRAMES;
        }

        referenceBits[victim] = true;
        int tmp = victim;
        victim = (victim + 1) % Main.FRAMES;
        return tmp;
    }

    private void setReferenceBitFor(int page) {
        for (int i = 0; i < Main.FRAMES; i++) {
            if (page == frames[i]) {
                referenceBits[i] = true;
                return;
            }
        }
    }

}
