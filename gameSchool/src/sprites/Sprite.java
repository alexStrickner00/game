package sprites;

import interfaces.Renderable;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * In dieser Klasse befinden sich die Methoden, die zum Zeichnen der einzelnen
 * Bilder der Spielfiguren(und Basen) auf der Oberflaeche notwendig sind.
 * 
 * @author Widerin Alexander, Strickner Alexander
 * @version 1.0
 */
public class Sprite implements Renderable {

	protected Rectangle2D boundaries;

	protected Rectangle2D hitBox;

	protected Image currentImage;

	protected double velocityX;
	protected double velocityY;
	protected double posX;
	protected double posY;
	/**
	 * Diese Methode wir ausschliesslich fuer die Death-Animation des Castles verwendet
	 */
	public void setSprite(Image cr, double x, double y) {
		this.currentImage = cr;
		this.velocityX = 0;
		this.velocityY = 0;
		this.posX = x;
		this.posY = y;
	}

	@Override
	/**
	 * Zeichnet das aktuell auszugebene Bild der Spielfigur nach jedem Frame auf die Spieloberflaeche 
	 */
	public void render(GraphicsContext gc) {
		if (currentImage != null) {
			if (this instanceof GameFigure) {
				GameFigure g = (GameFigure) this;
				if ((g.getSpeed() > 0 || velocityX > 0) || this instanceof Castle) {
					gc.drawImage(currentImage, posX, posY, currentImage.getWidth(), currentImage.getHeight());
				} else {
					gc.drawImage(currentImage, posX, posY, -currentImage.getWidth(), currentImage.getHeight());
				}

			} else {

				if (velocityX > 0 || this instanceof Castle) {
					gc.drawImage(currentImage, posX, posY, currentImage.getWidth(), currentImage.getHeight());
				} else {
					gc.drawImage(currentImage, posX, posY, -currentImage.getWidth(), currentImage.getHeight());
				}

			}
		}
	}

	@Override
	/**
	 * Position der figur wird errechnet (Abhaengig immer von Geschwindigkeit und elapsedTime)
	 */
	public void update(double elapsedTime) {
		this.posX += (double) ((double) this.velocityX * (double) elapsedTime);
		this.posY += this.velocityY * elapsedTime;
	}

	public void addVelocityX(double velToAdd) {
		this.velocityX += velToAdd;
	}

	public void addVelocityY(double velToAdd) {
		this.velocityY += velToAdd;
	}

	public void setVelocityX(double vel) {
		this.velocityX = vel;
	}

	public void setVelocityY(double vel) {
		this.velocityY = vel;
	}
	/**
	 * Gibt zurueck, ob sich die Boundaries zweier Figuren ueberschneiden
	 */
	public boolean intersects(Sprite sprite) {
		return sprite.getBoundaries().intersects(this.boundaries);
	}
	/**
	 * Ueberschneiden sich die Hitboxen zweier Figuren, kann ein Angriff stattfinden
	 */
	public boolean canAttack(Sprite sprite) {
		return sprite.getBoundaries().intersects(this.hitBox);
	}

	public Rectangle2D getBoundaries() {
		return this.boundaries;
	}

	public double getX() {
		return this.posX;
	}

	public double getY() {
		return this.posY;
	}

}
