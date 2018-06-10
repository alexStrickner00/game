package sprite;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;

/**
 * Diese Klasse dient zum Beschreiben eines Elementes in der Tabelle.
 * @author alex
 *
 */
public class GameFigureTableElement {

	/**
	 * ID des Entities
	 */
	private final SimpleIntegerProperty entityId;
	
	/**
	 * Name des Entities
	 */
	private final SimpleStringProperty name;
	
	/**
	 * Titel des Entities
	 */
	private final SimpleStringProperty title;
	
	/**
	 * Health des Entities
	 */
	private final SimpleIntegerProperty health;
	
	/**
	 * Attacken-Delay des Entities
	 */
	private final SimpleIntegerProperty delay;
	
	/**
	 * Schaden des Entities
	 */
	private final SimpleIntegerProperty damage;
	
	/**
	 * Geschwindigkeit des Entities
	 */
	private final SimpleIntegerProperty speed;
	
	/**
	 * Kosten des Entities
	 */
	private final SimpleIntegerProperty costs;
	
	/**
	 * Spritesheet des Entities
	 */
	private Image image;
	
	/**
	 * Zwischenspeicher der alten ID, falls diese im Organizer geaendert wird
	 */
	private int oldId;

	public GameFigureTableElement(int entityId, String name, String title, int health, int delay, int damage, int speed, Image image, int costs) {

		this.entityId = new SimpleIntegerProperty(entityId);
		this.name = new SimpleStringProperty(name);
		this.title = new SimpleStringProperty(title);
		this.health = new SimpleIntegerProperty(health);
		this.delay = new SimpleIntegerProperty(delay);
		this.damage = new SimpleIntegerProperty(damage);
		this.speed = new SimpleIntegerProperty(speed);
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
