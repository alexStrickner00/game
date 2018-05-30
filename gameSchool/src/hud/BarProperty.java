package hud;

import javafx.beans.property.SimpleStringProperty;

public class BarProperty {

	private String propertyName;
	private SimpleStringProperty value;
	
	public BarProperty(String name , SimpleStringProperty prop) {
		this.propertyName = name;
		this.value = prop;
	}
	
	public String getValue() {
		return value.getValue();
	}
	
}
