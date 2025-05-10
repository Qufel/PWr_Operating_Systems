package paging.results;

import paging.algorithms.PagingAlgorithm;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

public class ResultBuilder {

    public static final String PATH = "src/paging/results/task_3/";

    public static final String PAGE_FAULT_FILE = "page_faults.csv";
    public static final String THRASHING_FILE = "thrashing.csv";

    LinkedList<PagingAlgorithm> algorithms;

    File thrashingReport;
    File pageFaultsReport;

    public ResultBuilder(PagingAlgorithm ...algorithms) {
        if (algorithms == null || algorithms.length == 0) {
            throw new IllegalArgumentException("Algorithms cannot be null or empty");
        }

        this.algorithms = new LinkedList<>(Arrays.asList(algorithms));

        pageFaultsReport = new File(PATH + PAGE_FAULT_FILE);
        thrashingReport = new File(PATH + THRASHING_FILE);

        try {

            if (!pageFaultsReport.createNewFile()) {
                pageFaultsReport.delete();
                pageFaultsReport.createNewFile();
            }

            if (!thrashingReport.createNewFile()) {
                thrashingReport.delete();
                thrashingReport.createNewFile();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void report() {

    }

    private void writeRow(String path) {

    }

}
