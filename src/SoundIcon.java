import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class SoundIcon extends JLabel implements MouseListener{
	private Game game;
	private ImageIcon image;
	private boolean playActive;
	
	SoundIcon(Game game){
		this.game = game;
		setImage(new ImageIcon(this.getClass().getResource("/resource/play.png")));
		playActive = true;
		setBounds(0, 0, 50, 50);
		setPreferredSize(new Dimension(50, 50));
		setOpaque(false);
		setVisible(true);
	}
	
	public void setImage(ImageIcon icon){
		super.setIcon(icon);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		game.toggleMusic();	
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {	
	}

	public void toggleIcon() {
		if(playActive){
			setImage(new ImageIcon(this.getClass().getResource("/resource/pause.png")));
			playActive = false;
		}
		else{
			setImage(new ImageIcon(this.getClass().getResource("/resource/play.png")));
			playActive = true;
		}
	}
}
