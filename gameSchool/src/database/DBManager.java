package database;

import java.io.BufferedInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import configuration.ConfigReader;
import javafx.scene.image.Image;
import sprites.GameFigure;
/**
 * In dieser Klasse befinden sich die ausprogrammierten Methoden, die dazu benoetigt werden, Daten aus der Datenbank zu holen und zu speichern 
 * @author Widerin Alexander
 * @version 1.0
 */
public class DBManager {
	private String uname;
	private String pw;
	private String address;
	private String driver;
	private Connection conn;

	// TODO: alle throws entfernen und innerhalb diese Klasse behandeln
	/**
	 * Im Konstruktur wird eine Verbindung zur Datenbank hergestellt
	 * @param filePath Pfad zur Config-Datei
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public DBManager(String filePath) throws ClassNotFoundException, SQLException {

		ConfigReader cr = new ConfigReader(filePath);
		cr.readConfigFile();
		uname = cr.getPropertyByName("username");
		pw = cr.getPropertyByName("password");
		address = cr.getPropertyByName("database_address");
		driver = cr.getPropertyByName("database_driver");

		// TODO try and catch statt throw und Dialoge mit Fehlermeldung
		Class.forName(driver);
		conn = DriverManager.getConnection(address, uname, pw);

	}
	/**
	 * Diese Methode gibt alle GameFigure-Objekte aus der Datenbank in einer ArrayList zurück 
	 * @return ArrayList with type GameFigure
	 * @throws SQLException
	 */
	public ArrayList<GameFigure> getAllEntities() throws SQLException {
		String sql = "SELECT * from spielfigur";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);

		ArrayList<GameFigure> list = new ArrayList<>();

		while (rs.next()) {
			Image image;

			BufferedInputStream bim = new BufferedInputStream(rs.getBinaryStream("image"));
			image = new Image(bim);

			list.add(new GameFigure(rs.getInt("entityId"), rs.getString("entity_name"), rs.getString("title"),
					rs.getInt("health"), rs.getInt("delay"), rs.getInt("damage"), rs.getInt("speed"), image,
					rs.getInt("costs")));
		}
		return list;
	}
	/**
	 * Diese Methode gibt die GameFigure aus der Datenbank zurueck, deren Id mit dem Methodenparameter uebereinstimmt.
	 * @param entityId Id of inquired GameFigure
	 * @return
	 */
	public GameFigure getGameFigureById(int entityId) {

		try {
			String sql = "SELECT * FROM spielfigur WHERE entityId=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, entityId);
			ResultSet rs = pstmt.executeQuery();
			Image image;

			if (rs.next()) {
				BufferedInputStream bim = new BufferedInputStream(rs.getBinaryStream("image"));
				image = new Image(bim);
				return new GameFigure(rs.getInt("entityId"), rs.getString("entity_name"), rs.getString("title"),
						rs.getInt("health"), rs.getInt("delay"), rs.getInt("damage"), rs.getInt("speed"), image,
						rs.getInt("costs"));
			} else {
				System.err.println("Keine Spielfigur mit dieser ID gefunden!");
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;

	}
	/**
	 * Diese Methode pusht die aktuellen Spieldaten(Fortschritt, Laufzeit) in die Datenbank
	 * @param xp Fortschrittswert des Spielers im aktuellen Spiel
	 * @param gameTime Aktuelle Laufzeit des Spiels
	 */
	public void pushStats(int xp, String gameTime) {
		try {
			String sql = "INSERT INTO stats (xp,gameTime) VALUES (?,?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, xp);
			pstmt.setString(2, gameTime);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Verbindung zur Datenbank wird beendet
	 * @throws SQLException
	 */
	public void closeConnection() throws SQLException {
		conn.close();
	}

}