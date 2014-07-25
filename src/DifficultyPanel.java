import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;




public class DifficultyPanel extends JPanel {
	private ButtonGroup difficulties = new ButtonGroup();
	private JRadioButton easy, normal, hard, hardcore;
	private OkButton okButton;
	private Game game;
	
	public DifficultyPanel(Game game){
		
		easy = new JRadioButton("Easy");
		normal = new JRadioButton("Normal");
		hard = new JRadioButton("Hard");
		hardcore = new JRadioButton("Hardcore");
		okButton = new OkButton("OK", this, game);
		
		setLayout(null);
		
		difficulties.add(easy);
		difficulties.add(normal);
		difficulties.add(hard);
		difficulties.add(hardcore);
		normal.setSelected(true);
		
		easy.setBounds(75,40,100,25);
		normal.setBounds(175,40,100,25);
		hard.setBounds(75,65,100,25);
		hardcore.setBounds(175,65,100,25);
		okButton.setBounds(120,100,75,25);
		
		okButton.addActionListener(okButton);
		
		add(easy);
		add(normal);
		add(hard);
		add(hardcore);
		add(okButton);
		
		setBorder(
	            BorderFactory.createCompoundBorder( BorderFactory.createRaisedBevelBorder(), BorderFactory.createCompoundBorder( 
	            		BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),
	            		"Choose Difficulty"), BorderFactory.createEmptyBorder(5,5,5,5))));
		
		setVisible(true);
	}
	

	public Difficulty getSelection() {
		if(easy.getSelectedObjects() != null)
			return Difficulty.EASY;
		else if(normal.getSelectedObjects() != null)
			return Difficulty.NORMAL;
		else if(hard.getSelectedObjects() != null)
			return Difficulty.HARD;
		else if(hardcore.getSelectedObjects() != null)
			return Difficulty.HARDCORE;
		return null;
	}
	
	private class OkButton extends JButton implements ActionListener{
		private static final long serialVersionUID = 1L;
		private DifficultyPanel dp;
		private Game game;
		
		OkButton(String name, DifficultyPanel dp, Game game){
			super(name);
			this.dp = dp;
			this.game = game;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			game.setDifficulty(dp.getSelection());
			dp.setVisible(false);
		}
	}
}


