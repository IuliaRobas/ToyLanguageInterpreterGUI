package model.statements;

import exceptions.ExpressionException;
import model.PrgState;
import model.Tuple;
import model.expressions.Exp;
import model.structures.MyIDictionary;

import java.io.BufferedReader;
import java.io.IOException;

public class readFile implements IStmt {

    Exp expFileID;
    String varName;

    public readFile(Exp expFileID, String varName) {
        this.expFileID = expFileID;
        this.varName = varName;
    }

    public readFile() {
    }

    @Override
    public String toString() {
        return "readFile{" + expFileID +
                "," + varName +
                '}';
    }

    @Override
    public PrgState execute(PrgState state) throws ExpressionException, IOException {
        MyIDictionary<Integer, Tuple<String, BufferedReader>> fileTable = state.getFileTable();

        int fd = this.expFileID.eval(state.getSymTable(), state.getHeap());

        if (fileTable.isDefined(fd) == false) {
            throw new ExpressionException("No opened file with file descriptor " + fd);
        }

        Tuple<String, BufferedReader> br = fileTable.lookUp(fd);

        String line = br.getItem2().readLine();
        int val = 0;
        if (line != null) {
            val = Integer.parseInt(line);
        }
        state.getSymTable().add(this.varName, val);

        return null;
    }
}
