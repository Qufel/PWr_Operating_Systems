package paging.simulation;

import paging.algorithms.OPT;
import paging.algorithms.PagingAlgorithm;
import paging.process.Process;

public class OPTSimulator implements Simulator {

    OPT algorithm;

    private final int seed;
    private final int programSize;

    public OPTSimulator(int seed, int programSize) {
        this.seed = seed;
        this.programSize = programSize;
        this.algorithm = new OPT();
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
