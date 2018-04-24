package sprites;

import enums.Team;

public class Castle extends Sprite{

	private double health;
	private int level;
	private Team team;
	
	public Castle(Team player) {
		this(player, 1, 100);
	}
	
	public Castle(Team team, int level , double health) {
		this.team = team;
		this.level = level;
		this.health = health;
	}

}
