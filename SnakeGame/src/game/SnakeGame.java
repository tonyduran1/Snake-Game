package game;

/*
CLASS: YourGameNameoids
DESCRIPTION: Extending Game, YourGameName is all in the paint method.
NOTE: This class is the metaphorical "main method" of your program,
      it is your control center.

*/
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import javax.swing.Timer;
 
/**This is a Snake Game class that displays a functional game that responds to keyboard input. The
 * goal of the game is to collect as many items in thirty seconds as possible. Depending on the
 * Item Collided to determines how to increment that current trials score. The only way the game ends is
 * if the game runs out of time. The player uses arrow keys to navigate the main snake character throughout
 * the display
 * 
 * @author Tony
 * @version 3/3/2024
 */
public class SnakeGame extends Game {
	
 
	private Snake snake;
	private Apple apple; 
	private Star star;
	private boolean isGameOver = false;; 
	
	private int amountOfTime = 30;
	private Timer timer;
	 
	private int totalScore;
	

	


	
	/** Constructor initializes its fields such as the snake, apple, star, and sets the timer up for
	 * thirty seconds. This class sets the window for the display size of the game as well.  
	 * 
	 */
	public SnakeGame() {
    super("Snake Game",800,600);
    this.setFocusable(true);  
	this.requestFocus();  
	
	timer = new Timer(1000, new ActionListener() { 
		public void actionPerformed(ActionEvent e) {
			amountOfTime--;
			if(amountOfTime<0) {
				timer.stop();
				isGameOver = true;
			} 
			
		}
		
	});
	 
	
	Point[] snakePoints = {new Point(0,0), new Point(0,30), new Point(30,30), new Point(30,0)};
	snake = new Snake(snakePoints,new Point(400,300),0 );
	
	Point[] applePoints = {new Point(0,0), new Point(0,-1), new Point(1,-1),new Point(1,0)};
	// i gave the apple these points so that you can use it w collide
	apple = new Apple(applePoints, new Point(200,440),0);
	
	this.addKeyListener(snake);
	
	Point[] starPoints = { new Point(0, -15),new Point(4, -5),new Point(15, -5),new Point(6, 4),new Point(9, 15),
						   new Point(0, 10),new Point(-9, 15),new Point(-6, 4),new Point(-15, -5),new Point(-4, -5)};
	star = new Star(starPoints,new Point(-100, -100),0);
	
  }
  
  	 
  
  	
 
  	
  	/** Paints all the elements that a Snake Game has. A game has a main snake , and items
  	 * such as stars and apples that can be collided with for points . This is the method that displays
  	 * everything , as well as the rules and end score. everything that appears on the board goes through
  	 * this method
  	 * 
  	 * @param brush calls the methods to draw the game
  	 * 
  	 */
	public void paint(Graphics brush) {
		
		 
		brush.setColor(Color.gray);
    	brush.fillRect(0,0,width,height);
    	brush.setColor(Color.black);
    	
    	if(amountOfTime<0) {
    		amountOfTime =0;
    	}
    	 
    	brush.drawString("Time Left: " + amountOfTime, 10,550);
    	if(!snake.getHasItMoved()) { 
    	brush.drawString("ATTENTION ! HERE ARE THE RULES : ", 25,65);
    	brush.drawString("*Eat as many things in 30 Seconds as You can! ", 35,85);
    	brush.drawString("*Better hope luck is on your side , and that the stars are out!!", 35,105);
    	brush.drawString("*Apples add 3 points to your score while stars give 5. ", 35,125);
    	brush.drawString("*This also corresponds to the snakes length", 35,145);
    	brush.drawString("*Use the boundaries strategically for quicker pathing ", 35,165);
    	brush.drawString("*Although there is no penalty, avoid intersecting body as an extra challenge :-)", 35,185);
		}
		if(!isGameOver) {
    	if(snake.getHasItMoved()) {
    		timer.start();  // this will set the time if true .start is cueing the actionlistner 
    		// and the one method carries out as often as i set in the constructor
    	} 
    	snake.setDirection();
    	snake.move();
    	snake.paint(brush);
    	checkAndInitialize();
    	apple.paint(brush);
    	star.paint(brush);
    	star.rotate(4);
    	
    	 
		} else {
		//stuff here will happen when the game is over
		brush.drawString("GAME OVER!", 352, 280);
		if(totalScore < 50) {
		brush.drawString("Total Score: " + totalScore, 350,300);
		brush.drawString("SMH DO BETTER LOL" , 345,320);
		}
		if(totalScore >= 50 && totalScore < 120) {
		brush.drawString("Total Score: " + totalScore, 350,300);
		brush.drawString("OKAY GOOD JOB !"  , 345,320);
		}
		if(totalScore >= 120) {
		brush.drawString("Total Score: " + totalScore, 350,300);
		brush.drawString("YEAH YOURE COMP" , 345,320);
		}
	}
  }
	/**Returns an integer value of the width 
	 * 
	 * @return width of game display
	 */
	public int getBoardWidth() {
		return this.width;
	}
	/**Returns an integer value of the height
	 * 
	 * @return height of game display
	 */
	public int getBoardHeight() {
		return this.height;
	}
	
	
	

	/** Method that respawns either a apple or star at a random spot on the board when touching
	 *  as well as increase the score according to item the snake collides into. There is only one item
	 *  on the board at a time this method randomly spawns either an apple or star and the other 
	 *  one goes invisible 
	 * 
	 */
	public void checkAndInitialize() {
		//would be a private method if not for needing to write javadocs.
		Random random = new Random();
		int number = random.nextInt(100);
		
		
		if(snake.collides(apple)|| snake.collides(apple.getStem())){
			
			totalScore += 3;
			if(number<65) {
				apple.appearItem(width,height,snake);
				snake.addSegment();
				snake.addSegment();
				snake.addSegment(); 
				 
			}
			if(number>=65) {
				// since it collided with an apple that means the visibility of the apple was on .
				// we want to turn it off because this 'if' is the 35% chance that it would be a star.
				// after colliding with an apple and placing a the apple and star paint methods do not 
				// run unless their visibility is true
				// the reason for the if statement right above is because there was just an apple and 
				// the random number was the chance of another apple spawning. same logic below for when
				// the snake collides into a star there is less implementation if the random number
				// lands in the range of another star.
				// JUST BECAUSE YOU DONT PAINT IT DOES NOT MEAN COLLIDES WONT DECTECT IT 
				// hence why i changed the positions to be off the board
				apple.position = new Point(-100,-100);
				apple.setVisibility(false);
				star.setVisibility(true);
				 
				star.appearItem(width, height, snake);
				snake.addSegment();
				snake.addSegment();
				snake.addSegment();
				  
			}
		} if(snake.collides(star )) {
			totalScore += 5;
			if(number<65) {
				star.position = new Point(-100,100);
				star.setVisibility(false);
				apple.setVisibility(true);
				apple.appearItem(width,height,snake);
				snake.addSegment();
				snake.addSegment();
				snake.addSegment();
				snake.addSegment();
				snake.addSegment(); 
			}
			if(number>=65) { 
				 
				star.appearItem(width, height, snake);
				snake.addSegment();
				snake.addSegment();
				snake.addSegment();
				snake.addSegment();
				snake.addSegment();	 
			}
		}
		// if you collide with a star it adds 40% more
}
	 
  
	/**
	 * Initiate the game
	 * @param args
	 */
	public static void main (String[] args) {
   		SnakeGame a = new SnakeGame();
		a.repaint();
  }
}