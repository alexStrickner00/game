package hud;

import java.io.File;
import java.util.ArrayList;

import interfaces.Renderable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * In dieser Klasse wird mithilfe den Objekten der BarPropertyKlasse die
 * StatusBar in das Spiel gezeichnet
 * 
 * @author Zanon Simon
 * @version 1.0
 *
 */
public class StatusBar implements Renderable {

	private ArrayList<BarProperty> props;

	public StatusBar() {
		props = new ArrayList<>();
	}

	// Geld: ca. (75/15)
	// XP: ca. (195/15)
	// Zeit: ca. (315/15)

	@Override
	/**
	 * Zeichnet nach jedem Frame die Statusbar mit den aktuellen Spieldaten(Value der BarProperties)  
	 */
	public void render(GraphicsContext gc) {

		Image bar = new Image(new File("res/statusbar_small.png").toURI().toString());
		gc.drawImage(bar, 600, 25);

		for (BarProperty b : props) {

			gc.setFill(Color.BLACK);
			Font theFont = Font.font("Verdana", FontWeight.BOLD, 18);
			gc.setFont(theFont);
			gc.fillText(b.getValue(), b.getX(), b.getY());
			gc.strokeText(b.getValue(), b.getX(), b.getY());
		}
	}

	@Override
	public void update(double elapsedTime) {
	}
	/**
	 * Hinzufuegen von Spieldaten(Properties) zur Statusbar
	 * @param b
	 */
	public void addProperty(BarProperty b) {
		this.props.add(b);
	}

}
