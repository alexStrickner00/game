package hud;

import javafx.beans.property.SimpleStringProperty;
/**
 * Objekte dieser Klasse enthalten die Eigenschaften der einzelnen Felder der Status-Bar
 * @author Zanon Simon
 * @version 1.0
 * 
 */
public class BarProperty {

	private String propertyName;
	private SimpleStringProperty value;
	private int x;
	private int y;
	/**
	 * 
	 * @param name Name des Bar-Abschnitts
	 * @param i Inhalt des Bar-Abschnitts
	 * @param x x-Position des Bar-Abschnitts
	 * @param y y-Position des Bar-Abschnitts
	 */
	public BarProperty(String name , SimpleStringProperty i, int x, int y) {
		this.propertyName = name;
		this.value = i;
		this.x = x;
		this.y = y;
	}
	
	public String getValue() {
		return value.getValue();
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	
	
}
