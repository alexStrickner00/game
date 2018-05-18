package sprites;

import enums.Team;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class Castle extends Sprite {
	private static final int PLAYER_SPAWN_X = 167;
	private static final int PLAYER_SPAWN_Y = 419;
	private static final int ENEMY_SPAWN_X = 855;
	private static final int ENEMY_SPAWN_Y = 419;
	private double health;
	private int level;
	private Team team;
	protected Image image;
	protected Point2D spawnpoint;

	public Castle createCastle(Team player) {
		if (player == Team.PLAYER) {
			return new Castle(player, 1, 100, new Image("res/base_player.png"),
					new Point2D(PLAYER_SPAWN_X, PLAYER_SPAWN_Y));
		} 
		else {
			return new Castle(player, 1, 100, new Image("res/base_enemy.png"),
					new Point2D(ENEMY_SPAWN_X, ENEMY_SPAWN_Y));
		}
	}

	public Castle(Team team, int level, double health, Image image, Point2D spawnpoint) {
		this.team = team;
		this.level = level;
		this.health = health;
		this.image = image;
		this.spawnpoint = spawnpoint;
	}
	public void increaseLevel() {
		level++;
		this.calcHealth();
	}
	private void calcHealth() {
		health=100*level;
	}
	public Team getTeam() {
		return team;
	}
	public double getHealth() {
		return health;
	}
	public Point2D getSpawnpoint() {
		return spawnpoint;
	}

	public Image getImage() {
		return image;
	}
	public void addHealth(double damage) {
		health+=damage;
	}
}
