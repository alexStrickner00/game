package hud;

import interfaces.*;
import sprites.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * In dieser Klasse befindet sich die Funktionalitaet fuer die Health-Bars, die
 * im Spiel zu sehen sind
 * 
 * @author Zanon Simon
 * @version 1.0
 *
 */
public class Healthbar implements Renderable {

	private GameFigure parent;
	private Castle castleParent;
	private int whiteX;
	private int y = 20;
	private int redX;
	public static final int HEIGHT = 3;

	private double maxHealth;
	public static final double HEALTHBAR = 50;
	private double health;
	private double whiteLength;
	private double redLength;

	/**
	 * Konstruktor fuer die Truppen-Healthbar
	 * 
	 * @param parent
	 */
	public Healthbar(GameFigure parent) {

		this.parent = parent;
		this.maxHealth = parent.getHealth();

	}

	/**
	 * Konstruktor fuer die Schloss-Healthbar
	 * 
	 * @param parent
	 */
	public Healthbar(Castle castleParent) {
		this.castleParent = castleParent;
		this.maxHealth = castleParent.getHealth();
	}

	@Override
	/**
	 * Zeichnet die Health-Bar nach jedem Frame and die richtige Stelle ueber dem
	 * Sprite der Truppe oder des Schlosses mit der richtigen Laenge, die Abhaengig
	 * von der Health des Objekts ist
	 */
	public void render(GraphicsContext gc) {

		double x = 0;
		double y = 0;

		if (castleParent != null) {
			health = castleParent.getHealth() / maxHealth;
			x = castleParent.getX();
			y = castleParent.getY();
		}

		int offset = 10;

		if (parent != null) {
			health = parent.getHealth() / maxHealth;

			x = parent.getX();
			y = parent.getY();
			// Um das Spiegeln der Sprites zu beruecksichtigen
			if (parent.getSpeed() < 0) {
				offset -= parent.getBoundaries().getWidth() - GameFigure.BOUNDARY_MARGIN * 2;
			}
		}

		redLength = (int) (HEALTHBAR * health);
		whiteLength = HEALTHBAR - redLength;

		gc.setFill(Color.RED);
		gc.fillRect(x + offset, y - 10, redLength, HEIGHT);

		// Wenn gewollt,
		// gc.setFill(Color.WHITE);
		// gc.fillRect(parent.getX() + offset + redLength, parent.getY() - y,
		// whiteLength, HEIGHT);

	}

	@Override
	public void update(double elapsedTime) {

	}

}
