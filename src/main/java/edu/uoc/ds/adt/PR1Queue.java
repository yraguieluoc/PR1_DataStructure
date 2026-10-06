package edu.uoc.ds.adt;

import edu.uoc.ds.adt.sequential.Queue;
import edu.uoc.ds.adt.sequential.QueueArrayImpl;

public class PR1Queue {
    public static final int CAPACITY = 15;

    private final Queue<Integer> queue = new QueueArrayImpl<>(CAPACITY);

    public Queue<Integer> getQueue() {
        return queue;
    }

    public void add(int value) {
        queue.add(value);
    }

    public int poll() {
        return queue.poll();
    }
}
