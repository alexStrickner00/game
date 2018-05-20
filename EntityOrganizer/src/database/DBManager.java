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
import sprite.ProjectileTableElement;

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
					rs.getInt("speed"), rs.getInt("shooting"), rs.getInt("projectileId"), image, rs.getInt("costs")));
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
		String sql = "UPDATE spielfigur SET entity_name = ? , title = ? , health = ? , delay = ? , damage = ? , speed = ? , shooting = ? , projectileId = ? , costs = ? , entityId = ? WHERE entityId = ?";
		PreparedStatement stmt =  conn.prepareStatement(sql);
		stmt.setString(1, element.getName());
		stmt.setString(2, element.getTitle());
		stmt.setInt(3, element.getHealth());
		stmt.setInt(4, element.getDelay());
		stmt.setInt(5, element.getDamage());
		stmt.setInt(6, element.getSpeed());
		stmt.setShort(7 , (short)element.getShooting());
		stmt.setInt(8, element.getProjId());
		stmt.setInt(9, element.getCosts());
		stmt.setInt(10, element.getEntityId());
		stmt.setInt(11, element.getOldId());
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

	public ArrayList<ProjectileTableElement> getAllProjectiles() throws SQLException{
		ArrayList<ProjectileTableElement> list = new ArrayList<>();
		
		String sql = "SELECT * FROM projectile";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		
		while(rs.next()) {
			
			Image image;
			
			BufferedInputStream bis = new BufferedInputStream(rs.getBinaryStream("image"));
			image = new Image(bis);
			
			list.add(new ProjectileTableElement(rs.getInt("projectileId"), rs.getString("projectile_name"), rs.getInt("flyspeed"), image));
		}
		
		stmt.close();
		return list;
	}

	public void addEmptyProjectile() throws SQLException{
		String sql = "INSERT INTO projectile(projectile_name , flyspeed) values(? , ?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, "");
		pstmt.setInt(2, 0);
		
		pstmt.executeUpdate();
		pstmt.close();
	}

	public void uploadProjectileSprite(ProjectileTableElement element ,File file) throws SQLException, FileNotFoundException{
		String sql = "UPDATE projectile SET image = ? WHERE projectileId=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(2, element.getProjectileId());
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream bis = new BufferedInputStream(fis);
		
		pstmt.setBinaryStream(1, bis);
		
		pstmt.executeUpdate();
		pstmt.close();
		
	}
	
	public void updateProjectile(ProjectileTableElement element) throws SQLException{
		String sql = "UPDATE projectile SET projectileId = ? , projectile_name = ? , flyspeed = ? WHERE projectileId = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, element.getProjectileId());
		pstmt.setString(2, element.getName());
		pstmt.setInt(3, element.getFlyspeed());
		pstmt.setInt(4, element.getOldProjectileId());
		
		pstmt.executeUpdate();
		pstmt.close();
		
	}

	public void deleteProjectile(ProjectileTableElement aktElement) throws SQLException{
		String sql = "DELETE FROM projectile WHERE projectileId = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, aktElement.getProjectileId());
		
		stmt.executeUpdate();
		stmt.close();
	}
	
}
