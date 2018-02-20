package model.structures;

import exceptions.ExpressionException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MyBarrier<K, V> implements MyIBarrier<K, V> {

    private int firstFree;
    Map<K, V> dictionary = new HashMap<K, V>();


    @Override
    public int getFirstFree() {
        return this.firstFree;
    }

    public void setFirstFree() {
        this.firstFree = this.firstFree + 1;
    }

    public Map<K, V> getDictionary() {
        return dictionary;
    }

    public void setDictionary(Map<K, V> dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    public void add(K key, V value) {
        dictionary.put(key, value);

    }

    @Override
    public Iterable<K> getAll() {
        return dictionary.keySet();
    }

    @Override
    public Iterable<K> getAllKeys() {
        return dictionary.keySet();
    }

    @Override
    public MyIDictionary<K, V> clone() {
        MyIDictionary<K, V> newDictionary = new MyDictionary<>();
        for (K key : dictionary.keySet())
            newDictionary.add(key, dictionary.get(key));
        return newDictionary;
    }

    @Override
    public V lookUp(K key) throws ExpressionException {
        V value = dictionary.get(key);
        if (value == null) {
            throw new ExpressionException("ID " + key + " does not exist in the symTable.");
        }
        return value;
    }

    @Override
    public boolean isDefined(K key) {
        return dictionary.containsKey(key);
    }

    @Override
    public void update(K key, V value) {
        dictionary.put(key, value);
    }

    @Override
    public int size() {
        return this.dictionary.size();
    }

    @Override
    public boolean isEmpty() {
        return this.dictionary.isEmpty();
    }

    @Override
    public boolean containsValue(V value) {
        return this.dictionary.containsValue(value);
    }

    @Override
    public void remove(K key) {
        this.dictionary.remove(key);
    }

    @Override
    public void clear() {

    }

    @Override
    public Collection<V> values() {
        return dictionary.values();
    }

    @Override
    public Map<K, V> getContent() {
        return this.dictionary;
    }

    @Override
    public void setContent(Map<K, V> content) {
        this.dictionary = content;
    }

    @Override
    public MyIDictionary<K, V> copy() {
        MyIDictionary<K, V> newDictionary = new MyDictionary<>();
        newDictionary.setContent(this.getContent());
        return newDictionary;
    }

    @Override
    public String toString() {
        String text = "";
        for (Map.Entry<K, V> entry : dictionary.entrySet()) {
            K key = entry.getKey();
            V value = entry.getValue();
            text += "{" + key.toString() + "->" + value.toString() + "}";
        }
        return text;
    }
}
