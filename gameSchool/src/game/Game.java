package game;

import static enums.Team.ENEMY;
import static enums.Team.PLAYER;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBManager;
import hud.Shop;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
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

	public Game(Pane gamePane, boolean sound) {
		this.pane = gamePane;
		this.sound=sound;
		this.canvas = new Canvas(1000, 600);
		this.gc = canvas.getGraphicsContext2D();
		this.pane.getChildren().add(canvas);
		
		try {
			this.dbmanager = new  DBManager("res/databaseConnection.conf");
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
		shop = new Shop();
		GameFigure g = dbmanager.getGameFigureById(1);
		g.addVelocityX(10);
		g.setY(419);
		ownSprites.add(g);

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
				shop.update(et);

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
	
	private void doAttacks() {
		for (GameFigure so : ownSprites) {
			for (GameFigure se : enemySprites) {
				so.attack(se);
				se.attack(so);
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
}
