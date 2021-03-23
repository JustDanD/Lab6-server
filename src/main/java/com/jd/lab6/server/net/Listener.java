package com.jd.lab6.server.net;

import com.jd.lab6.data.SpaceMarine;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.TreeSet;

public class Listener {
    private static SocketAddress address;
    private static ServerSocketChannel serverSocketChannel;

    public static void listenConnections(int port, TreeSet<SpaceMarine> target) {
        address = new InetSocketAddress(port);
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(address);
            while (true) {
                SocketChannel client = serverSocketChannel.accept();
                if (client != null)
                    new Reader(client, target).start();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
