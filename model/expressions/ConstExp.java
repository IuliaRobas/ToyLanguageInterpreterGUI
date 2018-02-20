package model.expressions;

import exceptions.ExpressionException;
import model.structures.MyIDictionary;
import model.structures.MyIHeap;

public class ConstExp extends Exp {
    int number;

    public ConstExp(int number) {
        this.number = number;

    }


    @Override
    public int eval(MyIDictionary<String, Integer> tbl, MyIHeap<Integer> hp) throws ExpressionException {
        return number;
    }

    @Override
    public String toString() {
        return Integer.toString(number);
    }
}
