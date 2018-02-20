package model.statements;

import exceptions.ExpressionException;
import model.PrgState;
import model.expressions.Exp;
import model.expressions.NotExp;
import model.structures.MyIStack;

import java.io.IOException;

public class RepeatStmt implements IStmt {

   private IStmt stmt1;
    private Exp exp2;

    public RepeatStmt() {
    }

    @Override
    public String toString() {
        return "repeat(" +
                stmt1.toString() +" until " +
                exp2.toString() +
                ')';
    }

    public Exp getExp2() {
        return exp2;
    }

    public void setExp2(Exp exp2) {
        this.exp2 = exp2;
    }

    public RepeatStmt(IStmt stmt1, Exp exp2) {

        this.stmt1 = stmt1;
        this.exp2 = exp2;
    }

    public IStmt getStmt1() {

        return stmt1;
    }

    public void setStmt1(IStmt stmt1) {
        this.stmt1 = stmt1;
    }




    @Override
    public PrgState execute(PrgState state) throws ExpressionException, IOException {
        MyIStack<IStmt> stk = state.getExeStack();
        IStmt newStmt=new CompStmt(stmt1,
                new WhileStmt(new NotExp(exp2),stmt1));
        stk.push(newStmt);
        return null;
    }
}
