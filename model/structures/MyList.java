package model.structures;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements MyIList<T> {

    List<T> list=new ArrayList<T>();


    @Override
    public String toString() {
        String text="";
        for(T elem:list){
            text+=elem.toString()+" ";
        }
        return text;

    }

    @Override
    public boolean add(T element) {
        return list.add(element);
    }

    @Override
    public T get(int index) {
        return list.get(index);
    }

    @Override
    public T remove(int index) {
        return list.remove(index);
    }

    @Override
    public T set(int index, T element) {
       return this.list.set(index,element);
    }

    @Override
    public Iterable<T> getAll() {
        return list;
    }

}
