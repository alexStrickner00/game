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



public class EndRunnable implements Runnable {
	private GraphicsContext gc;
	private Team winner;
	private final static int width = 1000;
	private final static int height = 600;

	public EndRunnable(GraphicsContext gc, Team winner) {
		this.gc = gc;
		this.winner = winner;
	}

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
