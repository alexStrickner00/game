package sprite;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;

public class ProjectileTableElement {

	private SimpleIntegerProperty projectileId;
	private SimpleStringProperty name;
	private SimpleIntegerProperty flyspeed;
	private Image image;
	private int oldProjectileId;

	public ProjectileTableElement(int projectileId, String projectileName, int flySpeed, Image image) {

		this.projectileId = new SimpleIntegerProperty(projectileId);
		this.name = new SimpleStringProperty(projectileName);
		this.flyspeed =new SimpleIntegerProperty(flySpeed);
		this.image = image;

	}

	public int getProjectileId() {
		return projectileId.get();
	}

	public int getOldProjectileId() {
		return oldProjectileId;
	}
	
	public void setProjectileId(int projectileId) {
		this.oldProjectileId = this.projectileId.get();
		this.projectileId.set(projectileId);
	}

	public String getName() {
		return this.name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	
	public int getFlyspeed() {
		return flyspeed.get();
	}
	
	public void setFlyspeed(int flyspeed) {
		this.flyspeed.set(flyspeed);
	}
	
}
