package application;

import java.sql.SQLException;
import java.util.ArrayList;

import database.DBManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import sprite.GameFigureTableElement;

public class MainController {

	@FXML
	TableView<GameFigureTableElement> table;

	@FXML
	TableColumn<GameFigureTableElement, String> id_col;

	@FXML
	TableColumn<GameFigureTableElement, String> name_col;

	@FXML
	TextField entId;
	
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
	ImageView image;
	
	private GameFigureTableElement aktElement;
	
	private final ObservableList<GameFigureTableElement> obsList = FXCollections.observableArrayList();
	private DBManager dbmanager;

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

	//TODO details bei click aktiualisieren
	
	public void refreshDetails() {
		aktElement = table.getSelectionModel().getSelectedItems().get(0);
		entId.setText(aktElement.getEntityId() + "");
		name.setText(aktElement.getName());
		title.setText(aktElement.getTitle());
		health.setText(aktElement.getHealth() + "");
		
	}
	
}
