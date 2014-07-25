//package Game;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

/********************************************************************************
 * GUIRunner (class)
 * 
 * A class representing the creation and management of all JFrames.
 * 
 * @author Grant O'Brien, Nicholas Noel, Michael McKenzie
 *
 ********************************************************************************/
public class GUIRunner{
	
	private JFrame frame = new JFrame("200 : The Card Game");
	private LayeredGUI contentPane;
	
	private MenuBar menu;

	//The dimensions of the main frame
	static public final int FRAME_X = 200;
	static public final int FRAME_Y = 100;
	static public final int FRAME_WIDTH = 850;
	static public final int FRAME_HEIGHT = 650;
	
	/** GUIRunner*************************************************************
	 * Constructor for the GUIRunner class.
	 * 
	 * @param game    - A reference to the game
	 * @param players - A references to all the players in the game
	 **************************************************************************/
	GUIRunner(Game game, ArrayList<Player> players){

		menu = new MenuBar(game);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(FRAME_X, FRAME_Y, FRAME_WIDTH, FRAME_HEIGHT);
		frame.setJMenuBar((MenuBar)menu);

		contentPane = new LayeredGUI(game, players);
		contentPane.setOpaque(true);
		
		frame.setContentPane(contentPane);
		frame.setVisible(true);
		frame.setResizable(false);
	}
	
	/** resetHand ********************************************************************
	 * Calls the resetHand method for the layered components inside of frame
	 * 
	 ******************************************************************************/
	public void resetHand(){
		contentPane.resetHand();
	}

	/** repaint ********************************************************************
	 * Notifies the painter that frame needs to be repainted
	 * 
	 ******************************************************************************/
	public void repaint(){
		frame.repaint();
	}
	
	public void updateTrick(ArrayList<Card> trick, int position){
		contentPane.updateTrick(trick, position);
	}
	
	public void hideCard(int playerIndex, Card card){
		contentPane.hideCard(playerIndex, card);
	}
	
	public void printLine(String string){
		contentPane.printLine(string);
		
	}

	public void clearConsole() {
		contentPane.clearConsole();
	}
	
	public void refreshImages(){
		contentPane.refreshImages();
		repaint();
	}
	
	public void refreshStatus(){
		contentPane.refreshStatus();
	}
	
	public void refreshLastTrick(){
		contentPane.refreshLastTrick();
	}

	public void clearLastTrick() {
		contentPane.clearLastTrick();
	}
	
	public void setBackground(ImageIcon image){
		contentPane.setBackground(image);
	}
	
	public void enableBidPanel(){
		contentPane.enableBidPanel();
	}
	
	public void disableBidPanel(){
		contentPane.disableBidPanel();
	}
	
	public void enableTrumpPanel(){
		contentPane.enableTrumpPanel();
	}
	
	public void disableTrumpPanel(){
		contentPane.disableTrumpPanel();
	}

	public void refreshKitty(boolean b) {
		contentPane.refreshKitty(b);
		
	}

	public void removeKitty() {
		contentPane.removeKitty();		
	}

	public void disableTrick() {
		contentPane.disableTrick();		
	}

	public void enableHint() {
		contentPane.enableHint();		
	}

	public void disableHint() {
		contentPane.disableHint();		
	}

	public void toggleSoundIcon() {
		contentPane.toggleSoundIcon();		
	}
		
}

//In case more JFrames are created
//JFrame.DISPOSE_ON_CLOSE