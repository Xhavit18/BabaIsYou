package fr.umlv.BabaIsYou;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

import javax.imageio.ImageIO;

import fr.umlv.BabaIsYou.Names.Decor;
import fr.umlv.zen5.ApplicationContext;

/**
 * Class for the function creating and changing the game board
 * 
 * @author Ghamri Samy and Osaj Xhavit
 *
 */
public class GameBoard {

	private ArrayList<Words> playable;
	private ArrayList<Words> message;
	private ArrayList<Rules> rules;

	/**
	 * Constructor for the type GameBoard
	 */
	public GameBoard() {
		this.playable = new ArrayList<Words>();
		this.message = new ArrayList<Words>();
		this.rules = new ArrayList<Rules>();
	}
	
	/**
	 * Function changing the horizontal position of a text
	 * 
	 * @param context an ApplicationContext
	 * @param player a Player representing the items movable in the game
	 * @param width a float representing the width of the screen
	 * @param height a float representing the height of the screen
	 * @param dx an int representing the movement horizontally
	 * @param namesMES a list of Words with the text for the nouns
	 * @param opMES a list of Words with the text for the operators
	 * @param propMES a list of Words with the text for the properties
	 */
	public void moveMessX(ApplicationContext context, Player player, float width, float height, int dx, ArrayList<Words> namesMES, ArrayList<Words> opMES, ArrayList<Words> propMES) {
		for (var p : player.getPosList()) {
			if (this.containsMess(p)) {
				var m = pushMessageX(context, width, height, p, dx);
				var copy_m = m;
				while (m != null) {
					copy_m = m;
					m = this.pushNextMessageX(context, width, height, m.getPos(), dx, m);
				}
				this.movePlayableFromMessX(context, width, height, copy_m, dx);
				Rules.pickRulesFromBoard(this, namesMES, opMES, propMES);
			}
		}
	}
	
	/**
	 * Function changing the vertical position of a text
	 * 
	 * @param context an ApplicationContext
	 * @param player a Player representing the items movable in the game
	 * @param width a float representing the width of the screen
	 * @param height a float representing the height of the screen
	 * @param dy an int representing the movement vertically
	 * @param namesMES a list of Words with the text for the nouns
	 * @param opMES a list of Words with the text for the operators
	 * @param propMES a list of Words with the text for the properties
	 */
	public void moveMessY(ApplicationContext context, Player player, float width, float height, int dy, ArrayList<Words> namesMES, ArrayList<Words> opMES, ArrayList<Words> propMES) {
		for (var p : player.getPosList()) {
			if (this.containsMess(p)) {
				var m = pushMessageY(context, width, height, p, dy);
				var copy_m = m;
				while (m != null) {
					copy_m = m;
					m = this.pushNextMessageY(context, width, height, m.getPos(), dy, m);
				}
				this.movePlayableFromMessY(context, width, height, copy_m, dy);
				Rules.pickRulesFromBoard(this, namesMES, opMES, propMES);
			}
		}
	}
	
	/**
	 * Function changing the horizontal position of a playable item
	 * 
	 * @param context an ApplicationContext
	 * @param width a float representing the width of the screen
	 * @param height a float representing the height of the screen
	 * @param copy_m a Words
	 * @param dx an int representing the movement horizontally
	 */
	private void movePlayableFromMessX(ApplicationContext context, float width, float height, Words copy_m, int dx) {
		for(var tmp : this.getRules()) {
			if(tmp.getProp().getW().equals("Push")) {
				for(var play : this.playable) {
					if(play.getW().equals(tmp.getName().getW()) && play.getPos().equals(copy_m.getPos())) {
						context.renderFrame(graphics -> {
							graphics.clearRect((int) width * play.getPos().getX() / Main.X,
									(int) height * play.getPos().getY() / Main.Y, (int) width / Main.X,
									(int) height / Main.Y);
						});
						play.setX(play.getPos().getX() + dx);
						while(this.containsPlay(play)) {
							play.setX(play.getPos().getX() + dx);
						}
					}
				}
			}
		}
	}
	
	/**
	 * Function changing the vertical position of a playable item
	 * 
	 * @param context an ApplicationContext
	 * @param width a float representing the width of the screen
	 * @param height a float representing the height of the screen
	 * @param copy_m a Words
	 * @param dy an int representing the movement vertically
	 */
	private void movePlayableFromMessY(ApplicationContext context, float width, float height, Words copy_m, int dy) {
		for(var tmp : this.getRules()) {
			if(tmp.getProp().getW().equals("Push")) {
				for(var play : this.playable) {
					if(play.getW().equals(tmp.getName().getW()) && play.getPos().equals(copy_m.getPos())) {
						context.renderFrame(graphics -> {
							graphics.clearRect((int) width * play.getPos().getX() / Main.X,
									(int) height * play.getPos().getY() / Main.Y, (int) width / Main.X,
									(int) height / Main.Y);
						});
						play.setX(play.getPos().getX() + dy);
						while(this.containsPlay(play)) {
							play.setX(play.getPos().getX() + dy);
						}
					}
				}
			}
		}
	}
	
	/**
	 * Function that applies the rules to the game and its elements.
	 * 
	 * @param context an ApplicationContext
	 * @param width a float representing the width of the screen
	 * @param height a float representing the height of the screen
	 * @param p a Player representing the items movable in the game
	 * @param dx an int representing the movement horizontally
	 * @param dy an int representing the movement vertically
	 * @param names a list of Words representing the nouns
	 * @param op a list of Words representing the operators
	 * @param prop a list of Words representing the properties
	 */
	public void applyRules(ApplicationContext context, float width, float height, Player p, int dx, int dy, ArrayList<Words> names, ArrayList<Words> op, ArrayList<Words> prop) {
		var play = new ArrayList<String>();
		var pos = new ArrayList<Position>();
		for (Rules r : rules)
			switch (r.getProp().getW()) {
				case "You":
					you(r, p, this, play, pos); break;
				case "Stop":
					stop(this, r, dx, dy, p); break;
				case "Push":
					push(p, context, height, width, r, dx, dy, names, op, prop); break;
				case "Melt":
					Melt(this, r); break;
				case "Hot":
					hot(this, r, context, height, width); break;
				case "Defeat":
					defeat(this, r, p, context); break;
				case "Sink":
					sink(this, r, context, height, width, p); break;
				case "Crash":
					crash(this, r, context, height, width); break;
				case "Skull": case "Lava": case "Rock": case "Flag": case "Water": case "Wall":
					changeDecor(this, r, p, play); break;
			}
	}
	
	/**
	 * Parts of the Push function
	 * 
	 * @param context an ApplicationContext
	 * @param width a float representing the width of the screen
	 * @param height a float representing the height of the screen
	 * @param t a Words
	 * @param dx an int representing the movement horizontally
	 * @param dy an int representing the movement vertically
	 */
	private void if1ForPush(ApplicationContext context, float height, float width, Words t, int dx, int dy) {
		context.renderFrame(graphics -> {
			graphics.clearRect((int) width * t.getPos().getX() / Main.X,
					(int) height * t.getPos().getY() / Main.Y, (int) width / Main.X,
					(int) height / Main.Y);
		});
		t.setX(t.getPos().getX() + dx);
		t.setY(t.getPos().getY() + dy);
		while (this.containsPlay(t)) {
			t.setX(t.getPos().getX() + dx);
			t.setY(t.getPos().getY() + dy);
		}
	}
	
	/**
	 * Parts of the Push function
	 * 
	 * @param context an ApplicationContext
	 * @param width a float representing the width of the screen
	 * @param height a float representing the height of the screen
	 * @param t a Words
	 * @param dx an int representing the movement horizontally
	 * @param dy an int representing the movement vertically
	 * @param names a list of Words representing the nouns
	 * @param op a list of Words representing the operators
	 * @param prop a list of Words representing the properties
	 */
	private void if2ForPush(ApplicationContext context, float height, float width, Words t, int dx, int dy, ArrayList<Words> names, ArrayList<Words> op, ArrayList<Words> prop) {
		for (var z : this.message) {
			if (t.getPos().equals(z.getPos())) {
				context.renderFrame(graphics -> {
					graphics.clearRect((int) Math.floor(width * z.getPos().getX() / Main.X),
							(int) Math.floor(height * z.getPos().getY() / Main.Y),
							(int) width / Main.X, (int) height / Main.Y);
				});
				z.setX(z.getPos().getX() + dx);
				z.setY(z.getPos().getY() + dy);
				var m = z;
				while (this.samePosMess()) {
					if (dx != 0)
						m = this.pushNextMessageX(context, width, height, m.getPos(), dx, m);
					else
						m = this.pushNextMessageY(context, width, height, m.getPos(), dy, m);
				}
				Rules.pickRulesFromBoard(this, names, op, prop);
			}
		}
	}
	
	/**
	 * Function that applies the property Push to the game and its elements
	 * 
	 * @param p a Player representing the items movable in the game
	 * @param context an ApplicationContext
	 * @param width a float representing the width of the screen
	 * @param height a float representing the height of the screen
	 * @param r a Rules
	 * @param dx an int representing the movement horizontally
	 * @param dy an int representing the movement vertically
	 * @param names a list of Words representing the nouns
	 * @param op a list of Words representing the operators
	 * @param prop a list of Words representing the properties
	 */
	private void push(Player p, ApplicationContext context, float height, float width, Rules r, int dx, int dy, ArrayList<Words> names, ArrayList<Words> op, ArrayList<Words> prop) {
		for (var s : p.getPosList()) 
			for (var t : this.playable)
				if (s.equals(t.getPos()) && t.getW().equals(r.getName().getW())) {
					if1ForPush(context, height, width, t, dx, dy);
					if (this.containsMess(t.getPos())) 
						if2ForPush(context, height, width, t, dx, dy, names, op, prop);
				}
	}
	
	/**
	 * Function that applies the property You to the game and its elements
	 * 
	 * @param r a Rules
	 * @param p a Player representing the items movable in the game
	 * @param game a GameBoard to get the different elements of the game
	 * @param play
	 * @param pos a list of Position
	 */
	private void you(Rules r, Player p, GameBoard game, ArrayList<String> play, ArrayList<Position> pos) {
		p.addP(r.getName().getW());

		for (Words wo : game.playable)
			if (r.getName().getW().equals(wo.getW())) {
				play.add(wo.getW());
				pos.add(wo.getPos());
			}
		p.setPlayerName(play);
		p.setPlayerPos(pos);
	}
	
	/**
	 * Parts of the Stop function
	 * 
	 * @param game a GameBoard to get the different elements of the game
	 * @param i a Words
	 * @param dx an int representing the movement horizontally
	 * @param dy an int representing the movement vertically
	 * @param p a Player representing the items movable in the game
	 * @param words
	 */
	private void forStop(GameBoard game, Words i, int dx, int dy, Player p, ArrayList<Words> words) {
		for (var k : words) {
			if (!k.equals(i) && k.getPos().equals(i.getPos())) {
				k.getPos().setX(k.getPos().getX() - dx);
				k.getPos().setY(k.getPos().getY() - dy);
				for (var x : game.getPlayable()) {
					if (x.getPos().equals(k.getPos()) && !x.getW().equals(k.getW())) {
						x.getPos().setX(x.getPos().getX() - dx);
						x.getPos().setY(x.getPos().getY() - dy);
					}
				}
			}
		}
	}
	
	/**
	 * Function that applies the property Stop to the game and its elements
	 * 
	 * @param game a GameBoard to get the different elements of the game
	 * @param r a Rules
	 * @param dx an int representing the movement horizontally
	 * @param dy an int representing the movement vertically
	 * @param p a Player representing the items movable in the game
	 */
	private void stop(GameBoard game, Rules r, int dx, int dy, Player p) {
		for (Words i : game.getPlayable()) {
			if ((i.getW()).equals(r.getName().getW())) {
				forStop(game, i, dx, dy, p, game.getPlayable());
				forStop(game, i, dx, dy, p, game.getMessage());
			}
		}
	}
	
	/**
	 * Function that applies the property Melt to the game and its elements
	 * 
	 * @param game a GameBoard to get the different elements of the game
	 * @param r a Rules
	 */
	private void Melt(GameBoard game, Rules r) {
		for (var tmp : game.playable) {
			if (r.getName().getW().equals(tmp.getW())) {
				tmp.setIsMelt(true);
			} else {
				tmp.setIsMelt(false);
			}
		}
	}
	
	/**
	 * Function that applies the property Defeat to the game and its elements
	 * 
	 * @param game a GameBoard to get the different elements of the game
	 * @param r a Rules
	 * @param p a Player representing the items movable in the game
	 */
	private void defeat(GameBoard game, Rules r, Player p, ApplicationContext context) {
		for (var i = 0; i < game.playable.size(); i++) {
			if (game.playable.get(i).getW().equals(r.getName().getW())) {
				for (var j = 0; j < game.playable.size(); j++) {
					if (game.playable.get(j).getPos().equals(game.playable.get(i).getPos())
							&& !game.playable.get(j).getW().equals(game.playable.get(i).getW())
							&& p.getPosList().contains(game.playable.get(i).getPos())) {
						game.playable.remove(j);
						if( p.getPlayList().size() <= 1 ) {
							System.out.println("GAME OVER!");
							context.exit(0);
						}
					}
				}
			}
		}
	}
	
	/**
	 * Function that applies the property Sink to the game and its elements
	 * 
	 * @param game a GameBoard to get the different elements of the game
	 * @param r a Rules
	 * @param context an ApplicationContext
	 * @param width a float representing the width of the screen
	 * @param height a float representing the height of the screen
	 */
	private void sink(GameBoard game, Rules r, ApplicationContext context, float height, float width, Player p) {
		for (var i = 0; i < game.playable.size(); i++) {
			if (game.playable.get(i).getW().equals(r.getName().getW())) {
				for (var j = 0; j < game.playable.size(); j++) {
					if (game.playable.get(j).getPos().equals(game.playable.get(i).getPos())
							&& !game.playable.get(j).getW().equals(game.playable.get(i).getW())) {
						var tmp = game.playable.get(j);
						context.renderFrame(graphics -> {
							graphics.clearRect(tmp.getPos().getX() * (int) width / Main.X,
									tmp.getPos().getY() * (int) height / Main.Y, (int) width / Main.X,
									(int) height / Main.Y);
						});
						game.playable.remove(i);
						
						try {
							game.playable.remove(j);
						}catch (Exception e) {
							if( p.getPlayList().size() <= 1 ) {
								System.out.println("GAME OVER!");
								context.exit(0);
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * Function that applies the property Crash to the game and its elements
	 * 
	 * @param game a GameBoard to get the different elements of the game
	 * @param r a Rules
	 * @param context an ApplicationContext
	 * @param width a float representing the width of the screen
	 * @param height a float representing the height of the screen
	 */
	private void crash(GameBoard game, Rules r, ApplicationContext context, float height, float width) {
		for (var tm : game.playable) {
			if (tm.getW().equals(r.getName().getW())) {
				for (var tm1 : game.playable) {
					if (tm1.getPos().equals(tm.getPos()) && !tm.getW().equals(tm1.getW())) {
						try {
							var image = ImageIO.read(new File(Paths.get(System.getProperty("user.dir") + "/src/Images/BlueScreen.png").toString()));
							context.renderFrame(graphics -> {
								graphics.clearRect(0, 0, (int) width, (int) height);
								graphics.drawImage(image, 0, 0, (int) width, (int) height, null);
							});
							Thread.sleep(4000);
							context.exit(0);
						} catch (IOException e) {
							e.printStackTrace();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	/**
	 * Parts of the Hot function
	 * 
	 * @param game a GameBoard to get the different elements of the game
	 * @param r a Rules
	 */
	private void for1Hot(GameBoard game, Rules r) {
		for (var tmp : game.rules) {
			if (tmp.getName().getW().equals(r.getName().getW()) && tmp.getProp().getW().equals("Push")) {
				for (var tmp1 : game.playable) {
					tmp1.setIsMelt(false);
				}
				break;
			}
		}
	}
	
	/**
	 * Parts of the Hot function
	 * 
	 * @param game a GameBoard to get the different elements of the game
	 * @param r a Rules
	 * @param context an ApplicationContext
	 * @param width a float representing the width of the screen
	 * @param height a float representing the height of the screen
	 */
	private void for2Hot(GameBoard game, Rules r, ApplicationContext context, float height, float width) {
		for (var i = 0; i < game.playable.size(); i++) {
			if (game.playable.get(i).getW().equals(r.getName().getW())) {
				for (var j = 0; j < game.playable.size(); j++) {
					if (game.playable.get(i).getPos().equals(game.playable.get(j).getPos())
							&& !game.playable.get(i).getW().equals(game.playable.get(j).getW())
							&& game.playable.get(j).getIsMelt() == true) {
						var tmp = game.playable.get(j);
						context.renderFrame(graphics -> {
							graphics.clearRect((int) Math.floor(width * tmp.getPos().getX() / Main.X),
									(int) Math.floor(height * tmp.getPos().getY() / Main.Y), (int) width / Main.X,
									(int) height / Main.Y);
						});
						this.playable.remove(j);
					}
				}
			}
		}
	}
	
	/**
	 * Function that applies the property Hot to the game and its elements
	 * 
	 * @param game a GameBoard to get the different elements of the game
	 * @param r a Rules
	 * @param context an ApplicationContext
	 * @param width a float representing the width of the screen
	 * @param height a float representing the height of the screen
	 */
	private void hot(GameBoard game, Rules r, ApplicationContext context, float height, float width) {
		for1Hot(game, r);
		for2Hot(game, r, context, height, width);

	}
	
	/**
	 * Function that applies the properties Flag, Wall, Water, Skull, Lava, Rock, Troll to the game and its elements
	 * 
	 * @param game a GameBoard to get the different elements of the game
	 * @param r a Rules
	 * @param p a Player representing the items movable in the game
	 * @param play
	 */
	private void changeDecor(GameBoard game, Rules r, Player p, ArrayList<String> play) {
		for (var i = 0; i < this.playable.size(); i++) {
			if (r.getName().getW().equals(this.playable.get(i).getW())) {
				if (r.getName().getW().equals("Baba")) {
					var pos1 = new ArrayList<Position>();
					for (Words w1 : this.playable) {
						if (r.getProp().getW().equals(w1.getW())) {
							pos1.add(w1.getPos());
						}
					}
					p.setPlayerPos(pos1);
					p.setPlayerName(play);
				}
				var d = new Decor(r.getProp().getW(), this.playable.get(i).getPos());
				this.playable.remove(i);
				this.playable.add(d);
			}
		}
	}
	
	/**
	 *  Function that clears the elements of the three list of a GameBoard.
	 */
	public void clearGameBoard() {
		this.playable.clear();
		this.message.clear();
		this.rules.clear();
	}
	
	/**
	 * Function drawing the playable items of the game
	 * 
	 * @param context an ApplicationContext
	 * @param width a float representing the width of the screen
	 * @param height a float representing the height of the screen
	 */
	void drawBoardPlayable(ApplicationContext context, float width, float height) {
		for (Words w : this.playable) {
			w.drawImage(context, w.getPos().getX(), w.getPos().getY(), width, height);
		}
	}
	
	/**
	 * Function drawing the text items of the game
	 * 
	 * @param context an ApplicationContext
	 * @param width a float representing the width of the screen
	 * @param height a float representing the height of the screen
	 */
	void drawBoardMessage(ApplicationContext context, float width, float height) {
		for (Words m : this.message) {
			m.drawText(context, m.getPos().getX(), m.getPos().getY(), width, height);
		}
	}

	/**
	 * Function adding a Words to the list of the field playable
	 * 
	 * @param words a List of Words
	 */
	public void addPlayable(Words... words) {
		for (Words w : words) {
			Objects.requireNonNull(w);
			if (w.isPlayable())
				this.playable.add(w);
		}
	}

	/**
	 * Function adding a Words to the list of the field message
	 * 
	 * @param message a list of Words representing the text in the game
	 */
	public void addMessage(Words... message) {
		for (Words m : message) {
			Objects.requireNonNull(m);
			this.message.add(m);
		}
	}

	/**
	 * Function adding a Rules to the list of the field rules
	 * 
	 * @param rules a list of Rules representing the rules in the game
	 */
	public void add(Rules... rules) {
		for (Rules r : rules) {
			Objects.requireNonNull(r);
			this.getRules().add(r);
		}
	}

	/**
	 * Getter for the value of the field playable
	 * 
	 * @return the value of the field playable
	 */
	public ArrayList<Words> getPlayable() {
		return this.playable;
	}

	/**
	 * Getter for the value of the field message
	 * 
	 * @return the value of the field message
	 */
	public ArrayList<Words> getMessage() {
		return this.message;
	}

	/**
	 * Getter for the value of the field rules
	 * 
	 * @return the value of the field rules
	 */
	public ArrayList<Rules> getRules() {
		return this.rules;
	}

	/**
	 * Function checking if a position is in the list of the field message
	 * 
	 * @param pos a Position
	 * @return true if a value of the field message contains the position pos or false otherwise
	 */
	public boolean containsMess(Position pos) {
		for (var m : this.message) {
			if (m.getPos().equals(pos))
				return true;
		}
		return false;
	}

	/**
	 * Function checking if a Words is in the list of the field playable
	 * 
	 * @param t a Words
	 * @return true if a value of the field playable contains the Words t or false otherwise
	 */
	public boolean containsPlay(Words t) {
		for (var p : playable) {
			if (t != p && t.equals(p))
				return true;
		}
		return false;
	}

	/**
	 * Function changing the horizontal position of an item and returning the word
	 * 
	 * @param context an ApplicationContext
	 * @param width a float representing the width of the screen
	 * @param height a float representing the height of the screen
	 * @param pos a Position
	 * @param dx an int representing the movement horizontally
	 * @return a Words with his new position
	 */
	public Words pushMessageX(ApplicationContext context, float width, float height, Position pos, int dx) {
		for (var m : this.message) {
			if (m.getPos().equals(pos)) {
				context.renderFrame(graphics -> {
					graphics.clearRect((int) Math.floor(width * m.getPos().getX() / Main.X),
							(int) Math.floor(height * m.getPos().getY() / Main.Y), (int) width / Main.X,
							(int) height / Main.Y);
				});
				m.setX(m.getPos().getX() + dx);
				return m;
			}
		}
		return null;
	}
	
	/**
	 * Function changing the vertical position of an item and returning the word
	 * 
	 * @param context an ApplicationContext
	 * @param width a float representing the width of the screen
	 * @param height a float representing the height of the screen
	 * @param pos a Position
	 * @param dy an int representing the movement vertically
	 * @return a Words with his new position
	 */
	public Words pushMessageY(ApplicationContext context, float width, float height, Position pos, int dy) {
		for (var m : this.message) {
			if (m.getPos().equals(pos)) {
				context.renderFrame(graphics -> {
					graphics.clearRect((int) Math.floor(width * m.getPos().getX() / Main.X),
							(int) Math.floor(height * m.getPos().getY() / Main.Y), (int) Math.floor(width / Main.X),
							(int) Math.floor(height / Main.Y));
				});
				m.setY(m.getPos().getY() + dy);
				return m;
			}
		}
		return null;
	}

	/**
	 * Function changing the vertical position of an item and returning the word
	 * 
	 * @param context an ApplicationContext
	 * @param width a float representing the width of the screen
	 * @param height a float representing the height of the screen
	 * @param pos a Position
	 * @param dx an int representing the movement horizontally
	 * @param mes a Words
	 * @return a Words with his new position
	 */
	public Words pushNextMessageX(ApplicationContext context, float width, float height, Position pos, int dx, Words mes) {
		for (var m : this.message) {
			if (m.getPos().equals(mes.getPos()) && !(m.equals(mes))) {
				context.renderFrame(graphics -> {
					graphics.clearRect((int) Math.floor(width * m.getPos().getX() / Main.X),
							(int) Math.floor(height * m.getPos().getY() / Main.Y), (int) width / Main.X,
							(int) height / Main.Y);
				});
				m.setX(m.getPos().getX() + dx);
				return m;
			}
		}
		return null;
	}

	/**
	 * Function changing the vertical position of a text item and returning the word
	 * 
	 * @param context an ApplicationContext
	 * @param width a float representing the width of the screen
	 * @param height a float representing the height of the screen
	 * @param pos a Position
	 * @param dy an int representing the movement vertically
	 * @param mes a Words
	 * @return a Words with his new position
	 */
	public Words pushNextMessageY(ApplicationContext context, float width, float height, Position pos, int dy, Words mes) {
		for (var m : this.message) {
			if (m.getPos().equals(mes.getPos()) && !(m.equals(mes))) {
				context.renderFrame(graphics -> {
					graphics.clearRect((int) Math.floor(width * m.getPos().getX() / Main.X),
							(int) Math.floor(height * m.getPos().getY() / Main.Y), (int) width / Main.X,
							(int) height / Main.Y);
				});
				m.setY(m.getPos().getY() + dy);
				return m;
			}
		}
		return null;
	}

	/**
	 * Function checking if two text items have the same position
	 * 
	 * @return true if two text items have the same position and false otherwise
	 */
	public boolean samePosMess() {
		for (var m : this.message) {
			for (var m2 : this.message) {
				if (m.getPos().equals(m2.getPos()) && !(m.equals(m2))) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Setter for the value of the field rules
	 * 
	 * @param rules a list of Rules
	 */
	public void setRules(ArrayList<Rules> rules) {
		this.rules.clear();
		for (var r : rules) {
			this.rules.add(r);
		}
	}
	
	// Override of the methods toString
	
	@Override
	public String toString() {
		var str = new StringBuilder();
		str.append("********Board********\n\n");
		str.append("****Playable element on board:****\n");
		playable.forEach(thing -> str.append(thing).append('\n'));
		str.append("\n****Message element on board:****\n");
		message.forEach(thing -> str.append(thing).append('\n'));
		str.append("\n****Valid rules on board:****\n");
		getRules().forEach(thing -> str.append(thing).append('\n'));
		str.append("\n*********************");
		return str.toString();
	}

}