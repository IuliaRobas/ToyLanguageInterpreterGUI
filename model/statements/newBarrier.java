package model.statements;

import exceptions.ExpressionException;
import model.PrgState;
import model.Tuple;
import model.expressions.Exp;
import model.structures.MyIBarrier;
import model.structures.MyIDictionary;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class newBarrier implements IStmt {


    private String var;
    private Exp exp;

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public Exp getExp() {
        return exp;
    }

    public void setExp(Exp exp) {
        this.exp = exp;
    }

    public newBarrier(String var, Exp exp) {
        this.var = var;
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "newBarrier(" +
                var + ',' +
                exp.toString() +
                ')';
    }

    @Override
    public PrgState execute(PrgState state) throws ExpressionException, IOException {

        MyIDictionary<String, Integer> symTbl = state.getSymTable();
        int number = exp.eval(symTbl, state.getHeap());

        synchronized (state.getBarrierTable()) {
            int firstFree = state.getBarrierTable().getFirstFree();
            List<Integer> list = new ArrayList<>();
            Tuple<List<Integer>, Integer> pair = new Tuple<List<Integer>, Integer>(list, number);

            state.getBarrierTable().add(firstFree, pair);

            if (symTbl.isDefined(var)) {
                symTbl.update(var, state.getBarrierTable().getFirstFree());
            } else {
                symTbl.add(var, state.getBarrierTable().getFirstFree());
            }

            state.getBarrierTable().setFirstFree();
        }

        return null;
    }


}
