package com.jd.lab6.server;

import com.jd.lab6.commands.*;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        SocketAddress a = new InetSocketAddress(2222);
        ByteBuffer f = ByteBuffer.wrap(new byte[1000]);
        byte[] b = new byte[1000];
        try {
            ServerSocketChannel ss = ServerSocketChannel.open();
            ss.bind(a);
            while (true) {
                SocketChannel s = ss.accept();
                s.read(f);
                b = f.array();
                ByteArrayInputStream bytes = new ByteArrayInputStream(b);
                ObjectInputStream ois = new ObjectInputStream(bytes);
                Command curCom = (Command) ois.readObject();
                System.out.println(Arrays.toString(b));
                s.write(ByteBuffer.wrap(curCom.execute().getBytes()));
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
