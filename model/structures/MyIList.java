package model.structures;

public interface MyIList<T> {
    public boolean add(T element);

    public String toString();

    T get(int index);

    public T remove(int index);

    public T set(int index, T element);

    public Iterable<T> getAll();

}
