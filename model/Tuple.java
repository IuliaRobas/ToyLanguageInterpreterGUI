package model;

public class Tuple<X, Y> {
    X item1;
    Y item2;

    public Tuple() {
    }

    public Tuple(X item1, Y item2) {
        this.item1 = item1;
        this.item2 = item2;
    }

    public X getItem1() {
        return item1;
    }

    public void setItem1(X item1) {
        this.item1 = item1;
    }

    public Y getItem2() {
        return item2;
    }

    public void setItem2(Y item2) {
        this.item2 = item2;
    }
}
