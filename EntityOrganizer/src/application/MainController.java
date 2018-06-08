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

public class MainController {

	private static final int SPRITE_HEIGHT = 65;

	private static final int SPRITE_WIDTH = 60;

	protected static final long SWITCH_TIME = 200000000;

	private static final int SPRITE_COUNT = 5;

	@FXML
	TableView<GameFigureTableElement> table;

	@FXML
	TableColumn<GameFigureTableElement, String> id_col;

	@FXML
	TableColumn<GameFigureTableElement, String> name_col;

	@FXML
	TextField entityId;

	@FXML
	TextField name;

	@FXML
	TextField title;

	@FXML
	TextField health;

	@FXML
	TextField delay;

	@FXML
	TextField damage;

	@FXML
	TextField speed;

	@FXML
	TextField shooting;

	@FXML
	TextField projectileId;

	@FXML
	TextField costs;

	@FXML
	ImageView image;

	@FXML
	MenuBar menuBar;

	private GameFigureTableElement aktElement;

	private final ObservableList<GameFigureTableElement> obsList = FXCollections.observableArrayList();
	private DBManager dbmanager;
	private Image[] sprites = new Image[SPRITE_COUNT];

	@FXML
	public void initialize() {

		// nid_column.setCellValueFactory(new PropertyValueFactory<Note,
		// String>("notenID"));
		System.out.println("test");
		id_col.setCellValueFactory(new PropertyValueFactory<GameFigureTableElement, String>("entityId"));
		name_col.setCellValueFactory(new PropertyValueFactory<GameFigureTableElement, String>("name"));

		table.setItems(obsList);
		try {
			dbmanager = new DBManager("databaseConnection.conf");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			syncTable();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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

	public void syncTable() throws SQLException {

		obsList.clear();

		ArrayList<GameFigureTableElement> list = dbmanager.getAllEntities();

		obsList.setAll(list);
		table.refresh();
	}

	public void addNewEntityIntoList() {
		try {
			dbmanager.addEmptyEntity();
			syncTable();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("addNewEN");

	}

	// TODO details bei click aktiualisieren

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
			for (int i = 0; i < SPRITE_COUNT; i++) {
				sprites[i] = SwingFXUtils.toFXImage(bi.getSubimage(i * SPRITE_WIDTH, 0, SPRITE_WIDTH, SPRITE_HEIGHT), null);
			}

		}
	}

	public void uploadSpritesheet() throws SQLException {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Spritesheet auswaehlen...");
		File file = chooser.showOpenDialog(null);

		dbmanager.uploadSprite(aktElement, file);
		syncTable();
	}

	public void saveSelectedEntity() throws SQLException {
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
