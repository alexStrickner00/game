package sprites;

import java.awt.image.BufferedImage;

import hud.Healthbar;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
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
	private Image spritesheet;
	private double lastAttack;
	
	private Healthbar healthbar;

	public GameFigure clone() {
		return new GameFigure(this.entityId, this.entity_name, this.title, this.health, this.attackDelay, this.damage,
				this.speed, this.shooting, this.projectileId, this.spritesheet, this.costs);
	}

	public GameFigure(int entityId, String entity_name, String title, int health, int attackDelay, int damage,
			int speed, int shooting, int projectileId, Image image, int costs) {
		this.spritesheet = image;
		this.entityId = entityId;
		this.entity_name = entity_name;
		this.title = title;
		this.health = health;
		this.attackDelay = attackDelay;
		this.damage = damage;
		this.speed = speed;
		this.shooting = shooting;
		this.projectileId = projectileId;
		sprites = new Image[ATTACK_IMAGE + 1];
		loadSprites(image);
		this.costs = costs;
		start = System.nanoTime();
		lastAttack = System.nanoTime();

		this.healthbar = new Healthbar(this);
		
		refreshBoundaries();
	}

	private void refreshBoundaries() {
		this.boundaries = new Rectangle2D(this.posX, this.posY, this.sprites[0].getWidth(), this.sprites[0].getWidth());
		this.hitBox = new Rectangle2D(this.posX - 10, this.posY, this.sprites[0].getWidth() + 20,
				this.sprites[0].getHeight());
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
		if (this.canAttack(figure) && (System.nanoTime() - lastAttack)/1000000 > this.attackDelay) {
			figure.addHealth(this.damage);
			
			//Wahrscheinlichkeit von 10%, dass es ein kritischer Hit ist
			if(Math.random() > 0.9) {
				figure.addHealth((int)(this.damage * 0.1));
			}
			
			this.lastAttack = System.nanoTime();
		}
	}

	private void addHealth(int healthToAdd) {
		this.health += healthToAdd;
		System.out.println(this.getHealth());
	}
	
	public boolean isDead() {
		return this.health <= 0;
	}

	@Override
	public void render(GraphicsContext gc) {
		super.render(gc);
		healthbar.render(gc);
	}
	
	@Override
	public void update(double elapsedTime) {
		super.update(elapsedTime);

		refreshBoundaries();

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

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public double getY() {
		return this.posY;
	}

	public double getX() {
		return this.posX;
	}

	public void setY(double y) {
		this.posY = y;
	}

	public void addDamage(int damageIncrease) {
		// TODO Auto-generated method stub

	}

	public double getSpeed() {
		return this.speed;
	}

	public void setX(double x) {
		this.posX = x;
		
	}
	
	public void inverseSpeed() {
		this.speed = -speed;
	}

}