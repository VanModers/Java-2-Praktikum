package Aufgabe_5_Klausurenserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Klausurenserver extends Thread {
    private boolean isRunning = true;

    private Teilnahmedaten teilnahmedaten;
    private int port;

    public Klausurenserver(int _port) {
        this.teilnahmedaten = new Teilnahmedaten();
        this.port = _port;
    }

    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while(isRunning) {
                Socket so = serverSocket.accept();
                new ServerThread(so, teilnahmedaten, this).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        isRunning = false;
        this.interrupt();
    }
}
