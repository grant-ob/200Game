import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;


@SuppressWarnings("serial")
public class toggleMusicMenuItem extends JMenuItem implements ActionListener{
	private Game game;
	
	toggleMusicMenuItem(String name, Game game){
		super(name);
		this.game = game;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		game.toggleMusic();
	}
	
}
