package sprites;

import java.io.File;

import enums.Team;
import hud.Healthbar;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
/**
 * Diese Klasse beinhaltet die Methoden zur Verwaltung der Spieler-Basen
 * @author Strickner Alexander
 * @version 1.0
 */
public class Castle extends Sprite {
	private static final int CASTLE_Y = 328;
	private double health;
	private int level;
	private Team team;
	private Healthbar healthbar;
	
	/**
	 * Eine Basis wird erzeugt
	 * @param player Angabe ob freundliche oder feindliche Basis
	 * @return Gibt freundliches bzw. feindliche Basis zureuck
	 */
	public static Castle createCastle(Team player) {
		if (player == Team.PLAYER) {
			return new Castle(player, 1,1000, new Image(new File("res/base_player.png").toURI().toString()));
		} else {
			return new Castle(player, 1, 1000, new Image(new File("res/base_enemy.png").toURI().toString()));
		}
	}
	/**
	 * @param team Spieler oder Gegner
	 * @param level Startlevel der Basis
	 * @param health Startgesundheit der Basis
	 * @param image Bild der Basis, das im Spiel angezeigt wird
	 */
	public Castle(Team team, int level, double health, Image image) {
		this.team = team;
		this.level = level;
		this.health = health;
		this.currentImage = image;
		this.posY = CASTLE_Y;

		this.healthbar = new Healthbar(this);

		if (team == Team.PLAYER) {
			this.posX = 0;
		} else {
			this.posX = 900;
		}

		this.boundaries = new Rectangle2D(posX, posY, image.getWidth(), image.getHeight());
		this.hitBox = new Rectangle2D(posX - 10, posY, image.getWidth() + 20, image.getHeight());
	}
	/**
	 * Wird bei Basisupgrade ausgefuehrt
	 */
	public void increaseLevel() {
		level++;
		this.calcHealth();
	}

	private void calcHealth() {
		health = 100 * level;
	}

	public Team getTeam() {
		return team;
	}

	public double getHealth() {
		return health;
	}
	/**
	 * Wird ausgefuehrt, wenn Schloss angegriffen wird
	 * @param damage Schaden durh den Feind
	 */
	public void decHealth(double damage) {
		health -= damage;
	}

	@Override
	/**
	 * Zeichnet Basis mit zugehoeriger Healthbar
	 */
	public void render(GraphicsContext gc) {
		super.render(gc);
		healthbar.render(gc);
	}
}
