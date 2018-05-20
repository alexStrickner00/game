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
	protected double posY;

	@Override
	public void render(GraphicsContext gc) {
		if(velocityX>0 || this instanceof Castle) {
		gc.drawImage(currentImage, posX, posY, 80, 100);
		}
		else {
		gc.drawImage(currentImage, posX, posY, -80, 100);
		}
	}

	@Override
	public void update(double elapsedTime) {
		System.out.println(this.velocityX);
		System.out.println(this.posX);
		System.out.println(elapsedTime);
		this.posX += (double)((double)this.velocityX * (double)elapsedTime);
		this.posY += this.velocityY * elapsedTime;
	}

	public void addVelocityX(double velToAdd) {
		this.velocityX += velToAdd;
	}

	public void addVelocityY(double velToAdd) {
		this.velocityY += velToAdd;
	}

	public boolean intersects(Sprite sprite) {
		return sprite.getBoundaries().intersects(this.hitBox);
	}

	public Rectangle2D getBoundaries() {
		return this.boundaries;
	}

}
