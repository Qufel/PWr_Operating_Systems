package hdd_scheduling.requests;

import hdd_scheduling.Main;

import java.util.Random;

public class Request {

    protected int position;

    protected int addedAt;
    protected int waitTime;

    public Request(int position) {
        this.position = position;
        this.waitTime = 0;
    }

    public int getPosition() {
        return position;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void incrementWaitTime() {
        waitTime++;
    }

    public int getAddTime() {
        return addedAt;
    }

    public void setAddTime(int addTime) {
        addedAt = addTime;
    }

    @Override
    public String toString() {
        return "Position: " + position;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Request request)) return false;
        if (this == request) return true;

        return this.position == request.position;
    }

    /**
     * Generates a random request.
     * @param seed a seed for RNG
     * @param isRealTime should request be considered a real time request or not
     * @return {@link Request} {@code request} a randomly generated request
     */
    public static Request generateRequest(int seed) {
        Random random = new Random(seed);

        int position = random.nextInt(1, Main.MAX + 1);

        return new Request(position);
    }
}
