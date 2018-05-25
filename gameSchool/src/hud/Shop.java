package hud;

import java.sql.SQLException;
import java.util.ArrayList;

import database.DBManager;
import interfaces.Renderable;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import sprites.GameFigure;

public class Shop implements Renderable {
	ArrayList<shopItem> shopitems;
	private static final double UPGRADE_COST_MULTIPLIER=0.2;
	private static final double COST_INCREASE_MULTIPLIER=0.15;
	private static final double DAMAGE_INCREASE_MULTIPLIER=0.15;
	public Shop(Pane pane, Paint p) throws ClassNotFoundException, SQLException {

		DBManager db = new DBManager("gameSchool/res/database_create.sql");
		ArrayList<GameFigure> figures= db.getAllEntities();
		int recx=0;
		for (GameFigure figure : figures) {
			Rectangle2D rec=new Rectangle2D(recx, 0, 0, 0);
			recx+=rec.getWidth()+10;
			shopitems.add(new shopItem(figure,figure.getEntity_name(),(int)((double)figure.getCosts()*COST_INCREASE_MULTIPLIER),(int)((double)figure.getCosts()*UPGRADE_COST_MULTIPLIER),(int)((double)figure.getDamage()*DAMAGE_INCREASE_MULTIPLIER),1,figure.getCosts(),rec,p));
		}

		pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				for (int i = 0; i < shopitems.size(); i++) {
					Rectangle2D itemspace = shopitems.get(i).getItemspace();
					if ((itemspace.getMaxX() - event.getX()) > 0 && (itemspace.getMinX() - event.getX()) < 0
							&& (itemspace.getMaxY() - event.getY()) > 0 && (itemspace.getMinY() - event.getY()) < 0) {

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
		this.drawShop();

	}

	public void drawShop() {

	}

}
