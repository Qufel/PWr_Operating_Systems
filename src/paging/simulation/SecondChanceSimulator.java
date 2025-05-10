package paging.simulation;

import paging.algorithms.PagingAlgorithm;
import paging.algorithms.SecondChance;
import paging.process.Process;

public class SecondChanceSimulator implements Simulator {

    SecondChance algorithm;
    private final int seed;

    public SecondChanceSimulator(int seed) {
        this.seed = seed;
        algorithm = new SecondChance();
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
