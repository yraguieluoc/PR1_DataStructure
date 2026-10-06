package edu.uoc.ds.adt;

import edu.uoc.ds.adt.exceptions.SnakeOutOfBoundsException;
import edu.uoc.ds.adt.model.Snake;
import edu.uoc.ds.adt.sequential.QueueArrayImpl;
import edu.uoc.ds.traversal.Iterator;

import java.util.Arrays;
import java.util.Objects;

public class SnakeGame implements ISnakeGame {
    private final Snake snake = new Snake();
    private final char[][] board = new char[BOARD_SIZE][BOARD_SIZE];
    private final QueueArrayImpl<LogEntry> log = new QueueArrayImpl<>(MAX_LOG_SIZE);

    public SnakeGame() {
        for (char[] row : board) {
            Arrays.fill(row, '.');
        }
    }

    @Override
    public void addSegment(int x, int y) {
        requireOnBoard(x, y);
        snake.addSegment(x, y);
        board[y][x] = 'S';
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
        board[y][x] = '@';
    }

    @Override
    public boolean hasFoodAt(int x, int y) {
        return isOnBoard(x, y) && board[y][x] == '@';
    }

    @Override
    public char getSegment(int x, int y) {
        if (!isOnBoard(x, y)) {
            return '.';
        }
        return board[y][x];
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

        boolean eats = hasFoodAt(nextX, nextY);
        Segment currentTail = snake.tail();
        boolean movingIntoVacatingTail = !eats
                && currentTail.getX() == nextX
                && currentTail.getY() == nextY;
        if (snake.contains(nextX, nextY) && !movingIntoVacatingTail) {
            appendLog(currentHead.getX(), currentHead.getY(), direction, "COLLISION");
            throw new SnakeOutOfBoundsException();
        }

        appendLog(currentHead.getX(), currentHead.getY(), direction, eats ? "EAT" : "MOVE");
        if (eats) {
            board[nextY][nextX] = '.';
            appendLog(currentHead.getX(), currentHead.getY(), direction, "MOVE");
        } else {
            Segment removedTail = snake.deleteTail();
            board[removedTail.getY()][removedTail.getX()] = '.';
        }
        snake.addHead(new Segment(nextX, nextY));
        board[nextY][nextX] = 'S';
    }

    @Override
    public Iterator<LogEntry> logEntries() {
        return log.values();
    }

    private void appendLog(int x, int y, Direction direction, String message) {
        if (log.isFull()) {
            log.poll();
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
        StringBuilder display = new StringBuilder();
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                display.append(board[y][x]);
            }
            if (y + 1 < BOARD_SIZE) {
                display.append(System.lineSeparator());
            }
        }
        return display.toString();
    }
}
