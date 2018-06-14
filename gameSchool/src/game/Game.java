
package game;

import static enums.Team.ENEMY;
import static enums.Team.PLAYER;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import application.Main;
import database.DBManager;
import enums.Team;
import hud.BarProperty;
import hud.Shop;
import hud.StatusBar;
import hud.shopItem;
import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import sprites.Castle;
import sprites.GameFigure;
import sprites.Sprite;

/**
 * Klasse fuer das eigentliche Hauptspiel
 * 
 * @author SimonZanon
 *
 */

public class Game {
	DBManager db;
	/**
	 * Arraylist fuer die Animationen der eigenen Truppen
	 */

	private ArrayList<GameFigure> ownSprites;

	/**
	 * Arraylist fuer die Animation der feindlichen Truppen
	 */

	private ArrayList<GameFigure> enemySprites;

	/**
	 * Eigenes Base-Objekt
	 */

	private Castle ownCastle;

	/**
	 * Feindliches Base-Objekt
	 */

	private Castle enemyCastle;

	/**
	 * Shop-Objekt zum Upgraden der Truppen
	 */

	private Shop shop;

	/**
	 * Pane, um das eigentliche Spiel zu zeichnen
	 */

	private Pane pane;

	/**
	 * Canvas-Objekt auf dem gezeichnet wird und das auf dem Pane sitzt
	 */

	private Canvas canvas;

	/**
	 * Grafik-Kontext vom Canvas
	 */

	private GraphicsContext gc;

	/**
	 * Variable zur Bestimmen der Zeit zwischen Frames
	 */

	private long lt;

	/**
	 * ArrayList fuer aktuell gedrueckte Taste - eventuell fuer Tastenkuerzel
	 */

	private ArrayList<KeyCode> keysPressed;

	/**
	 * Variable zum Ein- und Ausschalten vom Sound
	 */

	private boolean sound;

	/**
	 * Das Spielfeld selbst auf dem Basen und Truppen plaziert werden
	 */

	private Image background;

	/**
	 * Thread welcher zeitlich das Geld an beide Bases verteilt
	 */

	private Thread timeMoneyThread;

	/**
	 * Controller zur Steuerung des Feindes
	 */

	private EnemyController enemyController;

	/**
	 * Statusbar zum Anzeigen des Geldes, der Zeit und den XP
	 */

	private StatusBar statusBar;

	/**
	 * Runnable, welches timeMoneyThread uebergeben wird zur Verteilung des Geldes
	 */

	private MoneyRunnable mRunnable;

	/**
	 * moneyProperty fuer die Statusbar
	 */

	private SimpleStringProperty moneyProperty;

	/**
	 * xpProperty fuer die Statusbar
	 */

	private SimpleStringProperty xpProperty;

	/**
	 * timeProperty fuer die Statusbar
	 */

	private SimpleStringProperty timeProperty;

	/**
	 * Variable fuer die XP, die in der Statusbar angezeigt werden
	 */

	private double xp;

	/**
	 * Variable, die die Zeit seit Spielbeginn speichert
	 */

	private long time;

	/**
	 * Variable zur Speicherung des Schwierigkeitsgrades
	 */

	private int difficulty;

	/**
	 * Enum zur Speicherung, welches Team gewonnen hat
	 */

	private Team winner;

	/**
	 * Variable vom eigenen Geld-Stand
	 */

	private double ownMoney;

	/**
	 * Variable vom gegnerischen Geld-Stand
	 */

	private double enemyMoney;

	/**
	 * Spawn-Punkt fuer eigene Truppen
	 */

	// private static final int PLAYER_SPAWN_X = 67;
	private static final int PLAYER_SPAWN_X = 700;
	/**
	 * Spawn-Punkt fuer eigene Truppen
	 */

	private static final int SPAWN_Y = 479;

	/**
	 * Spawn-Punkt fuer feindliche Truppen
	 */

	private static final int ENEMY_SPAWN_X = 855;



	/**
	 * Animation Timer fuer fixe Wiederholungsrate
	 */
	private AnimationTimer at;

	/**
	 * Speichert, wann das Spiel gewonnen wurde
	 */
	private long winTime;

	/**
	 * Konstruktor fuer das Game-Objekt
	 * 
	 * @param gamePane
	 *            Spielpane
	 * @param sound
	 *            Sound-Variable
	 * @param difficulty
	 *            Schwierigkeitsgrad
	 */
	public Game(Pane gamePane, boolean sound, int difficulty) {
		this.pane = gamePane;
		this.sound = sound;
		this.difficulty = difficulty;

		this.canvas = new Canvas(1000, 600);
		this.gc = canvas.getGraphicsContext2D();
		this.pane.getChildren().add(canvas);

		keysPressed = new ArrayList<>();
		addKeyListener();
		initGame();

	}

	/**
	 * Key-Listener zur Aufnahme von gedrueckten Tasten
	 */

	private void addKeyListener() {

		this.pane.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				KeyCode code = event.getCode();

				if (!keysPressed.contains(code)) {
					keysPressed.add(code);
				}
			}
		});

		this.pane.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				KeyCode code = event.getCode();
				if (keysPressed.contains(code)) {
					keysPressed.remove(code);
				}
			}

		});

	}

	/**
	 * Methode, um das Game zu initialisierung (Fuer den Start wichtige und
	 * einmalige Methoden aufrufen)
	 */

	private void initGame() {
		ownCastle = Castle.createCastle(PLAYER);
		enemyCastle = Castle.createCastle(ENEMY);
		ownSprites = new ArrayList<>();
		enemySprites = new ArrayList<>();
		background = new Image(new File("res/playground_clear.png").toURI().toString());
		shop = new Shop(this, Paint.valueOf("BLUE"));

		time = System.nanoTime();
		winTime = -1;

		moneyProperty = new SimpleStringProperty();
		xpProperty = new SimpleStringProperty();
		timeProperty = new SimpleStringProperty();

		statusBar = new StatusBar();

		statusBar.addProperty(new BarProperty("Money", moneyProperty, 675, 65));
		statusBar.addProperty(new BarProperty("Money", xpProperty, 795, 65));
		statusBar.addProperty(new BarProperty("Money", timeProperty, 915, 65));

		ownMoney = 300;
		enemyMoney = 50 + this.getDifficulty() * 20;

	}

	/**
	 * Methode, um das Spiel zu starte
	 */

	public void run() {

		lt = System.nanoTime();
		at = new AnimationTimer() {

			@Override
			public void handle(long now) {

				double et = now - lt;
				lt = now;
				et /= 1000000000.0;

				if (winner == null) {
					drawBackground();
					ownCastle.update(et);
					enemyCastle.update(et);
					updateFigures(ownSprites, et);
					updateFigures(enemySprites, et);

					doAttacks();

					ownCastle.render(gc);
					enemyCastle.render(gc);
					renderFigures(ownSprites, gc);
					renderFigures(enemySprites, gc);
					shop.render(gc);
					statusBar.render(gc);
					checkStateOfCastles();
				}
				handleKeys();
				refreshProperties();
				if (isFinished()) {

					if (winTime == -1) {
						System.out.println("expl");
						Sprite sp = new Sprite();
						if (winner == Team.PLAYER) {
							xp+=100;
							sp.setSprite(new Image(new File("res/castle_explosion.png").toURI().toString()), 1000, 380);
							sp.render(gc);
						} else {
							sp.setSprite(new Image(new File("res/castle_explosion.png").toURI().toString()), 110, 380);
							sp.render(gc);
						}
						if (sound) {
							MediaPlayer mp = new MediaPlayer(
									new Media(new File("res/sound/castle_explode.wav").toURI().toString()));
							mp.setCycleCount(1);
							mp.play();
						}

						winTime = System.currentTimeMillis();
					}

					if (System.currentTimeMillis() - winTime > 1000) {
						at.stop();
						try {
							db = new DBManager("res/databaseConnection.conf");
							db.pushStats((int) xp, "" + getPlayTime());
						} catch (ClassNotFoundException e) {

							e.printStackTrace();
						} catch (SQLException e) {

							e.printStackTrace();
						}
						try {
							showEndcard();
						} catch (SQLException e) {
	
							e.printStackTrace();
						}
					}

				}
			}

		};

		mRunnable = new MoneyRunnable(this);
		timeMoneyThread = new Thread(mRunnable);
		enemyController = new EnemyController(this);

		at.start();
		timeMoneyThread.start();
		enemyController.start();
	}

	/**
	 * Properties fuer die Statusbar aktualisieren
	 */

	private void refreshProperties() {
		moneyProperty.set("" + (int) ownMoney);
		xpProperty.set("" + (int) xp);
		timeProperty.set("" + getPlayTime());

	}

	/**
	 * Methode, die die Zeit seit Spielbeginn zurueckgibt
	 * 
	 * @return Zeit
	 */

	private int getPlayTime() {
		return (int) ((double) (System.nanoTime() - time) / 1000000000.0);
	}

	/**
	 * Gibt das Leben der Basen zurueck
	 */

	private void checkStateOfCastles() {
		if (ownCastle.getHealth() <= 0) {
			winner = ENEMY;
			return;
		}
		if (enemyCastle.getHealth() <= 0) {
			winner = PLAYER;
		}
	}

	/**
	 * Methode um die Sprites/Truppen zu updaten
	 * 
	 * @param sprites
	 *            Sprites
	 * @param et
	 *            ET
	 */

	private void updateFigures(ArrayList<GameFigure> sprites, double et) {
		for (GameFigure s : sprites) {
			s.update(et);
		}
	}

	/**
	 * Spielfeld wird gezeichnet
	 */

	private void drawBackground() {
		gc.drawImage(background, 0, 0);
	}

	/**
	 * Truppen attakieren die Gegnerischen
	 */
	private void doAttacks() {

		ArrayList<GameFigure> deadSprites = new ArrayList<>();

		for (GameFigure so : ownSprites) {

			boolean vornFrei = true;

			for (GameFigure se : enemySprites) {

				if (so != se) {

					if (so.intersects(se)) {
						so.setVelocityX(0);
						vornFrei = false;
					}
				}
				if (!deadSprites.contains(so) && !deadSprites.contains(se)) {
					if (so.attack(se) || se.attack(so)) {
						if (sound) {
							MediaPlayer mp = new MediaPlayer(
									new Media(new File("res/sound/sword_hit.wav").toURI().toString()));
							mp.setCycleCount(1);
							mp.play();
						}
					}
				}

				if (so.isDead()) {
					if (!deadSprites.contains(so)) {
						deadSprites.add(so);
						if (sound) {
							MediaPlayer mp = new MediaPlayer(
									new Media(new File("res/sound/death_sound.wav").toURI().toString()));
							mp.setCycleCount(1);
							mp.play();
						}
						enemyMoney += so.getEarnedMoney();
					}
				}
				if (se.isDead()) {
					if (!deadSprites.contains(se)) {
						deadSprites.add(se);
						if (sound) {
							MediaPlayer mp = new MediaPlayer(
									new Media(new File("res/sound/death_sound.wav").toURI().toString()));
							mp.setCycleCount(1);
							mp.play();
						}
						ownMoney += se.getEarnedMoney();
						xp += se.getEarnedMoney() * 0.1;
					}
				}

			}

			if (so.intersects(enemyCastle)) {
				so.setVelocityX(0);
				vornFrei = false;
			}

			if (so.attack(enemyCastle)) {

			}

			if (vornFrei) {
				so.setVelocityX(so.getSpeed());
			}
		}

		for (GameFigure g : deadSprites) {
			if (ownSprites.contains(g)) {
				ownSprites.remove(g);
			}
			if (enemySprites.contains(g)) {
				enemySprites.remove(g);
			}
		}

		for (GameFigure se : enemySprites) {

			boolean vornFrei = true;

			for (GameFigure so : ownSprites) {

				if (se != so) {

					if (se.intersects(so)) {
						se.setVelocityX(0);
						vornFrei = false;
					}
				}
			}

			// check ob gegner bei eigener burg ist
			if (se.intersects(ownCastle)) {
				vornFrei = false;
			}

			se.attack(ownCastle);

			if (vornFrei) {
				se.setVelocityX(se.getSpeed());
			} else {
				se.setVelocityX(0);
			}
		}
	}

	/**
	 * Methode zum Rendern der Figuren
	 * 
	 * @param sprites
	 *            Sprite
	 * @param gc
	 *            Grafikkontext
	 */

	private void renderFigures(ArrayList<GameFigure> sprites, GraphicsContext gc) {
		for (GameFigure s : sprites) {
			s.render(gc);
		}
	}

	/**
	 * Methode, um die gedrueckten Keys einzulesen
	 */

	private void handleKeys() {

		for (KeyCode c : keysPressed) {
			switch (c) {
			// case A: doSomething();break;
			default:
				break;
			}
		}

	}

	/**
	 * Gibt das Pane zurueck
	 * 
	 * @return Pane
	 */

	public Pane getPane() {
		return this.pane;
	}

	/**
	 * Methode um Truppen zu spawnen
	 * 
	 * @param team
	 *            Team
	 * @param item
	 *            Item
	 */

	public synchronized void spawn(Team team, shopItem item) {
		int specY = (int) (SPAWN_Y - item.getFigure().getSpritesheet().getHeight());
		GameFigure clone = item.getFigure().clone();
		if (team == PLAYER) {
			if (ownMoney >= item.getPrice()) {
				clone.setX(PLAYER_SPAWN_X);
				clone.setY(specY);
				ownSprites.add(clone);
				ownMoney -= item.getPrice();
			}
		} else if (team == ENEMY) {
			if (enemyMoney >= item.getPrice()) {
				clone.setX(ENEMY_SPAWN_X);
				clone.setY(specY);
				clone.inverseSpeed();
				enemySprites.add(clone);
				enemyMoney -= item.getPrice();
			}
		}
	}

	/**
	 * Methode um zu ueberpruefen ob das Spiel zu Ende ist
	 * 
	 * @return Victory
	 */

	public boolean isFinished() {
		return winner != null;
	}

	/**
	 * Methode um den Spielerkonten Geld hinzuzufuegen
	 * 
	 * @param player
	 *            Spieler
	 * @param moneyFromDifficulty
	 *            Geldmenge
	 */

	public void addMoney(Team player, double moneyFromDifficulty) {
		if (player == PLAYER) {
			ownMoney += moneyFromDifficulty;
		} else if (player == ENEMY) {
			enemyMoney += moneyFromDifficulty;
		}
	}

	/**
	 * Gibt den Schwierigkeitsgrad zurueck
	 * 
	 * @return Schwierigkeit
	 */

	public double getDifficulty() {
		return difficulty;
	}

	/**
	 * Methode um das Geld nach Einkauf im Shop upzudaten
	 * 
	 * @param team
	 *            Team
	 * @param item
	 *            Item
	 * @return Upgrade
	 */

	public boolean upgradeItem(Team team, shopItem item) {
		if (team == PLAYER) {
			if (ownMoney >= item.getUpgradeCost()) {
				ownMoney -= item.getUpgradeCost();
				return true;
			}
		}
		return false;
	}

	/**
	 * Methode um das Spiel zu stoppen
	 */

	public void stopGame() {
		enemyController.stopController();
		mRunnable.stopRunnable();
		at.stop();
	}

	/**
	 * Methode, um zur Endcard Ansicht zu gelangen
	 * @throws SQLException 
	 */
	private void showEndcard() throws SQLException {

		Stage stage = (Stage) pane.getScene().getWindow();
		AnchorPane pane = null;
		String name = "";
		if (winner == PLAYER) {
			name = "endcard_victory.fxml";
		} else {
			name = "endcard_game_over.fxml";
		}

		try {
			pane = (AnchorPane) FXMLLoader.load(Main.class.getResource(name));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Canvas canvas = new Canvas(600, 300);
		canvas.setLayoutX(200);
		canvas.setLayoutY(300);
		
		pane.getChildren().add(canvas);
		Scene scene = new Scene(pane, 1000, 600);
		stage.setScene(scene);
		ArrayList<String> stats=db.getStats(); 
		String[] results= new String[3];
		for(int i=0;i<3;i++) {
			if(stats.get(i)!=null) {
				results[i]=stats.get(i);
			}
			else {
				results[i]=" NO RESULT YET!";
			}
		}
		GraphicsContext gcc = canvas.getGraphicsContext2D();
		gcc.fillText("BEST 3 RESULTS", 20, 10);
		gcc.fillText("1. "+results[0], 30, 50);
		gcc.fillText("2. "+results[1], 30, 90);
		gcc.fillText("3. "+results[2], 30, 130);
		gcc.fillText("CURRENT RESULT:", 20, 180);
		gcc.fillText("XP: "+(int)xp , 30, 200);
		gcc.fillText("Played Time: "+getPlayTime(), 30, 220);
		
	}

}
