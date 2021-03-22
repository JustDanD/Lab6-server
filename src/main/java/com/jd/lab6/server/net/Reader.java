package com.jd.lab6.server.net;

import com.jd.lab6.commands.Command;
import com.jd.lab6.data.SpaceMarine;
import com.jd.lab6.server.cmd.CMD;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.TreeSet;

public class Reader {
    private SocketChannel client;
    private CMD cmd;
    private Writer clientResponse;
    public Reader(SocketChannel client, TreeSet<SpaceMarine> target) {
        this.client = client;
        cmd = new CMD(target);
        clientResponse = new Writer(client);
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
            clientResponse.sendResponse(cmd.executeCommand(inputCommand));
            return true;
        } catch (IOException e) {
            System.out.println("Клиент отключился или отправил битый запрос");
            return false;
        }
        catch (ClassNotFoundException e) {
            System.out.println("Такой команды нет");
             return true;
        }
    }
}
