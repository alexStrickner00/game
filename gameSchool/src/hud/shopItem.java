package hud;

import sprites.GameFigure;

public class shopItem {
	
	protected int upgradeCostIncrease;
	protected int upgradeCost;
	protected int damageIncrease;
	protected int level;
	protected int price;
	GameFigure figure;
	public shopItem(GameFigure figure, int uci, int uc,int di, int level,int price) {
		this.figure=figure;
		this.upgradeCostIncrease=uci;
		this.upgradeCost=uc;
		this.damageIncrease=di;
		this.level=level;
		this.price=price;
	}
	public void upgrade() {
		level++;
		this.calcItemData();
	}
	private void calcItemData() {
		this.upgradeCost+=this.upgradeCostIncrease;
		figure.addDamage(damageIncrease);
		damageIncrease-=10;
	}
}