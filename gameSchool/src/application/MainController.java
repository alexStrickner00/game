package application;

import game.Game;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;;

public class MainController {

	private Thread gameThread;
	
	public void startGame(ActionEvent ae) {
		
		Node src = ((Node)ae.getSource());
		BorderPane bp = (BorderPane)src.getScene().getRoot();
		
		Canvas canvas = new Canvas(1000,600);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		bp.getChildren().add(canvas);
		
		Game game = new Game(gc);
		Runnable r = new Runnable() {
			
			@Override
			public void run() {
				game.run();
			}
		};
		gameThread = new Thread(r);
		gameThread.start();
		
	}
	
}
