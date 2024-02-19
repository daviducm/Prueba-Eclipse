
package tp1.logic;


// Represents the allowed moves of the Ship

public enum Move {
	LEFT(-1,0), LLEFT(-2,0), RIGHT(1,0), RRIGHT(2,0), DOWN(0,1), UP(0,-1), NONE(0,0);
	
	private int x;
	private int y;
	
	private Move(int x, int y) {
		this.x=x;
		this.y=y;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	
	public Move switchDir() {
		if (this == LEFT)
			return RIGHT; 
		else 
			return LEFT; 
	}
	
	public static boolean validMove(String s) {
		s = s.toUpperCase(); 
		return (s.equals("LEFT") || s.equals("LLEFT") || s.equals("RIGHT") || s.equals("RRIGHT")
				|| s.equals("DOWN") || s.equals("UP") || s.equals("NONE")); 
	}
	

	


}
