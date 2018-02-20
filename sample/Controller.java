package sample;

import exceptions.ExpressionException;
import exceptions.MyStackException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.PrgState;
import repository.IRepository;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {

    private IRepository repository;
    private ExecutorService executor;

    public Controller() {
    }


    public Controller(IRepository repo) {
        this.repository = repo;
    }

    public int noPrgStates() {
        return repository.getPrgList().size();
    }

    public ObservableList<String> getPrgStatesID() {
        ObservableList<String> list = FXCollections.observableArrayList();
        for (PrgState i : repository.getPrgList())
            list.add(String.valueOf(i.getID()));

        return list;
    }

    public PrgState getPrgStateByIndex(int index) {
        return repository.getPrgList().get(index);
    }

    List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) {
        return inPrgList.stream()
                .filter(p -> !p.isCompleted())
                .collect(Collectors.toList());
    }

    public void oneStepForAllPrg(List<PrgState> prgList) throws InterruptedException, MyStackException, ExpressionException, IOException {

        /*            try {
                        repository.logPrgStateExec(prg);
                    } catch (IOException ioe) {
                        throw new RuntimeException(ioe);
                    }
                }
        );*/

        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>) (() -> {
                    return p.oneStep();
                }))
                .collect(Collectors.toList());


        List<PrgState> newPrgList = null;
        newPrgList=executor.invokeAll(callList)
                .stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        return null;
                    }
                })
                .filter(p -> p != null)
                .collect(Collectors.toList());


        prgList.addAll(newPrgList);
        prgList.forEach(prg -> {
            try {
                repository.logPrgStateExec(prg);
                //System.out.println(prg);
            } catch (IOException ioe) {
                throw new RuntimeException(ioe);
            }
        });
        repository.setPrgList(prgList);

    }

    public boolean oneStepGUI() throws InterruptedException, MyStackException, IOException, ExpressionException {

        executor = Executors.newFixedThreadPool(2);
        List<PrgState> prgList = removeCompletedPrg(repository.getPrgList());

        if (prgList.size() > 0) {
            try {
                oneStepForAllPrg(prgList);
                prgList = removeCompletedPrg(repository.getPrgList());
                return true;
            } catch (InterruptedException ie) {
                throw ie;
            } catch (MyStackException e) {
                throw e;
            } catch (IOException ioe) {
                throw ioe;
            } catch (ExpressionException ee) {
                throw ee;
            }



        } else {
            executor.shutdownNow();
            repository.setPrgList(prgList);
            return false;
        }


    }
}
