package hdd_scheduling.algorithms;

import hdd_scheduling.Main;
import hdd_scheduling.requests.Request;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.ListIterator;

public class SCAN extends SchedulingAlgorithm {


    public SCAN() {
        super();
    }

    /**
     * Runs a simulation for this algorithm.
     * @param seed a seed for RNG
     * @param count a count of requests to be done
     */
    public void run(int seed, int count) {

        this.reset();

        int offsetSeed = seed;

        boolean forward = true;

        while (this.data.requestsDone < count || !this.queue.isEmpty()) {

            // Chance for a new random request to be queued before each head jump
            offsetSeed = this.queueRandomRequest(offsetSeed, count, false);

            // Get all requests that are on head
            ArrayList<Request> requestsOnHead = this.getRequestsOnPosition(head);

            // If such exist, serve all of them
            while (!requestsOnHead.isEmpty()) {
                Request request = requestsOnHead.removeFirst();
                serveRequest(request);
            }

            //region Move head in correct direction and check if it reached the end of the disk

            this.moveHead(forward ? 1 : -1);

            if (this.head >= Main.MAX)
                forward = false;

            if (this.head <= 1)
                forward = true;

            //endregion
        }

    }

    private ArrayList<Request> getRequestsOnPosition(int position) {

        ArrayList<Request> requests = new ArrayList<>();

        if (this.getRequests().isEmpty())
            return requests;

        for (Request request : this.getRequests()) {
            if (request.getPosition() == position) {
                requests.add(request);
            }
        }

        return requests;

    }


}
