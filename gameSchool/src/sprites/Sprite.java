package sprites;

import interfaces.Renderable;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import lombok.Getter;

public abstract class Sprite implements Renderable {

	@Getter
	private Rectangle2D boundaries;
	@Getter
	private Rectangle2D hitBox;

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
	
	public void intersects(Sprite sprite) {
		
	}
}
