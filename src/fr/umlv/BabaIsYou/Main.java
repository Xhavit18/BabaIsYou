package fr.umlv.BabaIsYou;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import fr.umlv.zen5.Application;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.KeyboardKey;
import fr.umlv.zen5.ScreenInfo;

/**
 * Main class to launch the game
 * 
 * @author Ghamri Samy and Osaj Xhavit
 *
 */
public class Main {
	
	static int X = 33;
	static int Y = 18;
	
	/**
	 * Function to set variable X and Y for in-game image's size purpose.
	 * 
	 * @param x an int
	 * @param y an int
	 */
	private static void setXY(int x, int y) {
		if (x <= 0 || y <= 0)
			throw new IllegalArgumentException("x and y must be positive!");
		X = x;
		Y = y;
	}
	
	/**
	 * Main function to launch the game.
	 * 
	 * @param args an array of strings
	 * @throws FileNotFoundException if the file is not found
	 */
	public static void main(String[] args) throws FileNotFoundException {

		// Picking the different elements of the game from a file
		GameBoard game = new GameBoard();
		
		// Default level
		StringBuilder level = new StringBuilder("Levels/level0.txt");
		
		//Choose the level in command line
		ReadFile.chooseLevelFromCommandLine(args, level);
		
		ReadFile.createGameWithFile(level.toString(), game);
		ReadFile.pickRulesFromFile(game);
		Player player = new Player();
		Application.run(Color.BLACK, context -> {
			
			// Picking the different nouns, properties and operators
			ArrayList<Words> namesMES = new ArrayList<Words>();
			ArrayList<Words> opMES = new ArrayList<Words>();
			ArrayList<Words> propMES = new ArrayList<Words>();
			Rules.forPick(game, namesMES, opMES, propMES);

			// get the size of the screen
			ScreenInfo screenInfo = context.getScreenInfo();
			float width = screenInfo.getWidth();
			float height = screenInfo.getHeight();
			game.drawBoardPlayable(context, width, height);
			game.drawBoardMessage(context, width, height);
			
			//Main game loop
			context.renderFrame(graphics -> {
				
				//Execute rules in command line
				Rules.executeRulesFromCommandLine(args, game);
				
				var i = Integer.parseInt(String.valueOf(level.charAt(level.length() - 5)));
				
				for (;;) {
					game.applyRules(context, width, height, player, 0, 0, namesMES, opMES, propMES);
					Event event = context.pollOrWaitEvent(100);
					if (event == null) { // no event
						continue;
					}
					KeyboardKey action = event.getKey();
					Event.Action key = event.getAction();
					if (key == Event.Action.KEY_PRESSED) {
						if (action == KeyboardKey.Q) {
							context.exit(0);
							return;
						}
						if (action == KeyboardKey.DOWN) {
							player.clearPlayer(context, width, height);
							player.moveY(1);
							game.moveMessY(context, player, width, height, 1, namesMES, opMES, propMES);
							game.applyRules(context, width, height, player, 0, 1, namesMES, opMES, propMES);
						}
						if (action == KeyboardKey.UP) {
							player.clearPlayer(context, width, height);
							player.moveY(-1);
							game.moveMessY(context, player, width, height, -1, namesMES, opMES, propMES);
							game.applyRules(context, width, height, player, 0, -1, namesMES, opMES, propMES);
						}
						if (action == KeyboardKey.LEFT) {
							player.clearPlayer(context, width, height);
							player.moveX(-1);
							game.moveMessX(context, player, width, height, -1, namesMES, opMES, propMES);
							game.applyRules(context, width, height, player, -1, 0, namesMES, opMES, propMES);
						}
						if (action == KeyboardKey.RIGHT) {
							player.clearPlayer(context, width, height);
							player.moveX(1);
							game.moveMessX(context, player, width, height, 1, namesMES, opMES, propMES);
							game.applyRules(context, width, height, player, 1, 0, namesMES, opMES, propMES);
						}
						game.drawBoardPlayable(context, width, height);
						game.drawBoardMessage(context, width, height);
						
						if (Conditions.defeatCondition(player, game)) {
							context.exit(0);
							return;
						}
						
						// Checking if the user completes the level
						if (Conditions.victoryCondition(player, game)) {
							i += 1;
							if (i > 7) {
								context.exit(0);
								return;
							}
							if ( i == 7) {
								setXY(25, 25);
							}
							level.replace(level.length() - 5, level.length() - 4, String.valueOf(i));
							game.clearGameBoard();
							try {
								ReadFile.createGameWithFile(level.toString(), game);
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							}
							ReadFile.pickRulesFromFile(game);
							player.clearPlayerList();
							graphics.clearRect(0, 0, (int) width, (int) height);
							namesMES.clear();
							opMES.clear();
							propMES.clear();
							Rules.forPick(game, namesMES, opMES, propMES);
							game.drawBoardPlayable(context, width, height);
							game.drawBoardMessage(context, width, height);

						}
					}
				}
			});
		});
	}
}