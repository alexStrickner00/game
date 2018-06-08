package hud;


import java.util.ArrayList;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import interfaces.Renderable;
import javafx.scene.canvas.GraphicsContext;

public class StatusBar implements Renderable {

	private ArrayList<BarProperty> props;
	
	public StatusBar() {
	props = new ArrayList<>();
	}
	
	//Geld: ca. (75/15) 
	//XP: ca. (195/15)
	//Zeit: ca. (315/15)
	
	
	
	@Override
	public void render(GraphicsContext gc) {
		
	
		Image bar = new Image("res/statusbar_small.png");
	    gc.drawImage( bar, 600, 25);
	    

		
		
		for (BarProperty b : props) {
			
		    gc.setFill( Color.BLACK );
		    Font theFont = Font.font( "Verdana", FontWeight.BOLD, 48 );
		    gc.setFont( theFont );
		    gc.fillText( b.getValue(), b.getX(), b.getY() );
		    gc.strokeText( b.getValue(), b.getX(), b.getY() );
			
			
			// TODO: Implementierung des Leistenrenderings, hier sollen alle Properties im
			// Format: *NAME*: *VALUE* dargestellt werden
		}
	}
	


	@Override
	public void update(double elapsedTime) {
		//TODO: wahrscheinlich leer
	}

	public void addProperty(BarProperty b) {
		this.props.add(b);
	}

}
