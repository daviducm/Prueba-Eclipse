package tp1.logic.gameobjects;



import tp1.logic.Game;
import tp1.logic.Move;
import tp1.logic.Position;
import tp1.view.Messages;

/**
 * Class that represents the laser fired by {@link UCMShip}
 */
public class UCMLaser {
	
	private static final int ARMOUR = 1; 
	public static final int DAMAGE = 1; 
	
	private Move dir;
	private Game game;
	private Position pos; 
	private int life; 


	
	public UCMLaser(Game game, Position pos) {
		life = 1; 
		dir = Move.UP; 
		this.game = game; 
		this.pos = pos;
	}
	
	public void enable(Position pos) {
		this.pos = pos.move(Move.NONE); 
	}
	
	
	public String getSymbol() {
		return Messages.LASER_SYMBOL; 
	}
	
	public int getDamage() {
		return DAMAGE; 
	}
	
	public int getLife() {
		return this.life; 
	}
	
	
	
	public String toString(Position pos) { // Used in positionToString
		if (this.pos != null && pos.equals(this.pos))
			return getSymbol(); 
		else 
			return " "; 
	}
	
	public void automaticMove () {
		if (isAlive()) {
			performMovement(this.dir);
			game.performAttack(this); 
			
			if(isAlive() && isOut()) {
				die();
			}
		}
	}
	private void performMovement(Move dir) {
		pos = pos.move(dir); 
	}
	
	public boolean isAlive() {
		return getLife() > 0 && this.pos != null; 
	}
	
	private boolean isOut() {
		return this.pos.getRow() == -1; 
	}
	
	private void die() {
		this.life = 0; 
		onDelete(); 
	}
	
	public void onDelete() {
		this.pos = null; 
	}
	
	/**
	 * Method that implements the attack by the laser to a regular alien.
	 * It checks whether both objects are alive and in the same position.
	 * If so call the "actual" attack method {@link weaponAttack}.
	 * @param other the regular alien possibly under attack
	 * @return <code>true</code> if the alien has been attacked by the laser.
	 */
	
	public boolean performAttack(RegularAlien other) {	
		if (other.equals(this.pos) && this.isAlive() && other.isAlive())
			return weaponAttack(other); 
		else
			return false; 
	}
	
	/**
	 * Method that implements the attack by the laser to a destroyer alien.
	 * It checks whether both objects are alive and in the same position.
	 * If so call the "actual" attack method {@link weaponAttack}.
	 * @param other the destroyer alien possibly under attack
	 * @return <code>true</code> if the alien has been attacked by the laser.
	 */

	public boolean performAttack(DestroyerAlien other) {
		if (other.equals(this.pos) && this.isAlive() && other.isAlive())
			return weaponAttack(other); 
		else
			return false;
	}
	
	public boolean performAttack(Bomb other) {
		if (other.isInPosition(this.pos) && this.isAlive() && other.isAlive())
			return weaponAttack(other); 
		else
			return false;
	}
	
	public boolean performAttack(Ufo other) {
		if (this.isAlive() && other.isAlive() && other.equals(this.pos))
			return weaponAttack(other); 
		else
			return false;
	}
	
	/**
	 * 
	 * @param other regular alien under attack by the laser
	 * @return always returns <code>true</code>
	 */
	private boolean weaponAttack(RegularAlien other) {
		die(); 
		return other.receiveAttack(this);	
	}
	
	private boolean weaponAttack(DestroyerAlien other) {
		die(); 
		return other.receiveAttack(this); 
	}
	    
	private boolean weaponAttack(Bomb other) {
		die(); 
		return other.receiveAttack(this); 
	}
	
	private boolean weaponAttack(Ufo other) {
		die(); 
		return other.receiveAttack(this); 
	}
	
	/**
	 * Method to implement the effect of bomb attack on a laser
	 * @param weapon the received bomb
	 * @return always returns <code>true</code>
	 */
	public boolean equals(Position pos) {
		return this.pos.equals(pos); 
	}
	public boolean checkAttack(Bomb weapon) {
		return weapon.performAttack(this); 
	}
	
	public boolean receiveAttack(Bomb weapon) {
		receiveDamage(weapon);
		return true;
	}
	
	
	public void receiveDamage(Bomb weapon) {
		this.life -= weapon.getDamage(); 
		if (!isAlive())
			die(); 
	}
	

}
