package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.booleanExpressions.EqualExp;
import model.booleanExpressions.LessExp;
import model.expressions.ArithExp;
import model.expressions.ConstExp;
import model.expressions.VarExp;
import model.expressions.rH;
import model.statements.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class mainWindowController {

    @FXML
    private Button runProgramButton;

    @FXML
    private ListView<String> listView;

    public static IStmt statement;
    private List<IStmt> stmtList = new ArrayList<>();

    @FXML
    public void initialize() {

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

        /*v=10;
        new(v,20);
        new(a,22);
        print(v)
         */
        IStmt stmt6 = new CompStmt(new AssignStmt("v", new ConstExp(10)),
                new CompStmt(new newStmt("v", new ConstExp(20)),
                        new CompStmt(
                                new newStmt("a", new ConstExp(22)),
                                new PrintStmt(new VarExp("v")))));

        /*v=10;
        new(v,20);
        new(a,22);
        print(100+rH(v));
        print(100+rH(a))
         */

        IStmt stmt7 = new CompStmt(new AssignStmt("v", new ConstExp(10)),
                new CompStmt(new newStmt("v", new ConstExp(20)),
                        new CompStmt(new newStmt("a", new ConstExp(22)),
                                new CompStmt(new PrintStmt(new ArithExp('+', new ConstExp(100), new rH("v"))),
                                        new PrintStmt(new ArithExp('+', new ConstExp(100), new rH("a")))))));

       /* v=10;
       new(v,20);
       new(a,22);
       wH(a,30);
       print(a);
       print(rH(a)) */
        IStmt stmt8 = new CompStmt(new AssignStmt("v", new ConstExp(10)),
                new CompStmt(new newStmt("v", new ConstExp(20)),
                        new CompStmt(new newStmt("a", new ConstExp(22)),
                                new CompStmt(new wH("a", new ConstExp(30)),
                                        new CompStmt(new PrintStmt(new VarExp("a")),
                                                new PrintStmt(new rH("a")))))));

        /* v=10;
        new(v,20);
        new(a,22);
        wH(a,30);
        print(a);
        print(rH(a));
        a=0
        */
        IStmt stmt9 = new CompStmt(new AssignStmt("v", new ConstExp(10)),
                new CompStmt(new newStmt("v", new ConstExp(20)),
                        new CompStmt(new newStmt("a", new ConstExp(22)),
                                new CompStmt(new wH("a", new ConstExp(30)),
                                        new CompStmt(new PrintStmt(new VarExp("a")),
                                                new CompStmt(new PrintStmt(new rH("a")),
                                                        new AssignStmt("a", new ConstExp(0))))))
                ));

        //v=10; Print(v + (2<6)); => evaluates to 11
        IStmt stmt10 = new CompStmt(new AssignStmt("v", new ConstExp(10)), new PrintStmt(new ArithExp(
                '+', new VarExp("v"), new LessExp(new ConstExp(2), new ConstExp(6))
        )));


        //(10+2)<6 evaluates to 0
        IStmt stmt11 = new PrintStmt(new LessExp(new ArithExp('+', new ConstExp(10), new ConstExp(2)), new ConstExp(6)));

        // v=6; (wHile (v-4) print(v);v=v-1);print(v)
        IStmt stmt12 = new CompStmt(
                new CompStmt(
                        new AssignStmt("v", new ConstExp(6)),
                        new WhileStmt(
                                new ArithExp('-', new VarExp("v"), new ConstExp(4)),
                                new CompStmt(
                                        new PrintStmt(new VarExp("v")),
                                        new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ConstExp(1)))))),
                new PrintStmt(new VarExp("v")));

        IStmt stmt13 = new CompStmt(new AssignStmt("v", new ConstExp(10)),
                new CompStmt(new newStmt("a", new ConstExp(22)),
                        new CompStmt(new forkStmt(new CompStmt(new wH("a", new ConstExp(30)),
                                new CompStmt(new AssignStmt("v", new ConstExp(32)),
                                        new CompStmt(new PrintStmt(new VarExp("v")),
                                                new PrintStmt(new rH("a")))))),
                                new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new rH("a"))))));
    /*
    v=10;
    new(a,22);
    fork(wH(a,30);
    v=32;
    print(v);
    print(rH(a)));
    print(v);
    print(rH(a))

    At the end:
    Id=1
    SymTable_1={v->10,a->1}
    Id=10
    SymTable_10={v->32,a->1}
    Heap={1->30}
    Out={10,30,32,30}

    */
        IStmt stmt14 = new CompStmt(new AssignStmt("v", new ConstExp(10)),
                new CompStmt(new newStmt("a", new ConstExp(22)),
                        new CompStmt(new forkStmt(new CompStmt(new wH("a", new ConstExp(30)),
                                new CompStmt(new AssignStmt("v", new ConstExp(32)),
                                        new CompStmt(new PrintStmt(new VarExp("v")),
                                                new PrintStmt(new rH("a")))))),
                                new CompStmt(new PrintStmt(new VarExp("v")),
                                        new PrintStmt(new rH("a"))))));


        //v=10; Print(v + (2<6)); => evaluates to 11
        IStmt stmt15 = new CompStmt(new AssignStmt("v", new ConstExp(10)), new PrintStmt(new ArithExp(
                '+', new VarExp("v"), new LessExp(new ConstExp(2), new ConstExp(6))
        )));


        //(10+2)<6 evaluates to 0
        IStmt stmt16 = new PrintStmt(new LessExp(new ArithExp('+', new ConstExp(10), new ConstExp(2)), new ConstExp(6)));

        // v=6; (wHile (v-4) print(v);v=v-1);print(v)
        IStmt stmt17 = new CompStmt(
                new CompStmt(
                        new AssignStmt("v", new ConstExp(6)),
                        new WhileStmt(
                                new ArithExp('-', new VarExp("v"), new ConstExp(4)),
                                new CompStmt(
                                        new PrintStmt(new VarExp("v")),
                                        new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ConstExp(1)))))),
                new PrintStmt(new VarExp("v")));


        /*IStmt stmt19 = new CompStmt(new AssignStmt("v", new ConstExp(0)),
                new CompStmt(new RepeatStmt(new CompStmt(
                        new forkStmt(new CompStmt(new PrintStmt(new VarExp("v")),
                                new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ConstExp(1)))
                        )),
                        new AssignStmt("v", new ArithExp('+', new VarExp("v"), new ConstExp(1)))),
                        new EqualExp(new VarExp("v"), new ConstExp(3))
                ),
                        new PrintStmt(new VarExp("v"))
                ));
*/

        //FOR STMT
        /*v=20;
        (for(v=0;v<3;v=v+1) fork(print(v);v=v+1) );
        print(v*10)
        The final Out should be {0,1,2,30}*/
        IStmt stmt18 = new CompStmt(new AssignStmt("v", new ConstExp(20)),
                new CompStmt(new ForStmt(new ConstExp(0),
                        new ConstExp(3),
                        new ArithExp('+', new VarExp("v"), new ConstExp(1)),
                        //new PrintStmt(new VarExp("v"))),
                        new forkStmt(new CompStmt(new PrintStmt(new VarExp("v")),
                                new AssignStmt("v", new ArithExp('+', new VarExp("v"), new ConstExp(1)))))),
                        new PrintStmt(new ArithExp('*', new VarExp("v"), new ConstExp(10)))));

        /*IStmt stmt21 = new CompStmt(new AssignStmt("v", new ConstExp(0)),
                new CompStmt(new wHileStmt(new LessExp(new VarExp("v"), new ConstExp(3)),
                        new CompStmt(new forkStmt(new CompStmt(new PrintStmt(new VarExp("v")),
                                new AssignStmt("v", new ArithExp('+', new VarExp("v"), new ConstExp(1))))),
                                new AssignStmt("v", new ArithExp('+', new VarExp("v"), new ConstExp(1))))),
                        new PrintStmt(new ArithExp('*', new VarExp("v"), new ConstExp(10)))));
*/

        //REPEAT STMT
        /*
        * v=0; (repeat (fork(print(v);v=v-1);v=v+1) until v==3); x=1;y=2;z=3;w=4; print(v*10)
        * The final Out should be {0,1,2,30}*/
        IStmt stmt19 = new CompStmt(new AssignStmt("v", new ConstExp(0)),
                new CompStmt(new RepeatStmt(new CompStmt(new forkStmt(new CompStmt(new PrintStmt(new VarExp("v")),
                        new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ConstExp(1))))),
                        new AssignStmt("v", new ArithExp('+', new VarExp("v"), new ConstExp(1)))),
                        new EqualExp(new VarExp("v"), new ConstExp(3))),
                        new PrintStmt(new ArithExp('*', new VarExp("v"), new ConstExp(10)))));

        //SLEEP STMT
        /*v=0; (wHile(v<3) (fork(print(v);v=v+1);v=v+1); sleep(15); print(v*10)
        The final Out should be {0,1,2,30}
        */
        IStmt stmt20 = new CompStmt(new AssignStmt("v", new ConstExp(0)),
                new CompStmt(new WhileStmt(new LessExp(new VarExp("v"), new ConstExp(3)),
                        new CompStmt(new forkStmt(new CompStmt(new PrintStmt(new VarExp("v")),
                                new AssignStmt("v", new ArithExp('+', new VarExp("v"), new ConstExp(1))))),
                                new CompStmt(new AssignStmt("v", new ArithExp('+', new VarExp("v"), new ConstExp(1))),
                                        new SleepStmt(15)))),
                        new PrintStmt(new ArithExp('*', new VarExp("v"), new ConstExp(10)))));


        /*
        * new(v1,2);
        * new(v2,3);
        * new(v3,4);
        * newBarrier(cnt,rH(v3));
        * fork(wH(v1,rh(v1)*10));
        * print(rh(v1);await(cnt)));
        * fork(await(cnt);wH(v2,rh(v2)*10));
        * print(rh(v2)));
        * fork(await(cnt);
        * wH(v3,rh(v3)*10));
        * wH(v3,rh(v3)*10));
        * print(rh(v3)));
        * await(cnt); print(2000);*/

        IStmt fork1 = new CompStmt(
                new wH("v1", new ArithExp('*',
                        new rH("v1"),
                        new ConstExp(10))),
                new CompStmt(
                        new PrintStmt(new rH("v1")),
                        new await("cnt")
                )

        );
        IStmt fork2 = new CompStmt(
                new await("cnt"),
                new CompStmt(
                        new wH("v2", new ArithExp('*',
                                new rH("v2"),
                                new ConstExp(10))),
                        new PrintStmt(new rH("v2"))
                )
        );
        IStmt fork3 = new CompStmt(
                new await("cnt"),
                new CompStmt(
                        new wH("v3", new ArithExp('*',
                                new rH("v3"),
                                new ConstExp(10))),
                        new CompStmt(
                                new wH("v3", new ArithExp('*',
                                        new rH("v3"),
                                        new ConstExp(10))),
                                new PrintStmt(new rH("v3"))
                        )
                )
        );
        IStmt stmt21 = new CompStmt(
                new newStmt("v1", new ConstExp(2)),
                new CompStmt(
                        new newStmt("v2", new ConstExp(3)),
                        new CompStmt(
                                new newStmt("v3", new ConstExp(4)),
                                new CompStmt(
                                        new newBarrier("cnt", new rH("v3")),
                                        new CompStmt(
                                                new forkStmt(fork1),
                                                new CompStmt(
                                                        new forkStmt(fork2),
                                                        new CompStmt(
                                                                new forkStmt(fork3),
                                                                new CompStmt(
                                                                        new await("cnt"),
                                                                        new PrintStmt(new ConstExp(2000))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        /*new(v1,20);
        new(v2,30);
        newLock(x);
        fork(
                fork(   lock(x);wh(v1,rh(v1)-1);unlock(x)   );
                lock(x);
                wh(v1,rh(v1)+1);
                unlock(x)
            );
         newLock(q)
        *fork(
        *       fork(lock(q);wh(v2,rh(v2)+5);unlock(q));
        *       m=100;
        *       lock(q);
        *       wh(v2,rh(v2)+1);
        *       unlock(q)
        *   );
        *   z=200;
        *   z=300;
        *   z=400;
        *   lock(x);
        *   print (rh(v1));
        *   unlock(x);
        *   lock(q);
        *   print(rh(v2));
        *   unlock(q)
        *   The final Out should be {20,36} */

        IStmt stmt22 = new CompStmt(new newStmt("v1", new ConstExp(20)),
                new CompStmt(new newStmt("v2", new ConstExp(30)),
                        new CompStmt(new newLock("x"),
                                new CompStmt(new forkStmt(new CompStmt(new forkStmt(new CompStmt(new lock("x"),
                                        new CompStmt(new wH("v1", new ArithExp('-', new rH("v1"), new ConstExp(1))), new unlock("x")))), new CompStmt(new lock("x"), new CompStmt(new wH("v1", new ArithExp('+', new rH("v1"), new ConstExp(1))), new unlock("x"))))), new CompStmt(new newLock("q"), new CompStmt(new forkStmt(new CompStmt(new forkStmt(new CompStmt(new lock("q"), new CompStmt(new wH("v2", new ArithExp('+', new rH("v2"), new ConstExp(5))), new unlock("q")))), new CompStmt(new AssignStmt("m", new ConstExp(100)), new CompStmt(new lock("q"), new CompStmt(new wH("v2", new ArithExp('+', new rH("v2"), new ConstExp(1))), new unlock("q")))))), new CompStmt(new AssignStmt("z", new ConstExp(200)), new CompStmt(new AssignStmt("z", new ConstExp(300)), new CompStmt(new AssignStmt("z", new ConstExp(400)), new CompStmt(new lock("x"), new CompStmt(new PrintStmt(new rH("v1")), new CompStmt(new unlock("x"), new CompStmt(new lock("q"), new CompStmt(new PrintStmt(new rH("v2")), new unlock("q")))))))))))))));

         /*
        a=1;b=2;c=5;
        switch(a*10)
        (case (b*c) print(a);print(b))
        (case (10) print(100);print(200))
        (default print(300));
        print(300)      */

        IStmt assignStmt = new CompStmt(new AssignStmt("a", new ConstExp(1)),
                new CompStmt(new AssignStmt("b", new ConstExp(2)),
                        new AssignStmt("c", new ConstExp(5))));
        IStmt switchStmt = new switchStmt(new ArithExp('*', new VarExp("a"), new ConstExp(10)),
                new ArithExp('*', new VarExp("b"), new VarExp("c")),
                new CompStmt(new PrintStmt(new VarExp("a")), new PrintStmt(new VarExp("b"))),
                new ConstExp(10),
                new CompStmt(new PrintStmt(new ConstExp(100)), new PrintStmt(new ConstExp(200))),
                new PrintStmt(new ConstExp(300)));
        IStmt stmt23 = new CompStmt(assignStmt,
                new CompStmt(switchStmt, new PrintStmt(new ConstExp(300))));


        /*new(v1,2);new(v2,3);new(v3,4);newBarrier(cnt,rH(v2));
        fork(await(cnt);
            wh(v1,rh(v1)*10));
            print(rh(v1)));
        fork(await(cnt);wh(v2,rh(v2)*10));wh(v2,rh(v2)*10));print(rh(v2)));
        await(cnt);
        print(rH(v3))
        The final Out should be {4,20,300}*/

        IStmt newStmts = new CompStmt(new newStmt("v1", new ConstExp(2)),
                new CompStmt(new newStmt("v2", new ConstExp(3)),
                        new newStmt("v3", new ConstExp(4))));

        IStmt barrierStmt=new newBarrier("cnt",new rH("v2"));

        IStmt forkStmts1=new forkStmt(new CompStmt(new await("cnt"),new CompStmt(new wH("v1",new ArithExp('*',new rH("v1"),new ConstExp(10))),
                                                                                                new PrintStmt(new rH("v1")))));

        IStmt forkStmts2=new forkStmt(new CompStmt(new await("cnt"),new CompStmt(new wH("v2",new ArithExp('*',new rH("v2"),new ConstExp(10))),
                                                    new CompStmt(new wH("v2",new ArithExp('*',new rH("v2"),new ConstExp(10))),
                                                                    new PrintStmt(new rH("v2"))))));
        IStmt stmt24=new CompStmt(newStmts,
                                new CompStmt(forkStmts1,new CompStmt(forkStmts2,
                                                                        new CompStmt(new await("cnt"),new PrintStmt(new rH("v3"))))));


        stmtList.add(stmt14);
        stmtList.add(stmt8);
        stmtList.add(stmt10);
        //stmtList.add(stmt18);
        //stmtList.add(stmt19);
        //stmtList.add(stmt20);
        //stmtList.add(stmt21);
        //stmtList.add(stmt22);
        stmtList.add(stmt23);
        stmtList.add(stmt24);

        ObservableList<String> list = FXCollections.observableArrayList();
        for (IStmt i : stmtList)
            list.add("" + i);

        listView.setItems(list);
        listView.getSelectionModel().selectIndices(0);

    }

    @FXML
    public void runProgramButtonAction() throws IOException {
        statement = stmtList.get(listView.getSelectionModel().getSelectedIndex());

        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("runProgram.fxml"));
        stage.setTitle("Evaluation of the program");
        stage.setScene(new Scene(root, 800, 600));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

    }
}
