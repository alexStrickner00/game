package game;

import static enums.Team.ENEMY;
import static enums.Team.PLAYER;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBManager;
import enums.Team;
import hud.Shop;
import hud.shopItem;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Paint;
import sprites.Castle;
import sprites.GameFigure;

public class Game {

	private ArrayList<GameFigure> ownSprites;
	private ArrayList<GameFigure> enemySprites;
	private Castle ownCastle;
	private Castle enemyCastle;
	private Shop shop;
	private Pane pane;
	private Canvas canvas;
	private GraphicsContext gc;
	private long lt;
	private ArrayList<KeyCode> keysPressed;
	private boolean sound;
	private MediaPlayer backgroundMusic;
	private Image background;
	private DBManager dbmanager;
	private int difficulty;

	private double ownMoney;
	private double enemyMoney;

	private static final int PLAYER_SPAWN_X = 67;
	private static final int PLAYER_SPAWN_Y = 419;
	private static final int ENEMY_SPAWN_X = 855;
	private static final int ENEMY_SPAWN_Y = 419;

	public Game(Pane gamePane, boolean sound, int difficulty) {
		this.pane = gamePane;
		this.sound = sound;
		this.difficulty = difficulty;

		this.canvas = new Canvas(1000, 600);
		this.gc = canvas.getGraphicsContext2D();
		this.pane.getChildren().add(canvas);

		try {
			this.dbmanager = new DBManager("res/databaseConnection.conf");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		keysPressed = new ArrayList<>();
		// TODO: Nur falls anklicken der SHopItems nicht anders moeglich
		// pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
		//
		// @Override
		// public void handle(MouseEvent event) {
		//
		//
		// }
		// });
		//
		addKeyListener();
		initGame();
	}

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

	private void initGame() {
		ownCastle = Castle.createCastle(PLAYER);
		enemyCastle = Castle.createCastle(ENEMY);
		ownSprites = new ArrayList<>();
		enemySprites = new ArrayList<>();
		background = new Image(new File("res/playground_clear.png").toURI().toString());
		shop = new Shop(this, Paint.valueOf("BLUE"));
		GameFigure g = dbmanager.getGameFigureById(1);
		g.setVelocityX(g.getSpeed());
		g.setY(419);
		g.setHealth(200);
		ownSprites.add(g);
		GameFigure g2 = dbmanager.getGameFigureById(1);
		g2.inverseSpeed();
		g2.setVelocityX(g2.getSpeed());
		g2.setY(419);
		g2.setX(900);
		enemySprites.add(g2);

		ownMoney = 100;
		enemyMoney = 100;

	}

	public void run() {

		lt = System.nanoTime();
		AnimationTimer at = new AnimationTimer() {

			@Override
			public void handle(long now) {

				double et = now - lt;
				lt = now;
				et /= 1000000000.0;

				drawBackground();
				ownCastle.update(et);
				enemyCastle.update(et);
				updateFigures(ownSprites, et);
				updateFigures(enemySprites, et);
				// shop.update(et);

				doAttacks();

				ownCastle.render(gc);
				enemyCastle.render(gc);
				renderFigures(ownSprites, gc);
				renderFigures(enemySprites, gc);
				shop.render(gc);

				handleKeys();

			}

		};
		at.start();
	}

	private void updateFigures(ArrayList<GameFigure> sprites, double et) {
		for (GameFigure s : sprites) {
			s.update(et);
		}
	}

	private void drawBackground() {
		gc.drawImage(background, 0, 0);
	}

	// TODO: testen
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
					so.attack(se);
					se.attack(so);
				}

				if (so.isDead()) {
					if (!deadSprites.contains(so)) {
						deadSprites.add(so);
					}
				}
				if (se.isDead()) {
					if (!deadSprites.contains(se)) {
						deadSprites.add(se);
					}
				}

			}

			if (so.intersects(enemyCastle)) {
				so.setVelocityX(0);
				vornFrei = false;
			}

			so.attack(enemyCastle);

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
			}
		}
	}

	private void renderFigures(ArrayList<GameFigure> sprites, GraphicsContext gc) {
		for (GameFigure s : sprites) {
			s.render(gc);
		}
	}

	private void handleKeys() {

		for (KeyCode c : keysPressed) {
			switch (c) {
			// case A: doSomething();break;
			default:
				break;
			}
		}

	}

	public Pane getPane() {
		return this.pane;
	}

	public void spawn(Team team, shopItem item) {
		GameFigure clone = item.getFigure().clone();
		if (team == PLAYER) {
			if (ownMoney >= item.getPrice()) {
				clone.setX(PLAYER_SPAWN_X);
				clone.setY(PLAYER_SPAWN_Y);
				ownSprites.add(clone);
				ownMoney -= item.getPrice();
			}
		} else if (team == ENEMY) {
			if (enemyMoney >= item.getPrice()) {
				clone.setX(ENEMY_SPAWN_X);
				clone.setY(ENEMY_SPAWN_Y);
				enemySprites.add(clone);
				enemyMoney -= item.getPrice();
			}
		}
		System.out.println(ownMoney);
	}

}
