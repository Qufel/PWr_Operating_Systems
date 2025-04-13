package hdd_scheduling.data;

import hdd_scheduling.algorithms.Algorithm;
import hdd_scheduling.algorithms.SchedulingAlgorithm;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DataWriter {

    private File file;
    private FileWriter fw;

    public Algorithm algorithm;

    int no = 0;

    public DataWriter(Algorithm algorithm) throws IOException {

        String path = "src/" + this.getClass().getPackage().getName().replace(".", "/") + "/result/" + algorithm.name().toLowerCase() + "_data.csv";

        this.file = new File(path);
        this.algorithm = algorithm;

        try {
            // If previous data exists, delete it
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            fw = new FileWriter(file);
        } catch (IOException e) {
            System.out.println("Error creating file for " + algorithm.name());
            e.printStackTrace();
        }

        this.addRow(';',"No.", "Request Amount", "Head Jumps", "Avg. Waiting Time", "Starving Amount");

    }

    public void write(SchedulingAlgorithm algorithm) throws IOException {

        // Get algorithm's data
        Data data = algorithm.data;

        // Write row
        this.addRow(';', no, data.requestsDone, data.headJumps, data.requestsWaitTime, data.starvingRequests);

    }

    private void addRow (char delimiter, Object ...elements) {

        StringBuilder sb = new StringBuilder();

        try {
            for (Object element : elements) {
                sb.append(element.toString()).append(delimiter);
            }
            sb.append('\n');
            fw.write(sb.toString());
        } catch (IOException e) {
            System.out.println("Error writing to file for " + algorithm.name());
            e.printStackTrace();
        }

        no++;
    }

    public void close() throws IOException {
        fw.close();
    }

}
