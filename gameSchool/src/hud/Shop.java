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
	ArrayList<shopItem> shopitems;
	private static final double UPGRADE_COST_MULTIPLIER = 0.2;
	private static final double COST_INCREASE_MULTIPLIER = 0.15;
	private static final double DAMAGE_INCREASE_MULTIPLIER = 0.15;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int recx = 100;
		for (GameFigure figure : figures) {
			Rectangle2D rec = new Rectangle2D(recx, 100, 80, 100);
			recx += rec.getWidth() + 10;
			shopitems.add(new shopItem(figure, figure.getEntity_name(),
					(int) ((double) figure.getCosts() * COST_INCREASE_MULTIPLIER),
					(int) ((double) figure.getCosts() * UPGRADE_COST_MULTIPLIER),
					(int) ((double) figure.getDamage() * DAMAGE_INCREASE_MULTIPLIER), 1, figure.getCosts(), rec, p));
		}

		pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				for (int i = 0; i < shopitems.size(); i++) {
					Rectangle2D itemspace = shopitems.get(i).getItemspace();
					
					if(itemspace.contains(new Point2D(event.getSceneX() , event.getSceneY()))) {
						System.out.println("clicked:" + shopitems.get(i).getFigure().getTitle());
						game.spawn(Team.PLAYER , shopitems.get(i).getFigure().clone());
					}
					
				}
			}
		});
	}

	@Override
	public void render(GraphicsContext gc) {
		for (int i = 0; i < shopitems.size(); i++) {
			shopitems.get(i).render(gc);
		}

	}

	@Override
	public void update(double elapsedTime) {

	}

}
