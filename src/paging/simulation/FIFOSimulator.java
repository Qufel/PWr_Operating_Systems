package paging.simulation;

import paging.algorithms.FIFO;
import paging.algorithms.PagingAlgorithm;
import paging.process.Process;

public class FIFOSimulator implements Simulator {

    FIFO algorithm;
    private final int seed;

    private final int programSize;

    public FIFOSimulator(int seed, int programSize) {
        this.seed = seed;
        this.programSize = programSize;
        this.algorithm = new FIFO();
    }

    @Override
    public void simulate() {
        algorithm.clear();
        algorithm.addProcess(Process.generateProcess(1, seed, 1, programSize));
//        algorithm.addProcess(new Process(1, new int[]{1,2,3,4,5}, new int[]{1, 2, 3, 4, 1, 2, 5, 1, 2, 3, 4, 5}));
        while (!algorithm.isFinished()) {
            algorithm.step();
        }
//        System.out.println(algorithm.getTotalPageFaults());
    }

    @Override
    public PagingAlgorithm getAlgorithm() {
        return algorithm;
    }
}
