package edu.uoc.ds.adt.model;

import edu.uoc.ds.adt.Segment;
import edu.uoc.ds.adt.sequential.LinkedList;
import edu.uoc.ds.exceptions.InvalidPositionException;
import edu.uoc.ds.traversal.Iterator;

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
        segments.insertEnd(new Segment(x, y));
    }

    @Override
    public void addHead(Segment newHead) {
        segments.insertBeginning(newHead);
    }

    @Override
    public Segment deleteTail() {
        return segments.deleteLast();
    }

    @Override
    public int size() {
        return segments.size();
    }

    public boolean contains(int x, int y) {
        Iterator<Segment> iterator = segments.values();
        while (iterator.hasNext()) {
            try {
                Segment segment = iterator.next();
                if (segment.getX() == x && segment.getY() == y) {
                    return true;
                }
            } catch (InvalidPositionException e) {
                throw new IllegalStateException("Snake segment traversal failed.", e);
            }
        }
        return false;
    }
}
