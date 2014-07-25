//package Game;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/********************************************************************************
 * CardPanel (class)
 * 
 * A class representing Card that is also a JComponent.  
 * 
 * @author Grant O'Brien, Nicholas Noel, Michael McKenzie
 *
 ********************************************************************************/
@SuppressWarnings("serial")
public class CardPanel extends JLabel implements MouseListener{

	private Card card;
	private Game game;

	/** CardPanel*************************************************************
	 * Constructor for the CardPanel class.
	 * 
	 * @param card - The card to be displayed
	 * @param game - A reference to the game
	 **************************************************************************/
	CardPanel(Card card, Game game){
		this.card = card;
		this.game = game;
		super.setIcon(card.getImageIcon());		
		
		setBounds(0, 0, Card.WIDTH, Card.HEIGHT);
		setPreferredSize(new Dimension(Card.WIDTH, Card.HEIGHT));
		setOpaque(false);
	}
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	/** mousePressed ********************************************************************
	 * Handles the event of a mousePress by checking with game whether the card
	 * can be played or not.
	 * 
	 ******************************************************************************/
	@Override
	public void mousePressed(MouseEvent e) {
		if(game.playerLayCard(card))		
			this.setVisible(false);
		else if(game.playerClickKitty(card)){
			
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {}
	
	public ImageIcon getImageIcon(){
		return card.getImageIcon();
	}
	
	/** setImageIcon ********************************************************************
	 * Set the image to be displayed for this Panel
	 * 
	 * @param i - The new ImageIcon
	 ******************************************************************************/
	public void setImageIcon(ImageIcon i){
		super.setIcon(i);
	}
	
	public Card getCard(){
		return card;
	}
	
	public void setCard(Card card){
		this.card = card;
	}
	
}
