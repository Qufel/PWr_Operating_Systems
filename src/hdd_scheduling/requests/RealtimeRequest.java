package hdd_scheduling.requests;

import hdd_scheduling.Main;

import java.util.Random;

public class RealtimeRequest extends Request {

    private int deadline;

    public RealtimeRequest(int position, int deadline) {
        super(position);
        this.deadline = deadline;
    }

    public int getDeadline() {
        return deadline;
    }

    public static RealtimeRequest generateRequest(int seed) {
        Random random = new Random(seed);

        int position = random.nextInt(1, Main.MAX + 1);
        int deadline = random.nextInt(Main.MIN_DEADLINE, Main.MAX_DEADLINE + 1);

        return new RealtimeRequest(position, deadline);
    }

    @Override
    public String toString() {
        return "Position: " + getPosition() + " | Deadline: " + getDeadline();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof RealtimeRequest request)) return false;
        if (this == request) return true;

        return this.getPosition() == request.getPosition() && this.getDeadline() == request.getDeadline();
    }
}
