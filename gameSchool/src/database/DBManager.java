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

public class DBManager {
	private String uname;
	private String pw;
	private String address;
	private String driver;
	private Connection conn;
	
	public DBManager(String filePath) throws ClassNotFoundException, SQLException {
		
		ConfigReader cr = new ConfigReader(filePath);
		cr.readConfigFile();
		uname = cr.getPropertyByName("username");
		pw = cr.getPropertyByName("password");
		address = cr.getPropertyByName("database_address");
		driver = cr.getPropertyByName("database_driver");
		
		//TODO try and catch statt throw und Dialoge mit Fehlermeldung
		System.out.println(driver);
		Class.forName(driver);
		conn = DriverManager.getConnection(address, uname, pw);
		
	}
	
	
	public ArrayList<GameFigure> getAllEntities() throws SQLException{
		String sql = "SELECT * from spielfigur";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		
		ArrayList<GameFigure> list = new ArrayList<>();
		
		while(rs.next()) {
			Image image;
			
			BufferedInputStream bim = new BufferedInputStream(rs.getBinaryStream("image"));
			image = new Image(bim);
			
			list.add(new GameFigure(rs.getInt("entityId"), rs.getString("entity_name"), rs.getString("title"), rs.getInt("health"), rs.getInt("delay"), rs.getInt("damage"), rs.getInt("speed"), rs.getInt("shooting"), rs.getInt("projectileId"), image, rs.getInt("costs")));
		}
		return list;
	}
	public GameFigure getGameFigureById(int entityId) throws SQLException {
		String sql="SELECT * FROM spielfigure WHERE entityId=?";
		PreparedStatement pstmt= conn.prepareStatement(sql);
		pstmt.setInt(1, 0);
		ResultSet rs= pstmt.executeQuery();
		Image image;
		
		BufferedInputStream bim = new BufferedInputStream(rs.getBinaryStream("image"));
		image = new Image(bim);
		return new GameFigure(rs.getInt("entityId"), rs.getString("entity_name"), rs.getString("title"), rs.getInt("health"), rs.getInt("delay"), rs.getInt("damage"), rs.getInt("speed"), rs.getInt("shooting"), rs.getInt("projectileId"), image, rs.getInt("costs"));
	}
	public void closeConnection() throws SQLException {
		conn.close();
	}
	
}