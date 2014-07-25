import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;

import javax.sound.midi.Sequence;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;


@SuppressWarnings("serial")
public class ThemeMenuItem extends JMenuItem implements ActionListener{
	private ImageIcon bg;
	private ImageIcon cardBack;
	private String soundFile;
	private Game game;
	
	ThemeMenuItem(String name, URL bg, URL cardBack, String soundFile, Game game){
		super(name);
		this.bg = new ImageIcon(bg);
		this.cardBack = new ImageIcon(cardBack);
		this.soundFile = soundFile;
		this.game = game;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		game.setBackground(bg);
		Card.backSide = new Card(Suit.NONE, Rank.NONE, cardBack);
		game.toggleMusic();
		System.out.println(soundFile);
		game.setMusic(soundFile);
		game.toggleMusic();
		game.refreshImages();
	}

}
