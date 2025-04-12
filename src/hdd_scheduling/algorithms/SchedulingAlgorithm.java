package hdd_scheduling.algorithms;

import hdd_scheduling.Main;
import hdd_scheduling.data.Data;
import hdd_scheduling.requests.Request;
import hdd_scheduling.requests.RequestQueue;

import java.util.ArrayList;
import java.util.Random;

public abstract class SchedulingAlgorithm {

    protected RequestQueue queue;

    /**
     * Disk head position.
     */
    protected int head;

    /**
     * Flags if there are real time requests inside the queue. If so, they should be served first if possible.
     */
    protected boolean hasRealTime;

    public Data data;

    public SchedulingAlgorithm() {
        this.queue = new RequestQueue();
        this.data = new Data();
        this.head = 1;
    }

    /**
     * Queues a random request for this algorithm.
     * @param seed a seed for RNG
     * @param max a max count of request to be processed by this algorithm
     * @return {@code int} {@code seed} an incremented seed value
     */
    protected int queueRandomRequest(int seed, int max) {
        Random random = new Random();

        float p = random.nextFloat();

        // If p exceeds threshold and there are still requests to be generated, add a new request
        if (p >= Main.REQUEST_THRESHOLD && this.data.requestsCreated < max) {
            // Generate request

            Request r = Request.generateRequest(seed, false);

            this.addRequest(r);

            this.data.requestsCreated++;

        }
        // Increment seed
        return ++seed;
    }

    /**
     * Adds a new request to the algorithm's queue.
     * @param request a {@link Request} to be added to the queue
     */
    public void addRequest(Request request) {
        // Rise flag
        if (request.isRealTime() && !hasRealTime)
            hasRealTime = true;

        request.setAddTime(this.data.headJumps);
        this.queue.add(request);
    }

    /**
     * Moves head towards {@code request}.
     * @param request a request in which direction the head will move
     */
    protected void moveHead(Request request) {
        if (head < request.getPosition())
            head++;

        if (head > request.getPosition())
            head--;

        this.data.headJumps++;
    }

    protected void moveHead(int direction) {
        head += direction;
        this.data.headJumps++;
    }

    /**
     * Serves (finishes) a given request.
     * @param request a request to be served
     */
    protected void serveRequest(Request request) {
        this.queue.remove(request);

        int waitTime = this.data.headJumps - request.getAddTime();

        this.data.addWaitTime(waitTime);

        // Request is marked as starving if it exceeds twice the time of a current average waiting time for request.
        this.data.starvingRequests += waitTime >= (2 * this.data.requestsWaitTime) ? 1 : 0;

        this.data.requestsDone++;
    }

    /**
     * Resets an algorithm to its original state.
     */
    protected void reset() {
        this.queue = new RequestQueue();
        this.data = new Data();
        this.head = 1;
    }

    protected ArrayList<Request> getRequests() {
        return this.queue.getQueue();
    }
}
