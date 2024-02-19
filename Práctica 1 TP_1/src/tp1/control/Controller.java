package tp1.control;

// caca culo pedo pis asjpdofjasdfijaofds

import static tp1.view.Messages.debug;

import java.util.Scanner;

import tp1.Main;
import tp1.logic.Game;
import tp1.logic.Move;
import tp1.view.GamePrinter;
import tp1.view.Messages;

/**
 *  Accepts user input and coordinates the game execution logic
 */
public class Controller {

	private Game game;
	private Scanner scanner;
	private GamePrinter printer;

	public Controller(Game game, Scanner scanner) {
		this.game = game;
		this.scanner = scanner;
		printer = new GamePrinter(game);
	}

	/**
	 * Show prompt and request command.
	 *
	 * @return the player command as words
	 */
	private String[] prompt() {
		System.out.print(Messages.PROMPT);
		String line = scanner.nextLine();
		String[] words = line.toLowerCase().trim().split("\\s+");

		System.out.println(debug(line));

		return words;
	}

	/****** Runs the game logic ******/
	
	public void run() {
		boolean paint = true; 
		String[] commands; 
		printGame();
		do {
			commands = prompt(); 
			paint = performCommand(commands); 
			if (paint) {
				game.update();
				printGame();
			}
		} while(!game.isFinished()); 
		if (game.playerWin())
			System.out.println(Messages.PLAYER_WINS); 
		else if (game.aliensWin())
			System.out.println(Messages.ALIENS_WIN); 
	}

	public boolean performCommand(String[] input) {
		boolean paint = true;
		String command = input[0].toLowerCase(); 
		
		switch (command) {
			case "move":	
			case "m": 
				if (Move.validMove(input[1])) { 
					Move dir = Move.valueOf(input[1].toUpperCase()); 
					
					if (!this.game.move(dir)) { // If the movement results in the ship going out of bounds
						System.out.println(Messages.MOVEMENT_ERROR); 
						paint = false; 
					}
				}
				else {
					System.out.println(Messages.DIRECTION_ERROR + input[1]); 
					paint = false; 
				}
				break; 
			
			case "shoot":
			case "s": 
				if (!this.game.shootLaser(this.game)) { // If a laser is already on the field
					System.out.println(Messages.LASER_ERROR);
					paint = false; 
				}
				break; 
				
			case "shockwave":
			case "w": 
				if (!this.game.shockWave()) { // If there is no shock-wave available
					System.out.println(Messages.SHOCKWAVE_ERROR); 
					paint = false; 
				}
				break; 
				
			case "list":
			case "l": 
				System.out.println(game.infoToString()); 
				paint = false; 
				break; 

			case "reset":
			case "r": 
				game.reset(); 
				printGame(); 
				paint = false; 
				break; 

			case "help":
			case "h":
				System.out.println("hola");  // temporal
				paint = false; 
				break; 
				
			case "exit":
			case "e": 
				game.exit(); 
				System.out.println(Messages.PLAYER_QUITS); 
				paint = false; 
				break; 
				
			case "none":
			case "n": 
				break; 
				
			case "": // empty 
				break; 
				
			default: // non-existing command
				System.out.println(Messages.UNKNOWN_COMMAND); 
				paint = false; 
				break; 
		}
		return paint; 
	}
	
	private void printGame() {
		System.out.println(printer);
	}
	
	public void printEndMessage() {
		System.out.println(printer.endMessage());
	}
	
}
