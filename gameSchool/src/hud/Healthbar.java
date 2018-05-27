package hud;

import interfaces.*;
import sprites.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

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

	public Healthbar(GameFigure parent) {

		this.parent = parent;
		this.maxHealth = parent.getHealth();

	}

	public Healthbar(Castle castleParent) {
		this.castleParent = castleParent;
		this.maxHealth = castleParent.getHealth();
	}

	@Override
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
