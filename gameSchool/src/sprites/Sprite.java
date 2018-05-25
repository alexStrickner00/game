package sprites;

import interfaces.Renderable;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Sprite implements Renderable {

	protected Rectangle2D boundaries;

	protected Rectangle2D hitBox;

	protected Image currentImage;

	protected double velocityX;
	protected double velocityY;
	protected double posX;
	protected double posY;

	@Override
	public void render(GraphicsContext gc) {

		if (this instanceof GameFigure) {
			GameFigure g = (GameFigure) this;
			if ((g.getSpeed() > 0 || velocityX > 0) || this instanceof Castle) {
				gc.drawImage(currentImage, posX, posY, 60, 65);
			} else {
				gc.drawImage(currentImage, posX, posY, -60, 65);
			}
			
		} else {
			
			if (velocityX > 0 || this instanceof Castle) {
				gc.drawImage(currentImage, posX, posY, 60, 65);
			} else {
				gc.drawImage(currentImage, posX, posY, -60, 65);
			}
			
		}
	}

	@Override
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

	public boolean intersects(Sprite sprite) {
		return sprite.getBoundaries().intersects(this.boundaries);
	}

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
