package game;

import java.io.File;
import java.io.IOException;

import enums.Team;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Klasse fuer den Endscreen
 * @author SimonZanon
 *
 */

public class EndRunnable implements Runnable {
	private GraphicsContext gc;
	
	/**
	 * Variable zur Auswahl des Endscreens
	 */
	
	private Team winner;


	/*
	 * Konstruktor fuer die Endscreens
	 */
	
	public EndRunnable(GraphicsContext gc, Team winner) {
		this.gc = gc;
		this.winner = winner;
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
			if (winner == Team.PLAYER) {
				gc.drawImage(new Image(new File("res/finish_victory.png").toURI().toString()), 0, 0);
			} else {
				gc.drawImage(new Image(new File("res/finish_loss.png").toURI().toString()), 0, 0);

			}
			

			
		
	}

	
}
