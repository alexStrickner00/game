package hud;

import javafx.beans.property.SimpleStringProperty;

public class BarProperty {

	private String propertyName;
	private int value;
	private int x;
	private int y;
	
	public BarProperty(String name , int i, int x, int y) {
		this.propertyName = name;
		this.value = i;
		this.x = x;
		this.y = y;
	}
	
	public String getValue() {
		return Integer.toString(value);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	
	
}
