package configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Mithilfe dieser Klasse koennen ConfigReader Objekte erstellt werden, um Configurationswerte von Dateien einzulesen.
 * Standarddateiformat: dateiname.conf
 * 
 * Format der .conf Datei:
 * propertyName1=value1
 * propertyName2=value2
 * 
 * @author Alexander Strickner
 * @version 1.0
 */
public class ConfigReader {

	/**
	 * Speichert den Pfad zur Config-Datei
	 */
	private String filePath;

	/**
	 * HashMap zum verbinden der Werte mit deren Identifiern
	 */
	HashMap<String, String> configHashMap;

	/**
	 * Konstruktor der Klasse
	 * @param filePath Pfad zur ConfigDatei
	 */
	public ConfigReader(String filePath) {
		this.filePath = filePath;
		this.configHashMap = new HashMap<String, String>();
	}

	/**
	 * default Konstruktor mit relativen Pfad: config.conf
	 */
	public ConfigReader() {
		this("config.conf");
	}

	/**
	 * Diese Methode liest die Datei, welche mit dem Dateipfad in filePath beschrieben wird, ein und speichert deren Werte in der HashMap configHashMap
	 */
	public void readConfigFile() {

		//Wenn filePath leer ist, wird abgebrochen
		if (filePath.equals(""))
			return;

		//Scanner initialisieren
		Scanner sc = null;
		try {
			sc = new Scanner(new File(filePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}

		//Einlesen der Datei
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			if (line != "") {
				//Zeile wird in Name und Wert geteilt
				int splitIndex = line.indexOf("=");
				String propertyName = line.substring(0, splitIndex);
				String value = line.substring(splitIndex + 1);

				configHashMap.put(propertyName, value);
			}
		}
		sc.close();
	}
	/**
	 * Diese Methode gibt den Wert, welcher dem String propertyName zugeordnet ist, zurueck
	 * @param propertyName Identifier des Wertes
	 * @return Wert als String
	 */
	public String getPropertyByName(String propertyName) {
		return configHashMap.get(propertyName);
	}
}
