package com.jd.lab6.server.cmd;


import com.jd.lab6.commands.Command;
import com.jd.lab6.data.SpaceMarine;
import com.jd.lab6.server.CSV.CsvIO;

import java.util.TreeSet;

/**
 * @author Пименов Данила P3130
 * Команда сохранения коллекции в файл
 */
public class SaveCommand extends Command {
    public SaveCommand(String[] args, TreeSet<SpaceMarine> trg, boolean isInteractive) {
        super(args, trg, isInteractive);
    }

    @Override
    public String execute() {
        CsvIO.writeTo("src/test/testfiles/out.csv", target);
        return "";
    }
}
