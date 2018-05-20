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
	private final SimpleIntegerProperty costs;
	private Image image;
	private int oldId;

	public GameFigureTableElement(int entityId, String name, String title, int health, int delay, int damage, int speed,
			int shooting, int projId, Image image, int costs) {

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
		this.costs = new SimpleIntegerProperty(costs);
	}

	public int getEntityId() {
		return entityId.get();
	}

	public void setEntityId(int entityId) {
		this.entityId.set(entityId);
	}
	
	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public String getTitle() {
		return title.get();
	}

	public void setTitle(String title) {
		this.title.set(title);
	}

	public int getHealth() {
		return health.get();
	}

	public void setHealth(int health) {
		this.health.set(health);
	}

	public int getDelay() {
		return delay.get();
	}

	public void setDelay(int delay) {
		this.delay.set(delay);
	}

	public int getDamage() {
		return damage.get();
	}

	public void setDamage(int damage) {
		this.damage.set(damage);
	}

	public int getSpeed() {
		return speed.get();
	}

	public void setSpeed(int speed) {
		this.speed.set(speed);
	}

	public int getShooting() {
		return shooting.get();
	}

	public void setShooting(int shooting) {
		this.shooting.set(shooting);
	}

	public int getProjId() {
		return projId.get();
	}

	public void setProjId(int projId) {
		this.projId.set(projId);
	}

	public int getCosts() {
		return costs.get();
	}

	public void setCosts(int costs) {
		this.costs.set(costs);
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public void setOldId(int entityId2) {
		oldId = entityId2;
	}

	public int getOldId() {
		return oldId;
	}

}
