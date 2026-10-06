package edu.uoc.ds.adt;

public class LogEntry {
    private final int x;
    private final int y;
    private final ISnakeGame.Direction direction;
    private final String msg;

    public LogEntry(int x, int y, ISnakeGame.Direction direction, String msg) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.msg = msg;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ISnakeGame.Direction getDirection() {
        return direction;
    }

    public String getMsg() {
        return msg;
    }
}
