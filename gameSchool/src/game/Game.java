package game;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import sprites.Castle;
import sprites.Sprite;

public class Game {

	private ArrayList<Sprite> ownSprites;
	private ArrayList<Sprite>enemySprites;
	private Castle ownCastle;
	private Castle enemyCastle;
	private GraphicsContext gc;
	
	public Game(GraphicsContext gc){
		this.gc = gc;
	}
	
	public void run() {
		
		
		AnimationTimer at = new AnimationTimer() {
			
			@Override
			public void handle(long now) {
				
			}
		};
		at.start();
	}

}
