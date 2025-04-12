package hdd_scheduling.requests;

import java.util.ArrayList;

public class RequestQueue {

    private final ArrayList<Request> queue;

    public RequestQueue() {
        this.queue = new ArrayList<>();
    }

    public ArrayList<Request> getQueue() {
        return this.queue;
    }

    public boolean isEmpty() {
        return this.queue.isEmpty();
    }

    public int size() {
        return this.queue.size();
    }

    public boolean add(Request request) {
        return this.queue.add(request);
    }

    public Request get(int index) {
        return this.queue.get(index);
    }

    public Request remove(int index) {
        return this.queue.remove(index);
    }

    public boolean remove(Request request) {
        return this.queue.remove(request);
    }

}
