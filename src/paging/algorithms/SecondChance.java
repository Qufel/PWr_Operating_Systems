package paging.algorithms;

import paging.Main;
import paging.frame_allocators.AllocationAlgorithm;
import paging.process.Process;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;

public class SecondChance extends  PagingAlgorithm {

    /**
     * Currently served page.
     */
    int page;
    /**
     * Candidate frame to which new page will be allocated.
     */
    int candidate;

    boolean[] referenceBits;

    /**
     * Queue used to finalize all finished processes to prevent comod shit from happening.
     */
    public LinkedList<Process> finalizationQueue = new LinkedList<>();

    AllocationAlgorithm allocation;

    public SecondChance() {
        super();
        this.referenceBits = new boolean[Main.FRAMES];
    }

    public void setAllocator(AllocationAlgorithm allocation) {
        this.allocation = allocation;
    }
+
    @Override
    public void step() {
        if (allocation == null) throw new NullPointerException("Allocation algorithm is null");

        allocation.allocateProcesses();

        for (Process process : getProcesses()) {

            // Skip suspended processes
            if (process.isSuspended() || process.isFinished()) {
                process.pushFaultState(false); // Because process does nothing it's harmful for it that fault state is not updated
                continue;
            }

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
                finalizationQueue.add(process);
            }

        }

        // Finalize finished processes

        for (Process process : finalizationQueue) {
            System.out.println("Finished process: \n"  + process);
            finishProcess(process);
        }

        finalizationQueue.clear();

        showFrames();

    }

    public int getNextFrame(Process process) {
        int frame = getEmptyFrame(process);
        if (frame != -1) {
            referenceBits[frame] = true;
            return frame;
        }

        frame = getFrameOutOfRange(process);
        if (frame != -1) {
            referenceBits[frame] = true;
            return frame;
        }

        while(referenceBits[process.getVictim()]) {
            referenceBits[process.getVictim()] = false;
            if ((process.getLastAllowedFrame() - process.getFirstAllowedFrame() + 1) == 0)
                System.out.println("fucking piece of shit");
            process.setVictim((process.getVictim() + 1) % (process.getLastAllowedFrame() - process.getFirstAllowedFrame() + 1) + process.getFirstAllowedFrame());
        }

        referenceBits[process.getVictim()] = true;
        frame = process.getVictim();
        if ((process.getLastAllowedFrame() - process.getFirstAllowedFrame() + 1) == 0)
            System.out.println("fucking piece of shit");
        process.setVictim((process.getVictim() + 1) % (process.getLastAllowedFrame() - process.getFirstAllowedFrame() + 1) + process.getFirstAllowedFrame());

        return frame;
    }

    private void setReferenceBitFor(int page) {
        for (int i = 0; i < Main.FRAMES; i++) {
            if (page == frames[i]) {
                referenceBits[i] = true;
                return;
            }
        }
    }

    @Override
    public void clear() {
        super.clear();
        referenceBits = new boolean[Main.FRAMES];
    }

}
