package fr.umlv.BabaIsYou;

import fr.umlv.zen5.ApplicationContext;

/**
 * Interface that represents the elements of the game
 * 
 * @author Ghamri Samy and Osaj Xhavit
 *
 */
public interface Words {
	
	/**
	 * Getter of the value of the field representing the name of the item.
	 * 
	 * @return a string representing the field representing the name of the item.
	 */
	public String getW();

	/**
	 * Getter of the value of the field representing the position.
	 * 
	 * @return the value of the field representing the position
	 */
	public Position getPos();
	
	/**
	 * Function checking if the Words can be a property.
	 * 
	 * @return true if it can be a property or false otherwise
	 */
	public boolean isProperty();

	/**
	 * Function checking if the Words can be a playable item.
	 * 
	 * @return true if it can be a playable item or false otherwise
	 */
	public boolean isPlayable();

	/**
	 * Setter for the horizontal coordinate of the field representing the position.
	 * 
	 * @param x an int
	 */
	public void setX(int x);

	/**
	 * Setter for the vertical coordinate of the field representing the position.
	 * 
	 * @param y an int
	 */
	public void setY(int y);

	/**
	 * Function that draws the image of the text of the items on the screen.
	 * 
	 * @param context an ApplicationContext
	 * @param x an int representing the horizontal coordinate
	 * @param y an int representing the vertical coordinate
	 * @param width a float representing the width of the screen
	 * @param height a float representing the height of the screen
	 */
	public void drawImage(ApplicationContext context, int x, int y, float width, float height);
	
	/**
	 * Function that draws the image of the text of the items on the screen.
	 * 
	 * @param context an ApplicationContext
	 * @param x an int representing the horizontal coordinate
	 * @param y an int representing the vertical coordinate
	 * @param width a float representing the width of the screen
	 * @param height a float representing the height of the screen
	 */
	public void drawText(ApplicationContext context, int x, int y, float width, float height);

	/**
	 * Getter of the value of the field isMelt.
	 * 
	 * @return the value of the field isMelt (true or false)
	 */
	public boolean getIsMelt();

	/**
	 * Setter for the value of the isMelt field.
	 * 
	 * @param b a boolean (true or false)
	 */
	public void setIsMelt(boolean b);
}