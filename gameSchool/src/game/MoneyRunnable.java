package game;

import enums.Team;

/**
 * Klasse um das Geld beim Spieler mit der Zeit zu erhoehen
 * @author SimonZanon
 *
 */

public class MoneyRunnable implements Runnable {

	/**
	 * Variable wie viel Geld pro Sekunde der Spieler bekommt
	 */
	
	private static final double MONEY_PER_SECOND = 5;
	
	/**
	 * Wert um die Geldmenge je nach Schwierigkeitsgrad anzupassen
	 */
	
	private static final double DIFF_MULTYPLIER = 0.05;
	
	/**
	 * Variable um das Updaten der Geldmenge zu stoppen bei Schliessen des Spiels 
	 */
	
	private boolean shutdown;
	
	/**
	 * Spiel-Objekt
	 */
	
	private Game game;

	/**
	 * Konstruktor fuer die Klasse
	 * @param game Spiel
	 */
	
	public MoneyRunnable(Game game) {
		this.game = game;
	}
	
	/**
	 * Methode um die Gelderhoehung auszufuehren
	 */

	@Override
	public void run() {
		while (!game.isFinished() && !shutdown) {
			game.addMoney(Team.PLAYER, getMoneyFromDifficulty(Team.PLAYER));
			game.addMoney(Team.ENEMY, getMoneyFromDifficulty(Team.ENEMY));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Gibt das Geld je nach Schwierigkeitsgrad zurueck
	 * @param player Spieler
	 * @return Geld
	 */

	private double getMoneyFromDifficulty(Team player) {

		if (player == Team.PLAYER) {
			return MONEY_PER_SECOND;
		}
		return MONEY_PER_SECOND * Math.pow((1.0 - DIFF_MULTYPLIER), game.getDifficulty());
	}
	
	/**
	 * Methode um die Gelderhoehung bzw. den Thread zu stoppen
	 */

	public void stopRunnable() {
		shutdown = true;
	}

}
