package process_scheduling;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ResultBuilder {

    File fcfsRes;
    File srtfRes;
    File rrRes;

    StringBuilder fcfsSb = new StringBuilder();
    StringBuilder srtfSb = new StringBuilder();
    StringBuilder rrSb = new StringBuilder();

    int i = 1;

    public ResultBuilder () {

        fcfsSb.append("No.;Processes;");
        srtfSb.append("No.;Processes;");
        rrSb.append("No.;Processes;");

        fcfsSb.append("Average Execution Time;").append("Average Waiting Time;").append("Process Switches;").append(String.format("Processes Finished In %d Cycles;", SimulationData.CYCLES_COUNT)).append(System.lineSeparator());
        srtfSb.append("Average Execution Time;").append("Average Waiting Time;").append("Starving Processes;").append("Process Switches;").append(String.format("Processes Finished in %d Cycles;", SimulationData.CYCLES_COUNT)).append(System.lineSeparator());
        rrSb.append("Average Execution Time;").append("Average Waiting Time;").append("Average Processor Access Time;").append("Process Switches;").append(String.format("Processes Finished in %d Cycles;", SimulationData.CYCLES_COUNT)).append(System.lineSeparator());
    }

    public void append(int processes, SimulationData fcfsData, SimulationData srtfData, SimulationData rrData) {

        fcfsSb.append(String.format("%d;%d;", i, processes));
        srtfSb.append(String.format("%d;%d;", i, processes));
        rrSb.append(String.format("%d;%d;", i, processes));

        fcfsSb.append(String.format("%f;%f;%d;%f;", fcfsData.totalExecutionTime / processes, fcfsData.totalWaitingTime / processes, fcfsData.switches, fcfsData.getAverageFinishedProcessAmount())).append(System.lineSeparator());
        srtfSb.append(String.format("%f;%f;%d;%d;%f;", srtfData.totalExecutionTime / processes, srtfData.totalWaitingTime / processes, srtfData.starvedProcesses, srtfData.switches, srtfData.getAverageFinishedProcessAmount())).append(System.lineSeparator());
        rrSb.append(String.format("%f;%f;%f;%d;%f;", rrData.totalExecutionTime / processes, rrData.totalWaitingTime / processes, rrData.totalProcessorAccessTime / processes, rrData.switches, rrData.getAverageFinishedProcessAmount())).append(System.lineSeparator());

        i++;
    }

    public void createFile(String filename) throws IOException {

        try {

            fcfsRes = new File(filename + "_fcfs.csv");
            srtfRes = new File(filename + "_srtf.csv");
            rrRes = new File(filename + "_rr.csv");

            if (fcfsRes.exists()) {
                fcfsRes.delete();
            }

            if (srtfRes.exists()) {
                srtfRes.delete();
            }

            if (rrRes.exists()) {
                rrRes.delete();
            }

            if (fcfsRes.createNewFile()) {

                FileWriter fw = new FileWriter(fcfsRes);

                fw.write(fcfsSb.toString());

                fw.close();

            }

            if (srtfRes.createNewFile()) {

                FileWriter fw = new FileWriter(srtfRes);

                fw.write(srtfSb.toString());

                fw.close();
            }

            if (rrRes.createNewFile()) {
                FileWriter fw = new FileWriter(rrRes);

                fw.write(rrSb.toString());

                fw.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
