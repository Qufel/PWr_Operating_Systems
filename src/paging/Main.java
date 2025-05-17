package paging;

import paging.results.ResultBuilder;
import paging.simulation.*;

import java.io.IOException;

public class Main {

    public static int FRAMES = 16;
    public static final int PROCESSES = 1;

    public static final int PROGRAM_SIZE = 1024;
    public static final int SEED = 1151;

    /**
     * How often should locality of reference start occurring in generation of process's references sequence.
     */
    public static final float LOCALITY_START_CHANCE = 0.125f;
    /**
     *  When locality of reference should stop occurring.
     */
    public static final float LOCALITY_END_CHANCE = 0.25f;
    /**
     * At least how long a locality sequence chain should be.
     */
    public static final int MIN_LOCALITY_CHAIN = 8;
    /**
     * Max amount of localities.
     */
    public static final int MAX_LOCALITIES = PROGRAM_SIZE / 8;
    /**
     * How deep should locality look for references to copy.
     */
    public static final int LOCALITY_DEPTH = 8;
    /**
     * How much references should be in a locality.
     */
    public static final int LOCALITY_REFERENCES = PROGRAM_SIZE / MAX_LOCALITIES * 2;

    public static final int REFERENCES_WIDTH = 16;

    public static final int MIN_REFERENCE_COUNT = 8196;
    public static final int MAX_REFERENCE_COUNT = MIN_REFERENCE_COUNT * 2;

    public static final int THRASHING_THRESHOLD = 32;
    public static final int THRASHING_CHECK_LENGTH = 64;

    static FIFOSimulator fifoSim = new FIFOSimulator(SEED, PROGRAM_SIZE);
    static RANDSimulator randSim = new RANDSimulator(SEED, PROGRAM_SIZE);
    static OPTSimulator optSim = new OPTSimulator(SEED, PROGRAM_SIZE);
    static LRUSimulator lruSim = new LRUSimulator(SEED, PROGRAM_SIZE);
    static SecondChanceSimulator schSim = new SecondChanceSimulator(SEED, PROGRAM_SIZE);

    static ResultBuilder resultBuilder;

    public static void main(String[] args) {

        run();

    }

    public static void run() {
        resultBuilder = new ResultBuilder(
                fifoSim.getAlgorithm(),
                randSim.getAlgorithm(),
                optSim.getAlgorithm(),
                lruSim.getAlgorithm(),
                schSim.getAlgorithm());

        for (; FRAMES <= 128; FRAMES++) {
            fifoSim.simulate();
            randSim.simulate();
            optSim.simulate();
            lruSim.simulate();
            schSim.simulate();

            resultBuilder.report();
        }

        try {
            resultBuilder.finish();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
