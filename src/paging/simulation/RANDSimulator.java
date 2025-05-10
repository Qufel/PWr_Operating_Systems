package paging.simulation;

import paging.algorithms.PagingAlgorithm;
import paging.algorithms.RAND;
import paging.process.Process;

public class RANDSimulator implements Simulator {

    RAND algorithm;
    private final int seed;

    public RANDSimulator(int seed) {
        this.seed = seed;
        this.algorithm = new RAND(seed);
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
