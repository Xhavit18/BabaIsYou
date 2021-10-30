package fr.umlv.BabaIsYou;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import javax.imageio.ImageIO;
import fr.umlv.zen5.ApplicationContext;

/**
 * Class that contains methods that creates and manage the operators
 * 
 * @author Ghamri Samy and Osaj Xhavit
 *
 */
public class Operators implements Words {

	private String operator;
	private Position p;
	private BufferedImage text;

	/**
	 * Constructor for the type Operators representing the operators of the game (Is).
	 * 
	 * @param operator a String representing the name of the item (Is), not null
	 * @param p a Position representing the coordinates of the item, not null
	 */
	public Operators(String operator, Position p) {
		if (isValid(operator)) {
			this.operator = Objects.requireNonNull(operator);
		} else {
			throw new IllegalArgumentException("This is not a valid operator!");
		}
		this.p = Objects.requireNonNull(p);
		try {
			text = ImageIO.read(new File(Paths.get(System.getProperty("user.dir") + "/src/Images/Prop_IS-0.png").toString()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Constructor for the type Operators representing the operators of the game (Is) without the position.
	 * 
	 * @param operator a String representing the name of the item (Is), not null
	 */
	public Operators(String operator) {
		if (isValid(operator)) {
			this.operator = Objects.requireNonNull(operator);
		} else {
			throw new IllegalArgumentException("This is not a valid operator!");
		}
		try {
			text = ImageIO.read(new File(Paths.get(System.getProperty("user.dir") + "/src/Images/Prop_IS-0.png").toString()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Function that draws the image of the text of the items on the screen.
	 * 
	 * @param context an ApplicationContext
	 * @param x an int representing the horizontal coordinate
	 * @param y an int representing the vertical coordinate
	 * @param width a float representing the width of the screen
	 * @param height a float representing the height of the screen
	 */
	public void drawText(ApplicationContext context, int x, int y, float width, float height) {
		context.renderFrame(graphics -> {
			graphics.drawImage(text, (int) Math.floor(width * x / Main.X), (int) Math.floor(height * y / Main.Y),
					(int) Math.floor(width / Main.X), (int) Math.floor(height / Main.Y), null);
		});
	}

	/**
	 * Function that do nothing because a type Operators don't have image, they only have text images.
	 * 
	 * @param context an ApplicationContext
	 * @param x an int representing the horizontal coordinate
	 * @param y an int representing the vertical coordinate
	 * @param width a float representing the width of the screen
	 * @param height a float representing the height of the screen
	 */
	public void drawImage(ApplicationContext context, int x, int y, float width, float height) {
	}

	/**
	 * Function creating and returning a list of valid operators.
	 * 
	 * @return a list of valid operators
	 */
	public static ArrayList<String> validOp() {
		ArrayList<String> validOp = new ArrayList<String>();
		validOp.add("Is");
		return validOp;
	}
	
	/**
	 * Functions checking if a string is a valid operator or not.
	 * 
	 * @param s a String
	 * @return true if s is a valid operator or false otherwise
	 */
	private boolean isValid(String s) {
		return validOp().contains(s);
	}

	/**
	 * Static function checking if a string is an Operators
	 * 
	 * @param s a String
	 * @return true if s is a valid operator or false otherwise
	 */
	public static boolean isOp(String s) {
		return validOp().contains(s);
	}

	/**
	 * Getter of the value of the field representing the name of the item.
	 * 
	 * @return a string representing the field representing the name of the item.
	 */
	public String getW() {
		return this.operator;
	}

	/**
	 * Function checking if the Words can be a property.
	 * 
	 * @return true if it can be a property or false otherwise
	 */
	public boolean isProperty() {
		return false;
	}

	/**
	 * Function checking if the Words can be an operator.
	 * 
	 * @return true if it can be an operator or false otherwise
	 */
	public boolean isOperator() {
		return true;
	}

	/**
	 * Getter of the value of the field p representing the position.
	 * 
	 * @return the value of the field p representing the position
	 */
	public Position getPos() {
		return this.p;
	}

	/**
	 * Function checking if the Words can be a playable item.
	 * 
	 * @return true if it can be a playable item or false otherwise
	 */
	public boolean isPlayable() {
		return false;
	}

	/**
	 * Setter for the horizontal coordinate of the field p representing the position.
	 * 
	 * @param x an int
	 */
	public void setX(int x) {
		this.p.setX(x);	
	}
	
	/**
	 * Setter for the vertical coordinate of the field p representing the position.
	 * 
	 * @param y an int
	 */
	public void setY(int y) {
		this.p.setY(y);
	}
	
	/**
	 * Setter for the value of the isMelt field.
	 * 
	 * @param b a boolean (true or false)
	 */
	public void setIsMelt(boolean b) {
	}

	/**
	 * Getter of the value of the field isMelt.
	 * 
	 * @return the value of the field isMelt (true or false)
	 */
	public boolean getIsMelt() {
		return false;
	}
	
	// Overrides of the methods toString, hashCode and equals
	
	@Override
	public String toString() {
		return getW() + " " + p;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((operator == null) ? 0 : operator.hashCode());
		result = prime * result + ((p == null) ? 0 : p.hashCode());
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
		Operators other = (Operators) obj;
		if (operator == null) {
			if (other.operator != null)
				return false;
		} else if (!operator.equals(other.operator))
			return false;
		if (p == null) {
			if (other.p != null)
				return false;
		} else if (!p.equals(other.p))
			return false;
		return true;
	}
	
}