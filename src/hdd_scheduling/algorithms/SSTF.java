package hdd_scheduling.algorithms;

import hdd_scheduling.requests.Request;

public class SSTF extends SchedulingAlgorithm {

    public SSTF() {
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

        Request current = null;

        while (this.data.requestsDone < count || !this.queue.isEmpty()) {

            // Pick a new current request
            if (current == null && !this.queue.isEmpty()) {
                current = this.getClosestRequest();
            }

            // Chance for a new random request to be queued before each head jump
            offsetSeed = this.queueRandomRequest(offsetSeed, count, false);

            if (current == null)
                continue;

            // If head is on current's position, serve it
            if (this.head == current.getPosition()) {
                serveRequest(current);
                current = null;
                continue;
            }

            // Change head's position
            this.moveHead(current);
        }

    }

    /**
     * Searches for request that has the smallest distance to head position.
     * @return {@link Request} {@code request} a request of smallest distance to head
     */
    protected Request getClosestRequest() {

        int distance = Integer.MAX_VALUE;
        Request request = null;

        int d;

        for (Request r : this.getRequests()) {
            d = Math.abs(r.getPosition() - this.head);
            if (d < distance) {
                distance = d;
                request = r;
            }

        }

        return request;

    }

}
