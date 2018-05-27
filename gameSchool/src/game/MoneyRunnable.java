package game;

import enums.Team;

public class MoneyRunnable implements Runnable {

	private static final double MONEY_PER_SECOND = 10;
	private static final double DIFF_MULTYPLIER = 0.2;

	private Game game;

	public MoneyRunnable(Game game) {
		this.game = game;
	}

	@Override
	public void run() {
		while (!game.isFinished()) {
			game.addMoney(Team.PLAYER, getMoneyFromDifficulty(Team.PLAYER));
			game.addMoney(Team.ENEMY, getMoneyFromDifficulty(Team.ENEMY));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private double getMoneyFromDifficulty(Team player) {

		if (player == Team.PLAYER) {
			return MONEY_PER_SECOND * Math.pow((1.0 - DIFF_MULTYPLIER), game.getDifficulty());
		}
		return MONEY_PER_SECOND;
	}

}
