package model.expressions;

import exceptions.ExpressionException;
import model.structures.MyIDictionary;
import model.structures.MyIHeap;

public class ArithExp extends Exp {
    Exp e1;
    Exp e2;
    int op;

    /*
    1 - "+"
    2 - "-"
    3 - "*"
    4 - "/"
    */

    public ArithExp(Character op, Exp e1, Exp e2) {

        this.e1 = e1;
        this.e2 = e2;
        switch (op) {
            case '+': {
                this.op = 1;
                break;
            }
            case '-': {
                this.op = 2;
                break;
            }
            case '*': {
                this.op = 3;
                break;
            }
            case '/': {
                this.op = 4;
                break;
            }
        }

    }


    public Exp getE1() {
        return e1;
    }

    public void setE1(Exp e1) {
        this.e1 = e1;
    }

    public Exp getE2() {
        return e2;
    }

    public void setE2(Exp e2) {
        this.e2 = e2;
    }

    public int getOp() {
        return op;
    }

    public void setOp(int op) {
        this.op = op;
    }



    @Override
    public int eval(MyIDictionary<String, Integer> tbl, MyIHeap<Integer> hp) throws ExpressionException {
        switch (op) {
            case 1: {
                return (e1.eval(tbl,hp) + e2.eval(tbl,hp));
            }
            case 2: {
                return (e1.eval(tbl,hp) - e2.eval(tbl,hp));
            }
            case 3: {
                return (e1.eval(tbl,hp) * e2.eval(tbl,hp));
            }
            case 4: {
                int result=e2.eval(tbl,hp);
                if(result==0){
                    throw new ExpressionException("Error. Division by 0.");
                }
                return (e2.eval(tbl,hp) / e2.eval(tbl,hp));
            }
            default:
                return 0;
        }

    }

    @Override
    public String toString() {
        switch (op) {
            case 1: {
                return e1.toString() + "+" + e2.toString();
            }
            case 2: {
                return e1.toString() + "-" + e2.toString();
            }
            case 3: {
                return e1.toString() + "*" + e2.toString();
            }
            case 4: {
                return e1.toString() + "/" + e2.toString();
            }
        }
        return null;
    }
}
