import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Modulbeschreibungen {

    private List<Modul> module = new ArrayList<>();
    private Set<Studiengang> studiengaenge = new TreeSet<>();

    // Konstruktor: lädt die Datenbasis von einer Datei, deren Name als String übergeben wird.
    public Modulbeschreibungen(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            List<String> modulLines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.equals("")) {
                    modulLines.add(line);
                } else {
                    module.add(new Modul(modulLines));
                    modulLines = new ArrayList<>();
                }
            }
            if(modulLines.size() > 0 && !modulLines.get(0).equals(""))
                module.add(new Modul(modulLines));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        initialiseStudiengaenge();
    }

    private boolean studieangaengeContains(String studiengang) {
        for(Studiengang st : studiengaenge) {
            if(st.getBezeichnung().equals(studiengang))
                return true;
        }
        return false;
    }

    private void initialiseStudiengaenge() {
        studiengaenge = new TreeSet<>();
        for(Modul modul : module) {
            if(!studieangaengeContains(modul.getStudiengang())) {
                Map<Integer, Integer> sws = getSWS(modul.getStudiengang());
                int totalSWS = 0;
                for(int swsValue : sws.values()) {
                    totalSWS += swsValue;
                }
                Studiengang studiengang = new Studiengang(modul.getStudiengang(), totalSWS);
                studiengang.addModul(modul);
                studiengaenge.add(studiengang);
            }
            else {
                for(Studiengang studiengang : studiengaenge) {
                    if(studiengang.getBezeichnung().equals(modul.getStudiengang())) {
                        studiengang.addModul(modul);
                        break;
                    }
                }
            }
        }
    }

    // Liefert alle Zertifikate (wie z.B. "Zertifikat IT-Sicherheit") eines Studiengangs. Beachten Sie, dass ein Modul zu mehreren Zertifikaten gehören kann (siehe "Elektrische Antriebe").
    public Set<String> getZertifikate(String studiengang) {
        Set<String> result = new HashSet<>();
        for (Modul modul : module) {
            if (modul.getStudiengang().equals(studiengang)) {
                StringBuilder zertifikat = new StringBuilder();
                boolean recording = false;
                String[] art = modul.getArt().split("\\s+");
                for (int i = 0; i < art.length; i++) {
                    if (!recording && art[i].equals("Zertifikat") && i+1 < art.length) {
                        recording = true;
                        zertifikat.append(art[++i]);
                    }
                    else if (recording) {
                        if(art[i].equals("und") && i+2 < art.length && art[i+1].equals("Zertifikat")) {
                            result.add(zertifikat.toString());
                            zertifikat = new StringBuilder();
                            i += 2;
                            zertifikat.append(art[i]);
                        }
                        else {
                            zertifikat.append(" ").append(art[i]);
                        }
                    }
                }
                if(recording)
                    result.add(zertifikat.toString());
            }

        }
        return result;
    }

    // Liefert die Bezeichnungen aller verzahnten Module, d.h. Module, die in mehreren Studiengängen verwendet werden. Verzahnte Module sind daran zu erkennen, dass diese die gleiche Modulbezeichnung, die/den gleichen Modulverantwortliche(n) und bis zum Bindestrich das gleiche Kürzel besitzen.
    public Set<String> getVerzahnteModule() {
        Set<String> result = new HashSet<>();
        for (Modul modul1 : module) {
            for (Modul modul2 : module) {
                if(modul1.verzahnt(modul2))
                    result.add(modul1.getModulbezeichnung());
            }
        }
        return result;
    }

    // Liefert die Anzahl der Module eines Studiengangs. Wenn pflicht true ist, werden nur die Pflichtmodule (siehe Art) gezählt, bei false nur die Wahlpflichtmodule, bei null alle Module.
    public int getAnzahlModule(String studiengang, Boolean pflicht) {
        int result = 0;
        for(Modul modul : module) {
            if(modul.getStudiengang().equals(studiengang)) {
                if(pflicht == null)
                    result++;
                else if (pflicht && getModulArt(modul.getArt()).equals("Pflichtmodul"))
                    result++;
                else if(!pflicht && getModulArt(modul.getArt()).equals("Wahlpflichtmodul"))
                    result++;
            }
        }
        return result;
    }

    public static String getModulArt(String art) {
        return art.split("\\s+")[0];
    }

    // Liefert ähnlich zur vorherigen Methode die Anzahl der Veranstaltungen, die zu Pflicht- und/oder Wahlpflichtmodulen gehören.
    public int getAnzahlVeranstaltungen(String studiengang, Boolean pflicht) {
        int result = 0;
        for(Modul modul : module) {
            if(modul.getStudiengang().equals(studiengang)) {
                if(pflicht == null)
                    result+= modul.getVeranstaltungen().size();
                else if (pflicht && getModulArt(modul.getArt()).equals("Pflichtmodul"))
                    result+= modul.getVeranstaltungen().size();
                else if(!pflicht && getModulArt(modul.getArt()).equals("Wahlpflichtmodul"))
                    result+= modul.getVeranstaltungen().size();
            }
        }
        return result;
    }

    // Liefert die Summe der ECTS-Punkte der Pflichtmodule pro Studiengang pro Semester. Der Eintrag "(1, 30)" in der Map würde bedeuten, dass die Summe der Pflichtmodule im 1. Semester 30 ECTS beträgt. Die Summe wird gerundet und es erfolgt nur ein Eintrag in der Map, wenn die Summe größer als 0 ist.
    public Map<Integer, Integer> getECTS(String studiengang) {
        Map<Integer, Double> ectsPointsPerSemester = new HashMap<>();
        for(Modul modul : module) {
            if(modul.getStudiengang().equals(studiengang) && modul.getSemester() > 0) {
                if (!ectsPointsPerSemester.containsKey(modul.getSemester())) {
                    if(modul.getEctsPunkte() > 0)
                        ectsPointsPerSemester.put(modul.getSemester(), modul.getEctsPunkte());
                }
                else {
                    ectsPointsPerSemester.put(modul.getSemester(), ectsPointsPerSemester.get(modul.getSemester()) + modul.getEctsPunkte());
                }
            }
        }

        Map<Integer, Integer> result = new HashMap<>();
        for(Map.Entry<Integer, Double> entry : ectsPointsPerSemester.entrySet()) {
            if((int)Math.round(entry.getValue()) > 0)
                result.put(entry.getKey(), (int)Math.round(entry.getValue()));
        }

        return result;
    }

    // Liefert die Summe der SWS der Pflichtmodule pro Studiengang pro Semester. Der Eintrag "(1, 25)" in der Map würde bedeuten, dass die Summe der SWS zu Veranstaltungen von Pflichtmodulen im 1. Semester 25 SWS beträgt. Es erfolgt nur ein Eintrag in der Map, wenn die Summe größer als 0 ist.
    public Map<Integer, Integer> getSWS(String studiengang) {
        Map<Integer, Integer> result = new HashMap<>();
        for(Modul modul : module) {
            if(modul.getStudiengang().equals(studiengang) && modul.getSemester() > 0) {
                int totalSWS = 0;
                for(Veranstaltung veranstaltung : modul.getVeranstaltungen()) {
                    totalSWS += veranstaltung.getSws();
                }

                if (!result.containsKey(modul.getSemester())) {
                    if(totalSWS > 0)
                        result.put(modul.getSemester(), totalSWS);
                }
                else {
                    result.put(modul.getSemester(), result.get(modul.getSemester()) + totalSWS);
                }
            }
        }

        return result;
    }

    // Liefert die Studiengänge (z.B. BI) aufsteigend nach der Summe der SWS der enthaltenen Veranstaltungen zurück. Falls die Summe bei Studiengängen gleich ist, sollen diese alphabetisch sortiert werden.
    public List<String> getSortierteStudiengaenge() {
        List<String> result = new ArrayList<>();
        for(Studiengang studiengang : studiengaenge) {
            result.add(studiengang.getBezeichnung());
        }

        return result;
    }

    // Liefert die Modulbeschreibungen eines Studiengangs im JSON-Format.
    public String getJSON(String studiengang) {
        Studiengang currentStudiengang = null;
        for (Studiengang st : studiengaenge) {
            if(st.getBezeichnung().equals(studiengang)) {
                currentStudiengang = st;
                break;
            }
        }
        StringBuilder str = new StringBuilder();
        str.append("[");
        Iterator<Modul> it = currentStudiengang.getModule().iterator();
        while(it.hasNext()) {
            str.append(it.next().getJSON());
            if(it.hasNext())
                str.append(", ");
        }
        str.append("]");
        return str.toString();
    }
}
