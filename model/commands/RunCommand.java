package model.commands;


import exceptions.ExpressionException;
import exceptions.MyStackException;
import mycontroller.MyController;

import java.io.IOException;

public class RunCommand extends Command {
    private MyController ctrl;

    public RunCommand(String key, String desc, MyController ctrl) {
        super(key, desc);
        this.ctrl = ctrl;
    }

    @Override
    public void execute() throws ExpressionException, MyStackException, IOException, InterruptedException {
        try {
            ctrl.allSteps();
        } catch (MyStackException e) {
            throw e;
        } catch (ExpressionException ee) {
            throw ee;
        } catch (IOException ioe) {
            throw ioe;
        } catch (InterruptedException ie) {
            throw ie;
        }
    }
}
