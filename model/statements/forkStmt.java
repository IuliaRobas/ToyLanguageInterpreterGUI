package model.statements;

import exceptions.ExpressionException;
import model.PrgState;
import model.Tuple;
import model.structures.*;

import java.io.BufferedReader;
import java.io.IOException;

public class forkStmt implements IStmt {
    public forkStmt(IStmt stmt) {
        this.stmt = stmt;
    }

    IStmt stmt;

    @Override
    public String toString() {
        return "fork("+stmt.toString()+")";
    }

    @Override
    public PrgState execute(PrgState state) throws ExpressionException, IOException {

        MyIStack<IStmt> newStack=new MyStack<>();
        //MyIDictionary<String, Integer> newSymTable=state.getSymTable().copy();
        MyIDictionary<String, Integer> newSymTable=state.getSymTable().clone();

        MyIList<Integer> newOut=state.getOut();
        MyIDictionary<Integer, Tuple<String, BufferedReader>> newFileTable=state.getFileTable();
        MyIHeap<Integer> newHeap=state.getHeap();

        //newStack.push(stmt);

        PrgState newPrgState=new PrgState(newStack,newSymTable,newOut,newFileTable,stmt,newHeap);
        int currentID=newPrgState.getCurrentID();
        newPrgState.setID(currentID*10);
        return newPrgState;
    }
}
