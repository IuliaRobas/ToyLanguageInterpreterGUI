package model.statements;

import exceptions.ExpressionException;
import model.PrgState;

import java.io.IOException;

public interface IStmt {
    PrgState execute(PrgState state) throws ExpressionException, IOException;
    String toString();
}
