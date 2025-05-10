package paging.simulation;

import paging.algorithms.OPT;
import paging.algorithms.PagingAlgorithm;
import paging.process.Process;

public class OPTSimulator implements Simulator {

    OPT algorithm;

    private final int seed;

    public OPTSimulator(int seed) {
        this.seed = seed;
        this.algorithm = new OPT();
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
