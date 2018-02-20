package model.structures;

import exceptions.ExpressionException;

import java.util.Collection;
import java.util.Map;

public interface MyIDictionary<K, V> {

    public void add(K key, V value);//public void put(K key, V value);

    public Iterable<K> getAll();

    public Iterable<K> getAllKeys();

    public MyIDictionary<K,V> clone();

    String toString();

    V lookUp(K key) throws ExpressionException;//V get(K key);

    boolean isDefined(K key); // boolean containsKey(K key);

    public void update(K key, V value);

    int size();

    boolean isEmpty();

    boolean containsValue(V value);

    public void remove(K key);

    public void clear();

    public Collection<V> values();

    Map<K,V> getContent();
    void setContent(Map<K, V> content);

    public MyIDictionary<K,V> copy();


}
