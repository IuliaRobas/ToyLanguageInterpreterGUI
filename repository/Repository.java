package repository;

import model.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {

    List<PrgState> prgStates = new ArrayList<PrgState>();
    String logFilePath;
    PrgState prgState;

    @Override

    public void setPrgList(List<PrgState> prgStates) {
        this.prgStates=prgStates;
    }

    @Override
    public List<PrgState> getPrgList() {
        return prgStates;
    }

    public Repository(PrgState prgState, String logFilePath) {
        this.logFilePath = logFilePath;
        this.prgState = prgState;
    }

    @Override
    public boolean add(PrgState state) {
        return prgStates.add(state);
    }

    public Repository() {
    }

    public Repository(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    ;

    public Repository(List<PrgState> prgStates, String logFilePath) {
        this.prgStates = prgStates;
        this.logFilePath = logFilePath;
    }

    public Repository(List<PrgState> prgStates) {
        this.prgStates = prgStates;
    }

    public List<PrgState> getPrgStates() {
        return prgStates;
    }

    public void setPrgStates(List<PrgState> prgStates) {
        this.prgStates = prgStates;
    }

    public void logPrgStateExec(PrgState prgState) throws IOException {

        PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        logFile.write(prgState.toString());
        logFile.write("\n");
        logFile.close();
    }


    /*@Override
    public PrgState getCrtPrg() {
        return this.prgState;
    }*/
}
