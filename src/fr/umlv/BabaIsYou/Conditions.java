package fr.umlv.BabaIsYou;

import java.util.ArrayList;

/**
 * Class for win and defeat conditions
 * 
 * @author Ghamri Samy and Osaj Xhavit
 *
 */
public class Conditions {
	
	/**
	 * Function to get a rule by searching it with the property
	 * 
	 * @param prop a String representing the property
	 * @param g a GameBoard to get the different elements of the game
	 * @return a Rules representing the rule that we found
	 */
	private static Rules getRulesByProperty(String prop, GameBoard g) {
		Rules tmp = null;
		for (var r : g.getRules()) {
			if (r.getProp().getW().equals(prop)) {
				tmp = r;
			}
		}
		return tmp;
	}
	
	/**
	 * Function to get an ArrayList of Words where the names match.
	 * 
	 * @param name a String
	 * @param g a GameBoard to get the different elements of the game
	 * @return an ArrayList of Words
	 */
	private static ArrayList<Words> getNamePlayable(String name, GameBoard g) {
		ArrayList<Words> tmp = new ArrayList<Words>();
		for (var r : g.getPlayable()) {
			if (r.getW().equals(name)) {
				tmp.add(r);
			}
		}
		return tmp;
	}
	
	/**
	 * Function to check if the player has won.
	 * 
	 * @param p a player
	 * @param g a GameBoard to get the different elements of the game
	 * @return true if the player completes the level and false otherwise
	 */
	public static boolean victoryCondition(Player p, GameBoard g) {
		Rules r = getRulesByProperty("Win", g);
		if (r == null) {
			return false;
		}
		ArrayList<Words> w = getNamePlayable(r.getName().getW(), g);
		for (var pos : p.getPosList()) {
			for (var w_bis : w)
				if (pos.equals(w_bis.getPos())) {
					return true;
				}
		}
		return false;
	}
	
	/**
	 * Function to check if the player has lost.
	 * 
	 * @param p a player
	 * @param g a GameBoard to get the different elements of the game
	 * @return true if the player is defeated by the level and false otherwise
	 */
	public static boolean defeatCondition(Player p, GameBoard g) {
		
		if(p.getPlayList().isEmpty()) {
			System.out.println("GAME OVER!");
			return true;
		}
			
		else if (getRulesByProperty("You", g) == null) {
			System.out.println("GAME OVER!");
			return true;
		}
		return false;
	}

}
