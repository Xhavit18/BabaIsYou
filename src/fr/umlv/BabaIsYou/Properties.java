package fr.umlv.BabaIsYou;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import javax.imageio.ImageIO;
import fr.umlv.zen5.ApplicationContext;

/**
 * Class that contains methods that creates and manage the properties of the game
 * 
 * @author Ghamri Samy and Osaj Xhavit
 *
 */
public class Properties implements Words {

	private String property;
	private Position p;
	private BufferedImage text;

	/**
	 * Constructor for the type Properties representing the property of the game (Stop, You, ...).
	 * 
	 * @param property a String representing the name of the item (Stop, You, ...), not null
	 * @param p a Position representing the coordinates of the item, not null
	 */
	public Properties(String property, Position p) {
		if (isValid(property))
			this.property = Objects.requireNonNull(property);
		else {
			throw new IllegalArgumentException("This is not a valid property!");
		}
		this.p = Objects.requireNonNull(p);
		switch (property) {
		case "Defeat": case "Hot": case "Melt": case "Push": case "Sink": case "Stop": case "Win": case "You": case "Crash":
			try {
				text = ImageIO.read(new File(Paths.get(System.getProperty("user.dir") + "/src/Images/"+ property + ".png").toString()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		}
	}
	
	/**
	 * Constructor for the type Properties representing the property of the game (Stop, You, ...) without the position.
	 * 
	 * @param property a String representing the name of the item (Stop, You, ...), not null
	 */
	public Properties(String property) {
		if (isValid(property))
			this.property = Objects.requireNonNull(property);
		else {
			throw new IllegalArgumentException("This is not a valid property!");
		}
		switch (property) {
		case "Defeat": case "Hot": case "Melt": case "Push": case "Sink": case "Stop": case "Win": case "You": case "Crash":
			try {
				text = ImageIO.read(new File(Paths.get(System.getProperty("user.dir") + "/src/Images/"+ property + ".png").toString()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
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
	 * Function that do nothing because a type Properties don't have image, they only have text images.
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
	 * Function creating and returning a list of valid properties.
	 * 
	 * @return a list of valid properties
	 */
	public static ArrayList<String> validProp() {
		ArrayList<String> validProp = new ArrayList<String>();
		Collections.addAll(validProp, "You", "Win", "Stop", "Push", "Melt", "Hot", "Defeat", "Sink", "Crash");
		return validProp;
	}

	/**
	 * Functions checking if a string is a valid property or not.
	 * 
	 * @param s a String
	 * @return true if s is a valid property or false otherwise
	 */
	private boolean isValid(String s) {
		return validProp().contains(s);
	}

	/**
	 * Static function checking if a string is a Properties.
	 * 
	 * @param s a String
	 * @return true if s is a valid property or false otherwise
	 */
	public static boolean isPro(String s) {
		return validProp().contains(s);
	}

	/**
	 * Getter of the value of the field representing the name of the item.
	 * 
	 * @return a string representing the field representing the name of the item.
	 */
	public String getW() {
		return this.property;
	}

	/**
	 * Function checking if the Words can be a property.
	 * 
	 * @return true if it can be a property or false otherwise
	 */
	public boolean isProperty() {
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
		result = prime * result + ((p == null) ? 0 : p.hashCode());
		result = prime * result + ((property == null) ? 0 : property.hashCode());
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
		Properties other = (Properties) obj;
		if (p == null) {
			if (other.p != null)
				return false;
		} else if (!p.equals(other.p))
			return false;
		if (property == null) {
			if (other.property != null)
				return false;
		} else if (!property.equals(other.property))
			return false;
		return true;
	}
}