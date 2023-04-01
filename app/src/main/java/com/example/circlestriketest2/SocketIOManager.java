package com.example.circlestriketest2;


import io.socket.client.IO;
import io.socket.client.Socket;
import java.net.URISyntaxException;

public class SocketIOManager {
    private static SocketIOManager mInstance;
    private static Socket mSocket;

    private SocketIOManager() {
    }

    public static SocketIOManager getInstance() {
        if (mInstance == null) {
            mInstance = new SocketIOManager();
        }
        return mInstance;
    }

    public static void init(String serverUrl) throws URISyntaxException {
        IO.Options options = new IO.Options();
        options.forceNew = true;
        options.reconnection = true;
        options.reconnectionDelay = 1000;
        options.reconnectionAttempts = 10;
        options.timeout = 5000;

        mSocket = IO.socket(serverUrl, options);
    }

    public Socket getSocket() {
        return mSocket;
    }
}
