package paging.simulation;

import paging.algorithms.PagingAlgorithm;
import paging.algorithms.RAND;
import paging.process.Process;

public class RANDSimulator implements Simulator {

    RAND algorithm;

    private final int seed;
    private final int programSize;

    public RANDSimulator(int seed, int programSize) {
        this.seed = seed;
        this.programSize = programSize;
        this.algorithm = new RAND(seed);
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
