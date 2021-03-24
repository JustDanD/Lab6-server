package com.jd.lab6.server.net;

import com.jd.lab6.data.SpaceMarine;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Listener {
    private static Logger logger;
    public static void listenConnections(int port, TreeSet<SpaceMarine> target, Logger log) {
        logger = log;
        SocketAddress address = new InetSocketAddress(port);
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(address);
            while (true) {
                SocketChannel client = serverSocketChannel.accept();
                if (client != null) {
                    logger.log(Level.INFO,"Подлючение клиента");
                    new Reader(client, target, logger).start();
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
