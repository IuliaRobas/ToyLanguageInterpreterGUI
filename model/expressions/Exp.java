package model.expressions;

import exceptions.ExpressionException;
import model.structures.MyIDictionary;
import model.structures.MyIHeap;

public abstract class Exp {

    //public abstract int eval(MyIDictionary<String,Integer> tbl) throws ExpressionException;
    public abstract int eval(MyIDictionary<String,Integer> tbl, MyIHeap<Integer> hp) throws ExpressionException;
    public abstract String toString();

}
