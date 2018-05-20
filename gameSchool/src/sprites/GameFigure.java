package sprites;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class GameFigure extends Sprite {

	private static final int FIRST_WALK_IMAGE = 0;
	private static final int LAST_WALK_IMAGE = 3;
	private static final int ATTACK_IMAGE = 4;
	private static final double SPRITE_SWITCH_TIME = 150000000;

	protected int entityId;
	protected Image[] sprites;
	protected String entity_name;
	protected String title;
	protected int health;
	protected int speed;
	protected int shooting;
	protected int projectileId;
	protected int costs;
	protected int attackDelay;
	protected int damage;
	private int nextSprite = 0;
	private double start;
	private boolean fought = false;

	public GameFigure clone() {
		return new GameFigure(this.entityId);
	}

	public GameFigure(int entityId, String entity_name, String title, int health, int attackDelay, int damage,
			int speed, int shooting, int projectileId, Image image, int costs) {
		this.entityId = entityId;
		this.entity_name = entity_name;
		this.title = title;
		this.health = health;
		this.attackDelay = attackDelay;
		this.damage = damage;
		this.speed = speed;
		this.shooting = shooting;
		this.projectileId = projectileId;
		loadSprites(image);
		this.costs = costs;
		start = System.nanoTime();
		sprites = new Image[ATTACK_IMAGE + 1];
	}

	public GameFigure(int entityId) {
		// TODO: Entity aus Daten aus Spielfiguren-Datenbank erstellen.
	}

	public void loadSprites(Image image) {
		BufferedImage sprite = null;

		sprite = SwingFXUtils.fromFXImage(image, null);

		int width = 80;
		int height = 100;

		for (int i = 0; i <= ATTACK_IMAGE; i++) {
			sprites[i] = SwingFXUtils.toFXImage(sprite.getSubimage(width * i, 0, width, height), null);
		}
	}

	public void attack(GameFigure figure) {
		if (this.intersects(figure)) {
			figure.addHealth(this.damage);
		}
	}

	private void addHealth(int healthToAdd) {
		this.health += healthToAdd;
	}
	public void addDamage(int dg) {
		this.damage+=dg;
	}
	@Override
	public void update(double elapsedTime) {
		super.update(elapsedTime);

		if (System.nanoTime() - start >= SPRITE_SWITCH_TIME) {

			start = System.nanoTime();
			nextSprite++;

			if (super.velocityX == 0 && super.velocityY == 0) {
				if (!fought) {
					super.currentImage = sprites[ATTACK_IMAGE];
					fought = true;
				} else {
					super.currentImage = sprites[FIRST_WALK_IMAGE];
					fought = false;
				}
			} else {
				super.currentImage = sprites[nextSprite];
			}

			if (nextSprite >= LAST_WALK_IMAGE) {
				nextSprite = FIRST_WALK_IMAGE;
			}
		}

	}
}