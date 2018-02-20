package model.expressions;

import exceptions.ExpressionException;
import model.structures.MyIDictionary;
import model.structures.MyIHeap;

public class VarExp extends Exp {
    String id;

    public VarExp(String id) {
        this.id = id;
    }

    @Override
    public int eval(MyIDictionary<String, Integer> tbl, MyIHeap<Integer> hp) throws ExpressionException {
        return tbl.lookUp(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
