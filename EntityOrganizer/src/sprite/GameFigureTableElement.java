package sprite;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;

public class GameFigureTableElement {

	private final SimpleIntegerProperty entityId;
	private final SimpleStringProperty name;
	private final SimpleStringProperty title;
	private final SimpleIntegerProperty health;
	private final SimpleIntegerProperty delay;
	private final SimpleIntegerProperty damage;
	private final SimpleIntegerProperty speed;
	private final SimpleIntegerProperty shooting;
	private final SimpleIntegerProperty projId;
	private Image image;

	public GameFigureTableElement(int entityId, String name, String title, int health, int delay, int damage, int speed,
			int shooting, int projId, Image image) {

		this.entityId = new SimpleIntegerProperty(entityId);
		this.name = new SimpleStringProperty(name);
		this.title = new SimpleStringProperty(title);
		this.health = new SimpleIntegerProperty(health);
		this.delay = new SimpleIntegerProperty(delay);
		this.damage = new SimpleIntegerProperty(damage);
		this.speed = new SimpleIntegerProperty(speed);
		this.shooting = new SimpleIntegerProperty(shooting);
		this.projId = new SimpleIntegerProperty(projId);
		this.image = image;
	}

	public int getEntityId() {
		return entityId.get();
	}

	public String getName() {
		return name.get();
	}

	public String getTitle() {
		return title.get();
	}

	public int getHealth() {
		return health.get();
	}

	public int getDelay() {
		return delay.get();
	}

	public int getDamage() {
		return damage.get();
	}

	public int getSpeed() {
		return speed.get();
	}

	public int getShooting() {
		return shooting.get();
	}

	public int getProjId() {
		return projId.get();
	}
}
