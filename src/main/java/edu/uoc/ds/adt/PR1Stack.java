package edu.uoc.ds.adt;

import edu.uoc.ds.adt.sequential.Stack;
import edu.uoc.ds.adt.sequential.StackArrayImpl;

public class PR1Stack {
    public static final int CAPACITY = 15;

    private final Stack<Integer> stack = new StackArrayImpl<>(CAPACITY);

    public Stack<Integer> getStack() {
        return stack;
    }

    public void push(int value) {
        stack.push(value);
    }

    public int pop() {
        return stack.pop();
    }
}
