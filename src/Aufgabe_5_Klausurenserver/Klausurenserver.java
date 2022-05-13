package Aufgabe_5_Klausurenserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Klausurenserver extends Thread {
    private boolean isRunning = true;

    private Teilnahmedaten teilnahmedaten;
    private int port;

    private ServerSocket serverSocket;

    public Klausurenserver(int _port) {
        this.teilnahmedaten = new Teilnahmedaten();
        this.port = _port;
    }

    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            while(isRunning) {
                Socket so = serverSocket.accept();
                new ServerThread(so, teilnahmedaten, this).start();
            }
        } catch (IOException e) {
            System.out.println("Closing server");
        }
    }

    public void close() {
        isRunning = false;
        try {
            serverSocket.close();
        } catch (IOException e) {}
        this.interrupt();
    }
}
