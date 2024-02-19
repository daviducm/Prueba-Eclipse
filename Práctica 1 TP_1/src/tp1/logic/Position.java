package tp1.logic;

/**
 * 
 * Immutable class to encapsulate and manipulate positions in the game board
 * 
 */
public class Position {

	private final int col;
	private final int row;
	
	public Position(int c, int r) {
		this.col = c; 
		this.row = r; 
	}
	
	public Position(Position pos) {
		this.col = pos.getColumn(); 
		this.row = pos.getRow(); 
	}
	
	public int getColumn() {
		return this.col; 
	}
	public int getRow() {
		return this.row; 
	}
	
	
	/****** toString ******/
	public String toString() {
		return String.format(this.col + " " + this.row); 
	}
	
	
	/****** Changes the position based on Move ******/
	/*public void changePosition(Move move) {
		int x = move.getX(); 
		int y = move.getY(); 
		this.col += x; 
		this.row += y; 
	}*/
	
	public boolean equals(Position pos) {
		if (this.col == pos.getColumn() && this.row == pos.getRow())
			return true; 
		else
			return false; 
	}
	
	/****** Allowed due to it being an immutable class ******/
	public Position move(Move move) {
		return new Position(this.col + move.getX(), this.row + move.getY()); 
	}
	
	public boolean isBorder(Move dir) {
		if (dir == Move.LEFT)
			return this.col == 0; 
		else if (dir == Move.RIGHT)
			return this.col == Game.DIM_X - 1; 
		else
			return false; 
	}
	
	public boolean isFinalRow() {
		return this.row == Game.DIM_Y - 1; 
	}
	
	public boolean ufoOut() {
		return this.getColumn() == -1; 
	}
	
	public boolean shipOut(Move dir) {
		return this.move(dir).getColumn() < 0 || this.move(dir).getColumn() >= Game.DIM_Y; 
	}

}
