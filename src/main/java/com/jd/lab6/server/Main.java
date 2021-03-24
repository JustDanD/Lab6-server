package com.jd.lab6.server;

import com.jd.lab6.data.SpaceMarine;
import com.jd.lab6.server.CSV.CsvIO;
import com.jd.lab6.server.cmd.Cmd;
import com.jd.lab6.server.net.Listener;

import java.util.Scanner;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {
        //System.out.println("Введите порт для прослушивания");
        TreeSet<SpaceMarine> collection = CsvIO.readFrom("FILENAME");
        Cmd cmd = new Cmd(collection);
        cmd.start();
       try {
           Listener.listenConnections(Integer.parseInt(args[0]), collection);
       } catch (NumberFormatException e) {
           System.out.println("Порт чутка не инт в аргументах");
       }
    }
}
