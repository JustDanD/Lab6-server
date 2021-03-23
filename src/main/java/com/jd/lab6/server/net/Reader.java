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

public class Reader extends Thread {
    private SocketChannel client;
    private Interpreter interpreter;
    private Writer clientResponse;

    public Reader(SocketChannel client, TreeSet<SpaceMarine> target) {
        this.client = client;
        interpreter = new Interpreter(target);
        clientResponse = new Writer(client);
    }

    public void run() {
        System.out.println("Клиент подключился");
        readRequests();
    }

    public void readRequests() {
        while (parseRequest()) {
        }
    }

    private boolean parseRequest() {
        ByteBuffer requestBuf = ByteBuffer.wrap(new byte[10000]);
        try {
            client.read(requestBuf);
            ByteArrayInputStream requestBytes = new ByteArrayInputStream(requestBuf.array());
            ObjectInputStream ois = new ObjectInputStream(requestBytes);
            Command inputCommand = (Command) ois.readObject();
            clientResponse.sendResponse(interpreter.executeCommand(inputCommand));
            return true;
        } catch (IOException e) {
            System.out.println("Клиент отключился или отправил битый запрос");
            return false;
        } catch (ClassNotFoundException e) {
            System.out.println("Такой команды нет");
            return true;
        }
    }
}
