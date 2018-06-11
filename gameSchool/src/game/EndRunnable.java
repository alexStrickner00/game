package game;

import java.io.File;

import enums.Team;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class EndRunnable implements Runnable {
	private GraphicsContext gc;
	private Team winner;

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
