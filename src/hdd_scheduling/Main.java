package hdd_scheduling;

import hdd_scheduling.algorithms.Algorithm;
import hdd_scheduling.algorithms.*;

public class Main {

    /**
     * Max size of a disk.
     */
    public static final int MAX = 512;

    /**
     * Threshold for generating a new request each head jump.
     */
    public static final float REQUEST_THRESHOLD = 0.95f;

    /**
     * Threshold for determining whether a new request should be RT or not.
     */
    public static final float REALTIME_THRESHOLD = 0.2f;

    public static FCFS fcfs = new FCFS();
    public static SSTF sstf = new SSTF();
    public static SCAN scan = new SCAN();
    public static CSCAN cscan = new CSCAN();

    public static void main(String[] args) {

        simulate(Algorithm.FCFS, 100, false);
        simulate(Algorithm.SSTF, 100, false);
        simulate(Algorithm.SCAN, 100, false);
        simulate(Algorithm.CSCAN, 100, false);

    }

    public static void simulate(Algorithm algo, int requestCount, boolean generateRealTime) {

        switch (algo) {
            case FCFS -> fcfs.run(0, requestCount, generateRealTime);
            case SSTF -> sstf.run(0, requestCount, generateRealTime);
            case SCAN -> scan.run(0, requestCount, generateRealTime);
            case CSCAN -> cscan.run(0, requestCount, generateRealTime);
            default -> System.out.println("Invalid algorithm");
        }

    }

}
