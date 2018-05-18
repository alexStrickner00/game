package sprites;

import interfaces.Renderable;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Sprite implements Renderable {

	private Rectangle2D boundaries;

	private Rectangle2D hitBox;

	protected Image currentImage;

	protected double velocityX;
	protected double velocityY;
	protected double posX;
	protected double posy;

	@Override
	public void render(GraphicsContext gc) {
		if(velocityX>0 || this instanceof Castle) {
		gc.drawImage(currentImage, posX, posy, 80, 100);
		}
		else {
		gc.drawImage(currentImage, posX, posy, -80, 100);
		}
	}

	@Override
	public void update(double elapsedTime) {
		this.posX += this.velocityX * elapsedTime;
		this.posy += this.velocityY * elapsedTime;
	}

	public void addVelocityX(double velToAdd) {

	}

	public void addVelocityY(double velToAdd) {

	}

	public boolean intersects(Sprite sprite) {
		return sprite.getBoundaries().intersects(this.hitBox);
	}

	public Rectangle2D getBoundaries() {
		return this.boundaries;
	}

}
