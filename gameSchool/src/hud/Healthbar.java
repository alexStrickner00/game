package hud;



import interfaces.*;
import sprites.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Healthbar implements Renderable{

	
	private GameFigure parent;
	private int whiteX;
	private int y = 20;
	private int redX;
	public static final int HEIGHT = 3;

	private double maxHealth;
	public static final double HEALTHBAR = 50;
	private double health;
	private double whiteLength;
	private double redLength;
	
	
	
	
	
	
	public Healthbar(GameFigure parent) {
		
		this.parent = parent;
		this.maxHealth = parent.getHealth();
		
	}
	

	@Override
	public void render(GraphicsContext gc) {
		
	health = parent.getHealth() / maxHealth;
	redLength = (int)(HEALTHBAR * health);
	whiteLength = HEALTHBAR - redLength;
	
	int offset = 10;
	
	//Um das Spiegeln der Sprites zu beruecksichtigen
	if(parent.getSpeed() < 0) {
		offset -= parent.getBoundaries().getWidth();
	}
	
	gc.setFill(Color.RED);
	gc.fillRect(parent.getX() + offset, parent.getY() - y, redLength, HEIGHT);
	gc.setFill(Color.WHITE);
	gc.fillRect(parent.getX() + offset + redLength, parent.getY() - y, whiteLength, HEIGHT);
	
    
		
		
    	
	}
	

	@Override
	public void update(double elapsedTime) {
		// TODO Auto-generated method stub
		
	}

}
