package com.jd.lab6.server.net;

import com.jd.lab6.commands.Command;
import com.jd.lab6.data.SpaceMarine;
import com.jd.lab6.server.cmd.Interpreter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Reader extends Thread {
    private final SocketChannel client;
    private final Interpreter interpreter;
    private final Writer clientResponse;
    private  Logger logger;
    public Reader(SocketChannel client, TreeSet<SpaceMarine> target, Logger log) {
        logger = log;
        this.client = client;
        interpreter = new Interpreter(target);
        clientResponse = new Writer(client, logger);
    }

    public void run() {
        readRequests();
    }

    public void readRequests() {
        while (parseRequest()) {
        }
    }

    private boolean parseRequest() {
        ByteBuffer requestBuf = ByteBuffer.wrap(new byte[10000]);
        try {
            logger.log(Level.INFO,"Входящий запрос");
            client.read(requestBuf);
            ByteArrayInputStream requestBytes = new ByteArrayInputStream(requestBuf.array());
            ObjectInputStream ois = new ObjectInputStream(requestBytes);
            Command inputCommand = (Command) ois.readObject();
            clientResponse.sendResponse(interpreter.executeCommand(inputCommand));
            return true;
        } catch (IOException e) {
            logger.log(Level.INFO,"Клиент отключился или отправил битый запрос");
            return false;
        } catch (ClassNotFoundException e) {
            logger.log(Level.INFO,"Такой команды нет");
            return true;
        }
    }
}
