package edu.uoc.ds.adt.exceptions;

public class SnakeOutOfBoundsException extends Exception {
    public SnakeOutOfBoundsException() {
        super("The snake cannot move outside the board.");
    }
}
