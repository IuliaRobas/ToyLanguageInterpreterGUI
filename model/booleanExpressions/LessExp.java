package model.booleanExpressions;

import exceptions.ExpressionException;
import model.expressions.Exp;
import model.structures.MyIDictionary;
import model.structures.MyIHeap;

public class LessExp extends Exp {
    Exp exp1;
    Exp exp2;

    @Override
    public int eval(MyIDictionary<String, Integer> tbl, MyIHeap<Integer> hp) throws ExpressionException {
        if (exp1.eval(tbl, hp) < exp2.eval(tbl, hp))
            return 1;
        return 0;
    }

    @Override
    public String toString() {
        return "("+exp1.toString()+"<"+exp2.toString()+")";
    }

    public Exp getExp1() {
        return exp1;
    }

    public void setExp1(Exp exp1) {
        this.exp1 = exp1;
    }

    public Exp getExp2() {
        return exp2;
    }

    public void setExp2(Exp exp2) {
        this.exp2 = exp2;
    }

    public LessExp(Exp exp1, Exp exp2) {

        this.exp1 = exp1;
        this.exp2 = exp2;
    }
}
