package game;

import java.io.File;

import enums.Team;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

/**
 * Klasse fuer den Endscreen
 * @author SimonZanon
 *
 */

public class EndRunnable implements Runnable {

	private Game game;

	/**
	 * Konstruktor fuer die Endscreens
	 */
	
	public EndRunnable(Game game) {
		this.game = game;
	}
	
	/**
	 * In dieser Methode wird das eigentliche Bild vom Endscreen aufgerufen
	 */

	@Override
	public void run() {
		
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}

	
}
