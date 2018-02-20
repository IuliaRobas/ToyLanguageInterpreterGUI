package model.structures;

import exceptions.MyStackException;

public interface MyIStack<T>{

    T pop() throws MyStackException;
    T push(T v);
    String toString();
    boolean isEmpty();
    public  Iterable<T> getAll();

}
