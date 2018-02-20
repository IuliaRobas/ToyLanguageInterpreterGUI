package model.statements;

import exceptions.ExpressionException;
import model.PrgState;
import model.booleanExpressions.EqualExp;
import model.expressions.Exp;
import model.structures.MyIStack;

import java.io.IOException;

public class switchStmt implements IStmt {

    private Exp exp;
    private Exp exp1;
    private Exp exp2;

    private IStmt stmt1;
    private IStmt stmt2;
    private IStmt stmt3;

    @Override
    public String toString() {
        return "switch(" + exp.toString() + ")" +
                "(case" + "(" + exp1.toString() + ")" + stmt1.toString() + ")" +
                "(case" + "(" + exp2.toString() + ")" + stmt2.toString() + ")" +
                "(default " + stmt3.toString() + ")";
    }

    public Exp getExp() {
        return exp;
    }

    public void setExp(Exp exp) {
        this.exp = exp;
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


    public IStmt getStmt1() {
        return stmt1;
    }

    public void setStmt1(IStmt stmt1) {
        this.stmt1 = stmt1;
    }

    public IStmt getStmt2() {
        return stmt2;
    }

    public void setStmt2(IStmt stmt2) {
        this.stmt2 = stmt2;
    }

    public IStmt getStmt3() {
        return stmt3;
    }

    public void setStmt3(IStmt stmt3) {
        this.stmt3 = stmt3;
    }

    public switchStmt() {

    }

    public switchStmt(Exp exp, Exp exp1, IStmt stmt1, Exp exp2, IStmt stmt2, IStmt stmt3) {

        this.exp = exp;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.stmt1 = stmt1;
        this.stmt2 = stmt2;
        this.stmt3 = stmt3;
    }

    @Override
    public PrgState execute(PrgState state) throws ExpressionException, IOException {

        MyIStack<IStmt> stk = state.getExeStack();

        IfStmt ifStmt = new IfStmt(new EqualExp(exp, exp1), stmt1,
                new IfStmt(new EqualExp(exp, exp2), stmt2, stmt3));

        stk.push(ifStmt);

        return null;
    }
}
