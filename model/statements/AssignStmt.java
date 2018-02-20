package model.statements;

import exceptions.ExpressionException;
import model.PrgState;
import model.expressions.Exp;
import model.structures.MyIDictionary;
import model.structures.MyIStack;

public class AssignStmt implements IStmt {
    String id;
    Exp exp;

    public AssignStmt(String id, Exp exp) {
        this.id = id;
        this.exp = exp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id + "=" + exp.toString();
    }

    public Exp getExp() {
        return exp;
    }

    public void setExp(Exp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws ExpressionException {

        MyIStack<IStmt> stk = state.getExeStack();
        MyIDictionary<String, Integer> symTbl = state.getSymTable();
        int val = exp.eval(symTbl,state.getHeap());
        if (symTbl.isDefined(id)) {
            symTbl.update(id, val);
        } else {
            symTbl.add(id, val);
        }
        //return state;
        return null;

    }
}
