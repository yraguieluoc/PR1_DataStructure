package edu.uoc.ds.adt;

import java.util.Objects;

public class Segment {
    private final int x;
    private final int y;

    public Segment(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Segment)) {
            return false;
        }
        Segment segment = (Segment) other;
        return x == segment.x && y == segment.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Segment{" + "x=" + x + ", y=" + y + '}';
    }
}
