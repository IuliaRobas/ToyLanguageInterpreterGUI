package model.structures;

import exceptions.ExpressionException;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public interface MyIBarrier<K, V> extends MyIDictionary<K, V> {
    @Override
    void add(K key, V value);

    int getFirstFree();

    void setFirstFree();

    @Override
    Iterable<K> getAll();

    @Override
    Iterable<K> getAllKeys();

    @Override
    MyIDictionary<K, V> clone();

    @Override
    String toString();

    @Override
    V lookUp(K key) throws ExpressionException;

    @Override
    boolean isDefined(K key);

    @Override
    void update(K key, V value);

    @Override
    int size();

    @Override
    boolean isEmpty();

    @Override
    boolean containsValue(V value);

    @Override
    void remove(K key);

    @Override
    void clear();

    @Override
    Collection<V> values();

    @Override
    Map<K, V> getContent();

    @Override
    void setContent(Map<K, V> content);

    @Override
    MyIDictionary<K, V> copy();
}
