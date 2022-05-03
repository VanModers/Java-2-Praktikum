import static org.junit.Assert.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.junit.*;

public class Modulbeschreibungen_Test{
    String filename = "mb-junit.txt";
    PrintWriter pw;
    
    Class<Modul> cm = Modul.class; // Klasse Modul vorhanden?
    Class<Veranstaltung> cv = Veranstaltung.class; // Klasse Veranstaltung vorhanden?
    
	@Before
	public void setUp() throws IOException {
        pw = new PrintWriter(filename);
    }

	void einlesen() {
		pw.print(
				"Computeranimation|CMAN-I17|BI|WPM|Wahlpflichtmodul Zertifikat Medieninformatik|7,5|Klausur 1,5h oder mündliche Prüfung|M. Rauschenberger\n" +
				"Computeranimation|B. Arp|4\n" +
				"Praktikum Computeranimation|B. Arp|2\n" +
				"\n" +
				"Software-Qualitätsmanagement|SWQM-I17|BI|6|Pflichtmodul|5|Klausur 1,5h oder mündliche Prüfung|N. Streekmann\n" +
				"Software-Qualitätssicherung|N. Streekmann|2\n" +
				"Praktikum Software-Qualitätssicherung|N. Streekmann|2\n" +
				"\n" +
				"Interdisziplinäres Arbeiten|IARB-I17|BI|WPM|Wahlpflichtmodul|2,5|Studienarbeit|M. Krüger-Basener\n" +
				"Neue Technik-Horizonte|M. Krüger-Basener|2\n" +
				"\n" +
				"Mikrowellenmesstechnik|MWMT-I17|BI|WPM|Wahlpflichtmodul|2,5|mündliche Prüfung oder Kursarbeit oder Klausur 1 h|H.-F. Harms\n" +
				"Mikrowellenmesstechnik|J. Wiebe (LB)|2\n" +
				"\n" +
				"AV-Produktion|AVPR-M17|BMT|WPM|Wahlpflichtmodul Zertifikat AV-Technik und Zertifikat Computer-Aided Media Production|5|Klausur 1,5h oder mündliche Prüfung oder Studienarbeit|T. Lemke\n" +
				"Audiovisuelle Produktion|T. Lemke, A. Klein|4\n" +
				"\n" +
				"Marketing für Ingenieure|MRKT-I17|BI|WPM|Wahlpflichtmodul Zertifikat Marketing und Vertrieb|5|Klausur 1,5 h oder mündliche Prüfung oder Kursarbeit|L. Jänchen\n" +
				"Marketing für Ingenieure|L. Jänchen|2\n" +
				"Praktikum Marketing für Ingenieure|L. Jänchen|2\n" +
				"\n" +
				"Digitale Signalverarbeitung|DSVA-E17|BET|WPM|Wahlpflichtmodul Zertifikat Nachrichtentechnik und Zertifikat Technische Informatik|5|Klausur 1,5 h oder mündliche Prüfung|J.-M. Batke\n" +
				"Digitale Signalverarbeitung|J.-M. Batke|3\n" +
				"Praktikum Digitale Signalverarbeitung|J.-M. Batke|1\n" +
				"\n" +
				"Multimediaprojekte|MMPJ-M17|BMT|WPM|Wahlpflichtmodul Zertifikat Computer-Aided Media Production|5|Erstellung und Dokumentation von Rechnerprogrammen und/oder Mündliche Prüfung|G. J. Veltink\n" +
				"Multimediaprojekte|G. J. Veltink|2\n" +
				"Praktikum Multimediaprojekte|G. J. Veltink|2\n" +
				"\n" +
				"HW/SW Codesign|HWSW-E17|BET|WPM|Wahlpflichtmodul Zertifikat Technische Informatik|5|Klausur 1,5h oder mündliche Prüfung oder Studienarbeit|C. Koch\n" +
				"HW/SW-Codesign|C. Koch|2\n" +
				"Praktikum HW/SW-Codesign|C. Koch|2\n" +
				"\n" +
				"Softwaresicherheit|SWSE-I17|BI|WPM|Wahlpflichtmodul Zertifikat IT-Sicherheit|5|Kursarbeit oder Klausur 1,5h|C. Link\n" +
				"Softwaresicherheit|C. Link|4\n" +
				"\n" +
				"MATLAB Seminar||BI|WPM|Wahlpflichtmodul|2,5|Studienarbeit|G. Kane\n" +
				"MATLAB Seminar|G. Kane|2\n" +
				"\n" +
				"Data Science|DASC-I17|BI|5|Pflichtmodul|5|Klausur 1,5 h oder mündliche Prüfung|T. Schmidt\n" +
				"Data Science|T. Schmidt|3\n" +
				"Praktikum Data Science|T. Schmidt|1\n" +
				"\n" +
				"Kommunikationssysteme|KOSY-I17|BI|WPM|Wahlpflichtmodul|5|Kursarbeit oder mündliche Prüfung oder Klausur 1 h|H.-F. Harms\n" +
				"Kommunikationssysteme|T. Büscher|2\n" +
				"Praktikum Kommunikationssysteme|H.-F. Harms|2\n" +
				"\n" +
				"Betriebssysteme|BTRS-I17|BI|4|Pflichtmodul|5|Klausur 1,5h oder mündliche Prüfung|C. Link\n" +
				"Betriebssysteme|C. Link|2\n" +
				"Praktikum Betriebssysteme|C. Link|2\n" +
				"\n" +
				"Hochfrequenztechnik|HFTE-E17|BET|WPM|Wahlpflichtmodul Zertifikat Nachrichtentechnik|5|Kursarbeit oder mündliche Prüfung oder Klausur 1,0 h|H.-F. Harms\n" +
				"Hochfrequenztechnik|H.-F. Harms|2\n" +
				"Praktikum Hochfrequenztechnik|H.-F. Harms|2\n" +
				"\n" +
				"HW/SW Codesign|HWSW-E17|BI|WPM|Wahlpflichtmodul Zertifikat Technische Informatik|5|Klausur 1,5h oder mündliche Prüfung oder Studienarbeit|C. Koch\n" +
				"HW/SW-Codesign|C. Koch|2\n" +
				"Praktikum HW/SW-Codesign|C. Koch|2\n" +
				"\n" +
				"Digitale Fotografie|DIFO-M17|BMT|WPM|Wahlpflichtmodul|2,5|Kursarbeit|C. Koch\n" +
				"Digitale Fotografie|E. Bühler (LB)|2\n" +
				"\n" +
				"Elektrotechnik 3|ETE3-E17|BET|3|Pflichtmodul|5|Klausur 1,5 h|J. Rolink\n" +
				"Elektrische Maschinen|M. Masur|2\n" +
				"Praktikum Elektrotechnik B|J. Rolink|2\n" +
				"\n" +
				"Mathematik 2|MAT2-M17|BMT|2|Pflichtmodul|7,5|Klausur 1,5 h oder mündliche Prüfung|I. Schebesta\n" +
				"Mathematik 2|I. Schebesta|4\n" +
				"Übung Mathematik 2|R. Heuermann|2\n" +
				"\n");
        pw.close();
	}
	
	@Test
	public void getZertifikate_Test() throws IOException
	{
	    Set<String> s1 = new TreeSet<String>();
	    Set<String> s2 = new TreeSet<String>();
	    Set<String> s3 = new TreeSet<String>();
	    
	    s1.add("Nachrichtentechnik");
	    s1.add("Technische Informatik");

	    s2.add("IT-Sicherheit");
	    s2.add("Marketing und Vertrieb");
	    s2.add("Medieninformatik");
	    s2.add("Technische Informatik");

	    s3.add("AV-Technik");
	    s3.add("Computer-Aided Media Production");
	    
	    einlesen();
    
	    Modulbeschreibungen l = new Modulbeschreibungen(filename);
	    assertEquals(s1,l.getZertifikate("BET")); //Sortierung ist nicht erforderlich
		assertEquals(s2,l.getZertifikate("BI"));
		assertEquals(s3,l.getZertifikate("BMT"));
	}
	
	@Test
	public void getVerzahnteModule_Test() throws IOException
	{
	    Set<String> s1 = new TreeSet<String>();
	    
		s1.add("HW/SW Codesign");
		einlesen();
	    Modulbeschreibungen l = new Modulbeschreibungen(filename);

	    assertEquals(s1,l.getVerzahnteModule()); //Sortierung ist nicht erforderlich
	}

	@Test
	public void getAnzahlModule_Test() throws IOException
	{
		einlesen();
		Modulbeschreibungen l = new Modulbeschreibungen(filename);

		assertEquals(3,l.getAnzahlModule("BET", false));
		assertEquals(1,l.getAnzahlModule("BET", true));
		assertEquals(4,l.getAnzahlModule("BET", null));
		assertEquals(8,l.getAnzahlModule("BI", false));
		assertEquals(3,l.getAnzahlModule("BI", true));
		assertEquals(11,l.getAnzahlModule("BI", null));
		assertEquals(3,l.getAnzahlModule("BMT", false));
		assertEquals(1,l.getAnzahlModule("BMT", true));
		assertEquals(4,l.getAnzahlModule("BMT", null));
	}

	@Test
	public void getAnzahlVeranstaltungen_Test() throws IOException
	{
		einlesen();
		Modulbeschreibungen l = new Modulbeschreibungen(filename);

		assertEquals(6,l.getAnzahlVeranstaltungen("BET", false));
		assertEquals(2,l.getAnzahlVeranstaltungen("BET", true));
		assertEquals(8,l.getAnzahlVeranstaltungen("BET", null));
		assertEquals(12,l.getAnzahlVeranstaltungen("BI", false));
		assertEquals(6,l.getAnzahlVeranstaltungen("BI", true));
		assertEquals(18,l.getAnzahlVeranstaltungen("BI", null));
		assertEquals(4,l.getAnzahlVeranstaltungen("BMT", false));
		assertEquals(2,l.getAnzahlVeranstaltungen("BMT", true));
		assertEquals(6,l.getAnzahlVeranstaltungen("BMT", null));
	}

	@Test
	public void getSWS_Test() throws IOException
	{
	    Map<Integer, Integer> a1 = new HashMap<Integer, Integer>();
	    Map<Integer, Integer> a2 = new HashMap<Integer, Integer>();
	    Map<Integer, Integer> a3 = new HashMap<Integer, Integer>();
	    
	    a1.put(3, 4);

	    a2.put(4, 4);
	    a2.put(5, 4);
	    a2.put(6, 4);

	    a3.put(2, 6);

	    einlesen();
	    Modulbeschreibungen l = new Modulbeschreibungen(filename);
		assertEquals(a1,l.getSWS("BET"));
		assertEquals(a2,l.getSWS("BI"));
		assertEquals(a3,l.getSWS("BMT"));
	}

	@Test
	public void getECTS_Test() throws IOException
	{
	    Map<Integer, Integer> a1 = new HashMap<Integer, Integer>();
	    Map<Integer, Integer> a2 = new HashMap<Integer, Integer>();
	    Map<Integer, Integer> a3 = new HashMap<Integer, Integer>();
	    
	    a1.put(3, 5);

	    a2.put(4, 5);
	    a2.put(5, 5);
	    a2.put(6, 5);

	    a3.put(2, 8);
	    
	    einlesen();
	    Modulbeschreibungen l = new Modulbeschreibungen(filename);
		assertEquals(a1,l.getECTS("BET"));
		assertEquals(a2,l.getECTS("BI"));
		assertEquals(a3,l.getECTS("BMT"));
	}

	@Test
	public void getSortierteStudiengaenge_Test() throws IOException
	{
	    List<String> s1 = new ArrayList<String>();

	    s1.add("BET");
	    s1.add("BMT");
	    s1.add("BI");
	    einlesen();
	    Modulbeschreibungen l = new Modulbeschreibungen(filename);
		assertEquals(s1,l.getSortierteStudiengaenge());
	}

	@Test
	public void getJSON_Test() throws IOException
	{
	    String s1, s2;

	    einlesen();
	    Modulbeschreibungen l = new Modulbeschreibungen(filename);
	    s1 = l.getJSON("BET").replaceAll("\\s|\\w|ü|-|[.]|[/]","");
	    s2 = "[{\"\":\"\",\"\":\"\",\"\":\"\",\"\":\"\",\"\":\"\",\"\":,\"\":\",\",\"\":\"\",\"\":[{\"\":\"\",\"\":\"\",\"\":},{\"\":\"\",\"\":\"\",\"\":}]},{\"\":\"\",\"\":\"\",\"\":\"\",\"\":\"\",\"\":\"\",\"\":,\"\":\",\",\"\":\"\",\"\":[{\"\":\"\",\"\":\"\",\"\":},{\"\":\"\",\"\":\"\",\"\":}]},{\"\":\"\",\"\":\"\",\"\":\"\",\"\":\"\",\"\":\"\",\"\":,\"\":\",\",\"\":\"\",\"\":[{\"\":\"\",\"\":\"\",\"\":},{\"\":\"\",\"\":\"\",\"\":}]},{\"\":\"\",\"\":\"\",\"\":\"\",\"\":\"\",\"\":\"\",\"\":,\"\":\",\",\"\":\"\",\"\":[{\"\":\"\",\"\":\"\",\"\":},{\"\":\"\",\"\":\"\",\"\":}]}]";
	    assertEquals(s1,s2);
	}
}