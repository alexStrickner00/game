package interfaces;

import javafx.scene.canvas.GraphicsContext;

/**
 * KLasse zur Sicherstellung einer gleichartigen Struktur im Programm
 * @author SimonZanon
 *
 */

public interface Renderable {
	
	public void render(GraphicsContext gc);
	public void update(double elapsedTime);
}
