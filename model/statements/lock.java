package model.statements;

import exceptions.ExpressionException;
import model.PrgState;
import model.structures.MyIDictionary;

import java.io.IOException;

public class lock implements IStmt {

    String var;

    @Override
    public String toString() {
        return "lock(" + var + ")";
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public lock(String var) {

        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws ExpressionException, IOException {

        MyIDictionary<String, Integer> symTbl = state.getSymTable();
        int foundIndex = symTbl.lookUp(var);

        synchronized (state.getLockTable()) {
            int result = state.getLockTable().lookUp(foundIndex);
            if (result == -1) {
                state.getLockTable().update(foundIndex, state.getID());
            } else {
                state.getExeStack().push(this);
            }

        }

        return null;
    }
}
