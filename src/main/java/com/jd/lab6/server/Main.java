package com.jd.lab6.server;

import com.jd.lab6.commands.*;
import com.jd.lab6.data.SpaceMarine;
import com.jd.lab6.server.CSV.CsvIO;
import com.jd.lab6.server.net.Listener;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        TreeSet<SpaceMarine> collection = CsvIO.readFrom("FILENAME");
        Listener.listenConnections(2222, collection);
    }
}
