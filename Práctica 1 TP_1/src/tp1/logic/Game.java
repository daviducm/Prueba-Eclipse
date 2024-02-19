package tp1.logic;

import java.util.Random;
import tp1.logic.gameobjects.ShockWave;
import tp1.logic.gameobjects.Ufo;
import tp1.logic.gameobjects.DestroyerAlien;
import tp1.logic.gameobjects.Bomb;
import tp1.logic.lists.BombList;
import tp1.logic.gameobjects.UCMShip;
import tp1.logic.gameobjects.UCMLaser; 
import tp1.view.Messages;
import tp1.logic.gameobjects.RegularAlien; 
import tp1.logic.lists.RegularAlienList;
import tp1.logic.lists.DestroyerAlienList;


public class Game {

	public static final int DIM_X = 9;
	public static final int DIM_Y = 8;

	private RegularAlienList regularAliens;
	private DestroyerAlienList destroyerAliens; 
	private UCMShip player; 
	private UCMLaser laser; 
	private Level level; 
	private AlienManager alienManager; 
	private BombList bombList; 
	private Ufo ufo; 
	private ShockWave shockWave; 
	
	private boolean doExit; 
	private int currentCycle; 
	private int points; 
	private long seed; 
	
	private Random rand; 
	
	
	
	public Game(Level level, long seed) {
		this.level = level; 
		this.seed = seed; 
		initializer();
	}
	
	public void reset() {
		initializer(); 
	}
	
	private void initializer() {
		alienManager = new AlienManager(this, level, level.getRegularAliens() + level.getDestroyerAliens()); 
		regularAliens = alienManager.initializeRegularAliens(); 
		destroyerAliens = alienManager.initializeDestroyerAliens(); 
		bombList = new BombList();  
		
		laser = new UCMLaser(this, null); 
		player = new UCMShip(this); 
		ufo = new Ufo(this);
		shockWave = new ShockWave(); 
		rand  = new Random(seed); 
		
		doExit = false; 
		points = 0; 
		currentCycle = 0; 
	}
	
	
	
	public void update() {
		computerActions(); 
		automaticMoves(); 
		removeDead(); 
		updateCycle();  
	}
	
	private void automaticMoves() {
		regularAliens.automaticMoves();
		destroyerAliens.automaticMoves();
		bombList.automaticMoves();
		ufoAutomaticMove(); 
		laserAutomaticMove(); 
	}
	
	private void ufoAutomaticMove() {
		ufo.automaticMovement(); 
	}
	
	private void laserAutomaticMove() {
		if (laser.isAlive())
			laser.automaticMove();
	}
	
	private void computerActions() { 
		destroyerAliens.computerActions();
		ufo.computerAction();
	}
	
	private void removeDead() {
		regularAliens.removeDead();
		destroyerAliens.removeDead();
		bombList.removeDead();
	}
	
	private void updateCycle(){
		this.currentCycle += 1; 
	}
	
	public int getCycle() {
		return this.currentCycle; 
	}
	
	
	
	
	public String positionToString(int col, int row) {
		String ret; 
		Position posi = new Position(col, row); 
		ret = player.toString(posi); 
		if (ret.equals(" ")) {
			ret = regularAliens.toString(posi); 
			
			if (ret.equals(" ")) {
				ret = destroyerAliens.toString(posi); 
				
				if (ret.equals(" ")) {
					ret = laser.toString(posi); 
					
					if (ret.equals(" ")) {
						ret = bombList.toString(posi); 
						
						if (ret.equals((" ")))
								ret = ufo.toString(posi); 
					}
				}
			}
					
		}
		return ret;
	}
	
	public String stateToString() {
		String aux = "OFF"; 
		if (shockWave.status() == true)
			aux = "ON"; 
		
		StringBuilder sb = new StringBuilder(); 
		sb.append(player.stateToString() + "\n"); 
		sb.append("Points: " + this.points + "\n"); 
		sb.append("ShockWave: " + aux + "\n"); 

		return sb.toString();
	}
	
	public String infoToString() {
		StringBuilder sb = new StringBuilder();
		sb.append(UCMShip.getInfo() + "\n"); 
		sb.append(RegularAlien.getInfo() + "\n"); 
		sb.append(DestroyerAlien.getInfo() + "\n"); 
		sb.append(Ufo.getInfo()); 
		
		return sb.toString(); 
	}

	
	public boolean shootLaser(Game game) { 
		return player.shootLaser(laser); 
	}
	
	
	public void performAttack(UCMLaser laser) {
		regularAliens.checkAttacks(laser);  
		destroyerAliens.checkAttacks(laser); 
		bombList.checkAttacks(laser); 
		ufo.checkAttacks(laser); 			
	}
	
	public void performAttack(Bomb bomb) {
		player.checkAttack(bomb); 
		laser.checkAttack(bomb); 
	}
	public void performAttack(ShockWave shockwave) {
		if (shockwave.status()) {
			shockwave.weaponAttack(destroyerAliens); 
			shockwave.weaponAttack(regularAliens); 
			shockwave.disable();
		}
	}
	
	public void checkAttacksTo() { // Used to check for laser attacks when aliens descend
		this.regularAliens.checkAttacks(laser);
		this.destroyerAliens.checkAttacks(laser); 
	}

	
	public void enableShockWave() {
		shockWave.enable();
	}
	
	public boolean shockWave() {
		if (shockWave.status()) {
			performAttack(shockWave);
			return true; 
		}
		else
			return false; 

	}

	public boolean playerWin() {
		return alienManager.allAlienDead();
	}
	
	
	public boolean aliensWin() {
		return !player.isAlive() || alienManager.finalRowReached();
	}
	
	public boolean isFinished() {
		return playerWin() || aliensWin() || doExit;
	}
	
	public void exit() {
		this.doExit = true; 
	}
	
	public void receivePoints(int p) {
		this.points += p; 
	}

	public Random getRandom() {
		return rand;
	}

	public Level getLevel() {
		return this.level; 
	}
	
	public int getRemainingAliens() {
		return alienManager.getRemainingAliens(); 
	}
	
	public int getNumCyclesToMoveOneCell() {
		return level.getLevelCycles(); 
	}
	
	public boolean move(Move move) {
		return this.player.move(move); 
	}	
	
	
	public void addObject(Bomb bomb) {
		bombList.add(bomb);
	}
	
	public void addObject() {
		Position aux = new Position(9, 0); 
		ufo = new Ufo(this, aux); 
	}
	
	public void addObject(UCMLaser laser) {
		this.laser = laser; 
	}
	
}
