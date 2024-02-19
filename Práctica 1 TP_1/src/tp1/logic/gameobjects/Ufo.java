package tp1.logic.gameobjects;

import tp1.logic.Game;
import tp1.logic.Move;
import tp1.logic.Position;
import tp1.view.Messages;


public class Ufo {
	
	private static final int ARMOR = 1; 
	private static final int POINTS = 25; 
	private static final int HARM = 0; 
	private boolean enabled;
	private Game game;
	private Position pos; 
	private int life; 
	private Move dir; 
	
	public Ufo (Game game) {
		life = 0; 
		pos = null; 
		this.game = game; 
		enabled = false; 
	}
	
	public Ufo(Game game, Position p) {
		life = ARMOR; 
		this.game = game; 
		pos = p; 
		dir = Move.LEFT; 
		enabled = true; 
		}
	

	public String getSymbol() {
		return Messages.UFO_SYMBOL; 
	}
	
	public static String getDescription() {
		return Messages.UFO_DESCRIPTION; 
	}
	
	public String toString(Position pos) {
		if (this.pos != null && pos.equals(this.pos))
			return Messages.status(getSymbol(), this.life); 
		else 
			return " "; 
	}
	
	public static String getInfo() {
		return String.format(getDescription() + ": points='" + POINTS + "', damage='" + 
				HARM + "', endurance='" + ARMOR + "'");  
	}
	
	public void computerAction() {
		if(!enabled && canGenerateRandomUfo()) {
			enable();
		}
	}
	
	private void enable() {
		game.addObject();
	}
	
	public boolean enabled() {
		return this.enabled; 
	}
	
	public int getLife() {
		return this.life; 
	}
	
	public boolean isAlive() {
		return getLife() > 0; 
	}
	
	public void die() {
		onDelete(); 
	}
	
	public boolean isOut() {
		return this.pos.ufoOut(); 
	}
	
	public boolean equals(Position pos) {
		return this.pos.equals(pos); 
	}

	public void onDelete() {
		this.pos = null; 
		life = 0; 
		enabled = false; 
	}
	
	
	public void automaticMovement() {
		if (enabled) {
			performMovement(); 
			if (isOut())
				onDelete(); 
		}
	}
	
	public void performMovement() {
		pos = pos.move(dir); 
	}
	
	public boolean receiveAttack(UCMLaser laser) {
		receiveDamage(laser);
		return true; 
	}

	public void receiveDamage(UCMLaser laser) {
		this.life -= laser.getDamage(); 
		if (!isAlive()) {
			game.receivePoints(POINTS);
			game.enableShockWave();;
			die(); 
		}
	}
	
	public boolean checkAttacks(UCMLaser laser) {
		return laser.performAttack(this); 
	}
	/**
	 * Checks if the game should generate an ufo.
	 * 
	 * @return <code>true</code> if an ufo should be generated.
	 */
	private boolean canGenerateRandomUfo(){
		return game.getRandom().nextDouble() < game.getLevel().getUfoFrequency();
	}
	
}
