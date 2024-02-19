package tp1.logic.lists;
import tp1.logic.Position;

import tp1.logic.gameobjects.ShockWave;
import java.util.Random; 
import tp1.logic.gameobjects.DestroyerAlien;
import tp1.logic.gameobjects.RegularAlien;
import tp1.logic.gameobjects.UCMLaser;
import tp1.view.Messages;
import tp1.logic.Game; 

public class DestroyerAlienList {
	private DestroyerAlien objects[]; 
	private int num; 
	
	public DestroyerAlienList() {
		objects = new DestroyerAlien[10]; 
		num = 0; 
	}
	
	public void add(DestroyerAlien alien) {
		objects[num] = alien; 
		num += 1; 
	}
	
	private void remove(DestroyerAlien alien) {
		alien.die();
		num -= 1; 
	}
	
	public int size() {
		return this.num; 
	}
	
	public void removeDead() {
		for (int i = 0; i < num; i++) {
			if (!objects[i].isAlive()) {
				remove(objects[i]); 
				objects[i] = null; 
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
	
	public void computerActions() {
		for (int i = 0; i < num; i++) {
			objects[i].computerActions();
		}
	}
}
