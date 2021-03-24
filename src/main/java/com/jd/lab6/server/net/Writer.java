package com.jd.lab6.server.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Writer {
    private final SocketChannel client;

    public Writer(SocketChannel client) {
        this.client = client;
    }

    public void sendResponse(String response) {
        try {
            ByteArrayOutputStream responseBytes = new ByteArrayOutputStream();
            ObjectOutputStream ois = new ObjectOutputStream(responseBytes);
            ois.writeObject(response);
            client.write(ByteBuffer.wrap(responseBytes.toByteArray()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
