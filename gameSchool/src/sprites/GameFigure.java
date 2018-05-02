package sprites;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class GameFigure extends Sprite {

	private static final int WALK_IMAGE1 = 0;
	private static final int WALK_IMAGE2 = 1;
	private static final int WALK_IMAGE3 = 2;
	private static final int ATTACK_IMAGE = 3;
	private static final double SPRITE_SWITCH_TIME = 200000000;

	protected int entityId;
	protected Image[] sprites;
	protected int health;
	protected long lastAttack;
	protected int attackDelay;
	protected int damage;
	private double countdown;

	public GameFigure clone() {
		return new GameFigure(this.entityId);
	}

	public GameFigure(int entityId, int health, int lastAttack, int attackDelay, int damage, File spriteSheet) {
		this.entityId = entityId;
		this.health = health;
		this.lastAttack = lastAttack;
		this.attackDelay = attackDelay;
		this.damage = damage;
		loadSprites(spriteSheet);
		countdown = SPRITE_SWITCH_TIME;
	}

	public GameFigure(int entityId) {
		// TODO: Entity aus Daten aus Spielfiguren-Datenbank erstellen.
	}

	public void loadSprites(File spriteSheet) {
		BufferedImage sprite = null;

		try {
			sprite = ImageIO.read(spriteSheet);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		int width = 80;
		int height = 100;

		for (int i = 0; i < 3; i++) {
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

	@Override
	public void update(double elapsedTime) {
		super.update(elapsedTime);

		// Animation logik

	}

}