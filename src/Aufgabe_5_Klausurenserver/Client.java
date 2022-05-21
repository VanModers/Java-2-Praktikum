package Aufgabe_5_Klausurenserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class Client {

	public static void main(String[] args) {
		Socket so;
		String host =  args[0];

		int port = Integer.parseInt(args[1]);

		try {
			BufferedReader ein = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("> Eingabe");
			String eingabe;
			while ((eingabe = ein.readLine()) != null && !eingabe.equals("quit")) {
				try {
					so = new Socket(host, port);
					BufferedReader einSo = new BufferedReader(new InputStreamReader(so.getInputStream()));
					PrintWriter ausSo = new PrintWriter(so.getOutputStream(), true);

					
					ausSo.println(eingabe);
					System.out.println("> Antwort des Server: ");
					System.out.println(einSo.readLine());
					System.out.println("> Eingabe: ");
					//Schlieﬂen vom Socket und vom READER WRITER
					einSo.close();
					ausSo.close();
					so.close();
				} catch (IOException e) {
					System.out.println("Fehler");
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} // Einlesen der Antwort

	}

}
