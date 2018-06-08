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
		cr.readConfigFile();
		uname = cr.getPropertyByName("username");
		pw = cr.getPropertyByName("password");
		address = cr.getPropertyByName("database_address");
		driver = cr.getPropertyByName("database_driver");

		// TODO try and catch statt throw und Dialoge mit Fehlermeldung
		System.out.println(driver);
		Class.forName(driver);
		conn = DriverManager.getConnection(address, uname, pw);

	}

	public ArrayList<GameFigureTableElement> getAllEntities() throws SQLException {
		String sql = "SELECT * from spielfigur";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);

		ArrayList<GameFigureTableElement> list = new ArrayList<>();

		while (rs.next()) {
			Image image;
			image= new Image(new BufferedInputStream(rs.getBinaryStream("image")));
			list.add(new GameFigureTableElement(rs.getInt("entityId"), rs.getString("entity_name"),
					rs.getString("title"), rs.getInt("health"), rs.getInt("delay"), rs.getInt("damage"),
					rs.getInt("speed"),  image, rs.getInt("costs")));
		}
		return list;
	}
	// TODO implementierung der restlichen Methoden

	public void addEmptyEntity() throws SQLException {

		String sql = "INSERT INTO spielfigur(entity_name , title, health, delay , damage, speed, shooting, projectileId,costs) values(?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, "");
		pstmt.setString(2, "");
		pstmt.setInt(3, 0);
		pstmt.setInt(4, 0);
		pstmt.setInt(5, 0);
		pstmt.setInt(6, 0);
		pstmt.setInt(7, 0);
		pstmt.setInt(8, -1);
		pstmt.setInt(9, 0);
		
		pstmt.executeUpdate();
		pstmt.close();
	}

	public void updateElement(GameFigureTableElement element) throws SQLException{
		String sql = "UPDATE spielfigur SET entity_name = ? , title = ? , health = ? , delay = ? , damage = ? , speed = ?  , costs = ? , entityId = ? WHERE entityId = ?";
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
	}

	public void uploadSprite(GameFigureTableElement aktElement, File file) throws SQLException {
		
		String sql = "UPDATE spielfigur SET image = ? WHERE entityId = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(2, aktElement.getEntityId());
		
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedInputStream bis = new BufferedInputStream(fis);
		stmt.setBinaryStream(1, bis);
		
		stmt.executeUpdate();
		stmt.close();
		
	}

	public void addEmptyProjectile() throws SQLException{
		String sql = "INSERT INTO projectile(projectile_name , flyspeed) values(? , ?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, "");
		pstmt.setInt(2, 0);
		
		pstmt.executeUpdate();
		pstmt.close();
	}
}
