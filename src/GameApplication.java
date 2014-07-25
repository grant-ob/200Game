/*********************************************************************************
 * GameApplication (class)
 * 
 * A class containing the main function for the 200 game. Creates a
 * GameRunner object and calls the gameLoop() method.
 * 
 * @author - Grant O'Brien, Nicholas Noel and Michael McKenzie
 *
 ********************************************************************************/

public class GameApplication {
	
	/** main *********************************************************************
	 * The starting point of the application
	 * 
	 * @param args
	 ****************************************************************************/
	
	public static void main(String[] args) {
		GameRunner runner = new GameRunner();
		runner.gameLoop();
	}

}
