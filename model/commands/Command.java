package model.commands;

import exceptions.ExpressionException;
import exceptions.MyStackException;

import java.io.IOException;

public abstract class Command {
    private String key, description;

    public Command(String key, String description) {
        this.key = key;
        this.description = description;
    }

    public abstract void execute() throws ExpressionException, MyStackException,IOException,InterruptedException;

    public String getKey() {
        return key;
    }

    public String getDescription() {
        return description;
    }
}
