package sprites;

import java.io.File;

import enums.Team;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class Castle extends Sprite {
	private static final int PLAYER_SPAWN_X = 167;
	private static final int PLAYER_SPAWN_Y = 419;
	private static final int ENEMY_SPAWN_X = 855;
	private static final int ENEMY_SPAWN_Y = 419;
	private static final int CASTLE_Y = 378;
	private double health;
	private int level;
	private Team team;
	protected Point2D spawnpoint;

	public static Castle createCastle(Team player) {
		if (player == Team.PLAYER) {
			return new Castle(player, 1, 100, new Image(new File("res/base_player.png").toURI().toString()),
					new Point2D(PLAYER_SPAWN_X, PLAYER_SPAWN_Y));
		} 
		else {
			return new Castle(player, 1, 100, new Image(new File("res/base_enemy.png").toURI().toString()),
					new Point2D(ENEMY_SPAWN_X, ENEMY_SPAWN_Y));
		}
	}

	public Castle(Team team, int level, double health, Image image, Point2D spawnpoint) {
		this.team = team;
		this.level = level;
		this.health = health;
		this.currentImage = image;
		this.spawnpoint = spawnpoint;
		this.posY = CASTLE_Y;
		if(team == Team.PLAYER) {
			this.posX = 0;
		} else {
			this.posX = 900;
		}
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
	public void addHealth(double damage) {
		health+=damage;
	}
}
