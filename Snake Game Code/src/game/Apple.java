package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/**Represents an Apple item in the game that can be collided with by the Snake to increase the player's
 * score.This class extends the Polygon class and implements the Item interface.
 *  
 * @author tony
 *
 */
public class Apple extends Polygon implements Item{
	
	private boolean visibility = true;
	private Stem theStem;

	/**
     * Constructs an Apple object with a specific shape, position, and rotation. Also initializes the stem of the apple.
     * 
     * @param inShape An array of Point objects creating the shape of the Apple.
     * @param inPosition The position of the apple, represented as a Point object.
     * @param inRotation The rotation of the apple.
     */
	public Apple(Point[] inShape, Point inPosition, double inRotation) {
		super(inShape, inPosition, inRotation);
		Point[] stemPoints = {new Point(0,0), new Point (+7.5,-10), new Point(10,-7), new Point(0,0)};
		theStem = new Stem(stemPoints, new Point(position.x+2,position.y-13),0);
	}
	
	
	 /**
     * The Stem class represents the stem of an Apple. It is a Polygon that is only
     *  used within the Apple class.
     */
	class Stem extends Polygon{ // this is a class that is only needed in the apple class in the game

		/**
         * Constructs a Stem object with a specific shape, position, and rotation.
         * 
         * @param inShape The shape of the stem, defined by an array of Point objects.
         * @param inPosition The position of the stem, represented as a Point object.
         * @param inRotation The rotation angle of the stem, in degrees.
         */
		public Stem(Point[] inShape, Point inPosition, double inRotation) {
			super(inShape, inPosition, inRotation);
		}
		
		/**
		 * Method that collects the current points of the stem and then draws it.
		 * Takes the points and creates parameters for polygons out of them.
		 * @param brush Calls the methods to draw the game
		 */
		public void paint(Graphics brush) { 
			// in this class i am making a four point stem thats going to go in the spot 
			// according to the apple oval  
			Point[] stemPoints =  theStem.getPoints(); 
			/* getPoints does not recalculate its positions based off where the apple is  
			 */
			int[] xPoints = new int[stemPoints.length];
			int[] yPoints = new int[stemPoints.length];
		     
			for (int i = 0; i < stemPoints.length; i++) {
				xPoints[i] = (int) stemPoints[i].x;
				yPoints[i] = (int) stemPoints[i].y;
			}
			brush.setColor(Color.BLACK);
			brush.drawPolygon(xPoints,yPoints,stemPoints.length);
			brush.fillPolygon(xPoints,yPoints,stemPoints.length);
		}
	}
	
	 
	/**
	 * Method that collects the current points of the apple and then draws it if it is visible.
	 * Calls the paint method for the stem.Takes the points and creates parameters for polygons out of them.
	 * @param brush Calls the methods to draw the game
	 */
	public void paint(Graphics brush) {
		
		if(visibility) { 
		// drawOval has the parameters : 
		brush.setColor(Color.red);
		brush.drawOval((int)(position.x -7.5),(int)(position.y -7.5),15,15); // accounting for the top left
		brush.fillOval((int)(position.x -7.5),(int)(position.y -7.5),15,15);
		brush.setColor(Color.black);
		
		theStem.paint(brush); 
		}
		 
		// this should be making the oval at the same spot as the position it is given in the 
		// constructor
		
		// because the position is going to be the top left
	}
	
	/**
    * Places the apple at a new random location within the game area making sure it does not 
    * spawn inside the snake.
    * @param width The width of the game area.
    * @param height The height of the game area.
    * @param snake The Snake object to check for collisions.
    */
	public void appearItem(int width, int height, Snake snake ) {
		Random random = new Random();
		 
		int randomXValue = random.nextInt(width -70);
		int randomYValue = random.nextInt(height -50);
		
		Point newRandomPoints = new Point(randomXValue,randomYValue);
		 
		this.position = newRandomPoints;
		
		if(snake.collides(this)) {
			appearItem(width,height,snake);
		}
		
		position = newRandomPoints;
		
		theStem.position = new Point(position.x + 2, position.y - 13);
	}
	
	/**Gets the stem of the apple.
    * 
    * @return stem of the apple.
    */
	public Stem getStem() {
		return theStem;
	}
	
	/** Sets the visibility of the apple.
    * 
    * @param b Boolean that determines visibility of apple. True is visible
    */
	public void setVisibility(Boolean b) {
		visibility = b;
	}
	
 /**
    * Checks the visibility of the apple.
    * 
    * @return Boolean true or false on whether its visible.
    */
	public boolean getVisibility( ) {
		return visibility ;
	}
	 
}
