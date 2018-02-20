package model.statements;

import exceptions.ExpressionException;
import model.PrgState;
import model.structures.MyIStack;

import java.io.IOException;

public class SleepStmt implements IStmt {

    @Override
    public String toString() {
        return "sleep(" + number + ")";
    }

    public SleepStmt(int number) {
        this.number = number;
    }

    private int number;

    @Override
    public PrgState execute(PrgState state) throws ExpressionException, IOException {

        MyIStack<IStmt> stk = state.getExeStack();
        if (number != 0) {
            stk.push(new SleepStmt(number - 1));
        }
        return null;
    }
}
