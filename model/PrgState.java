package model;

import exceptions.ExpressionException;
import exceptions.MyStackException;
import model.statements.IStmt;
import model.structures.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class PrgState {

    MyIStack<IStmt> exeStack;
    MyIDictionary<String, Integer> symTable;
    MyIList<Integer> out;
    IStmt originalProgram;
    MyIDictionary<Integer, Tuple<String, BufferedReader>> fileTable;
    MyIHeap<Integer> heap;
    MyIBarrier<Integer, Tuple<List<Integer>, Integer>> barrierTable;
    MyILockTable<Integer,Integer> lockTable;

    public MyILockTable<Integer, Integer> getLockTable() {
        return lockTable;
    }

    public void setLockTable(MyILockTable<Integer, Integer> lockTable) {
        this.lockTable = lockTable;
    }



    int ID;
    static int currentID = 1;

    public PrgState(MyIStack<IStmt> exeStack, MyIDictionary<String, Integer> symTable, MyIList<Integer> out, IStmt originalProgram, MyIDictionary<Integer, Tuple<String, BufferedReader>> fileTable, MyIHeap<Integer> heap, MyIBarrier<Integer, Tuple<List<Integer>, Integer>> barrierTable, MyILockTable<Integer, Integer> lockTable) {

        this.ID=1;
        //currentID=this.ID;

        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.originalProgram = originalProgram;
        this.fileTable = fileTable;
        this.heap = heap;
        this.barrierTable = barrierTable;
        this.lockTable = lockTable;

        this.exeStack.push(originalProgram);
    }

    public PrgState(MyIStack<IStmt> exeStack, MyIDictionary<String, Integer> symTable, MyIList<Integer> out, IStmt originalProgram, MyIDictionary<Integer, Tuple<String, BufferedReader>> fileTable, MyIHeap<Integer> heap, MyIBarrier<Integer, Tuple<List<Integer>, Integer>> barrierTable) {

        this.ID = 1;
        //currentID=this.ID;

        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.originalProgram = originalProgram;
        this.fileTable = fileTable;
        this.heap = heap;
        this.barrierTable = barrierTable;

        this.exeStack.push(originalProgram);
    }


    public PrgState(MyIStack<IStmt> exeStack, MyIDictionary<String, Integer> symTable, MyIList<Integer> out, MyIDictionary<Integer, Tuple<String, BufferedReader>> fileTable, IStmt originalProgram, MyIHeap<Integer> heap) {

        this.ID = 1;
        //currentID=this.ID;

        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.originalProgram = originalProgram;
        this.fileTable = fileTable;
        this.heap = heap;

        this.exeStack.push(originalProgram);
    }

    public int getCurrentID() {
        return currentID;
    }

    public void setCurrentID(int currentID) {
        this.currentID = currentID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID=ID;
        this.currentID = this.ID * 10;
    }

    public boolean isCompleted() {
        return this.exeStack.isEmpty();
    }


    public PrgState oneStep() throws MyStackException, ExpressionException, IOException {
        MyIStack<IStmt> stk = this.getExeStack();
        try {
            IStmt crtStmt = stk.pop();
            return crtStmt.execute(this);
        } catch (MyStackException e) {
            throw e;
        } catch (ExpressionException ee) {
            throw ee;
        } catch (IOException ioe) {
            throw ioe;
        }
    }

    @Override
    public String toString() {
        return "\n" + "***************PrgState***************" + "\n" +
                "ID: " + this.ID + "\n" +
                "ExeStack: " + exeStack + "\n" +
                "SymTable_" + ID + ": " + symTable + "\n" +
                "Out: " + out + "\n" +
                "Heap: " + heap;
                //"BarrierTable: "+barrierTable;
    }

    public MyIBarrier<Integer, Tuple<List<Integer>, Integer>> getBarrierTable() {
        return barrierTable;
    }

    public void setBarrierTable(MyIBarrier<Integer, Tuple<List<Integer>, Integer>> barrierTable) {
        this.barrierTable = barrierTable;
    }
    public MyIHeap<Integer> getHeap() {
        return heap;
    }

    public void setHeap(MyIHeap<Integer> heap) {
        this.heap = heap;
    }

    public MyIStack<IStmt> getExeStack() {
        return exeStack;
    }

    public void setExeStack(MyIStack<IStmt> exeStack) {
        this.exeStack = exeStack;
    }

    public MyIDictionary<String, Integer> getSymTable() {
        return symTable;
    }

    public void setSymTable(MyIDictionary<String, Integer> symTable) {
        this.symTable = symTable;
    }

    public MyIList<Integer> getOut() {
        return out;
    }

    public void setOut(MyIList<Integer> out) {
        this.out = out;
    }

    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    public void setOriginalProgram(IStmt originalProgram) {
        this.originalProgram = originalProgram;
    }

    public MyIDictionary<Integer, Tuple<String, BufferedReader>> getFileTable() {
        return fileTable;
    }

    public void setFileTable(MyIDictionary<Integer, Tuple<String, BufferedReader>> fileTable) {
        this.fileTable = fileTable;
    }
}


