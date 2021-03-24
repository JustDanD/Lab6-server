package com.jd.lab6.server;

import com.jd.lab6.data.SpaceMarine;
import com.jd.lab6.server.CSV.CsvIO;
import com.jd.lab6.server.cmd.Cmd;
import com.jd.lab6.server.net.Listener;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {
    private static Logger logger;
    public static void main(String[] args) {
        try(FileInputStream ins = new FileInputStream("src/log.config")){
            LogManager.getLogManager().readConfiguration(ins);
            logger = Logger.getLogger(Main.class.getName());
        }catch (Exception e){
            System.out.println("Ошибка создания лог-файла");
        }
        logger.log(Level.INFO, "Начало работы");
        TreeSet<SpaceMarine> collection = CsvIO.readFrom("FILENAME");
        Cmd cmd = new Cmd(collection);
        cmd.start();
       try {
           Listener.listenConnections(Integer.parseInt(args[0]), collection, logger);
       } catch (NumberFormatException e) {
           System.out.println("Порт чутка не инт в аргументах");
       }
    }
}
