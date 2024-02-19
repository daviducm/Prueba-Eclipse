package tp1.logic.gameobjects;

import tp1.logic.Game;
import tp1.logic.Move;
import tp1.logic.Position;
import tp1.view.Messages;

public class Bomb {
	
	private static final int ARMOUR = 1; 
	public static final int DAMAGE = 1; 
	
	private Move dir;
	private Game game;
	private Position pos; 
	private int life;  
	private DestroyerAlien shootingAlien; 
	
	public Bomb (Game game, DestroyerAlien alien, Position pos) {
		life = ARMOUR; 
		dir = Move.DOWN; 
		this.pos = pos.move(Move.NONE); 
		shootingAlien = alien; 
		this.game = game; 
	}
	
	public String getSymbol() {
		return Messages.BOMB_SYMBOL; 
	}
	
	public int getLife() {
		return this.life; 
	}
	
	public boolean isAlive() {
		return getLife() > 0; 
	}
	
	private boolean isOut() {
		return this.pos.getRow() == Game.DIM_Y; 
	}
	
	public int getDamage() {
		return DAMAGE; 
	}
	
	public void die() {
		this.life = 0; 
		onDelete(); 
	}
	
	public void onDelete() {
		shootingAlien.enableBomb();
	}
	
	public String toString(Position pos) { // Used in positionToString
		if (this != null && pos.equals(this.pos))
			return getSymbol(); 
		else 
			return " "; 
	}
	
	public boolean receiveAttack(UCMLaser laser) {
		receiveDamage(laser); 
		return true;
	}
	
	public void receiveDamage(UCMLaser laser) {
		this.life -= laser.getDamage(); 
	}
	
	public void automaticMove () {
		performMovement(this.dir);
		game.performAttack(this);
		if(isAlive() && isOut()) {
			die();
		}
	}
	
	private void performMovement(Move dir) {
		pos = pos.move(dir); 
	}
	
	public boolean isInPosition(Position pos) {
		return this.pos.equals(pos); 
	}
	
	public boolean performAttack(UCMShip other) {	
		if (other.equals(this.pos) && this.isAlive() && other.isAlive())
			return weaponAttack(other); 
		else
			return false; 
	}
	
	public boolean performAttack(UCMLaser other) {
		if (this.isAlive() && other.isAlive() && other.equals(this.pos))
			return weaponAttack(other); 
		else
			return false; 
	}
	
	private boolean weaponAttack(UCMLaser other) {
		die(); 
		return other.receiveAttack(this); 
	}
	
	private boolean weaponAttack(UCMShip other) {
		die(); 
		return other.receiveAttack(this); 
	}

}
