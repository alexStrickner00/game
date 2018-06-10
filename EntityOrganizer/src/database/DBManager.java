package database;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import configuration.ConfigReader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import sprite.GameFigureTableElement;

/**
 * Diese Klasse beschreibt einen Datenbank-Manager, welcher zum Verwalten der bloodtime-Datenbank verwendet werden kann.
 * @author Alexander Strickner
 * @version 1.0
 */
public class DBManager {

	/**
	 * speichert den Datenbank-Benutzernamen
	 */
	private String uname;
	
	/**
	 * Speichert das Passwort des Datenbank-Benutzers
	 */
	private String pw;
	
	/**
	 * Speichert die URL zur Datenbank
	 */
	private String address;
	
	/**
	 * Speichert den JDBC-Treiber Pfad
	 */
	private String driver;
	
	/**
	 * Speichert die aktuelle Instanz der Datenbank-Verbindung
	 */
	private Connection conn;

	/**
	 * Konstrukter des DBManagers. 
	 * @param filePath Pfad zur Config-Datei
	 */
	public DBManager(String filePath) {

		ConfigReader cr = new ConfigReader(filePath);
		cr.readConfigFile();
		uname = cr.getPropertyByName("username");
		pw = cr.getPropertyByName("password");
		address = cr.getPropertyByName("database_address");
		driver = cr.getPropertyByName("database_driver");
		
		System.out.println(driver);
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(address, uname, pw);
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error in Database");
			alert.setHeaderText("Fehler bei Verbindung zur Datenbank!");
			alert.setContentText("Kann es sein, dass die Datenbank gerade nicht läuft, oder nicht erstellt wurde?");
			alert.showAndWait();
		}

	}

	/**
	 * Diese Methode holt alle Entities aus der Datenbank und gibt sie in Form einer ArrayList zurueck.
	 * @return Alle Entities in Datenbank
	 */
	public ArrayList<GameFigureTableElement> getAllEntities() {

		ArrayList<GameFigureTableElement> list = new ArrayList<>();
		
		
		try {
			String sql = "SELECT * from spielfigur";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);


			while (rs.next()) {
				Image image;
				image= new Image(new BufferedInputStream(rs.getBinaryStream("image")));
				list.add(new GameFigureTableElement(rs.getInt("entityId"), rs.getString("entity_name"),
						rs.getString("title"), rs.getInt("health"), rs.getInt("delay"), rs.getInt("damage"),
						rs.getInt("speed"),  image, rs.getInt("costs")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * Fuegt der Datenbank einen leeren Entity hinzu.
	 */
	public void addEmptyEntity() {

		String sql = "INSERT INTO spielfigur(entity_name , title, health, delay , damage, speed,costs) values(?,?,?,?,?,?,?)";
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "");
			pstmt.setString(2, "");
			pstmt.setInt(3, 0);
			pstmt.setInt(4, 0);
			pstmt.setInt(5, 0);
			pstmt.setInt(6, 0);
			pstmt.setInt(7, 0);
			
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Updatet ein Element in der Datenbank
	 * @param element zu aktualisierendes Element
	 */
	public void updateElement(GameFigureTableElement element) {
		String sql = "UPDATE spielfigur SET entity_name = ? , title = ? , health = ? , delay = ? , damage = ? , speed = ?  , costs = ? , entityId = ? WHERE entityId = ?";
		try {
			PreparedStatement stmt =  conn.prepareStatement(sql);
			stmt.setString(1, element.getName());
			stmt.setString(2, element.getTitle());
			stmt.setInt(3, element.getHealth());
			stmt.setInt(4, element.getDelay());
			stmt.setInt(5, element.getDamage());
			stmt.setInt(6, element.getSpeed());
			stmt.setInt(7, element.getCosts());
			stmt.setInt(8, element.getEntityId());
			stmt.setInt(9, element.getOldId());
			System.out.println(stmt);
			
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * laedt ein Spritesheet zu einem Entity hoch.
	 * @param aktElement Entity, zu welchem das Spritesheet gehoert
	 * @param file Spritesheet-Datei
	 */
	public void uploadSprite(GameFigureTableElement aktElement, File file) {
		
		String sql = "UPDATE spielfigur SET image = ? WHERE entityId = ?";
		
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		BufferedInputStream bis = new BufferedInputStream(fis);
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(2, aktElement.getEntityId());
			
			stmt.setBinaryStream(1, bis);
			
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
