package sprites;

import javafx.scene.image.Image;

public class GameFigure extends Sprite {

	private static final int WALK_IMAGE1 = 0;
	private static final int WALK_IMAGE2 = 1;
	private static final int WALK_IMAGE3 = 2;
	private static final int ATTACK_IMAGE = 3;
	
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
	
	@Override
	public void update(double elapsedTime) {
		super.update(elapsedTime);
		
		//Animation logik
		
	}

}