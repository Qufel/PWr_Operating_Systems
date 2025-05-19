package paging;

import paging.frame_allocators.AllocationType;
import paging.results.ResultBuilder;
import paging.simulation.*;

import java.io.IOException;

public class Main {

    public static final int FRAMES = 10;
    public static final int PROCESSES = 20;

    public static final int PROGRAM_SIZE = 16;
    public static final int SEED = 0;

    public static final int MIN_PROGRAM_SIZE = 16;
    public static final int MAX_PROGRAM_SIZE = 64;

    public static final float PROCESS_GENERATION_THRESHOLD = 0.05f;

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
    public static final int MAX_LOCALITIES = 4;
    /**
     * How deep should locality look for references to copy.
     */
    public static final int LOCALITY_DEPTH = 8;
    /**
     * How much references should be in a locality.
     */
    public static final int LOCALITY_REFERENCES = PROGRAM_SIZE / MAX_LOCALITIES * 2;

    public static final int REFERENCES_WIDTH = 16;

    public static final int MIN_REFERENCE_COUNT = 64;
    public static final int MAX_REFERENCE_COUNT = MIN_REFERENCE_COUNT * 2;

    public static final int THRASHING_THRESHOLD = 32;
    public static final int THRASHING_CHECK_LENGTH = 64;

    static FIFOSimulator fifoSim = new FIFOSimulator(SEED, PROGRAM_SIZE);
    static RANDSimulator randSim = new RANDSimulator(SEED, PROGRAM_SIZE);
    static OPTSimulator optSim = new OPTSimulator(SEED, PROGRAM_SIZE);
    static LRUSimulator lruSim = new LRUSimulator(SEED, PROGRAM_SIZE);
    static SecondChanceSimulator schSim = new SecondChanceSimulator(SEED, AllocationType.PFF);

    static ResultBuilder resultBuilder;

    public static void main(String[] args) {

        run();

    }

    public static void run() {

        schSim.simulate();

    }

}
