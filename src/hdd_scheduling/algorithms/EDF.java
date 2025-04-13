package hdd_scheduling.algorithms;

import hdd_scheduling.requests.RealtimeRequest;
import hdd_scheduling.requests.Request;

public class EDF extends SSTF {

    public EDF() {
        super();
    }

    Request current = null;

    int offsetSeed;

    @Override
    public void run(int seed, int count) {

        this.reset();
        this.offsetSeed = seed;

        while (this.data.requestsDone < count || !this.queue.isEmpty()) {

            if (this.hasRealTime && current == null) {

                // TODO: Serving of RT

            } else {
                sstfStep(count);
            }

            // TODO: Update deadline of all RT after each step

        }

    }

    private void sstfStep(int count) {
        // Pick a new current request
        if (current == null && !this.queue.isEmpty()) {
            current = this.getClosestRequest();
        }

        // Chance for a new random request to be queued before each head jump
        offsetSeed = this.queueRandomRequest(offsetSeed, count, true);

        if (current == null)
            return;

        // If head is on current's position, serve it
        if (this.head == current.getPosition()) {
            serveRequest(current);
            current = null;
            return;
        }

        // Change head's position
        this.moveHead(current);
    }

    private RealtimeRequest getShortestDeadline() {

        RealtimeRequest request = null;
        int shortestDeadline = Integer.MAX_VALUE;

        for (Request r : this.getRequests()) {
            if (!(r instanceof RealtimeRequest rr))
                continue;

            if (rr.getDeadline() < shortestDeadline) {
                shortestDeadline = rr.getDeadline();
                request = rr;
            }
        }

        return request;

    }
}
