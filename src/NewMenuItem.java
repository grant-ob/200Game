import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JMenuItem;


public class NewMenuItem extends JMenuItem implements ActionListener{
	private Game game;
	
	NewMenuItem(String name, Game game){
		super(name);
		this.game = game;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	}
	
}
