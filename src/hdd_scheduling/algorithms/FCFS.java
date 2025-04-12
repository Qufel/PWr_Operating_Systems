package hdd_scheduling.algorithms;

import hdd_scheduling.Main;
import hdd_scheduling.data.Data;
import hdd_scheduling.requests.Request;
import hdd_scheduling.requests.RequestQueue;

import java.util.ArrayList;
import java.util.Random;

public class FCFS extends SchedulingAlgorithm {

    int offset = 0;

    public FCFS() {
        super();
    }

    /**
     * Runs a simulation for this algorithm.
     * @param seed a seed for RNG
     * @param count a count of requests to be done
     */
    public void run(int seed, int count, boolean generateRealTime) {

        System.out.println("Starting FCFS algorithm");

        this.reset();

        int offsetSeed = seed;

        Request current = null;

        while (this.data.requestsDone < count || !this.queue.isEmpty()) {

            // Pick a new current request
            if (current == null && !this.queue.isEmpty()) {
                current = this.queue.get(0);
            }

            // Chance for a new random request to be queued before each head jump
            offsetSeed = this.queueRandomRequest(offsetSeed, count);

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

}
