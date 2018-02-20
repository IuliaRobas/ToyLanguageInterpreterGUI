package model.statements;

import exceptions.ExpressionException;
import model.PrgState;
import model.expressions.Exp;
import model.structures.MyIDictionary;
import model.structures.MyIStack;

public class PrintStmt implements IStmt {

    Exp exp;

    @Override
    public String toString() {
        return "Print("+exp.toString()+")";
    }

    public PrintStmt(Exp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState prgState) throws ExpressionException {

        MyIStack<IStmt> stk = prgState.getExeStack();
        MyIDictionary<String, Integer> symTbl = prgState.getSymTable();
        int val = exp.eval(symTbl,prgState.getHeap());
        prgState.getOut().add(val);
        return null;

    }
}
