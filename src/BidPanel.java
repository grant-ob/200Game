import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class BidPanel extends JPanel{
	
	private JLabel label;
	private JComboBox comboBox;
	private OkButton okButton;
	
	String[] bidStrings = { "PASS", "50", "55", "60", "65", "70",  // "0" represents a pass
			"75", "80", "85", "90", "95", "100"};

	
	BidPanel(final Game game){
		label = new JLabel("<html><u>Enter your bid.</u>");
		comboBox = new JComboBox(bidStrings);
		okButton = new OkButton("OK", this, game);
		
		setLayout(null);
		label.setBounds(130, 0, 100, 50);
		comboBox.setBounds(StatusPanel.STATUS_AREA_WIDTH/4,50, 100, 25);
		okButton.setBounds(200,50,75,25);
		
		comboBox.setSelectedIndex(0);
		okButton.addActionListener(	okButton);
		
		add(label);
		add(comboBox);
		add(okButton);
	}
	
	public int getSelection(){
		String value = (String)comboBox.getSelectedItem();
		int selection = 0;
		if(comboBox.getSelectedIndex() != 0){ 
			try  //Convert String to int
			{
				selection = Integer.parseInt(value.trim());
			}
			catch (NumberFormatException nfe)
			{
				System.out.println("NumberFormatException: " + nfe.getMessage());
			}
		}
		return selection;
	}
	
	public void enableBidPanel(){
		setVisible(true);
	}
	
	public void disableBidPanel(){
		setVisible(false);
	}
	
	
	private class OkButton extends JButton implements ActionListener{
		private BidPanel bp;
		private Game game;
		
		OkButton(String name, BidPanel bp, Game game){
			super(name);
			this.bp = bp;
			this.game = game;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			game.playerBid(bp.getSelection());
				//bp.setVisible(false);
		}
		
	}

}
