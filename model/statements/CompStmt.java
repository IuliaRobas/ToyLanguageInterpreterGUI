package model.statements;

import model.PrgState;
import model.structures.MyIStack;

public class CompStmt implements IStmt {

    IStmt first;
    IStmt second;

    @Override
    public PrgState execute(PrgState state) {
        MyIStack<IStmt> stk=state.getExeStack();
        stk.push(second);
        stk.push(first);
        return null;
    }

    @Override
    public String toString() {
        return first.toString()+"; "+second.toString();
    }

    public IStmt getFirst() {
        return first;
    }

    public void setFirst(IStmt first) {
        this.first = first;
    }

    public IStmt getSecond() {
        return second;
    }

    public void setSnd(IStmt second) {
        this.second = second;
    }

    public CompStmt(IStmt first, IStmt second) {
        this.first = first;
        this.second = second;
    }
}
