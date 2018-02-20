package model.statements;

import exceptions.ExpressionException;
import model.PrgState;
import model.Tuple;
import model.structures.MyIDictionary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class openRFile implements IStmt {

    String varFileID;
    String fileName;
    int fileDescriptor;

    public openRFile(String varFileID, String fileName) {
        this.varFileID = varFileID;
        this.fileName = fileName;
        this.fileDescriptor = 0;
    }

    public String getVarFileID() {
        return varFileID;
    }

    @Override
    public PrgState execute(PrgState state) throws ExpressionException, IOException {
        MyIDictionary<Integer, Tuple<String, BufferedReader>> fileTable = state.getFileTable();
        for (Tuple<String, BufferedReader> entry : fileTable.values())
            if (entry.getItem1() == this.fileName)
                throw new ExpressionException("Filename already in the FileTable.");
        BufferedReader br;
        FileReader in;
        try {
            in = new FileReader(fileName);
            br = new BufferedReader(in);
        } catch (IOException ioe) {
            throw new IOException(ioe.getMessage());
        }

        state.getFileTable().add(this.fileDescriptor, new Tuple<String, BufferedReader>(this.fileName, br));
        state.getSymTable().update(this.varFileID, this.fileDescriptor);

        this.fileDescriptor++;

        return null;
    }

    public void setVarFileID(String varFileID) {
        this.varFileID = varFileID;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "openRFile{" + varFileID +
                "," + fileName +
                '}';
    }
}
