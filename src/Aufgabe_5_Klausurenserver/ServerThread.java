package Aufgabe_5_Klausurenserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class ServerThread extends Thread {
    private final Socket clientSocket;
    private final Teilnahmedaten teilnahmedaten;
    private final Klausurenserver server;
    private final int clientID;


    public ServerThread(Socket _clientSocket, Teilnahmedaten _teilnahmedaten, Klausurenserver _server, int _clientID) {
        this.clientSocket = _clientSocket;
        this.teilnahmedaten = _teilnahmedaten;
        this.server = _server;
        this.clientID = _clientID;
    }

    private Set<Integer> getData(String data) {
        String[] ids = data.split(",");
        Set<Integer> result = new TreeSet<>();
        for(String id : ids) {
            if(id.contains("\\s+"))
                id = id.split("\\s+")[1];
            result.add(Integer.parseInt(id));
        }
        return result;
    }

    private void printValue(Set<Integer> values, PrintWriter socket_out) {
        Iterator<Integer> it = values.iterator();
        while(it.hasNext()) {
            socket_out.print(it.next() + (it.hasNext() ? "," : ""));
        }
    }

    public void run() {
        try {
            System.out.println("new client " + clientID + " connected");
            BufferedReader socket_in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String line = socket_in.readLine();
            String[] commands = line.split("\\s+");

            PrintWriter socket_out = new PrintWriter(clientSocket.getOutputStream(), true);

            if(commands[0].equalsIgnoreCase("PUT") && commands.length >= 3) {
                StringBuilder arguments = new StringBuilder();
                for(int i = 2; i < commands.length; i++) {
                    arguments.append(commands[i]);
                }
                Set<Integer> oldvalue = teilnahmedaten.put(commands[1], getData(arguments.toString()));
                socket_out.print("1");
                if(oldvalue != null) {
                    socket_out.print(" ");
                    printValue(oldvalue, socket_out);
                }
                socket_out.println();
            }
            else if(commands[0].equalsIgnoreCase("GET") && commands.length == 2) {
                Set<Integer> value = teilnahmedaten.get(commands[1]);
                if(value != null) {
                    socket_out.print("1 ");
                    printValue(value, socket_out);
                }
                else {
                    socket_out.print("0");
                }
                socket_out.println();
            }
            else if(commands[0].equalsIgnoreCase("DEL") && commands.length == 2) {
                if(!teilnahmedaten.containsKey(commands[1])) {
                    socket_out.print("0");
                }
                else {
                    Set<Integer> oldvalue = teilnahmedaten.del(commands[1]);
                    socket_out.print("1");
                    if (oldvalue != null) {
                        socket_out.print(" ");
                        printValue(oldvalue, socket_out);
                    }
                }
                socket_out.println();
            }
            else if(commands[0].equalsIgnoreCase("GETALL")) {
                Set<Set<Integer>> allValues = teilnahmedaten.getAll();
                if(allValues != null) {
                    socket_out.print("1 ");
                    Iterator<Set<Integer>> it = allValues.iterator();
                    while(it.hasNext()) {
                        socket_out.print("[");
                        printValue(it.next(), socket_out);
                        socket_out.print("]");
                        socket_out.print(it.hasNext() ? "," : "");
                    }
                }
                else {
                    socket_out.print("0");
                }
                socket_out.println();
            }
            else if(commands[0].equalsIgnoreCase("STOP")) {
                socket_out.println("1");
                server.close();
            }

            socket_out.flush();
            clientSocket.close();

            System.out.println("client " + clientID + " disconnected");
        }
        catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }
    }
}
