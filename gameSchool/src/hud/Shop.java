package hud;

import java.sql.SQLException;
import java.util.ArrayList;

import database.DBManager;
import enums.Team;
import game.Game;
import interfaces.Renderable;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import sprites.GameFigure;

public class Shop implements Renderable {
	private static ArrayList<shopItem> shopitems;
	private static final double UPGRADE_COST_MULTIPLIER = 0.2;
	private static final double COST_INCREASE_MULTIPLIER = 0.15;
	private static final double DAMAGE_INCREASE_MULTIPLIER = 0.5;
	private Pane pane;
	private Game game;

	public Shop(Game game, Paint p) {

		shopitems = new ArrayList<>();
		this.game = game;
		this.pane = game.getPane();

		DBManager db;
		ArrayList<GameFigure> figures = null;
		try {
			db = new DBManager("res/databaseConnection.conf");
			figures = db.getAllEntities();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int recx = 100;
		for (GameFigure figure : figures) {
			Rectangle2D rec = new Rectangle2D(recx, pane.getHeight() - 120, 100, 100);
			Rectangle2D rec2 = new Rectangle2D(recx, pane.getHeight() - 20, 100, 20);
			recx += rec.getWidth() + 10;
			shopitems.add(new shopItem(figure, figure.getEntity_name(),
					(int) ((double) figure.getCosts() * COST_INCREASE_MULTIPLIER),
					(int) ((double) figure.getCosts() * UPGRADE_COST_MULTIPLIER),
					(int) ((double) figure.getDamage() * DAMAGE_INCREASE_MULTIPLIER), 1, figure.getCosts(), rec, rec2,
					p));
		}

		pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				for (shopItem s : shopitems) {
					Rectangle2D itemspace = s.getItemspace();
					Rectangle2D upgradespace = s.getUpgradespace();
					if (itemspace.contains(new Point2D(event.getSceneX(), event.getSceneY()))) {
						game.spawn(Team.PLAYER, s);
					}
					if (upgradespace.contains(new Point2D(event.getSceneX(), event.getSceneY()))) {
						if (game.upgradeItem(Team.PLAYER, s))
							s.upgrade();
						//System.out.println("upgraded:" + s.getLevel());
					}
				}
			}
		});
	}

	@Override
	public void render(GraphicsContext gc) {
		for (shopItem s : shopitems) {
			s.render(gc);
		}
	}

	@Override
	public void update(double elapsedTime) {

	}

	public ArrayList<shopItem> getShopItems() {
		return shopitems;
	}

}
