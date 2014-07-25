import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GamePanel extends JLayeredPane{
	
	private Game game;
	
	private JPanel chooseDifficulty;
	private OkButton okButton;
	
	private SoundIcon toggleMusic;
	
	private HintButton hint;
	
	private JLayeredPane kitty = new JLayeredPane();
	private ArrayList<Player> players;
	private ArrayList<CardPanel>[] cards;
	
	public static final int gameAreaHeight = GUIRunner.FRAME_HEIGHT;
	public static final int gameAreaWidth = GUIRunner.FRAME_WIDTH - 350;
	public static int cardAreaHeight = GUIRunner.FRAME_HEIGHT - 51;
	
	private JLabel bg;
	@SuppressWarnings("unchecked")
	public GamePanel(Game game, ArrayList<Player> players){
		
		this.players = players;
		this.game = game;
		okButton = new OkButton("Accept", game);
		
		cards = new ArrayList[6];
		for(int i = 0; i < 6; i++){
			cards[i] = new ArrayList<CardPanel>();
		}
		
		chooseDifficulty = new DifficultyPanel(game);
		
		toggleMusic = new SoundIcon(game);
		
		hint = new HintButton(game, new HardAI(null,game), game.getPlayer(0));
		
		setBounds(0, 0, gameAreaWidth, gameAreaHeight);
		
		initMainPanel();		
		setVisible(true);
	}
	
	
	/** initMainPanel ********************************************************************
	 * Initialises the components that will be inside of the mainPanel
	 * 
	 ******************************************************************************/
	private void initMainPanel(){
		//Create each players hand
		populatePanel( Zones.SOUTH );
		populatePanel( Zones.WEST );
		populatePanel( Zones.NORTH );
		populatePanel( Zones.EAST );
		
			//Create the trick
			CardPanel sCard = new CardPanel(Card.backSide, game);
			CardPanel wCard = new CardPanel(Card.backSide, game);
			CardPanel nCard = new CardPanel(Card.backSide, game);
			CardPanel eCard = new CardPanel(Card.backSide, game);
			
			sCard.setBounds(gameAreaWidth/2 - 40, cardAreaHeight-(2*Card.HEIGHT)-40, Card.WIDTH, Card.HEIGHT);
			sCard.setVisible(false);
			
			wCard.setBounds(Card.WIDTH + 40, cardAreaHeight/2 - Card.HEIGHT/2 , Card.WIDTH, Card.HEIGHT);
			wCard.setVisible(false);
			
			nCard.setBounds(gameAreaWidth/2 - 40, Card.HEIGHT + 40, Card.WIDTH, Card.HEIGHT);
			nCard.setVisible(false);
			
			eCard.setBounds(gameAreaWidth - (2*Card.WIDTH) - 40, cardAreaHeight/2 - Card.HEIGHT/2 , Card.WIDTH, Card.HEIGHT);
			eCard.setVisible(false);
			
			add(sCard);
			add(wCard);
			add(nCard);
			add(eCard);

			cards[Zones.TRICK.position].add(sCard);
			cards[Zones.TRICK.position].add(wCard);
			cards[Zones.TRICK.position].add(nCard);
			cards[Zones.TRICK.position].add(eCard);
			
			initKitty();
			
			chooseDifficulty.setBounds(gameAreaWidth/2 - 350/2, cardAreaHeight/2 - 75, 350, 150);
			add(chooseDifficulty);
			
			toggleMusic.setBounds(0,0,50,50);
			toggleMusic.addMouseListener(toggleMusic);
			add(toggleMusic);
			
			hint.setBounds(gameAreaWidth/2 - 32, cardAreaHeight/2 - 150, 64, 300);
			hint.setVisible(false);
			hint.addMouseListener(hint);
			add(hint);
			
			
			
			kitty.setBounds(gameAreaWidth/2 - (Card.WIDTH+90)/2, cardAreaHeight/2 - Card.HEIGHT/2 , Card.WIDTH+90, Card.HEIGHT + 80);
			kitty.setVisible(false);
			add(kitty);
			
			
			okButton.addActionListener(okButton);
			okButton.setBounds(40, 100, 90, 40);
			okButton.setVisible(false);
			
			
			bg = new JLabel();
			ImageIcon myBG = Utils.createImageIcon("/resource/Wood-Background.jpg");
			bg.setBounds(0, 0, gameAreaWidth, gameAreaHeight);
			setBackground(myBG);
			add(bg); 
	}
	
	/** populatePanel ********************************************************************
	 * Creates all of the CardPanel objects for a given position, and adds them to
	 * the layeredPane.
	 * 
	 * @param position  -  The location that the cardPanels are being created.  
	 * 					   South=0, West=1, North=2, East=3 
	 ******************************************************************************/
	private void populatePanel( Zones zone ){
		ArrayList<Card> hand = new ArrayList<Card>();
		for(int i = 0; i<9; i++ ){
			hand.add(Card.backSide);
		}

		int xPos = gameAreaWidth - (gameAreaWidth - (240 + Card.WIDTH))/2 - Card.WIDTH
		, yPos = cardAreaHeight - (cardAreaHeight - (240 + Card.HEIGHT))/2 - Card.HEIGHT;
		for( Card c : hand ){
			CardPanel aCard = new CardPanel( c, game );
			
			if( zone == Zones.SOUTH){				
				aCard.setBounds(xPos, cardAreaHeight - Card.HEIGHT - 5, Card.WIDTH, Card.HEIGHT);
				aCard.addMouseListener(aCard); //User can only interact with South's cards
			}	
			else if (zone == Zones.WEST){		
				aCard.setBounds(5, yPos, Card.WIDTH, Card.HEIGHT);
			}
			else if (zone == Zones.NORTH){		
				aCard.setBounds(xPos, 5, Card.WIDTH, Card.HEIGHT);
			}
			else if (zone == Zones.EAST){		
				aCard.setBounds(gameAreaWidth - Card.WIDTH - 5, yPos, Card.WIDTH, Card.HEIGHT);
			}
			
			cards[zone.position].add(aCard);
			aCard.setVisible(false);
			add(aCard);
			yPos -= 30;
			xPos -= 30;
			
		}
	}	
	
	/** resetHand ********************************************************************
	 * Sets the value of each cardPanel to what is in the players hand
	 * and then returns all cards to a visible state.  The image for South's
	 * cards is also updated.
	 * 
	 ******************************************************************************/
	public void resetHand(){
		
		ArrayList<Card> hand = players.get(0).cardsInHand;
		
		for(int i=0; i<Game.NUM_PLAYERS; i++){
			for(int j = 0; j < players.get(i).cardsInHand.size(); j++){
				CardPanel c = cards[i].get(j);
				c.setCard(players.get(i).cardsInHand.get(j));
				c.setVisible(true);
			}	
		}
		for(int i = 0; i<9; i++ ){
			cards[0].get(i).setImageIcon(hand.get(i).getImageIcon());
		}
	}
	
	public void updateTrick(ArrayList<Card> trick, int playerPosition){
		if(trick.size() > 4){
			System.out.println("Trick too large");
		}
		else if(trick.isEmpty()){
			for(int i = 0; i<4; i++){
				cards[Zones.TRICK.position].get(i).setVisible(false);
			}
		}
		else{
			CardPanel aCard = cards[Zones.TRICK.position].get(playerPosition);
			aCard.setCard(trick.get(trick.size()-1));
			aCard.setImageIcon(aCard.getCard().getImageIcon());
			aCard.setVisible(true);
		}
	}
	
	public void hideCard(int playerIndex, Card card){
		if(playerIndex >= 4) return;
		
		for(CardPanel c : cards[playerIndex]){
			if(c.getCard() == card){
				c.setVisible(false);
				break;
			}
		}
		
	
	}
	
	public void refreshImages(){
		for(int i = Zones.WEST.position; i <= Zones.EAST.position; i++ ){
			for(CardPanel p : cards[i]){
				p.setImageIcon(Card.backSide.getImageIcon());
			}
		}
		for(CardPanel p : cards[Zones.KITTY.position]){
			p.setImageIcon(Card.backSide.getImageIcon());
		}
	}
	
	public void setBackground(ImageIcon image){
		Image img = image.getImage();
		image = new ImageIcon(img.getScaledInstance(gameAreaWidth, gameAreaHeight, 0));
		bg.setIcon(image);
	}
	
	private enum Zones{
		SOUTH(0),
		WEST(1),
		NORTH(2),
		EAST(3),
		TRICK(4),
		KITTY(5);
		
		Zones( int position ){
			this.position = position;
		}
		int position;
	}

	public void initKitty(){
		//Create the Kitty
		for(int i = 0; i<4; i++){
			CardPanel aCard = new CardPanel(Card.backSide, game);
			cards[Zones.KITTY.position].add(aCard);
			kitty.add(aCard);
			aCard.addMouseListener(aCard);
		}
		kitty.add(okButton);
		
		int startPos = 90;
		for(CardPanel c : cards[Zones.KITTY.position]){
			c.setBounds(startPos, 0, Card.WIDTH, Card.HEIGHT);
			startPos -= 30;
		}
		kitty.setVisible(false);
	}
	
	public void refreshKitty(boolean isPlayer) {
		for(int i = 0;i < 4;i++){
			CardPanel c = cards[Zones.KITTY.position].get(i);
			if(isPlayer){
				c.setCard(game.getKitty().cards.get(i));
				c.setImageIcon(c.getCard().getImageIcon());
				okButton.setVisible(true);
			}
			else{
				c.setImageIcon(Card.backSide.getImageIcon());
			}
			c.setVisible(true);
		}
		refreshPlayerHand();
		kitty.setVisible(true);
	}
	
	public void refreshPlayerHand(){
		for(int i = 0;i < 9;i++){
			CardPanel c = cards[Zones.SOUTH.position].get(i);
			c.setCard(players.get(0).cardsInHand.get(i));
			c.setImageIcon(c.getCard().getImageIcon());
		}
	}


	public void removeKitty() {
		kitty.setVisible(false);
		okButton.setVisible(false);
	}
	
	public void disableTrick(){
		for(CardPanel c : cards[Zones.TRICK.position])
			c.setVisible(false);
	}
	private class OkButton extends JButton implements ActionListener{
		private static final long serialVersionUID = 1L;
		private Game game;
		
		OkButton(String name, Game game){
			super(name);
			this.game = game;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(!game.getKitty().hasPointCards()){
				game.acceptKitty();
			}
			else game.printToConsole("\nPoint cards must not remain in the kitty.");
		}
		
	}
	public void enableHint() {
		hint.setVisible(true);		
	}


	public void disableHint() {
		hint.setVisible(false);		
	}


	public void toggleSoundIcon() {
		toggleMusic.toggleIcon();		
	}
		
}
