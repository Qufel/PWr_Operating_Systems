package paging.simulation;

import paging.algorithms.PagingAlgorithm;
import paging.algorithms.SecondChance;
import paging.process.Process;

public class SecondChanceSimulator implements Simulator {

    SecondChance algorithm;
    private final int seed;

    private final int programSize;

    public SecondChanceSimulator(int seed, int programSize) {
        this.seed = seed;
        algorithm = new SecondChance();
        this.programSize = programSize;
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
