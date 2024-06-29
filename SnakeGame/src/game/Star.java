package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/** Represents a Star item in the game that can be collected/collided by the Snake to increase the player's
*   score. This class extends the Polygon class and implements the Item interface.
*/
public class Star extends Polygon implements Item{
	
	private boolean visibility;


   /**
    * Constructs a Star object with a specific shape, position, and rotation. The visibility of the
    * star is initially set to true, allowing it to be drawn on the game board.
    * 
    * @param inShape An array of Point objects creating the shape of the star.
    * @param inPosition The position of the star, represented as a Point object.
    * @param inRotation The rotation of the star.
    */
	public Star(Point[] inShape, Point inPosition, double inRotation) {
		super(inShape, inPosition, inRotation);
	}
 
	/**
    * Draws the star if it is visible.The method uses the star's points to
    * create parameters to construct a polygon.
    * 
    * @param brush The graphics context used for drawing the star.
    */
	@Override 
	public void paint(Graphics brush) {

		if(visibility) { 
		Point [] starPoints = this.getPoints();	// this is going to get you the points of the stars 
		
		int[] xPoints = new int[starPoints.length];
        int[] yPoints = new int[starPoints.length];
        
        for (int i = 0; i < starPoints.length; i++) {
            xPoints[i] = (int) starPoints[i].x;
            yPoints[i] = (int) starPoints[i].y;
        }
    
        brush.setColor(Color.yellow);
        brush.drawPolygon(xPoints, yPoints, starPoints.length);
        brush.fillPolygon(xPoints, yPoints, starPoints.length);// Draw the filled polygon/star.
		}
    }
	
	/**
	* Places the star at a new random location within the game area making sure it does not 
	* spawn inside the snake.
	* @param width The width of the game area.
	* @param height The height of the game area.
	* @param snake The Snake object to check for collisions.
	*/
	public void appearItem(int width, int height, Snake snake) {
		Random random = new Random();
		 
		int randomXValue = random.nextInt(width -50);
		int randomYValue = random.nextInt(height -50);
		
		Point newRandomPoints = new Point(randomXValue,randomYValue);
		this.position = newRandomPoints;
		if(snake.contains(newRandomPoints)) {
			appearItem(width,height,snake);
		}
		position = newRandomPoints;
		
	}
	

	/** Sets the visibility of the star.
    * 
    * @param b Boolean that determines visibility of star. True is visible
    */
	public void setVisibility(Boolean b ) {
		visibility = b;
	}
	
	/**
	* Checks the visibility of the star.
	* 
	* @return Boolean true or false on whether its visible.
	*/
	public boolean getVisibility( ) {
		return visibility ;
	}	
	

}
