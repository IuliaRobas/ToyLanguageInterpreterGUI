package model.statements;

import exceptions.ExpressionException;
import model.PrgState;
import model.Tuple;
import model.expressions.Exp;
import model.structures.MyIBarrier;
import model.structures.MyIDictionary;
import model.structures.MyIStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class await implements IStmt {

    String var;

    @Override
    public String toString() {
        return "await(" +
                var +
                ')';
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public await(String var) {

        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws ExpressionException, IOException {


        MyIDictionary<String, Integer> symTbl = state.getSymTable();

        int foundIndex=0;
        try {
            foundIndex = symTbl.lookUp(var);
        } catch (ExpressionException ee) {
            System.out.println(ee.getMessage());
        }

        synchronized (state.getBarrierTable()) {

            MyIBarrier<Integer, Tuple<List<Integer>, Integer>> barrierTable = state.getBarrierTable();
            Integer barrierValue = null;
            List<Integer> barrierList = null;
            Tuple<List<Integer>, Integer> pair;

            //lookUp method throws ExpressionException if the key is not defined
            try {
                pair = barrierTable.lookUp(foundIndex);
                barrierList = pair.getItem1();
                barrierValue = pair.getItem2();
            } catch (ExpressionException ee) {
                System.out.println(ee.getMessage());
            }

            int NL = barrierList.size();


            if( barrierValue > barrierList.size()){
                int id=state.getID();
                if(barrierList.contains(id)){
                    state.getExeStack().push(this);
                }
                else{
                    state.getExeStack().push(this);
                    barrierList.add(id);
                    //List<Integer> threadIDList=new ArrayList<Integer>(id);
                    Tuple<List<Integer>,Integer> newPair=new Tuple<List<Integer>, Integer>(barrierList,barrierValue);
                    barrierTable.update(foundIndex,newPair);
                }
            }

        }

        return null;
    }
}
