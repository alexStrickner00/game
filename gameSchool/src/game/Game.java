package game;

import static enums.Team.ENEMY;
import static enums.Team.PLAYER;
import static javafx.scene.input.KeyCode.*;

import java.util.ArrayList;

import hud.Shop;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import sprites.Castle;
import sprites.Sprite;

public class Game {

	private ArrayList<Sprite> ownSprites;
	private ArrayList<Sprite> enemySprites;
	private Castle ownCastle;
	private Castle enemyCastle;
	private Shop shop;
	private Pane pane;
	private Canvas canvas;
	private GraphicsContext gc;
	private long lt;
	private ArrayList<KeyCode> keysPressed;
	
	
	public Game(Pane gamePane) {
		this.pane = gamePane;
		this.canvas = new Canvas(1000, 600);
		this.gc = canvas.getGraphicsContext2D();
		this.pane.getChildren().add(canvas);
//		TODO: Nur falls anklicken der SHopItems nicht anders moeglich
//		pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
//
//			@Override
//			public void handle(MouseEvent event) {
//				
//				
//			}
//		});
//		
		addKeyListener();
		initGame();
	}

	private void addKeyListener() {
		
		this.pane.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				KeyCode code = event.getCode();
				
				if(!keysPressed.contains(code)) {
					keysPressed.add(code);
				}
			}
		});
		
		this.pane.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				KeyCode code = event.getCode();
				if(keysPressed.contains(code)) {
					keysPressed.remove(code);
				}
			}
			
		});
		
	}

	private void initGame() {
		ownCastle = new Castle(PLAYER);
		enemyCastle = new Castle(ENEMY);
	}

	public void run() {

		lt = System.nanoTime();
		AnimationTimer at = new AnimationTimer() {

			@Override
			public void handle(long now) {

				long et = now - lt;

				ownCastle.update(et);
				enemyCastle.update(et);
				updateSprites(ownSprites, et);
				updateSprites(enemySprites, et);

				handleKeys();

			}

		};
		at.start();
	}

	private void updateSprites(ArrayList<Sprite> sprites, long et) {
		for (Sprite s : sprites) {
			s.update(et);
		}
	}

	private void handleKeys() {
		
		for(KeyCode c : keysPressed) {
			switch(c) {
		//	case A: doSomething();
			}
		}
		
	}
}
