package model.structures;
import exceptions.MyStackException;

import java.util.Stack;

public class MyStack<T> implements MyIStack<T> {
    Stack<T> stack=new Stack<T>();

    @Override
    public T pop() throws MyStackException {

        if(stack.isEmpty())
            throw new MyStackException("Execution stack is empty. Finished evaluating the program.");
        return stack.pop();
    }

    @Override
    public T push(T v) {
        stack.push(v);
        return v;
    }

    @Override
    public boolean isEmpty() {
        return stack.empty();
    }

    @Override
    public Iterable<T> getAll() {
        return stack;
    }

    @Override
    public String toString(){

        String text="";
        for(T elem:stack)
            text+=elem.toString()+" ";
        return text;
    }
}
