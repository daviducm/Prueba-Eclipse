package tp1.logic.lists;

import tp1.logic.Position;
import tp1.logic.gameobjects.ShockWave;


import tp1.logic.gameobjects.RegularAlien;
import tp1.logic.gameobjects.UCMLaser;
import tp1.view.Messages;
import tp1.logic.Game; 	

/**
 * Container of regular aliens, implemented as an array with a counter
 * It is in charge of forwarding petitions from the game to each regular alien
 * 
 */
public class RegularAlienList {

	private RegularAlien[] objects; 
	private int num;
	
	public RegularAlienList() {
		objects = new RegularAlien[72];  
		num = 0; 
	}
	
///////////////////////////
/** Add alien methods **/
///////////////////////////
	public void add(RegularAlien alien) {
		objects[num] = alien; 
		num += 1; 
	}
	
	// see
	private void remove(RegularAlien alien) {
		alien.die();
		alien = null; 
		num -= 1; 
	}
	
	public int size() {
		return this.num; 
	}

	
	public void removeDead() {
		for (int i = 0; i < num; i++) {
			if (!objects[i].isAlive()) {
				remove(objects[i]); 
				displaceAlive(i); 
				if (i != num)
					i--; 
			}
		}
	}
	private void displaceAlive(int i) {
		while (i < num) {
			objects[i] = objects[i+1]; 
			i++; 
		}
	}
	
	
	public String toString(Position pos) {
		String ret = " "; 
		int i = 0; 
		while (i < num && ret.equals(" ")){
			ret = objects[i].toString(pos); 
			i++; 
		}
		return ret; 
	}
	
	public void automaticMoves() {
		for (int i = 0; i < num; i++) {
			objects[i].automaticMove();
		}
		for (int j = 0; j < num; j++) {
			objects[j].performMovement(); 
		}
		
	}
	
	public boolean checkAttacks(UCMLaser laser) {
		int i = 0; 
		boolean attack = false; 
				
		while (laser.isAlive() && i < num) {
			attack = laser.performAttack(objects[i]); 
			i++; 
		}
		return attack; 
	}
	
	public boolean receiveAttacks(ShockWave shockwave) {
		for (int i = 0; i < num; i++) {
			objects[i].receiveAttack(shockwave); 
		}
		return true; 
	}
	

}
