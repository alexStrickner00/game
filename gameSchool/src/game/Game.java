package game;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import sprites.Castle;
import sprites.Sprite;

public class Game {

	private ArrayList<Sprite> ownSprites;
	private ArrayList<Sprite>enemySprites;
	private Castle ownCastle;
	private Castle enemyCastle;
	
	public Game(){
		
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
