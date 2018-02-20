package model.structures;

import exceptions.ExpressionException;

import java.util.HashMap;
import java.util.Map;

public class MyHeap<V> implements MyIHeap<V> {

    private Map<Integer, V> heap;
    private int firstFreePosition;

    public MyHeap() {
        this.heap = new HashMap<>();
        this.firstFreePosition = 1;
    }

    @Override
    public boolean containsKey(int key) {
        return heap.containsKey(key);
    }

    @Override
    public boolean containsValue(V value) {
        return heap.containsValue(value);
    }


    private int computeNextFreePosition() {
        while (heap.containsKey(this.firstFreePosition))
            this.firstFreePosition++;
        return this.firstFreePosition;
    }

    public Map<Integer, V> getHeap() {
        return heap;
    }

    public void setHeap(Map<Integer, V> heap) {
        this.heap = heap;
    }

    @Override
    public int put(V value) {
        int currentFreePosition = this.firstFreePosition;
        this.heap.put(this.firstFreePosition, value);

        this.firstFreePosition = computeNextFreePosition();
        return currentFreePosition;
    }

    @Override
    public void setContent(Map<Integer, V> content) {
        this.heap = content;
    }

    @Override
    public Map<Integer, V> getContent() {
        return this.heap;
    }

    @Override
    public V put(int key, V value) {
        return this.heap.put(key, value);

    }

    @Override
    public V get(int key) throws ExpressionException {

        V value = heap.get(key);
        if (value == null) {
            throw new ExpressionException("Heap location " + key + " is not allocated.");
        }
        return value;

    }

    @Override
    public V remove(int key) {
        return this.heap.remove(key);
    }

    @Override
    public String toString() {
        String text = "";
        for (Map.Entry<Integer, V> entry : heap.entrySet()) {
            Integer key = entry.getKey();
            V value = entry.getValue();
            text += "{" + key.toString() + "->" + value.toString() + "}";
        }
        return text;
    }

    @Override
    public Iterable<Integer> getAll() {
        return heap.keySet();
    }
}
