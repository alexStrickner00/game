package application;

import game.Game;

public class MenuController {
	
	public void startNewGame() {
		Game game = new Game();
		game.run();
	}
	
}
