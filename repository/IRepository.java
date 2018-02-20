package repository;


import model.PrgState;

import java.io.IOException;
import java.util.List;

public interface IRepository {
    //PrgState getCrtPrg();
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> prgStates);

    public boolean add(PrgState state);

    //Save the content of the PrgState into a textFile
    //Throws: IOException - if the file exists but is a directory rather than a regular file,
    // does not exist but cannot be created,
    // or cannot be opened for any other reason
    public void logPrgStateExec(PrgState prgState) throws IOException;

    //public void logPrgStateExec() throws IOException;

}
