package database;

import java.io.BufferedInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import configuration.ConfigReader;
import javafx.scene.image.Image;
import sprite.GameFigureTableElement;

public class DBManager {

	private String uname;
	private String pw;
	private String address;
	private String driver;
	private Connection conn;
	
	public DBManager(String filePath) throws ClassNotFoundException, SQLException {
		
		ConfigReader cr = new ConfigReader(filePath);
		
		uname = cr.getPropertyByName("username");
		pw = cr.getPropertyByName("password");
		address = cr.getPropertyByName("database_address");
		driver = cr.getPropertyByName("database_driver");
		
		//TODO try and catch statt throw und Dialoge mit Fehlermeldung
		
		Class.forName(driver);
		conn = DriverManager.getConnection(address, uname, pw);
		
	}
	
	public ArrayList<GameFigureTableElement> getAllEntities() throws SQLException{
		String sql = "SELECT * from spielfigur";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		
		ArrayList<GameFigureTableElement> list = new ArrayList<>();
		
		while(rs.next()) {
			Image image;
			
			BufferedInputStream bim = new BufferedInputStream(rs.getBinaryStream("image"));
			image = new Image(bim);
			
			list.add(new GameFigureTableElement(rs.getInt("entityId"), rs.getString("entity_name"), rs.getString("title"), rs.getInt("health"), rs.getInt("delay"), rs.getInt("damage"), rs.getInt("speed"), rs.getInt("shooting"), rs.getInt("projectileId"), image));
		}
		return list;
	}
	
}
