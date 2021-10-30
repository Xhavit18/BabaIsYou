package fr.umlv.BabaIsYou;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import fr.umlv.BabaIsYou.Names.Decor;

/**
 * Class that contains methods that initializes the game from a file
 * 
 * @author Ghamri Samy and Osaj Xhavit
 *
 */
public class ReadFile {
	
	/**
	 * Function adding playable element in a GameBoard type and used in createGameWithFile.
	 * @param parts an array of strings
	 * @param game a GameBoard to get the different elements of the game
	 */
	private static void ifCreate(String[] parts, GameBoard game) {

		for (int i = 0; i < parts.length; i++) {
			String s = parts[i];

			if (Decor.isDecor(s)) {
				Words a = new Decor(s, new Position(Integer.parseInt(parts[i + 1]), Integer.parseInt(parts[i + 2])));
				game.addPlayable(a);
			}
		}
	}

	/**
	 * Function adding text element in a GameBoard type and used in createGameWithFile.
	 * @param parts an array of strings
	 * @param game a GameBoard to get the different elements of the game
	 */
	private static void elseCreate(String[] parts, GameBoard game) {
		for (int i = 0; i < parts.length; i++) {
			String s = parts[i];
			if (Decor.isDecor(s)) {
				Words a = new Decor(s, new Position(Integer.parseInt(parts[i + 1]), Integer.parseInt(parts[i + 2])));
				game.addMessage(a);
			} else if (Properties.isPro(s)) {
				Words a = new Properties(s,
						new Position(Integer.parseInt(parts[i + 1]), Integer.parseInt(parts[i + 2])));
				game.addMessage(a);
			} else if (Operators.isOp(s)) {
				Words a = new Operators(s,
						new Position(Integer.parseInt(parts[i + 1]), Integer.parseInt(parts[i + 2])));
				game.addMessage(a);
			}
		}
	}
	
	/**
	 * Function that initializes the elements of a level from a file.
	 * 
	 * @param file a string representing the name of the file, not null
	 * @param game a GameBoard to get the different elements of the game
	 * @throws FileNotFoundException if the file is not found
	 */
	public static void createGameWithFile(String file, GameBoard game) throws FileNotFoundException {
		int j = 0;
		Objects.requireNonNull(file);
		File myObj = new File(file);
		Scanner myReader = new Scanner(myObj);
		while (myReader.hasNextLine()) {
			String data = myReader.nextLine();
			String[] parts = data.split(" ");
			if (parts[0].equals("Message")) {
				j = 1;
				continue;
			}
			if (j == 0)
				ifCreate(parts, game);
			else
				elseCreate(parts, game);
		}
		myReader.close();
	}
	
	/**
	 * Function adding valid rules on the field Rules of a GameBoard and used in pickRulesFromFile.
	 * @param mes a list of Words
	 * @param i an int
	 * @param name a Names
	 * @param op an Operators
	 * @param property a Propertues
	 * @param r a Rules
	 * @param game a GameBoard to get the different elements of the game
	 */
	private static void ifPick(ArrayList<Words> mes, int i, Names name, Operators op, Words property, Rules r,
			GameBoard game) {
		if (Decor.isDecor(mes.get(i).getW())) {
			name = new Decor(mes.get(i).getW(), mes.get(i).getPos());
			i++;
			op = new Operators(mes.get(i).getW(), mes.get(i).getPos());
			i++;
			try {
				property = new Properties(mes.get(i).getW(), mes.get(i).getPos());
			} catch (Exception e) {
				property = new Decor(mes.get(i).getW(), mes.get(i).getPos());
			}
			r = new Rules(name, op, property);
			if (r.isValidRule())
				game.add(r);
		}
	}
	
	/**
	 * Function that picks the default rules of the level.
	 * 
	 * @param game a GameBoard to get the different elements of the game
	 */
	public static void pickRulesFromFile(GameBoard game) {
		int len = game.getMessage().size();
		ArrayList<Words> mes = game.getMessage();
		for (int i = 0; i < len; i++) {
			Names name = null;
			Operators op = null;
			Words property = null;
			Rules r = null;
			try {
				ifPick(mes, i, name, op, property, r, game);
			} catch (Exception e) {
				continue;
			}
		}
	}
	
	/**
	 * Methods that selects levels file or directory from the command line
	 *  
	 * @param args an array of String 
	 * @param level a StringBuilder
	 */
	public static void chooseLevelFromCommandLine(String[] args, StringBuilder level) {
		for (int argument = 0; argument < args.length; argument++) {
		
			if ( args[argument].equals("--level")) {
				if ( args[argument + 1].equals("name")) {
					level.replace(0, level.length(), args[argument + 2]);
				}
			}
			
			if ( args[argument].equals("--levels")) {
				if ( args[argument + 1].equals("name")) {
					//starting with first level
					level.replace(0, level.length(), args[argument + 2] + "/level0.txt");
				}
			}
		}
	}
	
}
