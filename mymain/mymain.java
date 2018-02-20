package mymain;


import model.booleanExpressions.EqualExp;
import mycontroller.MyController;
import model.*;
import model.booleanExpressions.LessExp;
import model.commands.ExitCommand;
import model.commands.RunCommand;
import model.expressions.ArithExp;
import model.expressions.ConstExp;
import model.expressions.VarExp;
import model.expressions.rH;
import model.statements.*;
import model.structures.*;
import repository.IRepository;
import repository.Repository;
import view.TextMenu;

import java.io.BufferedReader;


public class mymain {
    public  void main() {

        /*
        *   v=2;
        *   Print(v)
        */
        IStmt stmt1 = new CompStmt(new AssignStmt("v", new ConstExp(2)), new PrintStmt(new VarExp("v")));

       /*
       *   a = 2 + 3 * 5;
       *   b = a + 1;
       *   print (b)
       *
       * */
        IStmt stmt2 = new CompStmt(new AssignStmt("a", new ArithExp('+', new ConstExp(2), new ArithExp('*', new ConstExp(3), new ConstExp(5)))), new CompStmt(new AssignStmt("b", new ArithExp('+', new VarExp("a"), new ConstExp(1))), new PrintStmt(new VarExp("b"))));

       /*
       * a = 2 - 2;
       * If a then v = 2 else v = 3;
       *   print (v)
       * */
        IStmt stmt3 = new CompStmt(new AssignStmt("a", new ArithExp('-', new ConstExp(2), new ConstExp(2))), new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ConstExp(2)), new AssignStmt("v", new ConstExp(3))), new PrintStmt(new VarExp("v"))));


        /*
        *   openRFile (var_f, "test.in");
        *   readFile (var_f, var_c); print (var_c);
        *   If var_c then readFile (var_f, var_c); print (var_c) else print (0);
        *   closeRFile (var_f)
        *
        * */
        IStmt stmt4 = new CompStmt(
                new openRFile("var_f", "C:/Users/Utilizator/Desktop/University/Year 2/MAP/ToyLanguageInterpretor/test.txt"),
                new CompStmt(new readFile(new VarExp("var_f"), "var_c"),

                        new CompStmt(
                                new PrintStmt(new VarExp("var_c")),
                                new CompStmt(
                                        new IfStmt(
                                                new VarExp("var_c"),
                                                new CompStmt(
                                                        new readFile(new VarExp("var_f"), "var_c"),
                                                        new PrintStmt(new VarExp("var_c"))
                                                ),
                                                new PrintStmt(new ConstExp(0))
                                        ),
                                        new closeRFile(new VarExp("var_f")))
                        )
                ));


        /*
        *   openRFile (var_f, "test.txt");
        *   readFile (var_f + 2, var_c); print (var_c);
        *   If var_c then readFile (var_f, var_c); print (var_c) else print (0);
        *   closeRFile (var_f)
        *
        * */
        IStmt stmt5 = new CompStmt(
                new openRFile("var_f", "test.txt"),
                new CompStmt(
                        new readFile(new ArithExp('+', new VarExp("var_f"), new ConstExp(2)), "var_c"),
                        new CompStmt(
                                new PrintStmt(new VarExp("var_c")),
                                new CompStmt(
                                        new IfStmt(new VarExp("var_c"),
                                                new CompStmt(
                                                        new readFile(new VarExp("var_f"), "var_c"),
                                                        new PrintStmt(new VarExp("var_c"))),
                                                new PrintStmt(new ConstExp(0))),
                                        new closeRFile(new VarExp("var_f")))

                        )

                )
        );

        IStmt stmt6 = new CompStmt(new AssignStmt("v", new ConstExp(10)),
                new CompStmt(new newStmt("v", new ConstExp(20)),
                        new CompStmt(
                                new newStmt("a", new ConstExp(22)),
                                new PrintStmt(new VarExp("v")))));

        IStmt stmt7 = new CompStmt(new AssignStmt("v", new ConstExp(10)),
                new CompStmt(new newStmt("v", new ConstExp(20)),
                        new CompStmt(new newStmt("a", new ConstExp(22)),
                                new CompStmt(new PrintStmt(new ArithExp('+', new ConstExp(100), new rH("v"))),
                                        new PrintStmt(new ArithExp('+', new ConstExp(100), new rH("a")))))));

        IStmt stmt8 = new CompStmt(new AssignStmt("v", new ConstExp(10)),
                new CompStmt(new newStmt("v", new ConstExp(20)),
                        new CompStmt(new newStmt("a", new ConstExp(22)),
                                new CompStmt(new wH("a", new ConstExp(30)),
                                        new CompStmt(new PrintStmt(new VarExp("a")),
                                                new PrintStmt(new rH("a")))))));

        //v=10;new(v,20);new(a,22);wH(a,30);print(a);print(rH(a));a=0

        IStmt stmt9 = new CompStmt(new AssignStmt("v", new ConstExp(10)),
                new CompStmt(new newStmt("v", new ConstExp(20)),
                        new CompStmt(new newStmt("a", new ConstExp(22)),
                                new CompStmt(new wH("a", new ConstExp(30)),
                                        new CompStmt(new PrintStmt(new VarExp("a")),
                                                new CompStmt(new PrintStmt(new rH("a")),
                                                        new AssignStmt("a", new ConstExp(0))))))
                ));

        //v=10; Print(v + (2<6)); => evaluates to 11
        IStmt stmt10=new CompStmt(new AssignStmt("v",new ConstExp(10)),new PrintStmt(new ArithExp(
                '+',new VarExp("v"),new LessExp(new ConstExp(2),new ConstExp(6))
        )));


        //(10+2)<6 evaluates to 0
        IStmt stmt11=new PrintStmt(new LessExp(new ArithExp('+',new ConstExp(10),new ConstExp(2)),new ConstExp(6)));

        // v=6; (while (v-4) print(v);v=v-1);print(v)
        IStmt stmt12=new CompStmt(
                                    new CompStmt(
                                            new AssignStmt("v",new ConstExp(6)),
                                            new WhileStmt(
                                                    new ArithExp('-',new VarExp("v"),new ConstExp(4)),
                                                    new CompStmt(
                                                            new PrintStmt(new VarExp("v")),
                                                            new AssignStmt("v",new ArithExp('-',new VarExp("v"),new ConstExp(1)))))),
                                    new PrintStmt(new VarExp("v")));

        IStmt stmt14=new CompStmt(new AssignStmt("v",new ConstExp(10)),
                                    new CompStmt(new newStmt("a",new ConstExp(22)),
                                                    new CompStmt(new forkStmt(new CompStmt(new wH("a",new ConstExp(30)),
                                                                                            new CompStmt(new AssignStmt("v",new ConstExp(32)),
                                                                                                    new CompStmt(new PrintStmt(new VarExp("v")),
                                                                                                                new PrintStmt(new rH("a")))))),
                                                            new CompStmt(new PrintStmt(new VarExp("v")),new PrintStmt(new rH("a"))))));

        IStmt stmt13 = new CompStmt(new AssignStmt("v",new ConstExp(10)),
                new CompStmt(new newStmt("a",new ConstExp(22)),
                        new CompStmt(new forkStmt(new CompStmt(new wH("a",new ConstExp(30)),
                                new CompStmt(new AssignStmt("v",new ConstExp(32)),
                                        new CompStmt(new PrintStmt(new VarExp("v")),
                                                new PrintStmt(new rH("a")))))),
                                new CompStmt(new PrintStmt(new VarExp("v")),
                                        new PrintStmt(new rH("a"))))));


        //v=10; Print(v + (2<6)); => evaluates to 11
        IStmt stmt16 = new CompStmt(new AssignStmt("v", new ConstExp(10)), new PrintStmt(new ArithExp(
                '+', new VarExp("v"), new LessExp(new ConstExp(2), new ConstExp(6))
        )));


        //(10+2)<6 evaluates to 0
        IStmt stmt17 = new PrintStmt(new LessExp(new ArithExp('+', new ConstExp(10), new ConstExp(2)), new ConstExp(6)));

        // v=6; (while (v-4) print(v);v=v-1);print(v)
        IStmt stmt18 = new CompStmt(
                new CompStmt(
                        new AssignStmt("v", new ConstExp(6)),
                        new WhileStmt(
                                new ArithExp('-', new VarExp("v"), new ConstExp(4)),
                                new CompStmt(
                                        new PrintStmt(new VarExp("v")),
                                        new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ConstExp(1)))))),
                new PrintStmt(new VarExp("v")));

        /*
          v=20;
         (for(v=0;v<3;v=v+1) fork(print(v);v=v+1) );
         print(v*10)
         */

        /*IStmt stmt15=new CompStmt(new AssignStmt("v",new ConstExp(20)),
                new ForStmt(new ConstExp(0),
                                new ConstExp(3),
                                new ArithExp('+',new VarExp("v"),new ConstExp(1)),
                        new PrintStmt(new ArithExp('*',new VarExp("v"),new ConstExp(10)))));
                                //new forkStmt(new CompStmt(new PrintStmt(new VarExp("v")),new AssignStmt("v",new ArithExp('+',new VarExp("v"),new ConstExp(1)))))),

                        //new PrintStmt(new VarExp("v"))));
                // new PrintStmt(new ArithExp('*',new VarExp("v"),new ConstExp(10)))

*/
        IStmt stmt19 = new CompStmt(new AssignStmt("v", new ConstExp(0)),
                new CompStmt(new RepeatStmt(new CompStmt(
                        new forkStmt(new CompStmt(new PrintStmt(new VarExp("v")),
                                new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ConstExp(1)))
                        )),
                        new AssignStmt("v", new ArithExp('+', new VarExp("v"), new ConstExp(1)))),
                        new EqualExp(new VarExp("v"), new ConstExp(3))
                ),
                        new PrintStmt(new VarExp("v"))
                ));


        //FOR STMT
        IStmt stmt20 = new CompStmt(new AssignStmt("v", new ConstExp(20)),
                new CompStmt(new ForStmt(new ConstExp(0),
                        new ConstExp(3),
                        new ArithExp('+', new VarExp("v"), new ConstExp(1)),
                        //new PrintStmt(new VarExp("v"))),
                        new forkStmt(new CompStmt(new PrintStmt(new VarExp("v")),
                                new AssignStmt("v", new ArithExp('+', new VarExp("v"), new ConstExp(1)))))),
                        new PrintStmt(new ArithExp('*', new VarExp("v"), new ConstExp(10)))));


        IStmt stmt21 = new CompStmt(new AssignStmt("v", new ConstExp(0)),
                new CompStmt(new WhileStmt(new LessExp(new VarExp("v"), new ConstExp(3)),
                        new CompStmt(new forkStmt(new CompStmt(new PrintStmt(new VarExp("v")),
                                new AssignStmt("v", new ArithExp('+', new VarExp("v"), new ConstExp(1))))),
                                new AssignStmt("v", new ArithExp('+', new VarExp("v"), new ConstExp(1))))),
                        new PrintStmt(new ArithExp('*', new VarExp("v"), new ConstExp(10)))));

        //REPEAT STMT
        IStmt stmt22 = new CompStmt(new AssignStmt("v", new ConstExp(0)),
                new CompStmt(new RepeatStmt(new CompStmt(new forkStmt(new CompStmt(new PrintStmt(new VarExp("v")),
                        new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ConstExp(1))))),
                        new AssignStmt("v", new ArithExp('+', new VarExp("v"), new ConstExp(1)))),
                        new EqualExp(new VarExp("v"), new ConstExp(3))),
                        new PrintStmt(new ArithExp('*', new VarExp("v"), new ConstExp(10)))));

        //SLEEP STMT
        IStmt stmt23 = new CompStmt(new AssignStmt("v", new ConstExp(0)),
                new CompStmt(new WhileStmt(new LessExp(new VarExp("v"), new ConstExp(3)),
                        new CompStmt(new forkStmt(new CompStmt(new PrintStmt(new VarExp("v")),
                                new AssignStmt("v", new ArithExp('+', new VarExp("v"), new ConstExp(1))))),
                                new CompStmt(new AssignStmt("v", new ArithExp('+', new VarExp("v"), new ConstExp(1))),
                                        new SleepStmt(15)))),
                        new PrintStmt(new ArithExp('*', new VarExp("v"), new ConstExp(10)))));

        /*new ForStmt(new ConstExp(0),
                new ConstExp(3),
                new ArithExp('+',new VarExp("v"),new ConstExp(1)),
                new forkStmt(new PrintStmt(new VarExp("v"))));*/

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "Exit"));
        /*menu.addCommand(new RunCommand("1", stmt1.toString(), initializeController(stmt1, "C:/Users/Utilizator/Desktop/University/Year 2/MAP/ToyLanguageInterpretor/log1.txt")));
        menu.addCommand(new RunCommand("2", stmt2.toString(), initializeController(stmt2, "C:/Users/Utilizator/Desktop/University/Year 2/MAP/ToyLanguageInterpretor/log2.txt")));
        menu.addCommand(new RunCommand("3", stmt3.toString(), initializeController(stmt3, "C:/Users/Utilizator/Desktop/University/Year 2/MAP/ToyLanguageInterpretor/log3.txt")));
        menu.addCommand(new RunCommand("4", stmt4.toString(), initializeController(stmt4, "C:/Users/Utilizator/Desktop/University/Year 2/MAP/ToyLanguageInterpretor/log4.txt")));
        menu.addCommand(new RunCommand("5", stmt5.toString(), initializeController(stmt5, "C:/Users/Utilizator/Desktop/University/Year 2/MAP/ToyLanguageInterpretor/log5.txt")));
        menu.addCommand(new RunCommand("6", stmt6.toString(), initializeController(stmt6, "C:/Users/Utilizator/Desktop/University/Year 2/MAP/ToyLanguageInterpretor/log6.txt")));
        menu.addCommand(new RunCommand("7", stmt7.toString(), initializeController(stmt7, "C:/Users/Utilizator/Desktop/University/Year 2/MAP/ToyLanguageInterpretor/log7.txt")));
        menu.addCommand(new RunCommand("8", stmt8.toString(), initializeController(stmt8, "C:/Users/Utilizator/Desktop/University/Year 2/MAP/ToyLanguageInterpretor/log8.txt")));
        menu.addCommand(new RunCommand("9", stmt9.toString(), initializeController(stmt9, "C:/Users/Utilizator/Desktop/University/Year 2/MAP/ToyLanguageInterpretor/log9.txt")));
        //menu.addCommand(new RunCommand("10", stmt10.toString(), initializeController(stmt10, "C:/Users/Utilizator/Desktop/University/Year 2/MAP/ToyLanguageInterpretor/log10.txt")));
        //menu.addCommand(new RunCommand("11", stmt11.toString(), initializeController(stmt11, "C:/Users/Utilizator/Desktop/University/Year 2/MAP/ToyLanguageInterpretor/log11.txt")));
        */menu.addCommand(new RunCommand("12", stmt12.toString(), initializeController(stmt12, "C:/Users/Utilizator/Desktop/University/Year 2/MAP/ToyLanguageInterpretor/log12.txt")));
        menu.addCommand(new RunCommand("23", stmt23.toString(), initializeController(stmt23, "C:/Users/Utilizator/Desktop/University/Year 2/MAP/lab8final/log23.txt")));




        menu.show();


    }


    public static MyController initializeController(IStmt stmt, String logFilePath) {

        MyIStack<IStmt> stk = new MyStack<IStmt>();
        MyIDictionary<String, Integer> symtbl = new MyDictionary<String, Integer>();
        MyIList<Integer> out = new MyList<Integer>();
        MyIDictionary<Integer, Tuple<String, BufferedReader>> fileTable = new MyDictionary<Integer, Tuple<String, BufferedReader>>();
        MyIHeap<Integer> heap = new MyHeap<>();

        PrgState crtPrgState = new PrgState(stk, symtbl, out, fileTable, stmt, heap);
        IRepository repo = new Repository(logFilePath);
        repo.add(crtPrgState);

        MyController ctrl = new MyController(repo);

        return ctrl;
    }
}
