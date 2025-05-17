package paging.algorithms;

import jdk.jshell.spi.ExecutionControl;
import paging.Main;
import paging.process.Process;

import java.util.Arrays;
import java.util.LinkedList;

public abstract class PagingAlgorithm {

    protected int[] frames;
    protected LinkedList<Process> processes;

    protected int finishedProcesses = 0;

    protected int totalPageFaults = 0;
    protected int totalThrashing = 0;

    public PagingAlgorithm() {
        this.processes = new LinkedList<Process>();

        this.frames = new int[Main.FRAMES];
        for (int i = 0; i < Main.FRAMES; i++) {
            frames[i] = -1; // Empty frames are marked by -1
        }

    }

    public void step() { throw new UnsupportedOperationException("Not supported yet."); }

    //region Processes Queue

    public void addProcess(Process process) {
        this.processes.add(process);
    }

    public LinkedList<Process> getProcesses() {
        return this.processes;
    }

    public int getProcessesSize() {
        return this.processes.size();
    }

    //endregion

    //region Frames Allocation

    public int getPageAtFrame(int frame) {
        if (frame < 0 || frame >= Main.FRAMES)
            throw new IllegalArgumentException("Invalid frame");

        return this.frames[frame];
    }

    public void setPageAtFrame(int frame, int page) {
        if (frame < 0 || frame >= Main.FRAMES)
            throw new IllegalArgumentException("Invalid frame");

        if (page < 0)
            throw new IllegalArgumentException("Invalid page");

        this.frames[frame] = page;
    }

    /**
     * Looks for the next frame to which the page value will be allocated. It is meant to be overwritten by the specific algorithm.
     * @return Frame index
     */
    public int getNextFrame() { throw new UnsupportedOperationException("Not supported yet."); }

    /**
     * Looks for frame which does not have any page assigned to it.
     * @return Frame index. If non found {@code -1}
     */
    public int getEmptyFrame() {
        for (int i = 0; i < Main.FRAMES; i++) {
            if (frames[i] == -1)
                return i;
        }
        return -1;
    }

    public boolean isPageInFrames(int page) {
        for (int frame : frames) {
            if (frame == page)
                return true;
        }
        return false;
    }

    //endregion

    public int getFinishedProcesses() {
        return finishedProcesses;
    }

    protected void finishProcess(Process process) {
        processes.remove(process);
        totalPageFaults += process.getPageFaults();
        totalThrashing += process.getThrashing();
        finishedProcesses++;
    }

    public boolean isFinished() {
        return finishedProcesses == Main.PROCESSES;
    }

    public void clear() {

        totalPageFaults = 0;
        totalThrashing = 0;
        finishedProcesses = 0;

        this.processes = new LinkedList<Process>();

        this.frames = new int[Main.FRAMES];
        for (int i = 0; i < Main.FRAMES; i++) {
            frames[i] = -1; // Empty frames are marked by -1
        }
    }

    public int getTotalPageFaults() {
        return totalPageFaults;
    }

    public int getTotalThrashing() {
        return totalThrashing;
    }

    public String showFrames() {
        return Arrays.toString(frames);
    }
}
