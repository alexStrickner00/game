package hud;

import java.util.ArrayList;

import interfaces.Renderable;
import javafx.scene.canvas.GraphicsContext;

public class StatusBar implements Renderable {

	ArrayList<BarProperty> props = new ArrayList<>();

	@Override
	public void render(GraphicsContext gc) {
		
		//TODO Hier ein Leisten-PNG rendern, geschaetzte passende groessse: 40x1000
		
		for (BarProperty b : props) {
			// TODO: Implementierung des Leistenrenderings, hier sollen alle Properties im
			// Format: *NAME*: *VALUE* dargestellt werden
		}
	}

	@Override
	public void update(double elapsedTime) {
		//TODO: wahrscheinlich leer
	}

	public void addProperty(BarProperty b) {
		this.props.add(b);
	}

}
