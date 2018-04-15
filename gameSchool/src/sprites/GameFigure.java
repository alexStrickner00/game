package sprites;

import javafx.scene.image.Image;

public class GameFigure extends Sprite {

	private Image mainImage;
	private Image[] walkImage;
	private Image[] attackImage;
	private int health;
	private long lastAttack;
	private int attackDelay;

	public GameFigure clone() {
		return new GameFigure();
	}
}