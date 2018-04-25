package sprites;

import interfaces.Renderable;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import lombok.Getter;

public abstract class Sprite implements Renderable {

	@Getter
	private Rectangle2D boundaries;
	@Getter
	private Rectangle2D hitBox;
	
	@Getter
	protected Image mainImage;
	
	private double velocityX;
	private double velocityY;
	private double posX;
	private double posy;
	
	@Override
	public void render(GraphicsContext gc) {

	}

	@Override
	public void update(double elapsedTime) {

	}
	
	public void addVelocityX(double velToAdd) {
		
	}
	
	public void addVelocityY(double velToAdd) {
		
	}
	
	public boolean intersects(Sprite sprite) {
		return sprite.getBoundaries().intersects(this.hitBox);
	}
}
