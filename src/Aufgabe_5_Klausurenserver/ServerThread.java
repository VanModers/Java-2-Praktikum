package Aufgabe_5_Klausurenserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class ServerThread extends Thread {
    Socket clientSocket;
    Teilnahmedaten teilnahmedaten;

    Klausurenserver server;

    public ServerThread(Socket _clientSocket, Teilnahmedaten _teilnahmedaten, Klausurenserver _server) {
        this.clientSocket = _clientSocket;
        this.teilnahmedaten = _teilnahmedaten;
        this.server = _server;
    }

    public Set<Integer> getData(String data) {
        String[] ids = data.split(",");
        Set<Integer> result = new TreeSet<>();
        for(String id : ids) {
            result.add(Integer.parseInt(id));
        }
        return result;
    }

    public void run() {
        try {
            BufferedReader socket_in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter socket_out = new PrintWriter(clientSocket.getOutputStream(), true);
            String line = socket_in.readLine();
            String[] commands = line.split("\\s+");

            if(commands[0].toUpperCase().equals("PUT") && commands.length == 3) {
                Set<Integer> oldvalue = teilnahmedaten.put(commands[1], getData(commands[2]));
                socket_out.print("1 ");
                socket_out.print(oldvalue.toString());
            }
            else if(commands[0].toUpperCase().equals("GET") && commands.length == 2) {
                Set<Integer> values = teilnahmedaten.get(commands[1]);
                socket_out.print("1 ");
                socket_out.print(values.toString());
            }
            else if(commands[0].toUpperCase().equals("DEL") && commands.length == 2) {
                Set<Integer> oldvalues = teilnahmedaten.del(commands[1]);
                socket_out.print("1 ");
                socket_out.print(oldvalues.toString());
            }
            else if(commands[0].toUpperCase().equals("GETALL")) {
                Set<Set<Integer>> allValues = teilnahmedaten.getAll();
                socket_out.print("1");
                for(Set<Integer> values : allValues) {
                    socket_out.print(" " + values.toString());
                }
            }
            else if(commands[0].toUpperCase().equals("STOP")) {
                server.close();
                socket_out.println("1");
            }

            socket_out.flush();
            clientSocket.close();
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
