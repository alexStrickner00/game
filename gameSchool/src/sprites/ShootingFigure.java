package sprites;

import java.util.ArrayList;

public class ShootingFigure extends GameFigure {

	private ArrayList<Projectile> projectiles;
	
	public ShootingFigure(int entityId) {
		super(entityId);
		projectiles = new ArrayList<>();
	}
	
}
