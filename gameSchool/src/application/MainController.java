package application;

import java.io.IOException;


import game.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;;

public class MainController {

	private static final String RES_SOUND_BACKGROUND_PATH = "../res/sound/background.wav";
	private Thread gameThread;
	private final static int width = 1000;
	private final static int height = 600;
	private boolean music;
	private boolean sound;
	private MediaPlayer mp;
	@FXML
	ToggleButton musicButton;
	@FXML
	ToggleButton soundButton;
	
	
	@FXML
	public void initialize() {
		
		mp= new MediaPlayer(new Media(RES_SOUND_BACKGROUND_PATH));
		mp.setCycleCount(MediaPlayer.INDEFINITE);
		music();
	}
	
	public void music() {
		music = musicButton.isSelected();
		if(music) {
			mp.play();
		}
		else {
			mp.pause();
		}
	}

	public void sound() {
		sound = soundButton.isSelected();
	}

	public void startGame(ActionEvent ae) {
		Node src = ((Node) ae.getSource());

		Stage stage = (Stage) src.getScene().getWindow();

		Pane gamePane = new Pane();
		stage.setScene(new Scene(gamePane, width, height));

		Game game = new Game(gamePane,sound);
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
