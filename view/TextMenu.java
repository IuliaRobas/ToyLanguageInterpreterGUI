package view;

import exceptions.ExpressionException;
import exceptions.MyStackException;
import model.PrgState;
import model.commands.Command;
import model.statements.IStmt;
import model.structures.MyIDictionary;
import model.structures.MyIList;
import model.structures.MyIStack;
import mycontroller.MyController;
import repository.IRepository;

import java.io.IOException;
import java.util.*;

public class TextMenu {

    private Map<String, Command> commands;


    List<IStmt> programs = new ArrayList<IStmt>();
    IStmt stmt;

    IRepository repository;
    MyController controller;
    MyIStack<IStmt> stk;
    MyIDictionary<String, Integer> symtbl;
    MyIList<Integer> out;
    PrgState crtPrgState;


    public TextMenu() {

       commands = new HashMap<>();

    }

    public void addCommand(Command c) {
        this.commands.put(c.getKey(), c);
    }

/*
    public void reinitializeStructures() {
        repository = new Repository() {
        };
        controller = new MyController(repository);
        stk = new MyStack<IStmt>();
        symtbl = new MyDictionary<String, Integer>();
        out = new MyList<Integer>();
        crtPrgState = new PrgState(stk, symtbl, out);
        repository.add(crtPrgState);

    }

*/
    public void printMenu() {

        System.out.println("\n ********FUNCTIONALITIES********");

        for (Command com : commands.values()) {
            String line = String.format("Command %s: %s", com.getKey(), com.getDescription());
            System.out.println(line);

        }
    }

    public void show() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMenu();
            System.out.print("\nInput the option: ");
            String key = scanner.nextLine();
            Command com = commands.get(key);
            if (com == null) {
                System.out.println("Invalid option");
                continue;
            }
            try {
                com.execute();
            } catch (MyStackException e) {
                System.out.println(e.getMessage());
            } catch (ExpressionException ee) {
                System.out.println(ee.getMessage());
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
            catch(InterruptedException ie){
                System.out.println(ie.getMessage());
            }
        }
    }


}
