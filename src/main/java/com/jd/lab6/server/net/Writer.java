package com.jd.lab6.server.net;

import com.jd.lab6.commands.Command;
import com.jd.lab6.data.SpaceMarine;
import com.jd.lab6.server.cmd.CMD;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.TreeSet;

public class Writer {
    private SocketChannel client;

    public Writer(SocketChannel client) {
        this.client = client;
    }

    public void sendResponse(String response) {
        try {
            ByteArrayOutputStream responseBytes = new ByteArrayOutputStream();
            ObjectOutputStream ois = new ObjectOutputStream(responseBytes);
            ois.writeObject(response);
            client.write(ByteBuffer.wrap(responseBytes.toByteArray()));
        } catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
