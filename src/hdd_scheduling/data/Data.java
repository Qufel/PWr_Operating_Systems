package hdd_scheduling.data;

import java.util.ArrayList;

public class Data {

    public int requestsCreated = 0;
    public int requestsDone = 0;
    public ArrayList<Integer> requestsPositions = new ArrayList<>();

    public int starvingRequests = 0;
    public float requestsWaitTime = 0f;

    public int headJumps = 0;

    // C-Scan only
    public int returnsAmount = 0;

    // Real Time only
    public int realTimeRequestsCreated = 0;
    public int realTimeRequestsDone = 0;

    public Data() {

    }

    public void addWaitTime(int time) {
        if (requestsDone == 0)
            return;

        requestsWaitTime *= (float) requestsDone - 1;
        requestsWaitTime += (float) time;
        requestsWaitTime /= (float) requestsDone;

    }

}
