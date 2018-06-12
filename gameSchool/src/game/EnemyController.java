package game;

import java.awt.Paint;

import enums.Team;
import hud.Shop;

/**
 * Klasse zur Einstellung des Feindes
 * @author SimonZanon
 *
 */

public class EnemyController extends Thread {

	
	/**
	 * Das Spiel selbst
	 */
	private Game game;
	
	/**
	 * Shop fuer Truppen
	 */
	
	private Shop shop;
	
	/**
	 * Variable fuer ein unplanmaessiges Schliessen
	 */
	
	private boolean shutdown;
	
	/**
	 * Konstruktor fuer den Controller
	 * @param game Spiel
	 */
	
	public EnemyController(Game game) {
		this.game = game;
		shop = new Shop(game, null);
	}

	/**
	 * Methode zur Ausfuehrung des eigentlichen 
	 */
	
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
	
	/**
	 * Methode zum Stoppen des Feindes
	 */

	public void stopController() {
		shutdown = true;
	}

}
