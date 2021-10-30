package fr.umlv.BabaIsYou;

/**
 * Class that contains methods that creates and manage the different position of the game
 * 
 * @author Ghamri Samy and Osaj Xhavit
 *
 */
public class Position {

	private int x, y;
	
	/**
	 * Constructor for the type Position representing the in-game position of an item.
	 * @param x an int
	 * @param y an int
	 */
	public Position(int x, int y) {

		if (x >= 0 && x < Main.X)
			this.x = x;
		else
			throw new IllegalArgumentException("Invalid coordinate!");

		if (y >= 0 && y < Main.Y)
			this.y = y;
		else
			throw new IllegalArgumentException("Invalid coordinate!");
	}
	
	/**
	 * Getter for the horizontal coordinate.
	 * 
	 * @return the value of the horizontal coordinate
	 */
	public int getX() {
		return x;
	}
	 /**
	  * Getter for the vertical coordinate.
	  * 
	  * @return the value of the vertical coordinate
	  */
	public int getY() {
		return y;
	}

	/**
	 * Setter for the horizontal coordinate.
	 * 
	 * @param x an int
	 */
	public void setX(int x) {
		if (x >= 0 && x < Main.X)
			this.x = x;
	}

	/**
	 * Setter for the vertical coordinate.
	 * 
	 * @param y an int
	 */
	public void setY(int y) {
		if (y >= 0 && y < Main.Y)
			this.y = y;
	}
	
	// Overrides of the methods toString, hashCode and equals

	@Override
	public String toString() {
		return "position : (" + getX() + ", " + getY() + ")";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
}