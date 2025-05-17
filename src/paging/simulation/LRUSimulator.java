package paging.simulation;

import paging.algorithms.LRU;
import paging.algorithms.PagingAlgorithm;
import paging.process.Process;

public class LRUSimulator implements Simulator {

    LRU algorithm;

    private final int seed;
    private final int programSize;

    public LRUSimulator(int seed, int programSize) {
        this.seed = seed;
        this.programSize = programSize;
        algorithm = new LRU();
    }

    @Override
    public void simulate() {
        algorithm.clear();
        algorithm.addProcess(Process.generateProcess(1, seed, 1, programSize));
        while (!algorithm.isFinished()) {
            algorithm.step();
        }
    }

    @Override
    public PagingAlgorithm getAlgorithm() {
        return algorithm;
    }

}
