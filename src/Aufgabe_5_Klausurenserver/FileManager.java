package Aufgabe_5_Klausurenserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FileManager {

	private final String path;

	public FileManager(String _path) {
		this.path = _path;
	}

	public Map<String, Set<Integer>> loadFile() {
		Map<String, Set<Integer>> result = new HashMap<>();
		if (new File(path).exists()) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(path));
				String line = br.readLine();

				while (line != null) {
					String[] stringarray = line.split(":");
					String email = stringarray[0];
					String[] klausuren = stringarray[1].split(",");
					Set<Integer> klausurenids = new HashSet<>();

					for (String id : klausuren) {
						klausurenids.add(Integer.parseInt(id));
					}
					result.put(email, klausurenids);
					line = br.readLine();
				}
				br.close();
			} catch (IOException e) {
				System.out.println("Fehler");
			}
		}
		return result;

	}

	public void saveFile(Map<String, Set<Integer>> daten) {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(path));

			for (Map.Entry<String, Set<Integer>> entry : daten.entrySet()) {

				pw.print(entry.getKey() + ":");
				Iterator<Integer> it = entry.getValue().iterator();
				while (it.hasNext()) {
					pw.print(it.next());
					if (it.hasNext())
						pw.print(",");
				}

				pw.println();
			}
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
