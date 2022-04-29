import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Studiengang implements Comparable<Studiengang> {
    private String bezeichnung;
    private int sws;
    private List<Modul> module = new ArrayList<>();

    public Studiengang(String bezeichnung, int sws) {
        this.bezeichnung = bezeichnung;
        this.sws = sws;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public int getSws() {
        return sws;
    }

    public List<Modul> getModule() {
        return module;
    }

    public void addModul(Modul modul) {
        this.module.add(modul);
    }

    @Override
    public int compareTo(Studiengang o) {
        if(sws != o.sws)
            return ((Integer)sws).compareTo(o.sws);
        return bezeichnung.compareTo(o.bezeichnung);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Studiengang that = (Studiengang) o;
        return Objects.equals(bezeichnung, that.bezeichnung);
    }
}
