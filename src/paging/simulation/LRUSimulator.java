package paging.simulation;

import paging.algorithms.LRU;
import paging.algorithms.PagingAlgorithm;
import paging.process.Process;

public class LRUSimulator implements Simulator {

    LRU algorithm;

    private final int seed;

    public LRUSimulator(int seed) {
        this.seed = seed;
        algorithm = new LRU();
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
