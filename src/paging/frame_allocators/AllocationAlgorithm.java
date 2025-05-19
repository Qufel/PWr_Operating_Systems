package paging.frame_allocators;

import paging.process.Process;

public interface AllocationAlgorithm {
    void allocateProcesses();
    void setupProcessFrames(Process process, int first, int last);
}
