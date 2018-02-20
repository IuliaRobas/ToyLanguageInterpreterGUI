package model.statements;

import exceptions.ExpressionException;
import model.PrgState;
import model.expressions.Exp;
import model.structures.MyIDictionary;
import model.structures.MyIStack;

import java.io.IOException;

public class WhileStmt implements IStmt {
    IStmt stmt;
    Exp exp;

    public WhileStmt( Exp exp,IStmt stmt) {
        this.stmt = stmt;
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "while("+exp.toString()+") {"+stmt.toString()+"}";
    }

    @Override
    public PrgState execute(PrgState state) throws ExpressionException, IOException {

        MyIStack<IStmt> stk = state.getExeStack();
        MyIDictionary<String, Integer> symTbl = state.getSymTable();
        int val = exp.eval(symTbl,state.getHeap());

        if(val!=0){
            stk.push(new WhileStmt(exp,stmt));
            stk.push(stmt);
        }

        return null;
    }
}
