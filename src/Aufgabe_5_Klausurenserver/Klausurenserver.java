package Aufgabe_5_Klausurenserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Klausurenserver extends Thread {
    private boolean isRunning = true;
    private final Teilnahmedaten teilnahmedaten;
    private final int port;
    private int clientCount = 1;

    private ServerSocket serverSocket;

    public Klausurenserver(int _port, FileManager fm) {
        this.teilnahmedaten = new Teilnahmedaten(fm);
        this.port = _port;
    }

    public void run() {
        try {
            System.out.println("starting server");
            serverSocket = new ServerSocket(port);
            while(isRunning) {
                Socket so = serverSocket.accept();
                new ServerThread(so, teilnahmedaten, this, clientCount++).start();
            }
        } catch (IOException e) {
            System.out.println("stopping server");
        }
    }

    public void close() {
        isRunning = false;
        try {
            serverSocket.close();
        } catch (IOException ignored) {}
        this.interrupt();
    }
}
