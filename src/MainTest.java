import java.io.File;

public class MainTest {
    public static void main(String[] args) {
        File f = new File("");
        Modulbeschreibungen modulbeschreibungen = new Modulbeschreibungen("./src/textdatei.txt");
        System.out.println(modulbeschreibungen.getZertifikate("BET"));
        System.out.println(modulbeschreibungen.getVerzahnteModule());
        System.out.println(modulbeschreibungen.getAnzahlModule("BI", null));
        System.out.println(modulbeschreibungen.getAnzahlVeranstaltungen("BI", null));
        System.out.println(modulbeschreibungen.getECTS("BI"));

        System.out.println("---------------------");
        System.out.println("BMT SWS: " + modulbeschreibungen.getSWS("BMT"));
        System.out.println("BI SWS: " + modulbeschreibungen.getSWS("BI"));
        System.out.println("BET SWS: " + modulbeschreibungen.getSWS("BET"));

        System.out.println(modulbeschreibungen.getSortierteStudiengaenge());

        System.out.println(modulbeschreibungen.getJSON("BI"));
    }
}
