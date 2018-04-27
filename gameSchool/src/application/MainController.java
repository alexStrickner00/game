package application;

import java.io.IOException;

import game.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;;

public class MainController {

	private Thread gameThread;
	private final static int width = 1000;
	private final static int height = 600;
	
	
	public void startGame(ActionEvent ae) {

		Node src = ((Node) ae.getSource());

		Stage stage = (Stage) src.getScene().getWindow();

		Pane gamePane = new Pane();
		stage.setScene(new Scene(gamePane, width , height));

		Game game = new Game(gamePane);
		Runnable r = new Runnable() {

			@Override
			public void run() {
				game.run();
			}
		};
		gameThread = new Thread(r);
		gameThread.start();

	}

	public void switchToSettings(ActionEvent ae) {
		Node src = ((Node) ae.getSource());
		Stage stage = (Stage) src.getScene().getWindow();
		try {
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("configView.fxml"));
			stage.setScene(new Scene(root, width, height));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void switchToCredits(ActionEvent ae) {
		Node src = ((Node) ae.getSource());
		Stage stage = (Stage) src.getScene().getWindow();
		try {
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("credits.fxml"));
			stage.setScene(new Scene(root, width, height));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void switchToMenu(ActionEvent ae) {
		Node src = ((Node) ae.getSource());
		Stage stage = (Stage) src.getScene().getWindow();
		try {
			BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("menu.fxml"));
			stage.setScene(new Scene(root, width, height));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
