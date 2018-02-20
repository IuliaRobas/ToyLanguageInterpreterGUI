package model.statements;

import exceptions.ExpressionException;
import model.PrgState;
import model.expressions.Exp;
import model.structures.MyIStack;

public class IfStmt implements IStmt {

    Exp exp;
    IStmt thenS;
    IStmt elseS;

    public IfStmt(Exp exp, IStmt thenS, IStmt elseS) {
        this.exp = exp;
        this.thenS = thenS;
        this.elseS = elseS;
    }

    @Override
    public String toString() {
        return "IF ("+ exp.toString()+") THEN (" +thenS.toString()
                +") ELSE ("+elseS.toString()+")";}

    @Override
    public PrgState execute(PrgState state) throws ExpressionException {

        MyIStack<IStmt> stk = state.getExeStack();
        int val = this.exp.eval(state.getSymTable(),state.getHeap());
        if (val != 0)
            stk.push(this.thenS);
        else
            stk.push(this.elseS);
        return null;

    }

    public Exp getExp() {
        return exp;
    }

    public void setExp(Exp exp) {
        this.exp = exp;
    }

    public IStmt getThenS() {
        return thenS;
    }

    public void setThenS(IStmt thenS) {
        this.thenS = thenS;
    }

    public IStmt getElseS() {
        return elseS;
    }

    public void setElseS(IStmt elseS) {
        this.elseS = elseS;
    }
}
