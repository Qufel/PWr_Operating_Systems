package paging.frame_allocators;

import paging.Main;
import paging.algorithms.PagingAlgorithm;
import paging.process.Process;

import java.util.LinkedList;

public class EqualAllocator implements AllocationAlgorithm {

    PagingAlgorithm pagingAlgorithm;

    public EqualAllocator(PagingAlgorithm pagingAlgorithm) {
        this.pagingAlgorithm = pagingAlgorithm;
    }

    @Override
    public void allocateProcesses() {

        int processes = getProcesses().size();
        if (processes == 0)
            return;

        int framesPerProcess = Math.max(1, Main.FRAMES / processes);

        for (int frame = 0, process = 0; frame < Main.FRAMES && process < processes; frame += framesPerProcess, process++) {
            Process p = getProcesses().get(process);
            p.setAllowedFrameRange(Math.max(0, frame), Math.max(0, Math.min(frame + framesPerProcess - 1, Main.FRAMES - 1)));
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
