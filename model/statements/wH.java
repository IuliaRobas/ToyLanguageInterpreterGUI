package model.statements;

import exceptions.ExpressionException;
import model.PrgState;
import model.expressions.Exp;

import java.io.IOException;

public class wH implements IStmt {
    String varName;
    Exp exp;

    public wH(String varName, Exp exp) {
        this.varName = varName;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws ExpressionException, IOException {
        try {
            int heapAddress = state.getSymTable().lookUp(varName);
            state.getHeap().get(heapAddress);
            state.getHeap().put(heapAddress, exp.eval(state.getSymTable(), state.getHeap()));
            return null;
        } catch (ExpressionException ee) {
            throw new ExpressionException(ee.getMessage());
        }
    }

    @Override
    public String toString() {
        return "wH(" + varName + ","+exp.toString() + ")";
    }
}
