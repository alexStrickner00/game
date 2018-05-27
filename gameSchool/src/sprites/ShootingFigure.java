package sprites;

import java.util.ArrayList;

import javafx.scene.image.Image;

public class ShootingFigure extends GameFigure {

	public ShootingFigure(int entityId, String entity_name, String title, int health, int attackDelay, int damage,
			int speed, int shooting, int projectileId, Image image, int costs) {
		super(entityId, entity_name, title, health, attackDelay, damage, speed, shooting, projectileId, image, costs);
	}

	private ArrayList<Projectile> projectiles;
	

	
}
