package paging;

import paging.process.Process;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;

public class Main {

    public static final int FRAMES = 8;
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
    public static final int MIN_LOCALITY_CHAIN = 16;
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
    public static final int LOCALITY_REFERENCES = 4;

    public static final int MAX_REFERENCE_ID = 64;

    public static final int MIN_REFERENCE_COUNT = 64;
    public static final int MAX_REFERENCE_COUNT = 128;

    public static void main(String[] args) {
        Random random = new Random();
        Process process = Process.generateProcess(1, 0);
        System.out.println(process.toString());
        System.out.println(process.getSequence().length);
    }

}
