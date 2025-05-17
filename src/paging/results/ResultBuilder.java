package paging.results;

import paging.Main;
import paging.algorithms.PagingAlgorithm;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

public class ResultBuilder {

    public static final String PATH = "src/paging/results/task_3/";

    public static final String PAGE_FAULT_FILE = "page_faults.csv";
    public static final String THRASHING_FILE = "thrashing.csv";

    LinkedList<PagingAlgorithm> algorithms;

    File thrashingReport;
    File pageFaultsReport;

    FileWriter pageFaultsWriter;
    FileWriter thrashingReportWriter;

    int rowCounter = 0;

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

            pageFaultsWriter = new FileWriter(pageFaultsReport);
            thrashingReportWriter = new FileWriter(thrashingReport);

            LinkedList<String> elements = new LinkedList<>();
            elements.add("No.");
            elements.add("Frames");

            for (PagingAlgorithm algorithm : algorithms) {
                elements.add(algorithm.getClass().getSimpleName());
            }

            writeRow(pageFaultsWriter, createRow(elements));
            writeRow(thrashingReportWriter, createRow(elements));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void report() {

        LinkedList<String> pageFaults = new LinkedList<>();
        LinkedList<String> thrashing = new LinkedList<>();

        int number = ++rowCounter;

        pageFaults.add(number + "");
        pageFaults.add(Main.FRAMES + "");

        thrashing.add(number + "");
        thrashing.add(Main.FRAMES + "");

        for (PagingAlgorithm algorithm : algorithms) {
            pageFaults.add(algorithm.getTotalPageFaults() + "");
            thrashing.add(algorithm.getTotalThrashing() + "");
        }

        try {
            writeRow(pageFaultsWriter, createRow(pageFaults));
            writeRow(thrashingReportWriter, createRow(thrashing));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void finish() throws IOException {
        pageFaultsWriter.close();
        thrashingReportWriter.close();
    }

    private void writeRow(FileWriter writer, String string) throws IOException {
        writer.write(string + "\n");
    }

    private String createRow(LinkedList<String> elements) {
        StringBuilder sb = new StringBuilder();
        for (String element : elements) {
            sb.append(element);
            sb.append(";");
        }
        return sb.toString();
    }

}
