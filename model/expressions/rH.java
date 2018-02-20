package model.expressions;

import exceptions.ExpressionException;
import model.structures.MyIDictionary;
import model.structures.MyIHeap;


public class rH extends Exp{
    String varName;

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    public rH(String varName) {

        this.varName = varName;
    }

    @Override
    public int eval(MyIDictionary<String, Integer> tbl, MyIHeap<Integer> hp) throws ExpressionException {
       try{
           int heapAddress = tbl.lookUp(varName);
           return hp.get(heapAddress);
       }
       catch (ExpressionException ee){
           throw new ExpressionException(ee.getMessage());
       }

    }

    @Override
    public String toString() {
        return "rH("+varName+")";
    }
}
