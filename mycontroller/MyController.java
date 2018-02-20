package mycontroller;

import exceptions.ExpressionException;
import exceptions.MyStackException;
import model.PrgState;
import repository.IRepository;
import repository.Repository;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class MyController {


    public MyController(IRepository repository) {
        this.repository = repository;
    }

    ExecutorService executor;
    IRepository repository = new Repository();

    List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) {
        return inPrgList.stream()
                .filter(p -> !p.isCompleted())
                .collect(Collectors.toList());
    }

    void oneStepForAllPrg(List<PrgState> prgList) throws InterruptedException, MyStackException, ExpressionException, IOException {

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

        List<PrgState> newPrgList = executor.invokeAll(callList)
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
                System.out.println(prg);
            } catch (IOException ioe) {
                throw new RuntimeException(ioe);
            }
        });
        repository.setPrgList(prgList);

    }

    public void allSteps() throws InterruptedException, MyStackException, IOException, ExpressionException {

        executor = Executors.newFixedThreadPool(2);
        List<PrgState> prgList = removeCompletedPrg(repository.getPrgList());
        while (prgList.size() > 0) {
            try {
                oneStepForAllPrg(prgList);
            } catch (InterruptedException ie) {
                throw ie;
            } catch (MyStackException e) {
                throw e;
            } catch (IOException ioe) {
                throw ioe;
            } catch (ExpressionException ee) {
                throw ee;
            }

            prgList = removeCompletedPrg(repository.getPrgList());
        }
        executor.shutdownNow();
        repository.setPrgList(prgList);
    }

    public Map<Integer, Integer> conservativeGarbageCollector(Collection<Integer> symTableValues,
                                                              Map<Integer, Integer> heap) {

        return heap.entrySet()
                .stream()
                .filter(e -> symTableValues.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        /* Map<Integer,Integer> result=new HashMap<>();
        for (Map.Entry<Integer,Integer> entry : heap.entrySet()) {
            Integer key = entry.getKey();
            if(symTableValues.contains(key)==true)
               // heap.remove(key);

                result.put(key,entry.getValue());

        }
        return result;*/
    }


}
