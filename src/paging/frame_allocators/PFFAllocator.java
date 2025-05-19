package paging.frame_allocators;

import paging.Main;
import paging.algorithms.PagingAlgorithm;
import paging.process.Process;

import java.util.LinkedList;

public class PFFAllocator implements AllocationAlgorithm {

    PagingAlgorithm pagingAlgorithm;

    public PFFAllocator(PagingAlgorithm pagingAlgorithm) {
        this.pagingAlgorithm = pagingAlgorithm;
    }

    @Override
    public void allocateProcesses() {

        int processes = getProcesses().size();

        for (int frame = 0, process = 0; frame < Main.FRAMES && process < processes; process++) {
            Process p = getProcesses().get(process);

            int pages = p.getLength();
            int frames = Math.max(1, pages / Main.FRAMES);

            setupProcessFrames(p, Math.min(frame, Main.FRAMES - 1), Math.min(frame + frames - 1, Main.FRAMES - 1));

            frame += frames;

            p.resume();
        }

    }

    @Override
    public void setupProcessFrames(Process process, int first, int last) {
        process.setAllowedFrameRange(first, last);
    }

    private LinkedList<Process> getProcesses() {
        if (pagingAlgorithm == null) throw new NullPointerException("Paging algorithm is null");
        return pagingAlgorithm.getProcesses();
    }
}
