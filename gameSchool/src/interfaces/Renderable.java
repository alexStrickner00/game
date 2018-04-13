package interfaces;

import javafx.scene.canvas.GraphicsContext;

public interface Renderable {
	
	public void render(GraphicsContext gc);
	public void update(double elapsedTime);
}
