package Aufgabe_4_Modulbeschreibungen;

public class Veranstaltung {
    private String titel;
    private String dozent;
    private int sws;

    public Veranstaltung(String titel, String dozent, int sws) {
        this.titel = titel;
        this.dozent = dozent;
        this.sws = sws;
    }

    public Veranstaltung(String data) {
        parseModulInfo(data);
    }

    private void parseModulInfo(String data) {
        String[] attributes = data.split("\\|");
        this.titel = attributes[0];
        this.dozent = attributes[1];
        this.sws = Integer.parseInt(attributes[2]);
    }

    public String getTitel() {
        return titel;
    }

    public String getDozent() {
        return dozent;
    }

    public int getSws() {
        return sws;
    }

    public String getJSON() {
        String str = "{\n" +
                "        \"titel\": " + "\"" + titel + "\",\n" +
                "        \"dozenten\": " + "\"" + dozent + "\",\n" +
                "        \"sws\": " + sws + "\n" +
                "    }";
        return str;
    }
}
