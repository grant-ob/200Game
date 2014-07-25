//package Game;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/********************************************************************************
 * LayeredGUI (class)
 * 
 * A class representing the creation and management of a layered Pane.  To be
 * used as a component in larger JObjects.
 * 
 * @author Grant O'Brien, Nicholas Noel, Michael McKenzie
 *
 ********************************************************************************/
@SuppressWarnings("serial")
public class LayeredGUI extends JPanel{
	
	private GamePanel gamePanel;
	private ConsolePanel consolePanel;
	private StatusPanel statusPanel;
	
	/** LayeredGUI*************************************************************
	 * Constructor for the LayeredGUI class.
	 * 
	 * @param game    - A reference to the game
	 * @param players - A references to all the players in the game
	 **************************************************************************/
	LayeredGUI(Game game, ArrayList<Player> players){
		
		gamePanel = new GamePanel(game, players);
		consolePanel = new ConsolePanel();
		statusPanel = new StatusPanel(game);

		setLayout(null);
		add(gamePanel);
		add(consolePanel);
		add(statusPanel);
		
		setVisible(true);
	}
	
	public void resetHand(){
		gamePanel.resetHand();
	}
	
	public void updateTrick(ArrayList<Card> trick, int playerPosition){
		gamePanel.updateTrick(trick, playerPosition);
	}
	
	public void refreshImages(){
		gamePanel.refreshImages();
	}
	
	public void hideCard(int playerIndex, Card card){
		gamePanel.hideCard(playerIndex, card);
	}
	
	public void printLine(String string){
		consolePanel.printLine(string);
	}
	
	public void clearConsole() {
		consolePanel.clearConsole();
	}
	
	public void refreshStatus(){
		statusPanel.refreshLabels();
	}
	
	public void refreshLastTrick(){
		statusPanel.refreshLastTrick();
	}
	
	public void clearLastTrick(){
		statusPanel.clearLastTrick();
	}
	
	public void setBackground(ImageIcon image){
		gamePanel.setBackground(image);
	}
	
	public void enableBidPanel(){
		statusPanel.enableBidPanel();
	}
	
	public void disableBidPanel(){
		statusPanel.disableBidPanel();
	}
	
	public void enableTrumpPanel(){
		statusPanel.enableTrumpPanel();
	}
	
	public void disableTrumpPanel(){
		statusPanel.disableTrumpPanel();
	}

	public void refreshKitty(boolean b) {
		gamePanel.refreshKitty(b);		
	}

	public void removeKitty() {
		gamePanel.removeKitty();		
	}

	public void disableTrick() {
		gamePanel.disableTrick();		
	}

	public void enableHint() {
		gamePanel.enableHint();		
	}

	public void disableHint() {
		gamePanel.disableHint();		
	}

	public void toggleSoundIcon() {
		gamePanel.toggleSoundIcon();
		
	}

}
