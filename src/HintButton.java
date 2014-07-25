import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class HintButton extends JLabel implements MouseListener {
	private Game game;
	private HardAI ai;
	private Player player;
	
	HintButton(Game game, HardAI ai, Player player){
		this.game = game;
		this.ai = ai;
		this.player = player;
		super.setIcon(new ImageIcon(this.getClass().getResource("/resource/hint.png")));
		setPreferredSize(new Dimension(64, 30));
		setOpaque(false);
		setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		ai.reccomend(player.cardsInHand, game);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
}
