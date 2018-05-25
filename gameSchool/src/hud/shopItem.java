package hud;



import interfaces.Renderable;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import sprites.GameFigure;

public class shopItem implements Renderable {
	GameFigure figure;
	protected String itemName;
	protected int upgradeCostIncrease;
	protected int upgradeCost;
	protected int damageIncrease;
	protected int level;
	protected int price;
	Rectangle2D itemspace;
	Paint paint;
	double x;
	double y;
	double width;
	double height;
	public shopItem(GameFigure figure, String itemName, int uci, int uc, int di, int level, int price,
			Rectangle2D itemspace, Paint paint) {
		this.figure = figure;
		this.itemName = itemName;
		this.upgradeCostIncrease = uci;
		this.upgradeCost = uc;
		this.damageIncrease = di;
		this.level = level;
		this.price = price;
		this.itemspace = itemspace;
		this.paint = paint;
		this.x = itemspace.getMinX();
		this.y = itemspace.getMinY();
		this.width = itemspace.getWidth();
		this.height = itemspace.getHeight();
	}

	public void upgrade() {
		level++;
		this.calcItemData();
	}

	private void calcItemData() {
		this.upgradeCost += this.upgradeCostIncrease;
		figure.addDamage(damageIncrease);
		damageIncrease -= 10;
	}

	public int getUpgradeCostIncrease() {
		return upgradeCostIncrease;
	}

	public int getUpgradeCost() {
		return upgradeCost;
	}

	public int getDamageIncrease() {
		return damageIncrease;
	}

	public int getLevel() {
		return level;
	}

	public int getPrice() {
		return price;
	}

	public GameFigure getFigure() {
		return figure;
	}

	public Rectangle2D getItemspace() {
		return itemspace;
	}

	public void setItemspace(Rectangle2D itemspace) {
		this.itemspace = itemspace;
	}

	@Override
	public void render(GraphicsContext gc) {
		gc.setFill(paint);
		gc.fillRect(x, y, width, height);
		gc.setFill(Paint.valueOf("yellow"));
		gc.setFont(new Font("Arial",14));
		gc.fillText("$" + price, x + 5, y + figure.getMainImage().getHeight()+20);
		
		//gc.fillText(itemName, x + 5, y + height - 5);
		//gc.fillText(itemName, x + 5, y + height - 5);
		gc.drawImage(figure.getMainImage(), x + (width - figure.getMainImage().getWidth()) / 2, y + 5);
	}

	@Override
	public void update(double elapsedTime) {

	}
}