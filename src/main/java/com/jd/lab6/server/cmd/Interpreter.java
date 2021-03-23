package com.jd.lab6.server.cmd;

import com.jd.lab6.commands.Command;
import com.jd.lab6.commands.HistoryCommand;
import com.jd.lab6.data.SpaceMarine;

import java.util.Stack;
import java.util.TreeSet;

public class Interpreter {
    private final Stack<String> commandHistory;
    private final TreeSet<SpaceMarine> currentCollection;

    public Interpreter(TreeSet<SpaceMarine> col) {
        currentCollection = col;
        commandHistory = new Stack<>();
    }

    public String executeCommand(Command currentCommand) {
        String output;
        currentCommand.setTarget(currentCollection);
        if (currentCommand instanceof HistoryCommand)
            output = ((HistoryCommand) currentCommand).execute(commandHistory);
        else
            output = currentCommand.execute();
        commandHistory.push(currentCommand.toString());
        return output;
    }
}
