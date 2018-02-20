package model.structures;

import exceptions.ExpressionException;

import java.util.Map;

public interface MyIHeap<V> {

    int put(V value);

    V put(int key, V value);

    V get(int key) throws ExpressionException;

    V remove(int key);

    boolean containsKey(int key);

    boolean containsValue(V value);

    void setContent(Map<Integer, V> content);
    Map<Integer, V> getContent();

    String toString();

    public Iterable<Integer> getAll();

}