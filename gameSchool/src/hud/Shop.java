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

public class Shop implements Renderable {
	ArrayList<shopItem> shopitems;

	public Shop(Pane pane) throws ClassNotFoundException, SQLException {

		DBManager db = new DBManager("gameSchool/res/database_create.sql");
		//shopitems = db.getAllEntities(); (Bitte noch im Db-Manager ändern)

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
