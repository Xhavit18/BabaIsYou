package fr.umlv.BabaIsYou;

import java.util.ArrayList;
import fr.umlv.BabaIsYou.Names.Decor;

/**
 * Class that contains methods that creates and manage the rules of the game
 * 
 * @author Ghamri Samy and Osaj Xhavit
 *
 */
public class Rules {

	private final Names name;
	private final Operators op;
	private final Words property;

	/**
	 * Constructor for the type Rules representing the rules.
	 * 
	 * @param name a Names
	 * @param op an Operators
	 * @param property a Properties
	 */
	public Rules(Names name, Operators op, Words property) {

		this.name = name;
		this.op = op;
		if (property.isProperty())
			this.property = property;
		else
			throw new IllegalArgumentException("This cannot be considered as a property!");
	}

	/**
	 * Function that checks if a rules is valid.
	 * 
	 * @return true if the rules is valid or false otherwise
	 */
	public Boolean isValidRule() {

		Position name = this.getName().getPos();
		Position op = this.getOp().getPos();
		Position prop = this.getProp().getPos();

		if (((name.getX() == (op.getX() - 1)) && (op.getX() == (prop.getX() - 1)))
				&& ((name.getY() == op.getY()) && (op.getY() == prop.getY()))) {
			return true;
		}

		if (((name.getY() == (op.getY() - 1)) && (op.getY() == (prop.getY() - 1)))
				&& ((name.getX() == op.getX()) && (op.getX() == prop.getX()))) {
			return true;
		}

		return false;
	}

	/**
	 * Getter for the field name
	 * 
	 * @return a Names representing the value of the field name
	 */
	public Names getName() {
		return this.name;
	}
	
	/**
	 * Getter for the field op
	 * 
	 * @return an Operators representing the value of the field op
	 */
	private Operators getOp() {
		return this.op;
	}

	/**
	 * Getter for the field property
	 * 
	 * @return an Words because a property can also be a noun (Wall Is Rock, ...) representing the value of the field property
	 */
	public Words getProp() {
		return this.property;
	}

	/**
	 * Function checking if two elements are next to each other
	 * 
	 * @param a a Words
	 * @param b a Words
	 * @return true if the two elements are next to each other or false otherwise
	 */
	private static boolean isPosPlus(Words a, Words b) {
		return (a.getPos().getX() == b.getPos().getX() - 1 || a.getPos().getY() == b.getPos().getY() - 1);
	}
	
	/**
	 * Function adding elements in their respective list representing the messages of the game (Baba, Is, You, ...)
	 * 
	 * @param game a GameBoard to get the different elements of the game
	 * @param names a list of Words representing the nouns
	 * @param op a list of Words representing the operators
	 * @param prop a list of Words representing the properties
	 */
	public static void forPick(GameBoard game, ArrayList<Words> names, ArrayList<Words> op, ArrayList<Words> prop) {
		
		for (var mes : game.getMessage()) {
			if (Decor.isDecor(mes.getW()) && !names.contains(mes)) {
				names.add(mes);
			}
			if (Operators.isOp(mes.getW()) && !op.contains(mes)) {
				op.add(mes);
			}
			if (mes.isProperty() && !prop.contains(mes)) {
				prop.add(mes);		
			}
		}
	}
	
	/**
	 * Function that tests in real time all the possible valid rules we can make in the game
	 * 
	 * @param game a GameBoard to get the different elements of the game
	 * @param names a list of Words representing the nouns
	 * @param op a list of Words representing the operators
	 * @param prop a list of Words representing the properties
	 * @param rules a list of Rules representing the rules
	 * @param tmp a Rules
	 * @param nm a Decor
	 * @param o an Operator
	 * @param p a property
	 */
	private static void tripleForPick(GameBoard game, ArrayList<Words> names, ArrayList<Words> op, ArrayList<Words> prop, ArrayList<Rules> rules, Rules tmp, Decor nm, Operators o, Words p) {
		for (var name : names) {
			nm = new Decor(name.getW(), name.getPos());
			for (var ope : op)
				if (isPosPlus(nm, ope)) {
					o = new Operators(ope.getW(), ope.getPos());
					for (var pro : prop)
						if (isPosPlus(o, pro)) {
							try {
								p = new Properties(pro.getW(), pro.getPos());
							} catch (Exception e) {
								p = new Decor(pro.getW(), pro.getPos());
							}
							tmp = new Rules(nm, o, p);
							if (tmp.isValidRule())
								rules.add(tmp);
						}
				}
		}
	}

	/**
	 * Function that picks the rules during the game 
	 * 
	 * @param game a GameBoard to get the different elements of the game
	 * @param names a list of Words representing the nouns
	 * @param op a list of Words representing the operators
	 * @param prop a list of Words representing the properties
	 */
	public static void pickRulesFromBoard(GameBoard game, ArrayList<Words> names, ArrayList<Words> op, ArrayList<Words> prop) {
		ArrayList<Rules> rules = new ArrayList<Rules>();
		Rules tmp = null;
		Decor nm = null;
		Operators o = null;
		Words p = null;
		tripleForPick(game, names, op, prop, rules, tmp, nm, o, p);
		game.setRules(rules);
	}
	
	/**
	 * Methods that executes rules entered in the command line
	 * 
	 * @param args an array of String
	 * @param game a GameBoard to get the different elements of the game
	 */
	public static void executeRulesFromCommandLine(String[] args, GameBoard game) {
		for (int argument = 0; argument < args.length; argument++) {
			if ( args[argument].equals("--execute")) {
				Decor name = new Decor(args[argument + 1]);
				Operators operator = new Operators(args[argument + 2]);
				Words property;
				try {
					property = new Properties(args[argument + 3]);
				} catch (Exception e) {
					property = new Decor(args[argument + 3]);
				}
				Rules r = new Rules(name, operator, property);
				game.add(r);
			}
		}
	}
	
	// Override of the methods toString

	@Override
	public String toString() {
		return this.getName().getW() + " " + this.getOp().getW() + " " + this.getProp().getW();
	}
}