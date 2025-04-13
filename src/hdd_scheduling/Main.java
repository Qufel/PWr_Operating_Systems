package hdd_scheduling;

import hdd_scheduling.algorithms.Algorithm;
import hdd_scheduling.algorithms.*;
import hdd_scheduling.data.DataWriter;

import java.io.IOException;

public class Main {

    /**
     * Seed for this simulation.
     */
    public static final int SEED = 0;

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
    public static final float REALTIME_THRESHOLD = 0.85f;

    public static final int MIN_DEADLINE = 64;
    public static final int MAX_DEADLINE = 512;

    public static FCFS fcfs = new FCFS();
    public static SSTF sstf = new SSTF();
    public static SCAN scan = new SCAN();
    public static CSCAN cscan = new CSCAN();
    public static EDF edf = new EDF();

    public static DataWriter fcfsDW;
    public static DataWriter sstfDW;
    public static DataWriter scanDW;
    public static DataWriter cscanDW;
    public static DataWriter edfDW;

    static {
        try {
            fcfsDW = new DataWriter(Algorithm.FCFS);
            sstfDW = new DataWriter(Algorithm.SSTF);
            scanDW = new DataWriter(Algorithm.SCAN);
            cscanDW = new DataWriter(Algorithm.CSCAN);
            edfDW = new DataWriter(Algorithm.EDF_SSTF);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {

        simulate(Algorithm.EDF_SSTF, 100);
        edfDW.write(edf);

        simulate(Algorithm.SSTF, 100);
        sstfDW.write(sstf);

        // Close all data writers
        fcfsDW.close();
        sstfDW.close();
        scanDW.close();
        cscanDW.close();
        edfDW.close();

    }

    public static void simulate(Algorithm algo, int requestCount) {

        switch (algo) {
            case FCFS -> fcfs.run(SEED, requestCount);
            case SSTF -> sstf.run(SEED, requestCount);
            case SCAN -> scan.run(SEED, requestCount);
            case CSCAN -> cscan.run(SEED, requestCount);
            case EDF_SSTF -> edf.run(SEED, requestCount);
            default -> System.out.println("Invalid algorithm");
        }

    }

}
