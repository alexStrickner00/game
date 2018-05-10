package game;

import static enums.Team.ENEMY;
import static enums.Team.PLAYER;

import java.io.File;
import java.util.ArrayList;

import hud.Shop;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
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

	public Game(Pane gamePane, boolean sound) {
		this.pane = gamePane;
		this.sound=sound;
		this.canvas = new Canvas(1000, 600);
		this.gc = canvas.getGraphicsContext2D();
		this.pane.getChildren().add(canvas);
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
		ownCastle = new Castle(PLAYER);
		enemyCastle = new Castle(ENEMY);
		ownSprites = new ArrayList<>();
		enemySprites = new ArrayList<>();
		//backgroundMusic = new MediaPlayer(new Media(new File("../../res/sound/music.wav").toURI().toString()));
		 
		backgroundMusic.setCycleCount(MediaPlayer.INDEFINITE);
	}

	public void run() {

		lt = System.nanoTime();
		AnimationTimer at = new AnimationTimer() {

			@Override
			public void handle(long now) {

				long et = now - lt;
				lt = now;

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

	private void updateFigures(ArrayList<GameFigure> sprites, long et) {
		for (GameFigure s : sprites) {
			s.update(et);
		}
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
