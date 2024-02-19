package tp1.logic.gameobjects;

import tp1.logic.AlienManager;
import tp1.logic.Game;
import tp1.logic.Move;
import tp1.logic.Position;
import tp1.view.Messages;

/**
 * 
 * Class representing a regular alien
 *
 */
public class RegularAlien {
	
	private static final int ARMOR = 2; 
	private static final int POINTS = 5; 
	private static final int HARM = 0; 
	
	private AlienManager alienManager;
	private Position pos; 
	protected Move dir;
	private Game game; 
	
	private int cyclesToMove;
	private int life;
	private int speed;
	
	
	public RegularAlien(Game game, Position pos, AlienManager alienManager) {
		this.pos = pos.move(Move.NONE); 
		this.life = ARMOR; 
		this.game = game; 
		dir = Move.LEFT; 
		this.alienManager = alienManager; 
		this.speed = game.getNumCyclesToMoveOneCell(); 
		cyclesToMove = speed; 
	}
	
	
	
	public int getLife() {
		return this.life; 
	}
	public static int getDamage() {
		return HARM; 
	}
	
	private String getSymbol() {
		return Messages.REGULAR_ALIEN_SYMBOL; 
	}
	
	private static String getDescription() {
		return Messages.REGULAR_ALIEN_DESCRIPTION; 
	}
	
	public static String getInfo() {
		return String.format(getDescription() + ": points='" + POINTS + "', damage='" + 
				HARM + "', endurance='" + ARMOR + "'");  
	}
	
	
	
	public String toString(Position pos) { // Used in positionToString
		if (pos.equals(this.pos))
			return Messages.status(getSymbol(), this.life); 
		else
			return " "; 
	}
	
	public boolean equals(Position pos) {
		return this.pos.equals(pos); 
	}
	
	public boolean isAlive() {
		return getLife() > 0 && this != null; 
	}
	
	public void die() {
		if (!isAlive()) {
			alienManager.alienDead();
			onDelete(); 
		}
	}
	
	public void onDelete() {
		game.receivePoints(POINTS);
	}

	
	public boolean isInFinalRow() {
		return this.pos.getRow() == Game.DIM_Y - 1; 
	}
	
	public void automaticMove() {
		if (isInBorder())
			alienManager.shipOnBorder(); 
	} 
	
	public void performMovement() {
		if (readyToDescend()) {
			descent(); 
			alienManager.decreaseOnBorder(); 
		}
		else if (this.cyclesToMove == 0) {
			this.pos = pos.move(dir);  
			updateCycle(); 
		}
		else
			updateCycle(); 
		
		alienManager.finalRow(this.pos);
		
	}
	
	private boolean readyToDescend() {
		return alienManager.readyToDescend(); 
	}
	
	private void updateCycle() {
		if (this.cyclesToMove > 0 )
			this.cyclesToMove--; 
		else 
			this.cyclesToMove = speed; 
		}
	
	private boolean isInBorder() {
		if (this.pos.isBorder(dir)) {
			alienManager.onBorder(pos, dir); 
			return true; 
		}
		else
			return false; 
			
	}

	private void descent() {
			this.pos = pos.move(Move.DOWN); 
			this.dir = dir.switchDir(); 
	}
	
	public boolean receiveAttack(UCMLaser laser) {
		receiveDamage(laser); 
		return true;
	}
	
	public void receiveDamage(UCMLaser laser) {
		this.life -= laser.getDamage(); 
	}
	
	public boolean receiveAttack(ShockWave shockwave) {
		receiveDamage(shockwave); 
		return true; 
	}
	
	public void receiveDamage(ShockWave shockwave) {
		this.life -= shockwave.getDamage(); 
	}

}