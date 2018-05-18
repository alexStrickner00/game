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
	public static final int HEIGHT = 10;

	private int maxHealth;
	public static final int HEALTHBAR = 60;
	private double health;
	private int whiteLength;
	private int redLength;
	
	
	
	
	
	
	public Healthbar(GameFigure parent) {
		
		this.parent = parent;
		this.maxHealth = parent.getHealth();
		
	}
	

	@Override
	public void render(GraphicsContext gc) {
		
	health = parent.getHealth() / maxHealth;
	redLength = (int)(HEALTHBAR * health);
	whiteLength = HEALTHBAR - redLength;
	
	
	
	gc.setFill(Color.RED);
	gc.fillRect(parent.getX() + 10, parent.getY() - y, redLength, HEIGHT);
	gc.setFill(Color.WHITE);
	gc.fillRect(parent.getX() + 10 + redLength, parent.getY() - y, whiteLength, HEIGHT);
	
    
		
		
    	
	}

	@Override
	public void update(double elapsedTime) {
		// TODO Auto-generated method stub
		
	}

}
