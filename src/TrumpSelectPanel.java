import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


public class TrumpSelectPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private JLabel label;
	private JRadioButton hearts, spades, clubs, diamonds;
	private OkButton okButton;
	
	TrumpSelectPanel(Game game){
		label = new JLabel("<html><u>Choose Trump</u>");
		hearts = new JRadioButton("Hearts");
		clubs = new JRadioButton("Clubs");
		spades = new JRadioButton("Spades");
		diamonds = new JRadioButton("Diamonds");
		okButton = new OkButton("OK", this, game);
		
		setLayout(null);
		
		ButtonGroup suits = new ButtonGroup();
		suits.add(hearts);
		suits.add(clubs);
		suits.add(diamonds);
		suits.add(spades);
		hearts.setSelected(true);
		
		label.setBounds(120, 0, 100, 50);
		hearts.setBounds(75,50,100,25);
		clubs.setBounds(175,50,100,25);
		diamonds.setBounds(75,75,100,25);
		spades.setBounds(175,75,100,25);
		okButton.setBounds(120,110,75,25);
		
		okButton.addActionListener(okButton);
		
		add(label);
		add(hearts);
		add(clubs);
		add(diamonds);
		add(spades);
		add(okButton);
				
	}
	
	public Suit getSelection(){
		if(hearts.getSelectedObjects() != null)
			return Suit.HEARTS;
		else if(clubs.getSelectedObjects() != null)
			return Suit.CLUBS;
		else if(spades.getSelectedObjects() != null)
			return Suit.SPADES;
		else if(diamonds.getSelectedObjects() != null)
			return Suit.DIAMONDS;
		return null;
	}
	
	public void enableTrumpPanel(){
		setVisible(true);
	}
	
	public void disableTrumpPanel(){
		setVisible(false);
	}
	
	
	private class OkButton extends JButton implements ActionListener{
		private static final long serialVersionUID = 1L;
		private TrumpSelectPanel tsp;
		private Game game;
		
		OkButton(String name, TrumpSelectPanel tsp, Game game){
			super(name);
			this.tsp = tsp;
			this.game = game;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			game.playerTrump(tsp.getSelection());
			tsp.setVisible(false);
		}
		
	}

}
