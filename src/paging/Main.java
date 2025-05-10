package paging;

import paging.results.ResultBuilder;
import paging.simulation.*;

public class Main {

    public static int FRAMES = 1;
    public static final int PROCESSES = 1;

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
    public static final int MAX_LOCALITIES = 100;
    /**
     * How deep should locality look for references to copy.
     */
    public static final int LOCALITY_DEPTH = 8;
    /**
     * How much references should be in a locality.
     */
    public static final int LOCALITY_REFERENCES = FRAMES;

    public static final int REFERENCES_WIDTH = 16;

    public static final int MIN_REFERENCE_COUNT = 10000;
    public static final int MAX_REFERENCE_COUNT = 10000;

    public static final int THRASHING_CHECK_LENGTH = 16;
    public static final int THRASHING_THRESHOLD = 12;

    static FIFOSimulator fifoSim = new FIFOSimulator(0);
    static RANDSimulator randSim = new RANDSimulator(0);
    static OPTSimulator optSim = new OPTSimulator(0);
    static LRUSimulator lruSim = new LRUSimulator(0);
    static SecondChanceSimulator schSim = new SecondChanceSimulator(0);

    static ResultBuilder resultBuilder;

    public static void main(String[] args) {

        resultBuilder = new ResultBuilder(
                fifoSim.getAlgorithm(),
                randSim.getAlgorithm(),
                optSim.getAlgorithm(),
                lruSim.getAlgorithm(),
                schSim.getAlgorithm());

        fifoSim.simulate();
        randSim.simulate();
        optSim.simulate();
        lruSim.simulate();
        schSim.simulate();

        resultBuilder.report();
    }

}
