package com.jd.lab6.server.cmd;

import com.jd.lab6.commands.*;
import com.jd.lab6.data.SpaceMarine;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;
import java.util.TreeSet;

public class Cmd extends Thread {
    private final HashMap<String, Class<? extends Command>> commandsMap;
    private final Stack<String> commandHistory;
    private final TreeSet<SpaceMarine> curCollection;
    private final LocalDateTime startDate;

    /**
     * Констурктор
     *
     * @param col    - текущая коллекция
     */
    public Cmd(TreeSet<SpaceMarine> col) {

        System.out.println("Доброго времени суток, уважаемый юзер.\nДобро пожаловать в систему управления вашей коллекцией космических корбалей!\nПриятного пользования!\nДля просмотра существующих команд введите help.\nВнимание! Это серверная версия приложения!!!!!");
        startDate = LocalDateTime.now();
        commandHistory = new Stack<>();
        commandsMap = new HashMap<>();
        commandsMap.put("exit", ExitCommand.class);
        commandsMap.put("help", HelpCommand.class);
        commandsMap.put("info", InfoCommand.class);
        commandsMap.put("show", ShowCommand.class);
        commandsMap.put("add", AddCommand.class);
        commandsMap.put("update", UpdateCommand.class);
        commandsMap.put("remove_by_id", RemoveCommand.class);
        commandsMap.put("clear", ClearCommand.class);
        commandsMap.put("save", SaveCommand.class);
        commandsMap.put("add_if_min", AddMinCommand.class);
        commandsMap.put("remove_lower", RemoveLowerCommand.class);
        commandsMap.put("history", HistoryCommand.class);
        commandsMap.put("print_descending", PrintDescendingCommand.class);
        commandsMap.put("remove_any_by_chapter", RemoveByChapterCommand.class);
        commandsMap.put("group_counting_by_heart_count", GroupCommand.class);
        commandsMap.put("execute_script", ExecuteScriptCommand.class);
        curCollection = col;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    /**
     * Метод, прослушивающий и обрабатывающий пользовательский ввод
     */
    private void listen() {
        Scanner in = new Scanner(System.in);
        String curCom;
        String[] curArgs;
        Class[] params = {String[].class, TreeSet.class, boolean.class};
        while (true) {
            try {
                curCom = in.next();
                curArgs = in.nextLine().replaceAll(" +", " ").split(" ");

                Class<? extends Command> command = (commandsMap.get(curCom));
                if (command != null) {
                    Command executedCom = command.getConstructor(params).newInstance(curArgs, curCollection, true);
                    if (executedCom instanceof ExitCommand) {
                        new SaveCommand(null, curCollection, true).execute();
                        System.out.println("Спасибо за визит!");
                        System.exit(0);
                    }
                    if (executedCom instanceof HistoryCommand)
                        System.out.println(((HistoryCommand)executedCom).execute(commandHistory));
                    else
                        System.out.println(executedCom.execute());
                    commandHistory.push(curCom);
                } else
                    System.out.println("Такой команды не существует");
                if (!in.hasNext()) {
                    System.out.println("Выполнение скрипта завершено");
                    return;
                }
            } catch (Exception e) {
                if (!in.hasNext()) {
                    System.out.println("Входной поток умер. Возможно, вы нажали ctrl + D. Аварийное(не очень) закрытие.");
                    System.exit(-1);
                }
                System.out.println(e.getMessage());
            }
        }
    }

    public Stack<String> getCommandHistory() {
        return commandHistory;
    }

    public void run() {
        listen();
    }
}

