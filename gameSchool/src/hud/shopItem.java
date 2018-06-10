package hud;

import java.io.File;

import interfaces.Renderable;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import sprites.GameFigure;
/**
 * In dieser Klasse befindet sich die Funkntionalitaet zur Verwaltung der Eigenschaften der Spielfiguren und die notwendigen Methoden um die einzelnen Spielfiguren zusammen als Shop(siehe Shop-Klasse) in das Spiel zeichnen zu koennen
 * @author Widerin Alexander
 * @version 1.0
 */
public class shopItem implements Renderable {
	GameFigure figure;
	protected String itemName;
	protected int upgradeCostIncrease;
	protected int upgradeCost;
	protected int damageIncrease;
	private int level;
	protected int price;
	Rectangle2D itemspace;
	Rectangle2D upgradespace;
	Paint paint;
	double xi;
	double yi;
	double widthi;
	double heighti;
	double xu;
	double yu;
	double widthu;
	double heightu;
	/**
	 * 
	 * @param figure GameFigure Objekt
	 * @param itemName Objektname
	 * @param uci Upgrade Cost Increase (Gibt an, wie viel teurer die Upgrades jedes mal werden)
	 * @param uc Upgrade Cost (Basis-upgradekosten (Kosten für 1. Upgrade))
	 * @param di Damage Increase (Schadensoutput-erhoehung der Figure bei Upgrade)
	 * @param level Aktuelles level der Truppe
	 * @param price Aktuelle Kosten der Truppe
	 * @param itemspace Position und Groeße des Shopfensters für die einzelne Figur (=Kaufbutton)
	 * @param upgradespace Position und Groeße des Upgardebuttons fuer die Figur
	 * @param paint Hintergrundfarbe des Objekts im Shop
	 */
	public shopItem(GameFigure figure, String itemName, int uci, int uc, int di, int level, int price,
			Rectangle2D itemspace, Rectangle2D upgradespace, Paint paint) {
		this.figure = figure;
		this.itemName = itemName;
		this.upgradeCostIncrease = uci;
		this.upgradeCost = uc;
		this.damageIncrease = di;
		this.level = level;
		this.price = price;
		this.itemspace = itemspace;
		this.paint = paint;
		this.xi = itemspace.getMinX();
		this.yi = itemspace.getMinY();
		this.widthi = itemspace.getWidth();
		this.heighti = itemspace.getHeight();
		this.upgradespace = upgradespace;
		this.xu = upgradespace.getMinX();
		this.yu = upgradespace.getMinY();
		this.widthu = upgradespace.getWidth();
		this.heightu = upgradespace.getHeight();

	}
	/**
	 * Wird beim Druecken des Upgradebuttons ausgefuehrt
	 */
	public void upgrade() {
		this.level += 1;
		this.calcItemData();
	}
	/**
	 * Berechnet nach einem Update die Eigenschaften der Truppe neu
	 */
	private void calcItemData() {
		this.price += upgradeCost;
		this.upgradeCost += this.upgradeCostIncrease;
		figure.addDamage(damageIncrease);
		damageIncrease += 10;
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

	public Rectangle2D getUpgradespace() {
		return upgradespace;
	}

	@Override
	/**
	 * Diese Methode ist zum Zeichnen des einzelnen Objekts im Shop notwendig
	 * Sie wird fuer alle Objekte in der Shopklasse aufgerufen
	 */
	public void render(GraphicsContext gc) {
		Image imageButton = new Image(new File("res/button_template.png").toURI().toString());
		Image imageUpgrade = new Image(new File("res/button_small.png").toURI().toString());

		// BUTTON BUY
		gc.drawImage(imageButton, xi, yi);
		gc.setFont(new Font("Arial", 14));
		gc.setFill(Paint.valueOf("yellow"));
		gc.fillText(" $" + this.price + " Level " + this.getLevel(), xi + 5, yi + figure.getMainImage().getHeight() + 20);

		// BUTTON UPGRADE
		gc.drawImage(imageUpgrade, xu, yu);
		// gc.setFill(paint);
		// gc.fillText("Upgrade", xu + 5, yu+15 );
		// gc.fillRect(xu, yu, widthu, heightu);
		//TODO: Hier Upgrade-Kosten neben "UPGRADE" Schriftzug einfuegen
		
		
		// gc.fillText(itemName, x + 5, y + height - 5);

		// IMAGE
		gc.drawImage(figure.getMainImage(), xi + (widthi - figure.getMainImage().getWidth()) / 2, yi + 5);
	}

	@Override
	public void update(double elapsedTime) {
	}

	public double getUpgradeCost() {
		return this.upgradeCost;
	}
}