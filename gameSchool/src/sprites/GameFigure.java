package sprites;

import javafx.scene.image.Image;

public class GameFigure extends Sprite {

	protected int entityId;
	protected Image[] walkImage;
	protected Image[] attackImage;
	protected int health;
	protected long lastAttack;
	protected int attackDelay;
	protected int damage;

	public GameFigure clone() {
		return new GameFigure(this.entityId);
	}

	public GameFigure(int entityId) {
		// TODO: Entity aus Daten aus Spielfiguren-Datenbank erstellen.
	}

	public void attack(GameFigure figure) {
		if (this.intersects(figure)) {
			figure.addHealth(this.damage);
		}
	}

	private void addHealth(int healthToAdd) {
		this.health += healthToAdd;
	}

}