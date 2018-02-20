package model.statements;

import exceptions.ExpressionException;
import model.PrgState;
import model.booleanExpressions.LessExp;
import model.expressions.Exp;
import model.expressions.VarExp;
import model.structures.MyIStack;

import java.io.IOException;

public class ForStmt implements IStmt {

    Exp exp1;
    Exp exp2;
    Exp exp3;
    IStmt stmt;

    @Override
    public String toString() {
        return "for(v="
                + exp1.toString() + ";v<"
                + exp2.toString() + ";"
                + exp3.toString() + ") " +
                stmt.toString();
    }

    public Exp getExp1() {
        return exp1;
    }

    public void setExp1(Exp exp1) {
        this.exp1 = exp1;
    }

    public Exp getExp2() {
        return exp2;
    }

    public void setExp2(Exp exp2) {
        this.exp2 = exp2;
    }

    public Exp getExp3() {
        return exp3;
    }

    public void setExp3(Exp exp3) {
        this.exp3 = exp3;
    }

    public IStmt getStmt() {
        return stmt;
    }

    public void setStmt(IStmt stmt) {
        this.stmt = stmt;
    }

    public ForStmt() {

    }

    public ForStmt(Exp exp1, Exp exp2, Exp exp3, IStmt stmt) {

        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws ExpressionException, IOException {
        MyIStack<IStmt> stk = state.getExeStack();
        IStmt iStmt = new CompStmt(
                new AssignStmt("v", exp1),
                new WhileStmt(new LessExp(new VarExp("v"), exp2),
                        new CompStmt(stmt, new AssignStmt("v", exp3))));
        stk.push(iStmt);
        return null;

    }
}