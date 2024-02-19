package tp1.logic.gameobjects;

import tp1.logic.lists.DestroyerAlienList;
import tp1.logic.lists.RegularAlienList;

public class ShockWave {
	boolean status = false; 
	private static final int DAMAGE = 1; 
	
	public boolean status() {
		return status;    
	}
	
	public int getDamage() {
		return DAMAGE; 
	}
	
	public void enable() {
		status = true; 
	}
	
	public void disable() {
		status = false; 
	}
	
	public boolean weaponAttack(RegularAlienList aliens) {
		return aliens.receiveAttacks(this);
	}
	public boolean weaponAttack(DestroyerAlienList destroyers) {
		return destroyers.receiveAttacks(this); 
	}
	
	
}
