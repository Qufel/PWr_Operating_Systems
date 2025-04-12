package hdd_scheduling.requests;

import hdd_scheduling.Main;

import java.util.Random;

public class Request {

    private int position;
    private boolean isRealTime;

    private int addedAt;
    private int waitTime;

    public Request(int position, boolean isRealTime) {
        this.position = position;
        this.isRealTime = isRealTime;
        this.waitTime = 0;
    }

    public int getPosition() {
        return position;
    }

    public boolean isRealTime() {
        return isRealTime;
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
        return "Position: " + position + " | RealTime: " + isRealTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Request request)) return false;
        if (this == request) return true;

        return this.position == request.position && this.isRealTime == request.isRealTime;
    }

    /**
     * Generates a random request.
     * @param seed a seed for RNG
     * @param isRealTime should request be considered a real time request or not
     * @return {@link Request} {@code request} a randomly generated request
     */
    public static Request generateRequest(int seed, boolean isRealTime) {
        Random random = new Random(seed);

        int position = random.nextInt(1, Main.MAX + 1);

        return new Request(position, isRealTime);
    }
}
