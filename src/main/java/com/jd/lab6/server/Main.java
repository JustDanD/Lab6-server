package com.jd.lab6.server;

import com.jd.lab6.data.SpaceMarine;
import com.jd.lab6.server.CSV.CsvIO;
import com.jd.lab6.server.cmd.Cmd;
import com.jd.lab6.server.net.Listener;

import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {
        TreeSet<SpaceMarine> collection = CsvIO.readFrom("FILENAME");
        Cmd cmd = new Cmd(collection, true, null);
        cmd.start();
        Listener.listenConnections(2222, collection);
    }
}
