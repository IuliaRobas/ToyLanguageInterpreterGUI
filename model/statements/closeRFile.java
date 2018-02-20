package model.statements;

import exceptions.ExpressionException;
import model.PrgState;
import model.Tuple;
import model.expressions.Exp;
import model.structures.MyIDictionary;

import java.io.BufferedReader;
import java.io.IOException;

public class closeRFile implements IStmt {
    Exp expFileID;

    public closeRFile() {
    }

    public closeRFile(Exp expFileID) {
        this.expFileID = expFileID;
    }

    public Exp getExpFileID() {
        return expFileID;
    }

    public void setExpFileID(Exp expFileID) {
        this.expFileID = expFileID;
    }

    @Override
    public PrgState execute(PrgState state) throws ExpressionException, IOException {
        MyIDictionary<Integer, Tuple<String, BufferedReader>> fileTable = state.getFileTable();

        int fd = this.expFileID.eval(state.getSymTable(), state.getHeap());
        if (state.getFileTable().isDefined(fd) == false) {
            throw new ExpressionException("No opened file with file descriptor " + fd);

        }

        Tuple<String, BufferedReader> br = fileTable.lookUp(fd);
        br.getItem2().close();
        fileTable.remove(fd);

        //return state;
        return null;
    }

    @Override
    public String toString() {
        return "closeRFile{" + expFileID +
                '}';
    }
}
