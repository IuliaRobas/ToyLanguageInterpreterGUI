package model.statements;

import exceptions.ExpressionException;
import model.PrgState;
import model.structures.MyIDictionary;
import model.structures.MyILockTable;
import model.structures.MyLockTable;

import java.io.IOException;

public class newLock implements IStmt {

    String var;

    public newLock(String var) {
        this.var = var;
    }

    public String getVar() {

        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }

    @Override
    public String toString() {
        return "newLock(" +
                var + ')';
    }

    @Override
    public PrgState execute(PrgState state) throws ExpressionException, IOException {

        synchronized (state.getLockTable()) {

            state.getLockTable().add(state.getLockTable().getFirstFree(), -1);
            MyIDictionary<String, Integer> symTbl = state.getSymTable();
            if (symTbl.isDefined(var)) {
                symTbl.update(var, state.getLockTable().getFirstFree());
            } else {
                symTbl.add(var, state.getLockTable().getFirstFree());
            }

        }
        return null;
    }
}
