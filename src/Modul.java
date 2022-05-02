import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Modul {
    private String modulbezeichnung;
    private String kuerzel;
    private String studiengang;
    private int semester;
    private String art;
    private double ectsPunkte;
    private String pruefungsform;
    private String modulverantwortlicher;

    private final List<Veranstaltung> veranstaltungen = new ArrayList<>();

    public Modul(List<String> data) {
        parseModulInfo(data);
    }

    private void parseModulInfo(List<String> data) {
        String[] attributes = data.get(0).split("\\|");
        this.modulbezeichnung = attributes[0];
        this.kuerzel = attributes[1];
        this.studiengang = attributes[2];
        if(!attributes[3].equals("WPM"))
            this.semester = Integer.parseInt(attributes[3]);
        else
            this.semester = -1;
        this.art = attributes[4];
        this.ectsPunkte = Double.parseDouble(attributes[5].replace(',', '.'));
        this.pruefungsform = attributes[6];
        this.modulverantwortlicher = attributes[7];

        for(int i = 1; i < data.size(); i++) {
            veranstaltungen.add(new Veranstaltung(data.get(i)));
        }
    }

    public String getModulbezeichnung() {
        return modulbezeichnung;
    }

    public String getKuerzel() {
        return kuerzel;
    }

    public String getStudiengang() {
        return studiengang;
    }

    public int getSemester() {
        return semester;
    }

    public String getArt() {
        return art;
    }

    public double getEctsPunkte() {
        return ectsPunkte;
    }

    public String getPruefungsform() {
        return pruefungsform;
    }

    public String getModulverantwortlicher() {
        return modulverantwortlicher;
    }

    public List<Veranstaltung> getVeranstaltungen() {
        return veranstaltungen;
    }

    public boolean verzahnt(Modul modul) {
        if(this == modul)
            return false;

        if(studiengang.equals(modul.studiengang))
            return false;

        if(!modulbezeichnung.equals(modul.modulbezeichnung))
            return false;

        if(!modulverantwortlicher.equals(modul.modulverantwortlicher))
            return false;

        return kuerzelBisBindestrich(this.kuerzel).equals(kuerzelBisBindestrich(modul.kuerzel));
    }

    public static String kuerzelBisBindestrich(String kuerzel) {
        int length = kuerzel.indexOf('-');
        if(length < 0)
            length = kuerzel.length();
        return kuerzel.substring(0, length);
    }

    public String getJSON() {
        StringBuilder str = new StringBuilder();
        str.append("{\n");
        str.append("    \"bezeichnung\": " + "\"").append(modulbezeichnung).append("\",\n");
        str.append("    \"kuerzel\": " + "\"").append(kuerzel).append("\",\n");
        str.append("    \"studiengang\": " + "\"").append(studiengang).append("\",\n");
        str.append("    \"semester\": \"").append(semester > 0 ? semester : "WPM").append("\",\n");
        str.append("    \"art\": " + "\"").append(art).append("\",\n");
        str.append("    \"ects\": ").append(ectsPunkte).append(",\n");
        str.append("    \"pruefungsform\": " + "\"").append(pruefungsform).append("\",\n");
        str.append("    \"verantwortlicher\": " + "\"").append(modulverantwortlicher).append("\",\n");
        str.append("    \"veranstaltungen\": " + "[");
        Iterator<Veranstaltung> it = veranstaltungen.iterator();
        while(it.hasNext()) {
            str.append(it.next().getJSON());
            if(it.hasNext())
                str.append(", ");
        }
        str.append("]\n");
        str.append("}");
        return str.toString();
    }
}
