package application;
	
import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Die Main-Klasse erbt von Application und ist damit die Startklasse unseres Programms
 * @author Strickner Alexander
 * @version 1.0
 */
public class Main extends Application {
	@Override
	/**
	 * Erstellt bei Programmstart das Anwednugsfenster mit dem Menue und ladet alle notwendigen Elemente hinein(Buttons,Labels,...). 
	 */
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
			BorderPane root = (BorderPane) loader.load();
			Scene scene = new Scene(root,1000,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Bloodtime");
			primaryStage.getIcons().add(new Image(new File("res/icon_wo_bg.png").toURI().toString()));
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
