package tp1.logic.lists;
import tp1.logic.Position;

import tp1.logic.gameobjects.Bomb;
import tp1.logic.gameobjects.RegularAlien;
import tp1.logic.gameobjects.UCMLaser;
import tp1.logic.gameobjects.UCMShip;
public class BombList {
	
	private Bomb objects[]; 
	private int num; 
	
	public BombList(){
		objects = new Bomb[72]; 
		num = 0; 
	}
	
	public void add(Bomb bomb) {
		objects[num] = bomb; 
		num += 1; 
	}
	
	private void remove(Bomb bomb) {
		bomb.die();
		bomb = null; 
		num -= 1; 
	}
	
	public int size() {
		return this.num; 
	}
	
	
	public void removeDead() {
		int aux = num; 
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
	

}
