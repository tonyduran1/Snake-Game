package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * Main Snake Class is a Polygon that has a list of segment polygons and acts as the movable main
 * character that collides with other items in the game
 * @author tony
 *
 */
public class Snake extends Polygon implements KeyListener{
	
	private boolean isTurningLeft; 
	private boolean isTurningRight;
	private boolean isTurningUp;
	private boolean isTurningDown;
	private Direction direction;
	private boolean hasItMoved; // this is here to help set the timer and have rules that disappear
	private int amountToMove = 5;
	private ArrayList<Segment> segments = new ArrayList<Segment>();
	
	
	/**
	 * Represents the possible directions that the Snake can move in the game.
	 * This enum is used to control the movement of the Snake.
	 */
	public enum Direction {
		UP,DOWN,LEFT,RIGHT;
	}
	
	

	/**
	 * Constructor that defines the size and shape of the Snake Object that will be controlled
	 * on the display in the game.
	 * @param inShape An array of Point objects creating the shape of the snake. 
	 * @param inPosition  A Point object indicating where the snake is located on the game board.
	 * @param inRotation  The amount of rotation applied to the Polygon in degrees.
	 */
	public Snake(Point[] inShape, Point inPosition, double inRotation) {
		super(inShape, inPosition, inRotation);
		segments = new ArrayList<Segment>(); 
		}
	
	/**
	 * Segment class is a polygon that takes the same shape as the MainSnake
	 * and represents the parts that add on to the snake throughout the game play.
	 * @author tony
	 *
	 */
	class Segment extends Polygon{
		
		/**
		 * Constructor that defines the size and shape of the segments  Object that will be attached 
		 * to the rest of the snake either a segment or the main head. Segments do not determine movement
		 * or collision.
		 * @param inShape   An array of Point objects creating the shape of the segment. 
		 * @param inPosition  A Point object indicating where this segment of the snake is located on the game board.
		 * @param inRotation  The amount of rotation
		 */
		public Segment(Point[] inShape, Point inPosition, double inRotation) {
			super(inShape, inPosition, inRotation);
		}
		
		/**Method thats called and paints the segments when the snake is painted after the
		 * head position is known. Takes the points and creates parameters for polygons out of them
		 * 
		 * @param brush calls the methods to draw the game
		 */
		public void paint(Graphics brush) {
			Point[] segmentPolygon = Segment.this.getPoints();
			
			int[] xPointsSeg = new int[segmentPolygon.length];
			int[] yPointsSeg = new int[segmentPolygon.length];
		     
			for (int j = 0; j < segmentPolygon.length; j++) {
				xPointsSeg[j] = (int) segmentPolygon[j].x;
				yPointsSeg[j] = (int) segmentPolygon[j].y ;
			}
			brush.setColor(Color.green);
			brush.drawPolygon(xPointsSeg, yPointsSeg, segmentPolygon.length); 
			brush.fillPolygon(xPointsSeg, yPointsSeg, segmentPolygon.length);
		}
	}
 
	/**
	 * Method that collects the current points of the main head and then draws it as well as
	 * going through its array of segments and painting them.
	 * Takes the points and creates parameters for polygons out of them
	 * @param brush Calls the methods to draw the game
	 */
	public void paint(Graphics brush) {
		
		Point[] snakePoints = this.getPoints();
		
		int[] xPoints = new int[snakePoints.length];
		int[] yPoints = new int[snakePoints.length];
	     
		for (int i = 0; i < snakePoints.length; i++) {
			xPoints[i] = (int) snakePoints[i].x;
			yPoints[i] = (int) snakePoints[i].y;
		}
		
		brush.setColor(Color.green);
		brush.drawPolygon(xPoints, yPoints, snakePoints.length);
		brush.fillPolygon(xPoints, yPoints, snakePoints.length);
		
		for(int i = 0 ; i < segments.size(); i++) {
		segments.get(i).paint(brush);	 
		}
	}
	 
	
	
	
	/**
	 * Creates a segment object adjusting its position according to the Main Snakes current direction
	 * and adds it to the segments ArrayList.
	 */
	public void addSegment() {
		
		Point newSegment;
		
		if((segments.isEmpty())) { 
			newSegment = new Point(position.x+50 ,position.y+50);
		}	
		else{
			Point lastSegment = segments.get(segments.size()-1).position;
			newSegment = new Point(lastSegment.x,lastSegment.y);
		}
		
		if(direction == Direction.UP) { 
			newSegment.y += 10000;		}
		else if(direction == Direction.DOWN) {
			newSegment.y -= 10000;
		}
		else if(direction == Direction.LEFT) {
			newSegment.x += 10000;
		}
		else if(direction == Direction.RIGHT) {
			newSegment.x -= 10000;
		}  
		
		Point[] newSegmentPoints = { new Point(0,0), new Point(0,30), new Point(30,30), new Point(30,0)};
		// these points make a square that surround the new center we found
		
		Segment segment = new Segment(newSegmentPoints, new Point(10000,10000),0); 
		
		segments.add(segment);
		 
		 
	// i know that the position of the segment that i add to the segments array has no influence on
	// its placment in the paint method but I also am not sure how to fix it and spent over a day on it 
	// so i know this is less efficent and smooth , as well as means my logic on the placement of 
	// position assingment is wrong and i dont know, but the loop that makes the segment the one in front 
	// of it works fine as the snake moves in a snake like fashion. in order to make the snake 
	// grow reasonably because i could not understand how to influence the position of each segment 
	// to place in a spot to where if you call addSegment once it would add a segment as big as the 
	// original head with is 30x30 so it would double after colliding with the first fruit, but mine is 
	// is just no accomplishing that and I am not sure why. definitley my move or addSegment method 
	// and my lack of the conceptual understanding of getPoints maybe

		 
	}
	 
	 
		
		/**
		 * Sets the direction of the snake based on the Main Snakes current instance variables that
		 * are changed by the keys being pressed .
		 */
		public void setDirection() {  // we will use this setter whenever the key is pressed
		 
		if(isTurningUp) {
			if(!(direction ==Direction.DOWN)) { // these are checking if they are already going 
												// in the inverted direction kinda like the OG game
				direction = Direction.UP;
			}
		} 
		if(isTurningRight) {
			if(!(direction ==Direction.LEFT)) {
				direction = Direction.RIGHT;
			}
		}
		if(isTurningLeft) {
			if(!(direction ==Direction.RIGHT)) {
				direction = Direction.LEFT;
			}
		}
		if(isTurningDown) {
			if(!(direction ==Direction.UP)) {
				direction = Direction.DOWN;
			}
		}
	}

		
		/**
		 * Moves the snake head as well as the segments that follows every time it is called
		 * by the amount to move field which is 4. Checks if out of display bounds and wraps 
		 * around screen for the effect of never leaving the screen. 
		 */
	public void move() {
		
		 Point headOldPosition = new Point(position.x, position.y);

		    int width = 800;
		    int height = 600;

		    if (direction == Direction.UP) {
		        position.y -= amountToMove;
		        hasItMoved = true;
		    }
		    if (direction == Direction.DOWN) {
		        position.y += amountToMove;
		        hasItMoved = true;
		    }
		    if (direction == Direction.LEFT) {
		        position.x -= amountToMove;
		        hasItMoved = true;
		    }
		    if (direction == Direction.RIGHT) {
		        position.x += amountToMove;
		        hasItMoved = true;
		    }
		    // basically in this part based off the direction will decide how to move the position and 
		    // in which direction to add them

		   
		    if (position.x < 0) {
		        position.x = width;
		    // if the position of x goes off the screen to the left, then we make the new x the 
		    // total width
		    }
		    if (position.x > width) {
		        position.x = 0;
		    // if the position of x is going off the screen to the right meaning the value is 
		    // bigger than the width ten we will set the x value to the left side of the screen
		    }
		    if (position.y < 0) {
		        position.y = height;
		    } 
		    if (position.y > height) {
		        position.y = 0;
		    }
		     
		    for (int i = 0; i < segments.size(); i++) {
		        Point holder = new Point(segments.get(i).position.x, segments.get(i).position.y);
		        segments.get(i).position = headOldPosition;
		        headOldPosition = holder;
		    }
		    
	 }
 
	/**
	 * Required by the KeyListener interface but not used in this implementation.
	 *
	 * @param e  
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		 
		
	}



	/**
	 * Handles key press events to determine the direction of the snake's movement.
	 * Sets the corresponding direction flag to true when an arrow key is pressed.
	 *
	 * @param e  Event that happens from releasing key
	 */ 
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			isTurningRight = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			isTurningLeft = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			isTurningUp = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			isTurningDown = true;
		}
		
		  
		
		
	}

	/**
	 * Handles key release events to stop the snake's movement in a specific direction.
	 * Resets the corresponding direction flag to false when an arrow key is released.
	 *
	 * @param e  Event that happens from releasing key
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			isTurningRight = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			isTurningLeft = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			isTurningUp = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			isTurningDown = false;
		}
	}
	 

	/**
	 * Returns the list of segments that make up the snake's body.
	 *
	 * @return An ArrayList of Segment objects representing the snake's body.
	 */
	 public ArrayList<Segment> getSegments(){
		 return segments;
	 }
	
	 /**
	 * Indicates whether the snake has moved from its initial position.
	 *  
	 *
	 * @return True if the snake has moved, false otherwise.
	 */
	public boolean getHasItMoved() {
		return hasItMoved;
	}
 
 
	
}

