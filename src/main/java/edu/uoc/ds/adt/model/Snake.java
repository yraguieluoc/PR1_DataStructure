package edu.uoc.ds.adt.model;

import edu.uoc.ds.adt.Segment;

import java.util.LinkedList;

public class Snake implements ISnake {
    private final LinkedList<Segment> segments = new LinkedList<>();

    @Override
    public Segment head() {
        return segments.peekFirst();
    }

    @Override
    public Segment tail() {
        return segments.peekLast();
    }

    @Override
    public void addSegment(int x, int y) {
        segments.addLast(new Segment(x, y));
    }

    @Override
    public void addHead(Segment newHead) {
        segments.addFirst(newHead);
    }

    @Override
    public Segment deleteTail() {
        return segments.removeLast();
    }

    @Override
    public int size() {
        return segments.size();
    }

    public boolean contains(int x, int y) {
        return segments.contains(new Segment(x, y));
    }
}
