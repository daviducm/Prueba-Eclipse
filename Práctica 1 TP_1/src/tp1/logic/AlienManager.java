package tp1.logic;

import tp1.logic.lists.DestroyerAlienList;
import tp1.logic.gameobjects.DestroyerAlien;
import tp1.logic.gameobjects.RegularAlien;
//import tp1.logic.lists.DestroyerAlienList;
import tp1.logic.lists.RegularAlienList;

/**
 * 
 * Manages alien initialization and
 * used by aliens to coordinate movement
 *
 */
public class AlienManager {
	
	private Level level;
	private Game game;
	private int remainingAliens;
	
	private boolean squadInFinalRow;
	private int shipsOnBorder;
	private boolean onBorder;

	public AlienManager(Game game, Level level, int numShips) {
		this.level = level;
		this.game = game;
		onBorder = false; 
		squadInFinalRow = false; 
		
		remainingAliens = numShips; 
	}
	
	// INITIALIZER METHODS
	
	private Position regAlienList[]; 
	
	private void regularAlienPosInitializer() {
		this.regAlienList = new Position[72]; 
		
		this.regAlienList[0] = new Position(2, 1); 
		this.regAlienList[1] = new Position(3, 1); 
		this.regAlienList[2] = new Position(4, 1); 
		this.regAlienList[3] = new Position(5, 1); 
		
		this.regAlienList[4] = new Position(2, 2); 
		this.regAlienList[5] = new Position(3, 2); 
		this.regAlienList[6] = new Position(4, 2); 
		this.regAlienList[7] = new Position(5, 2); 
	}
	
	private Position destAlienList[];
	
	private void destroyerAlienPosInitializer() {
		this.destAlienList = new Position[72]; 
		
		destAlienList[0] = new Position(3, 2); 
		destAlienList[1] = new Position(4, 2); 
		
		destAlienList[2] = new Position(3, 3); 
		destAlienList[3] = new Position(4, 3); 
		
		destAlienList[4] = new Position (2, 4); 
		destAlienList[5] = new Position (3, 4); 
		destAlienList[6] = new Position (4, 4); 
		destAlienList[7] = new Position (5, 4); 
	}
	
	/**
	 * Initializes the list of regular aliens
	 * @return the initial list of regular aliens according to the current level
	 */
	protected RegularAlienList initializeRegularAliens() {
		RegularAlienList alienList = new RegularAlienList();
		regularAlienPosInitializer(); 
		
		for (int i = 0; i < level.getRegularAliens(); i++) {
			RegularAlien alien = new RegularAlien(game, regAlienList[i], this); 
			alienList.add(alien);
		}
		return alienList;
	}

	/**
	 * Initializes the list of destroyer aliens
	 * @return the initial list of destroyer aliens according to the current level
	 */
	protected  DestroyerAlienList initializeDestroyerAliens() {
		DestroyerAlienList  destroyerList = new DestroyerAlienList(); 
		destroyerAlienPosInitializer(); 
		
		int i = 0; 
		if(level == Level.HARD) i = 2; 
		else if (level == Level.INSANE) i = 4; 
		int aux = level.getDestroyerAliens() + i; 
		while (i < aux) {
			DestroyerAlien alien = new DestroyerAlien(game, destAlienList[i], this); 
			destroyerList.add(alien);
			i++; 
		}
		return destroyerList; 
	}

	
	// CONTROL METHODS
		
	public void shipOnBorder() {
		if(!onBorder) {
			onBorder = true;
			shipsOnBorder = remainingAliens;
		}
	}
	
	public void decreaseOnBorder() {
		shipsOnBorder--; 
		if (shipsOnBorder == 0) {
			onBorder = false; 
			game.checkAttacksTo(); 
		} 
	}

	public boolean onBorder(Position pos, Move dir) {
		return pos.isBorder(dir); 
	}
	
	public boolean readyToDescend() {
		return onBorder;
	}
	
	public int getRemainingAliens() {
		return this.remainingAliens; 
	}
	
	public boolean finalRowReached() {
		return squadInFinalRow; 
	}
	public void finalRow(Position p) {
		if (p.isFinalRow())
			squadInFinalRow = true; 
	}
	
///////////////////////////
/** Dead alien methods **/
///////////////////////////
	
	public void alienDead() {
		remainingAliens -= 1; 
	}
	
	public boolean allAlienDead() {
		return remainingAliens == 0; 
	}
	
	public void haveLanded() {
		
	}

}
