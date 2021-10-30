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
 * Interface representing the nouns
 * 
 * @author Ghamri Samy and Osaj Xhavit
 *
 */
public interface Names extends Words {

	/**
	 * Class that contains methods that creates and manage the nouns of the game
	 * 
	 * @author Ghamri Samy and Osaj Xhavit
	 *
	 */
	public class Decor implements Names {

		private String decor;
		private Position p;
		private boolean isMelt;
		private BufferedImage text;
		private BufferedImage image;
		
		
		/**
		 * Constructor for the type Decor representing the nouns of the game (Baba, Wall, ...). 
		 * 
		 * @param decor a String representing the name of the item (Baba, Wall, ...), not null
		 * @param p a Position representing the coordinates of the item, not null
		 */
		public Decor(String decor, Position p) {
			if (isValid(decor)) {
				this.decor = Objects.requireNonNull(decor);
			} else {
				throw new IllegalArgumentException("This is not a valid name!");
			}
			this.p = Objects.requireNonNull(p);
			this.isMelt = false;
			switch (decor) {
			case "Baba": case "Rock": case "Lava": case "Skull": case "Wall": case "Water": case "Flag": case "Troll":
				try {
					image = ImageIO.read(new File(Paths.get(System.getProperty("user.dir") + "/src/Images/"+ decor + "I.png").toString()));
					text = ImageIO.read(new File(Paths.get(System.getProperty("user.dir") + "/src/Images/"+ decor + "T.png").toString()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
		}
		
		/**
		 * Constructor for the type Decor representing the nouns of the game (Baba, Wall, ...) without the position. 
		 * 
		 * @param decor a String representing the name of the item (Baba, Wall, ...), not null
		 */
		public Decor(String decor) {
			if (isValid(decor)) {
				this.decor = Objects.requireNonNull(decor);
			} else {
				throw new IllegalArgumentException("This is not a valid name!");
			}
			this.isMelt = false;
			switch (decor) {
			case "Baba": case "Rock": case "Lava": case "Skull": case "Wall": case "Water": case "Flag": case "Troll":
				try {
					image = ImageIO.read(new File(Paths.get(System.getProperty("user.dir") + "/src/Images/"+ decor + "I.png").toString()));
					text = ImageIO.read(new File(Paths.get(System.getProperty("user.dir") + "/src/Images/"+ decor + "T.png").toString()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
		}
		
		/**
		 * Setter for the value of the isMelt field.
		 * 
		 * @param b a boolean (true or false)
		 */
		public void setIsMelt(boolean b) {
			this.isMelt = b;
		}
		
		
		/**
		 * Getter of the value of the field isMelt.
		 * 
		 * @return the value of the field isMelt (true or false)
		 */
		public boolean getIsMelt() {
			return this.isMelt;
		}
		
		
		/**
		 * Function that draws the image of the playable items on the screen.
		 * 
		 * @param context an ApplicationContext
		 * @param x an int representing the horizontal coordinate
		 * @param y an int representing the vertical coordinate
		 * @param width a float representing the width of the screen
		 * @param height a float representing the height of the screen
		 */
		public void drawImage(ApplicationContext context, int x, int y, float width, float height) {
		    context.renderFrame(graphics -> {
		        graphics.drawImage(image, (int)Math.floor(width * x / Main.X), (int)Math.floor(height * y / Main.Y), (int)Math.floor(width/Main.X), (int)Math.floor(height/Main.Y), null);
		    });
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
		          graphics.drawImage(text, (int)Math.floor(width * x / Main.X), (int)Math.floor(height * y / Main.Y), (int)Math.floor(width/Main.X), (int)Math.floor(height/Main.Y), null);
		        });
		}
		
		/**
		 * Function creating an returning a list of valid names.
		 * 
		 * @return a list of valid names
		 */
		public static ArrayList<String> validNames() {
			ArrayList<String> validName = new ArrayList<String>();
			Collections.addAll(validName, "Baba", "Flag", "Wall", "Water", "Skull", "Lava", "Rock", "Troll");
			return validName;
		}
		
		
		/**
		 * Functions checking if a string is valid name or not.
		 * 
		 * @param s a String
		 * @return true if s is a valid name or false otherwise
		 */
		private boolean isValid(String s) {
			return validNames().contains(s);
		}
		
		/**
		 * Static function checking if a string is a Names
		 * 
		 * @param s a String
		 * @return true if s is a valid name or false otherwise
		 */
		public static boolean isDecor(String s) {
			return validNames().contains(s);
		}
		
		
		/**
		 * Getter of the value of the field representing the name of the item.
		 * 
		 * @return a string representing the field representing the name of the item.
		 */
		public String getW() {
			return this.decor;
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
			return true;
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
		
		
		// Overrides of the methods toString, hashCode and equals
		
		@Override
	    public String toString() {
	        return getW() + " " + p;
	    }
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((decor == null) ? 0 : decor.hashCode());
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
			Decor other = (Decor) obj;
			if (decor == null) {
				if (other.decor != null)
					return false;
			} else if (!decor.equals(other.decor))
				return false;
			if (p == null) {
				if (other.p != null)
					return false;
			} else if (!p.equals(other.p))
				return false;
			return true;
		}
	}
}