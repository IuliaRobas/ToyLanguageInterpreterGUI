package model.expressions;

import exceptions.ExpressionException;
import model.structures.MyIDictionary;
import model.structures.MyIHeap;

public class NotExp extends Exp {

    private Exp exp;

    public NotExp(Exp exp) {
        this.exp = exp;
    }

    @Override
    public int eval(MyIDictionary<String, Integer> tbl, MyIHeap<Integer> hp) throws ExpressionException {
        int x = exp.eval(tbl, hp);
        if (x == 0)
            return 1;
        return 0;
    }

    @Override
    public String toString() {
        return "!"+exp.toString();
    }
}
