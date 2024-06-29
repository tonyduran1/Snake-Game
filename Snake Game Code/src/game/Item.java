package game;

import java.awt.Graphics;

/**
 * Represents a game item that can be displayed and interacted with within the game.
 * Items are elements like stars or apples that the snake can collide with.
 */
public interface Item  {
	
	/** Draws the item on the game board.
    * 
    * @param brush The graphics context used for drawing the item.
    */
	public void paint(Graphics brush);  
	
	
	/**Rotates the Item
	 *  
	 * @param degrees The angle you want to rotate every tenths of a second
	 */
	public void rotate(int degrees);  
	
	
	 /** Positions the item at a new location within the game area,
	  *  ensuring it does not touch the snake.
	  * 
	  * @param width The width of the game area.
	  * @param height The height of the game area.
	  * @param snake The Snake object to avoid overlap with.
	  */
	public void appearItem(int width, int height, Snake snake);
	

}
