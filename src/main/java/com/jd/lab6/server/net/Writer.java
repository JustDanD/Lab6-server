package com.jd.lab6.server.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Writer {
    private final SocketChannel client;
    private Logger logger;
    public Writer(SocketChannel client, Logger log) {
        logger = log;
        this.client = client;
    }

    public void sendResponse(String response) {
        try {
            ByteArrayOutputStream responseBytes = new ByteArrayOutputStream();
            ObjectOutputStream ois = new ObjectOutputStream(responseBytes);
            ois.writeObject(response);
            client.write(ByteBuffer.wrap(responseBytes.toByteArray()));
            logger.log(Level.INFO,"Ответ успешно отправлен");
        } catch (IOException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }
}
