package model.statements;

import exceptions.ExpressionException;
import model.PrgState;
import model.structures.MyIDictionary;

import java.io.IOException;

public class unlock implements IStmt {

    String var;

    @Override
    public String toString() {
        return "unlock(" + var + ")";
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public unlock(String var) {

        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws ExpressionException, IOException {

        MyIDictionary<String, Integer> symTbl = state.getSymTable();
        int foundIndex = symTbl.lookUp(var);

        synchronized (state.getLockTable()) {
            try {
                int result = state.getLockTable().lookUp(foundIndex);
                if (result == state.getID()) {
                    state.getLockTable().update(foundIndex, -1);
                }
            } catch (ExpressionException ee) {
            }
        }

        return null;
    }
}
