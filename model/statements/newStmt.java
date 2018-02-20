package model.statements;

import exceptions.ExpressionException;
import model.PrgState;
import model.expressions.Exp;
import model.structures.MyIHeap;

public class newStmt implements IStmt {

    String varName;
    Exp exp;


    @Override
    public PrgState execute(PrgState state) throws ExpressionException {
        MyIHeap<Integer> heap = state.getHeap();
        int memoryAddress = heap.put(exp.eval(state.getSymTable(),state.getHeap()));
        state.getSymTable().update(varName, memoryAddress);

        return null;
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    public Exp getExp() {
        return exp;
    }

    public void setExp(Exp exp) {
        this.exp = exp;
    }

    public newStmt(String varName, Exp exp) {

        this.varName = varName;
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "new(" + varName + "," + exp.toString() + ")";
    }
}
