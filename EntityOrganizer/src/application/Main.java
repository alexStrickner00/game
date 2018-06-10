package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Diese Klasse ist die Main-Klasse der Anwendung.
 * 
 * @author Alexander Strickner
 * @version 1.0
 */
public class Main extends Application {
	@Override
	/**
	 * Diese Methode wird beim Start der JavaFX-Anwendung aufgerufen und erstellt
	 * das Fester, teilt diesem Elemente zu und legt ein Design darueber.
	 */
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("Main.fxml"));
			Scene scene = new Scene(root, 1000, 440);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Entity Organizer");
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
