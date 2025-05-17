package paging.algorithms;

import paging.Main;
import paging.process.Process;

import java.util.Random;

public class RAND extends PagingAlgorithm {

    private final int originalSeed;
    private int seed;

    int page, candidate;

    public RAND(int seed) {
        super();
        this.originalSeed = seed;
        this.seed = seed;
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

    @Override
    public int getNextFrame() {
        int frame;
        if ((frame = getEmptyFrame()) != -1) {
            return frame;
        }
        Random random = new Random(seed++);
        return Math.abs(random.nextInt() % Main.FRAMES);
    }

    @Override
    public void clear() {
        super.clear();
        seed = originalSeed;
    }
}
