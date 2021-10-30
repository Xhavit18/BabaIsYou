package fr.umlv.BabaIsYou;

import java.util.ArrayList;
import java.util.Objects;

import fr.umlv.zen5.ApplicationContext;

/**
 * Class that contains methods that creates and manage the different movable elements of the game
 * 
 * @author Ghamri Samy and Osaj Xhavit
 *
 */
public class Player {

	private ArrayList<String> player;
	private ArrayList<Position> position;

	
	/**
	 * Constructor for the type Player representing the itemq that can move in the game.
	 */
	public Player() {
		this.player = new ArrayList<String>();
		this.position = new ArrayList<Position>();
	}

	/**
	 * Function that clears the elements of the two list of a Player.
	 */
	public void clearPlayerList() {
		this.player.clear();
		this.position.clear();
	}
	
	/**
	 * Function that delete a Player on the screen.
	 * 
	 * @param context an ApplicationContext
	 * @param width a float representing the width of the screen
	 * @param height a float representing the height of the screen
	 */
	public void clearPlayer(ApplicationContext context, float width, float height) {
		context.renderFrame(graphics -> {
			for (var p : this.position) {
				graphics.clearRect((int) width * p.getX() / Main.X, (int) height * p.getY() / Main.Y,
						(int) width / Main.X, (int) height / Main.Y);
			}
		});
	}

	/**
	 * Getter of the value of the field player.
	 * 
	 * @return the value of the field player 
	 */
	public ArrayList<String> getPlayList() {
		return this.player;
	}

	/**
	 * Getter of the value of the field position.
	 * 
	 * @return the value of the field position 
	 */
	public ArrayList<Position> getPosList() {
		return this.position;
	}

	/**
	 * Setter for the value of the field player.
	 * 
	 * @param player is an ArrayList of strings
	 */
	public void setPlayerName(ArrayList<String> player) {
		this.player.clear();
		for (var name : player) {
			this.player.add(name);
		}
	}
	
	/**
	 * Setter for the value of the field position.
	 * 
	 * @param position is an ArrayList of Position
	 */
	public void setPlayerPos(ArrayList<Position> position) {
		this.position.clear();
		for (var pos : position) {
			this.position.add(pos);
		}
	}

	/**
	 * Function adding a string to the field player.
	 * @param p a String, not null
	 */
	public void addP(String p) {
		Objects.requireNonNull(p);
		this.player.add(p);
	}
	
	/**
	 * Function changing the value of the horizontal coordinate of the field position.
	 * @param dx an int 
	 */
	public void moveX(int dx) {
		for (var p : this.position) {
			p.setX(p.getX() + dx);
		}
	}
	
	/**
	 * Function changing the value of the vertical coordinate of the field position.
	 * @param dy an int 
	 */
	public void moveY(int dy) {
		for (var p : this.position) {
			p.setY(p.getY() + dy);
		}
	}
	
	// Override of the method toString

	@Override
	public String toString() {
		return this.player + " " + this.position;
	}
}