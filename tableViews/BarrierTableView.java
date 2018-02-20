package tableViews;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;

import java.util.ArrayList;



public class BarrierTableView {
    private SimpleIntegerProperty index, value;
    private SimpleListProperty<Integer> valuesList;


    public BarrierTableView(Integer index, Integer value) {
        this.index = new SimpleIntegerProperty(index);
        this.value = new SimpleIntegerProperty(value);
    }

    public int getIndex() {
        return index.get();
    }

    public SimpleIntegerProperty indexProperty() {
        return index;
    }

    public void setIndex(int index) {
        this.index.set(index);
    }

    public int getValue() {
        return value.get();
    }

    public SimpleIntegerProperty valueProperty() {
        return value;
    }

    public void setValue(int value) {
        this.value.set(value);
    }

    public ObservableList<Integer> getValuesList() {
        return valuesList.get();
    }

    public SimpleListProperty<Integer> valuesListProperty() {
        return valuesList;
    }

    public void setValuesList(ObservableList<Integer> valuesList) {
        this.valuesList.set(valuesList);
    }

    public BarrierTableView(Integer index, Integer value, ArrayList<Integer> valuesList) {

        this.index = new SimpleIntegerProperty(index);
        this.value = new SimpleIntegerProperty(value);
        //this.valuesList = new SimpleListProperty<SimpleIntegerProperty>(valuesList);
    }
}