package sprites;

import java.awt.image.BufferedImage;

import hud.Healthbar;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Objekte dieser Klasse sind die Spielfiguren mit all ihren Daten(siehe
 * Konstruktor) In dieser Klasse befinden sich auch Methoden fuer deren Funktion
 * im Spiel(Bewegen, Angreifen,...)
 * 
 * @author Widerin Alexander, Strickner Alexander
 * @version 1.0
 */
public class GameFigure extends Sprite {

	private static final int HITBOX_MARGIN = 10;
	private static final int FIRST_WALK_IMAGE = 0;
	private static final int LAST_WALK_IMAGE = 3;
	private static final int ATTACK_IMAGE = 4;
	private static final double SPRITE_SWITCH_TIME = 150000000;

	public static final double BOUNDARY_MARGIN = 30;
	private static final double EARN_FACTOR = 0.3;

	protected int entityId;
	protected Image[] sprites;
	protected String entity_name;
	protected String title;
	protected int health;
	protected int speed;
	protected int costs;
	protected int attackDelay;
	protected int damage;
	private int nextSprite = 0;
	private double start;
	private boolean fought = false;
	private Image spritesheet;
	private double lastAttack;

	private Healthbar healthbar;

	/**
	 * Kopiert eine Spielfigur(zum Kauf von Einheiten im Spiel)
	 */
	public GameFigure clone() {
		return new GameFigure(this.entityId, this.entity_name, this.title, this.health, this.attackDelay, this.damage,
				this.speed, this.spritesheet, this.costs);
	}

	/**
	 * 
	 * @param entityId
	 *            Figur ID
	 * @param entity_name
	 *            Figurname
	 * @param title
	 *            Figurtitel
	 * @param health
	 *            Startgesundheit der Figur
	 * @param attackDelay
	 *            Cooldown-Zeit zwischen Angriffen der Figur
	 * @param damage
	 *            Schaden pro Angriff, den die figur ausfuehrt
	 * @param speed
	 *            Tempo, mit der sich die figur bewegt
	 * @param image
	 *            Spritsheet der Figur(Zum Anzeigen am Feld)
	 * @param costs
	 *            Preis der Figur im Spiel
	 */
	public GameFigure(int entityId, String entity_name, String title, int health, int attackDelay, int damage,
			int speed, Image image, int costs) {
		this.spritesheet = image;
		this.entityId = entityId;
		this.entity_name = entity_name;
		this.title = title;
		this.health = health;
		this.attackDelay = (int) (attackDelay * (0.8 + Math.random() * 0.4));
		this.damage = damage;
		this.speed = speed;
		this.costs = costs;
		initFigureData();
	}

	/**
	 * Wird eine Figur gekauft und erzeugt(eigentlich geklont), werden in der
	 * initFigure Data die notwendigen Eigenschaften fuer di richtige Funktion der
	 * Figur im Spiel festgelegt.
	 */
	private synchronized void initFigureData() {
		sprites = new Image[ATTACK_IMAGE + 1];
		loadSprites();

		start = System.nanoTime();
		lastAttack = System.nanoTime();

		this.healthbar = new Healthbar(this);

		refreshBoundaries();
	}

	public static int getHitboxMargin() {
		return HITBOX_MARGIN;
	}

	public static int getFirstWalkImage() {
		return FIRST_WALK_IMAGE;
	}

	public static int getLastWalkImage() {
		return LAST_WALK_IMAGE;
	}

	public static int getAttackImage() {
		return ATTACK_IMAGE;
	}

	public static double getSpriteSwitchTime() {
		return SPRITE_SWITCH_TIME;
	}

	public static double getBoundaryMargin() {
		return BOUNDARY_MARGIN;
	}

	public int getEntityId() {
		return entityId;
	}

	public Image[] getSprites() {
		return sprites;
	}

	public String getEntity_name() {
		return entity_name;
	}

	public String getTitle() {
		return title;
	}

	public int getCosts() {
		return costs;
	}

	public int getAttackDelay() {
		return attackDelay;
	}

	public int getDamage() {
		return damage;
	}

	public int getNextSprite() {
		return nextSprite;
	}

	public double getStart() {
		return start;
	}

	public boolean isFought() {
		return fought;
	}

	public Image getSpritesheet() {
		return spritesheet;
	}

	public double getLastAttack() {
		return lastAttack;
	}

	public Healthbar getHealthbar() {
		return healthbar;
	}

	/**
	 * Nach jedem Frame wird die (Position und Hitbox) der Figur neu errechnet
	 */
	private void refreshBoundaries() {
		this.boundaries = new Rectangle2D(this.posX - BOUNDARY_MARGIN, this.posY,
				this.sprites[0].getWidth() + BOUNDARY_MARGIN * 2, this.sprites[0].getWidth());
		this.hitBox = new Rectangle2D(this.posX - HITBOX_MARGIN - BOUNDARY_MARGIN, this.posY,
				this.sprites[0].getWidth() + HITBOX_MARGIN * 2 + BOUNDARY_MARGIN * 2, this.sprites[0].getHeight());
	}

	/**
	 * Speichert immer die aktuellen Bewegungszustaende der figuren in einem Image
	 * Array ab(Zum Zeichnen auf Oberflaeche)
	 */
	public synchronized void loadSprites() {
		BufferedImage sprite = null;

		sprite = SwingFXUtils.fromFXImage(this.spritesheet, null);

		int width = 60;
		int height = 65;

		for (int i = 0; i <= ATTACK_IMAGE; i++) {
			sprites[i] = SwingFXUtils.toFXImage(sprite.getSubimage(width * i, 0, width, height), null);
		}
	}

	/**
	 * Diese Methode uebeprueft staendig, ob die Spielfigur eine andere Angreifen kann,
	 * wenn ja, erfolgt ein Angriff
	 * 
	 * @param figure
	 *            Spielfigur, deren Angreifbarkeit ueberprueft werden soll
	 * @return Angriff durchgefuerht oder nicht
	 */
	public boolean attack(GameFigure figure) {
		if (this.canAttack(figure) && (System.nanoTime() - lastAttack) / 1000000 > this.attackDelay) {
			figure.decHealth(this.damage);

			// Wahrscheinlichkeit von 10%, dass es ein kritischer Hit ist
			if (Math.random() > 0.9) {
				figure.decHealth((int) (this.damage * 0.1));
			}

			this.lastAttack = System.nanoTime();
			return true;
		}
		return false;
	}

	/**
	 * Gleich wie obrige Methode, aber die Basis betreffend
	 * 
	 * @param castle
	 *            Freund- oder feindBasis
	 * @return Angriff auf Basis durchgefuehrt oder nicht
	 */
	public boolean attack(Castle castle) {
		if (this.canAttack(castle) && (System.nanoTime() - lastAttack) / 1000000 > this.attackDelay) {
			castle.decHealth(this.damage);

			// Wahrscheinlichkeit von 10%, dass es ein kritischer Hit ist
			if (Math.random() > 0.9) {
				castle.decHealth((int) (this.damage * 0.5));
			}

			this.lastAttack = System.nanoTime();
			return true;
		}
		return false;
	}

	protected void decHealth(int healthToDec) {
		this.health -= healthToDec;
	}

	public boolean isDead() {
		return this.health <= 0;
	}

	@Override
	/**
	 * Rendert Spielfigur mit Healthbar
	 */
	public void render(GraphicsContext gc) {
		super.render(gc);
		healthbar.render(gc);
	}

	@Override
	/**
	 * Setzt den neuen Bewegungszustand(das aktuelle Bild) der Figur nach einer
	 * bestimmten Zeit (Elapsed Time)
	 */
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

	public void setY(double y) {
		this.posY = y;
	}

	public void addDamage(int damageIncrease) {
		this.damage += damageIncrease;
	}

	public double getSpeed() {
		return this.speed;
	}

	public void setX(double x) {
		this.posX = x;

	}

	public Image getMainImage() {
		return sprites[0];
	}

	public void inverseSpeed() {
		this.speed = -speed;
	}

	public double getEarnedMoney() {
		return EARN_FACTOR * costs;
	}

}