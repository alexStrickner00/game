package application;

import java.io.File;
import java.io.IOException;

import game.Game;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.stage.Stage;
import javafx.stage.WindowEvent;;

public class MainController {

	private static final String RES_SOUND_BACKGROUND_PATH = "res/sound/background.wav";
	private Thread gameThread;
	private final static int width = 1000;
	private final static int height = 600;
	private static boolean music = true;
	private static boolean sound = true;
	private static MediaPlayer mp;
	private static int difficulty = 0;
	private ToggleButton currentDiffButton;

	@FXML
	ToggleButton musicButton;
	@FXML
	ToggleButton soundButton;

	@FXML
	ToggleButton easyButton;

	@FXML
	ToggleButton mediumButton;

	@FXML
	ToggleButton hardButton;

	private ToggleButton[] diffButtons = new ToggleButton[3];
	private Game game;
	
	@FXML
	public void initialize() {

		diffButtons[0] = easyButton;
		diffButtons[1] = mediumButton;
		diffButtons[2] = hardButton;

		if (mp == null) {
			mp = new MediaPlayer(new Media(new File(RES_SOUND_BACKGROUND_PATH).toURI().toString()));
			mp.setCycleCount(MediaPlayer.INDEFINITE);
		}

		if (musicButton != null) {
			musicButton.setSelected(music);
		}

		if (easyButton != null) {
			diffButtons[difficulty].setSelected(true);
			currentDiffButton = diffButtons[difficulty];
		}
		
		if(soundButton != null) {
			soundButton.setSelected(sound);
		}

		// waehrend Entwicklung
		mp.setVolume(1);
		music();
	}

	public void music() {
		if (musicButton != null) {
			music = musicButton.isSelected();
		}
		if (music) {
			mp.play();
		} else {
			mp.pause();
		}
	}

	public void sound() {
		if(soundButton != null) {
			sound =soundButton.isSelected();
		}
	}

	public void startGame(ActionEvent ae) {
		Node src = ((Node) ae.getSource());

		Stage stage = (Stage) src.getScene().getWindow();
		MainController c = this;
		stage.setOnHidden(new EventHandler<WindowEvent>() {
			
			@Override
			public void handle(WindowEvent event) {
				c.onClose();
			}
		});
		
		Pane gamePane = new Pane();
		stage.setScene(new Scene(gamePane, width, height));

		game = new Game(gamePane, sound, difficulty);
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

	public void changeDifficulty(ActionEvent ae) {
		
		if(ae.getSource() == currentDiffButton) {
			currentDiffButton.setSelected(true);
		} else {
			currentDiffButton = (ToggleButton)ae.getSource();
		}
		
		if (hardButton.isSelected()) {
			difficulty = 2;
		} else if (mediumButton.isSelected()) {
			difficulty = 1;
		} else {
			difficulty = 0;
		}
	}

	public void onClose() {
	 game.stopGame();
	}
}
