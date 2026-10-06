package edu.uoc.ds.adt;

import edu.uoc.ds.adt.exceptions.SnakeOutOfBoundsException;
import edu.uoc.ds.adt.model.Snake;
import edu.uoc.ds.traversal.Iterator;
import edu.uoc.ds.exceptions.InvalidPositionException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SnakeGame implements ISnakeGame {
    private final Snake snake = new Snake();
    private final boolean[][] food = new boolean[BOARD_SIZE][BOARD_SIZE];
    private final List<LogEntry> log = new ArrayList<>();

    @Override
    public void addSegment(int x, int y) {
        requireOnBoard(x, y);
        snake.addSegment(x, y);
    }

    @Override
    public void addSegments(int[][] segments) {
        Objects.requireNonNull(segments, "segments");
        for (int[] segment : segments) {
            if (segment == null || segment.length < 2) {
                throw new IllegalArgumentException("Each segment must contain x and y coordinates.");
            }
            addSegment(segment[0], segment[1]);
        }
    }

    @Override
    public int size() {
        return snake.size();
    }

    @Override
    public void addFood(int x, int y) {
        requireOnBoard(x, y);
        food[x][y] = true;
    }

    @Override
    public boolean hasFoodAt(int x, int y) {
        return isOnBoard(x, y) && food[x][y];
    }

    @Override
    public char getSegment(int x, int y) {
        if (!isOnBoard(x, y)) {
            return ' ';
        }
        if (snake.contains(x, y)) {
            return 'S';
        }
        return food[x][y] ? '@' : ' ';
    }

    @Override
    public Segment head() {
        return snake.head();
    }

    @Override
    public Segment tail() {
        return snake.tail();
    }

    @Override
    public void move(Direction direction) throws SnakeOutOfBoundsException {
        Objects.requireNonNull(direction, "direction");
        Segment currentHead = snake.head();
        if (currentHead == null) {
            throw new IllegalStateException("Cannot move a snake with no segments.");
        }

        int nextX = currentHead.getX() + direction.getDx();
        int nextY = currentHead.getY() + direction.getDy();
        if (!isOnBoard(nextX, nextY)) {
            appendLog(currentHead.getX(), currentHead.getY(), direction, "COLLISION");
            throw new SnakeOutOfBoundsException();
        }

        boolean eats = food[nextX][nextY];
        appendLog(currentHead.getX(), currentHead.getY(), direction, eats ? "EAT" : "MOVE");
        if (eats) {
            food[nextX][nextY] = false;
            appendLog(currentHead.getX(), currentHead.getY(), direction, "MOVE");
        } else {
            snake.deleteTail();
        }
        snake.addHead(new Segment(nextX, nextY));
    }

    @Override
    public Iterator<LogEntry> logEntries() {
        List<LogEntry> snapshot = new ArrayList<>(log);
        return new Iterator<LogEntry>() {
            private int index;

            @Override
            public boolean hasNext() {
                return index < snapshot.size();
            }

            @Override
            public LogEntry next() throws InvalidPositionException {
                if (!hasNext()) {
                    throw new InvalidPositionException("No more log entries.");
                }
                return snapshot.get(index++);
            }
        };
    }

    private void appendLog(int x, int y, Direction direction, String message) {
        if (log.size() == MAX_LOG_SIZE) {
            log.remove(0);
        }
        log.add(new LogEntry(x, y, direction, message));
    }

    private static boolean isOnBoard(int x, int y) {
        return x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE;
    }

    private static void requireOnBoard(int x, int y) {
        if (!isOnBoard(x, y)) {
            throw new IllegalArgumentException("Coordinates must be inside the board.");
        }
    }

    @Override
    public String toString() {
        StringBuilder board = new StringBuilder();
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                board.append(getSegment(x, y));
            }
            if (y + 1 < BOARD_SIZE) {
                board.append(System.lineSeparator());
            }
        }
        return board.toString();
    }
}
