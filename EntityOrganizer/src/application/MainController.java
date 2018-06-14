package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBManager;
import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sprite.GameFigureTableElement;

/**
 * Diese Klasse dient als Main-Controller der Anwendung.
 * 
 * @author Alexander Strickner
 * @version 1.0
 */
public class MainController {

	/**
	 * speichert die Standard-Hoehe eines Sprites fuer die Vorschau
	 */
	private static final int SPRITE_HEIGHT = 65;

	/**
	 * speichert die Standard-Breite eines Sprites fuer die Vorschau
	 */
	private static final int SPRITE_WIDTH = 60;

	/**
	 * Speichert die Zeit, nach welcher das naechste Sprite angezeigt werden
	 * soll(Animation) in Nanosekunden.
	 */
	protected static final long SWITCH_TIME = 200000000;

	/**
	 * Gibt an, wie viele Sprites sich in einem Spritesheet befinden
	 */
	private static final int SPRITE_COUNT = 5;

	/**
	 * Referenz auf die Tabelle in der Anwendung
	 */
	@FXML
	TableView<GameFigureTableElement> table;

	/**
	 * Referenz auf die ID-Spalte in der Anwendung
	 */
	@FXML
	TableColumn<GameFigureTableElement, String> id_col;

	/**
	 * Referenz auf die NAME-Spalte in der Anwendung
	 */
	@FXML
	TableColumn<GameFigureTableElement, String> name_col;

	/**
	 * Referenz auf das ID-Textfeld
	 */
	@FXML
	TextField entityId;

	/**
	 * Referenz auf das Name-Textfeld
	 */
	@FXML
	TextField name;

	/**
	 * Referenz auf das Titel-Textfeld
	 */
	@FXML
	TextField title;

	/**
	 * Referenz auf das Health-Textfeld
	 */
	@FXML
	TextField health;

	/**
	 * Referenz auf das Delay-Textfeld
	 */
	@FXML
	TextField delay;

	/**
	 * Referenz auf das Damage-Textfeld
	 */
	@FXML
	TextField damage;

	/**
	 * Referenz auf das Speed-Textfeld
	 */
	@FXML
	TextField speed;

	/**
	 * Referenz auf das Costs-Textfeld
	 */
	@FXML
	TextField costs;

	/**
	 * Referenz auf das ImageView fuer die Spritevorschau
	 */
	@FXML
	ImageView image;

	/**
	 * Referenz auf die Menueleiste
	 */
	@FXML
	MenuBar menuBar;

	/**
	 * Speichert das aktuell ausgewaehlte Element
	 */
	private GameFigureTableElement aktElement;

	/**
	 * Dient der Tabelle als Datenliste. Diese Liste ist mit der Tabelle verbunden
	 */
	private final ObservableList<GameFigureTableElement> obsList = FXCollections.observableArrayList();

	/**
	 * die Datenbank-Manager Instanz des Organizers
	 */
	private DBManager dbmanager;

	/**
	 * speichert das Spritesheet als Einzelbilder
	 */
	private Image[] sprites = new Image[SPRITE_COUNT];

	/**
	 * Wird beim Aufruf der Seite ausgefuehrt, hier wird die Tabelle mit den Werten
	 * verknuepft, der Datenbank-Manager instanziiert und die Werte aus der
	 * Datenbank geholt.
	 */
	@FXML
	public void initialize() {

		// nid_column.setCellValueFactory(new PropertyValueFactory<Note,
		// String>("notenID"));
		System.out.println("test");
		id_col.setCellValueFactory(new PropertyValueFactory<GameFigureTableElement, String>("entityId"));
		name_col.setCellValueFactory(new PropertyValueFactory<GameFigureTableElement, String>("name"));

		table.setItems(obsList);
		dbmanager = new DBManager("databaseConnection.conf");

		syncTable();

		AnimationTimer at = new AnimationTimer() {

			private long lastSwitch = 0;
			private int picToRender = 0;

			@Override
			public void handle(long now) {
				if ((now - lastSwitch) > SWITCH_TIME) {
					picToRender++;
					lastSwitch = now;
					if (picToRender >= SPRITE_COUNT) {
						picToRender = 0;
					}
				}
				if (sprites[0] != null)
					image.setImage(sprites[picToRender]);
			}
		};

		at.start();

	}

	/**
	 * Diese Methode aktualisiert die Tabellenwerte mit jenen in der Datenbank.
	 */
	public void syncTable() {

		obsList.clear();

		ArrayList<GameFigureTableElement> list = dbmanager.getAllEntities();

		obsList.setAll(list);
		table.refresh();
	}

	/**
	 * Diese Methode fuegt der Datenbank einen leeren Entity hinzu.
	 */
	public void addNewEntityIntoList() {

		dbmanager.addEmptyEntity();
		syncTable();

		System.out.println("addNewEN");

	}

	/**
	 * Wird beim Klick auf ein Tabellenelement aufgerufen. Diese Methode
	 * aktualisiert die Daten in den Textfeldern.
	 */
	public void refreshDetails() {
		aktElement = table.getSelectionModel().getSelectedItems().get(0);
		entityId.setText(aktElement.getEntityId() + "");
		name.setText(aktElement.getName());
		title.setText(aktElement.getTitle());
		health.setText(aktElement.getHealth() + "");
		delay.setText(aktElement.getDelay() + "");
		damage.setText(aktElement.getDamage() + "");
		speed.setText(aktElement.getSpeed() + "");
		costs.setText(aktElement.getCosts() + "");

		if (aktElement.getImage() != null) {
			BufferedImage bi = null;
			bi = SwingFXUtils.fromFXImage(aktElement.getImage(), null);

			int widthpi = bi.getWidth() / SPRITE_COUNT;
			int height = bi.getHeight();

			for (int i = 0; i < SPRITE_COUNT; i++) {
				sprites[i] = SwingFXUtils.toFXImage(bi.getSubimage(i * widthpi, 0, widthpi, height), null);
			}

		}
	}

	/**
	 * Diese Methode oeffnet einen FileChooser-Dialog, und laesst somit den Benutzer
	 * ein Spritesheet hochladen.
	 */
	public void uploadSpritesheet() {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Spritesheet auswaehlen...");
		File file = chooser.showOpenDialog(null);

		dbmanager.uploadSprite(aktElement, file);
		syncTable();
	}

	/**
	 * Diese Methode speichert den aktuell ausgewaehlten Entity in die Datenbank.
	 */
	public void saveSelectedEntity() {
		aktElement.setName(name.getText());
		aktElement.setTitle(title.getText());
		aktElement.setOldId(aktElement.getEntityId());
		aktElement.setEntityId(Integer.parseInt(entityId.getText()));
		aktElement.setHealth(Integer.parseInt(health.getText()));
		aktElement.setDelay(Integer.parseInt(delay.getText()));
		aktElement.setDamage(Integer.parseInt(damage.getText()));
		aktElement.setSpeed(Integer.parseInt(speed.getText()));
		aktElement.setCosts(Integer.parseInt(costs.getText()));

		dbmanager.updateElement(aktElement);

		syncTable();

	}

}
