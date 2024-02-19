package tp1.logic.gameobjects;

import tp1.logic.AlienManager;
import tp1.logic.Game;
import tp1.logic.Move;
import tp1.logic.Position;
import tp1.view.Messages;

public class DestroyerAlien {
	
	private static final int ARMOR = 1; 
	private static final int HARM = 1; 
	private static final int POINTS = 10; 
	
	private Position pos; 
	private int life; 
	private Game game; 
	private int cyclesToMove;
	private int speed;
	protected Move dir;
	
	private boolean shootBomb;
	private boolean bombShot; 
	
	private AlienManager alienManager;
	
	
	public DestroyerAlien(Game game, Position pos, AlienManager alienManager) {
		this.pos = pos.move(Move.NONE); 
		this.life = ARMOR; 
		bombShot = false; 
		this.game = game; 
		dir = Move.LEFT; 
		speed = game.getNumCyclesToMoveOneCell(); 
		cyclesToMove = speed; 
		this.alienManager = alienManager; 
		shootBomb = false; 
	}
	
	private String getSymbol() {
		return Messages.DESTROYER_ALIEN_SYMBOL; 
	}
	private static String getDescription() {
		return Messages.DESTROYER_ALIEN_DESCRIPTION; 
	}
	
	public String toString(Position pos) {
		if (pos.equals(this.pos))
			return Messages.status(getSymbol(), this.life); 
		else
			return " "; 
	}
	public static String getInfo() {
		return String.format(getDescription() + ": points='" + POINTS + "', damage='" + 
				HARM + "', endurance='" + ARMOR + "'");  
	}
	
	public boolean equals(Position pos) {
		return this.pos.equals(pos); 
	}
	
	public boolean isInFinalRow() {
		return this.pos.getRow() == Game.DIM_Y - 1; 
	}
	
	public static int getDamage() {
		return HARM; 
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
		
		shootBomb(); 
		alienManager.finalRow(this.pos);
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
	private boolean readyToDescend() {
		return alienManager.readyToDescend(); 
	}
	
	
	private void descent() {
		this.pos = pos.move(Move.DOWN); 
		this.dir = dir.switchDir(); 
	}
	
	public boolean isAlive() {
		return getLife() > 0 && this != null; 
	}
	
	public int getLife() {
		return this.life; 
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
	
	
	public void enableBomb() {
		bombShot = false; 
	}
	
	public boolean shootBomb() {
		if (shootBomb && !bombShot) {
			Bomb bomb = new Bomb(game, this, this.pos); 
			game.addObject(bomb);
			shootBomb = false; 
			bombShot = true; 
		}
		return true; 
	}
	
	public void computerActions() { 
		if (!bombShot && canGenerateRandomBomb()) {
			shootBomb = true; 
		}
	}
	
	private boolean canGenerateRandomBomb(){
		return game.getRandom().nextDouble() < game.getLevel().getShootingFreq() ;
	}
	

}
