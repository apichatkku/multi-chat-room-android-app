package com.api.tawan.tawanapichatapplication;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

public class SocketSingleton {
    private Socket socket;
    private String token;
    private String id;

    private static SocketSingleton socketSingleton;

    private SocketSingleton() {
        try {
            token = "";
            id = "";
            socket = IO.socket("http://192.168.1.7:7777");
            socket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static SocketSingleton getInstance() {
        if (socketSingleton == null) {
            socketSingleton = new SocketSingleton();
        }
        return socketSingleton;
    }

    public Socket getSocket() {
        socket.off();
        return socket;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
