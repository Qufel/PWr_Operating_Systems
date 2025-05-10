package paging.simulation;

import paging.algorithms.FIFO;
import paging.algorithms.PagingAlgorithm;
import paging.process.Process;

public class FIFOSimulator implements Simulator {

    FIFO algorithm;
    private final int seed;

    public FIFOSimulator(int seed) {
        this.seed = seed;
        this.algorithm = new FIFO();
    }

    @Override
    public void simulate() {
        algorithm.addProcess(Process.generateProcess(1, 0, 1, 256));
        while (!algorithm.isFinished()) {
            algorithm.step();
        }
    }

    @Override
    public PagingAlgorithm getAlgorithm() {
        return algorithm;
    }
}
