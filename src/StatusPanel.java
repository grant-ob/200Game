import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class StatusPanel extends JPanel{
	
	private JLayeredPane lastTrick = new JLayeredPane();
	private BidPanel bidPanel;
	private TrumpSelectPanel trumpPanel;
	private JLabel lastTrickLabel = new JLabel("<html><u>Last Trick</u>"); 
	private JLabel teamOne = new JLabel("<html><u>Your Team</u>");
	private JLabel teamTwo = new JLabel("<html><u>AI Team</u>");
	private JLabel teamOneScore = new JLabel();
	private JLabel teamTwoScore = new JLabel();
	private JLabel trump = new JLabel("<html><u>Trump</u>");
	private JLabel trumpSuit = new JLabel();
	private CardPanel aCard;
	private Game game;
	
	public static final int STATUS_AREA_WIDTH = ConsolePanel.CONSOLE_AREA_WIDTH;
	public static final int STATUS_AREA_HEIGHT = GUIRunner.FRAME_HEIGHT - ConsolePanel.CONSOLE_AREA_HEIGHT;
	
	public StatusPanel(Game game){
		setBounds(GamePanel.gameAreaWidth, 0, ConsolePanel.CONSOLE_AREA_WIDTH-5, GamePanel.gameAreaHeight-250);
		this.game = game;
		bidPanel = new BidPanel(game);
		trumpPanel = new TrumpSelectPanel(game);
		
		init();
		setLayout(null);
		setVisible(true);
	}
	
	private void init(){
		teamOne.setBounds(40, 0, 100, 50);
		add(teamOne);
		
		teamOneScore.setBounds(60, 15, 100, 50);
		teamOneScore.setForeground(Color.RED);
		add(teamOneScore);
		
		teamTwo.setBounds(40 + 110, 0, 100, 50);
		add(teamTwo);
		
		teamTwoScore.setBounds(40+110+20, 15, 100, 50);
		teamTwoScore.setForeground(Color.RED);
		add(teamTwoScore);
				
		trump.setBounds(40+220, 0, 100, 50);
		add(trump);
		
		trumpSuit.setBounds(40+220, 15, 100, 50);
		trumpSuit.setForeground(Color.BLUE);
		add(trumpSuit);
		
		lastTrickLabel.setBounds(130, 260, 100, 20);
		lastTrickLabel.setVisible(false);
		add(lastTrickLabel);
		
		lastTrick.setBounds(0, 280, STATUS_AREA_WIDTH, Card.HEIGHT + 20);
		lastTrick.setBackground(Color.BLUE);
		add(lastTrick);
		
		bidPanel.setBounds(0+5, 70, STATUS_AREA_WIDTH-50, 100);
		bidPanel.setVisible(false);
		add(bidPanel);
		
		trumpPanel.setBounds(0+5, 170, STATUS_AREA_WIDTH-50, 150);
		trumpPanel.setVisible(false);
		add(trumpPanel);
		
		setBorder(
		            BorderFactory.createCompoundBorder( BorderFactory.createRaisedBevelBorder(), BorderFactory.createCompoundBorder( 
		            		BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),
		            		"Status Station"), BorderFactory.createEmptyBorder(5,5,5,5))));
		
	}
		
	public void refreshLabels(){
		teamOneScore.setText(Integer.toString(game.getTeam(0).getscore()));
		teamTwoScore.setText(Integer.toString(game.getTeam(1).getscore()));
		trumpSuit.setText(game.getTrump().toString());
	}
	
	public void refreshLastTrick(){
		int startPos = 175;
		for(int i = 0; i < 4; i++){
			Trick trick = game.getCurrentRound().getLastTrick();
			aCard = new CardPanel(trick.getCard(i), game);
			aCard.setBounds(startPos, 10, Card.WIDTH, Card.HEIGHT);
			startPos -= 30;	
			lastTrick.add(aCard);
			lastTrick.setVisible(true);
			lastTrickLabel.setVisible(true);
		}	
	}
	
	public void clearLastTrick(){
		lastTrick.removeAll();
		lastTrick.setVisible(false);
		lastTrickLabel.setVisible(false);
	}
	
	public void enableBidPanel(){
		bidPanel.enableBidPanel();
	}
	
	public void disableBidPanel(){
		bidPanel.disableBidPanel();
	}
	
	public void enableTrumpPanel(){
		trumpPanel.enableTrumpPanel();
	}
	
	public void disableTrumpPanel(){
		trumpPanel.disableTrumpPanel();
	}
}
