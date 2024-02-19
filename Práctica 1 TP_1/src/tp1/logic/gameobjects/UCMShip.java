
package tp1.logic.gameobjects;
import tp1.logic.Game;
import tp1.logic.lists.BombList;
import tp1.logic.Move; 
import tp1.logic.Position; 
import tp1.view.Messages;

public class UCMShip {
	
	private static final int ARMOR = 3; 
	private static final int DAMAGE = 1; 
	
	private Game game; 
	private Position pos; 
	
	private int life; 
	
	
	public UCMShip(Game game) {
		this.game = game; 
		pos = new Position(4, 7); 
		life = ARMOR; 
	}
	
	
	public String toString(Position pos) { // Used in positionToString
		if (pos.equals(this.pos)) { 
			if (!isAlive())
				return Messages.UCMSHIP_DEAD_SYMBOL; 
			else
				return getSymbol(); 
		}
		else 
			return " "; 
	}
	
	public static String getInfo() {
		return String.format(getDescription() + ": damage='" + DAMAGE + "', endurance='" + ARMOR + "'"); // Appears with the command list
	}

	public String stateToString() {
		return String.format("Life: " + getLife()); // Returns part of the top visual text information
	}
	
	private String getSymbol() {
		return Messages.UCMSHIP_SYMBOL; 
	}
	
	public static String getDescription() {
		return Messages.UCMSHIP_DESCRIPTION; 
	}
	
	public boolean equals(Position pos) {
		return this.pos.equals(pos); 
	}
	
	public boolean move(Move move) {
		if (!isOut(move)) {
				performMovement(move);  
				return true; 
		}
		else
			return false; 
	}

	public void performMovement(Move move) {
		this.pos = this.pos.move(move); 
	}

	public void enableLaser() {
		UCMLaser aux_laser = new UCMLaser(game, pos); 
		game.addObject(aux_laser);
	}
	
	public boolean shootLaser(UCMLaser laser) {
		if (!laser.isAlive()) {
			enableLaser(); 
			return true; 
		}
		return false; 
	}
	
	public boolean isOut(Move move) {
		return this.pos.shipOut(move); 
	}
	
	public boolean checkAttack(Bomb bomb) {
		return bomb.performAttack(this); 
	}
	
	public boolean receiveAttack(Bomb bomb) {
		receiveDamage(bomb); 
		return true; 
	}
	
	public void receiveDamage(Bomb bomb) {
		this.life -= bomb.getDamage(); 
	}
	
	public boolean isAlive() {
		return this.getLife() > 0; 
	}
	
	public int getLife() {
		return life; 	
	}
	
	public static int getDamage() {
		return DAMAGE; 
	}
	
	
	
}
