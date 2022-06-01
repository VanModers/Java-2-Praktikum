package Aufgabe_5_Klausurenserver;

public class Main {
    public static void main(String[] args) {
        Klausurenserver server = new Klausurenserver(Integer.parseInt(args[0]),new FileManager("Klausuren.txt"));
        server.start();
    }
}
