package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sprite.ProjectileTableElement;

public class ProjectileController {

	@FXML
	TableView<ProjectileTableElement> table;

	@FXML
	TableColumn<ProjectileTableElement, Integer> id_col;

	@FXML
	TableColumn<ProjectileTableElement, String> name_col;

	@FXML
	TextField projectileId;

	@FXML
	TextField name;

	@FXML
	TextField flyspeed;
	
	@FXML
	ImageView image;

	@FXML
	MenuBar menuBar;

	private ProjectileTableElement aktElement;

	private final ObservableList<ProjectileTableElement> obsList = FXCollections.observableArrayList();
	private DBManager dbmanager;

	@FXML
	public void initialize() throws SQLException {

		// nid_column.setCellValueFactory(new PropertyValueFactory<Note,
		// String>("notenID"));

		id_col.setCellValueFactory(new PropertyValueFactory<ProjectileTableElement, Integer>("projectileId"));
		name_col.setCellValueFactory(new PropertyValueFactory<ProjectileTableElement, String>("name"));

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

		syncTable();

	}

	public void syncTable() throws SQLException {

		obsList.clear();

		ArrayList<ProjectileTableElement> list = dbmanager.getAllProjectiles();

		obsList.setAll(list);
		table.refresh();

	}

	public void addEmptyProjectile() throws SQLException {
		dbmanager.addEmptyProjectile();
		syncTable();
	}

	public void saveSelectedProjectile() throws SQLException {
		aktElement.setProjectileId(Integer.parseInt(projectileId.getText()));
		aktElement.setName(name.getText());
		aktElement.setFlyspeed(Integer.parseInt(flyspeed.getText()));

		dbmanager.updateProjectile(aktElement);
		syncTable();
	}
	
	public void uploadSprite() throws SQLException{
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Sprite-Bilddatei auswaehlen...");
		File f = chooser.showOpenDialog(null);
		
		try {
			dbmanager.uploadProjectileSprite(aktElement, f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteProjectile() throws SQLException{
		dbmanager.deleteProjectile(aktElement);
	}
	
	public void switchView(ActionEvent ae) throws IOException{
			Stage stage = (Stage) menuBar.getScene().getWindow();
			AnchorPane root = FXMLLoader.load(getClass().getResource("Main.fxml"));
			stage.setScene(new Scene(root));
	}
	
	public void refreshDetails() {
		aktElement = table.getSelectionModel().getSelectedItems().get(0);
		projectileId.setText(aktElement.getProjectileId() + "");
		name.setText(aktElement.getName());
		flyspeed.setText(aktElement.getFlyspeed() + "");
		
		if(aktElement.getImage() != null) {
			image.setImage(aktElement.getImage());
		}
		
	}
	
}
