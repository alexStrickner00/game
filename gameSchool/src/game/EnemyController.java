package game;

import java.awt.Paint;

import enums.Team;
import hud.Shop;

public class EnemyController extends Thread {

	private Game game;
	private Shop shop;
	private boolean shutdown;
	
	public EnemyController(Game game) {
		this.game = game;
		shop = new Shop(game, null);
	}

	@Override
	public void run() {
		while (!game.isFinished() && !shutdown) {

			if (Math.random() < 0.2) {
				game.spawn(Team.ENEMY, shop.getShopItems().get((int) ((Math.random() * shop.getShopItems().size()))));
			}

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void stopController() {
		shutdown = true;
	}

}
