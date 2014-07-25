//package Game;
import java.util.ArrayList;

import javax.sound.midi.Sequence;
import javax.swing.ImageIcon;

/********************************************************************************
 * Game (class)
 * 
 * A class representing the 200 game. Creates the Deck, Round and Trick objects.
 * 
 * @author Grant O'Brien, Nicholas Noel, Michael McKenzie
 *
 ********************************************************************************/

public class Game {
	private ArrayList<Team> teams = new ArrayList<Team>();
	private ArrayList<Player> players = new ArrayList<Player>();
	private Round currentRound;
	private Trick currentTrick;
	private Kitty kitty;
	private Deck deck;
	private GUIRunner gui;
	private int state = 4;
	private int playerBid;
	private int currentBid;
	private MusicThread mthread;
	
	private int kittyCardPos = -1;
	private int playerCardPos = -1;
	
	private int lastTrickWinner;
	public static final int NUM_PLAYERS = 4;
	
	/** Game*******************************************************************
	 * Constructor for the Game class.
	 * 
	 * @param type - represents the difficulty of AI to be used
	 **************************************************************************/
	
	public Game(){
		mthread = new MusicThread();
		mthread.start();
		Team team1 = new Team();
		Team team2 = new Team();
		teams.add(team1);
		teams.add(team2);
		Player player = new Human(team1);
		Player ai1 = null;
		Player ai2 = null;
		Player ai3 = null;
		
		ai1 = new EasyAI(team2);
		ai2 = new EasyAI(team1);
		ai3 = new EasyAI(team2);
		
		
		players.add(player);
		players.add(ai1);
		players.add(ai2);
		players.add(ai3);
		
		deck = new Deck();
		kitty = new Kitty();
		
		gui = new GUIRunner(this, players);
		
		lastTrickWinner = 0;  //Defaults to South going first
		state = 8;
	}
	
	/** aiLayCard **************************************************************
	 * Function for AI to choose a card to lay. Chooses the card by 
	 * delegating to the layCard method in the AI and adds it to the
	 * current trick.  
	 *
	 **************************************************************************/
	
	void aiLayCard(Player player){
		Card chosen = player.aiLayCard(currentTrick, getTrump(), players, player);
		currentTrick.addCard(chosen);
		player.removeCard(chosen);
		gui.hideCard(players.indexOf(player), chosen);
		gui.updateTrick(currentTrick.cardsPlayed, currentTrick.getCurrentPlayerIndex(lastTrickWinner));
		if(currentTrick.cardsPlayed.size() == 4){
			state = 3;
		}
		else if(player == players.get(3)){
			 gui.enableHint();
			 state = 1;
		}
	}
	
	int aiBid(Player player, int position){
		int bid = player.aiMaxBid();
		if(position == 3)
			state = 4;
		return bid;
	}
	
	void aiSwapTrump(Player player){
		player.aiSwapTrump(kitty, currentRound.getTrumpSuit());
		gui.resetHand();
	}
	
	boolean playerBid(int bid){
		if(state == 4 && (bid > currentBid || bid == 0)){
			playerBid = bid;
			currentBid = bid;
			state = 5;
			return true;
		}
		if(state == 4){
			this.printToConsole("\nYou did not bid high enough. Please bid more than " + currentBid + " or bid 0 to pass");
		}
 		return false;
	}
	
	boolean playerTrump(Suit suit){
		if(state == 6){
			currentRound.setTrump(suit);
			state = 7;
			return true;
		}
 		return false;
	}

	/** deal **************************************************************
	 * Shuffles the deck and gives each player a new hand.  Then the gui is
	 * updated.
	 *
	 **************************************************************************/
	void deal(){
		for(Player p : players){
			System.out.println(p);
		}
		deck.reset();
		//In case a card is left in someone's hand
		for(Player p : players)
			p.emptyHand();
		kitty.emptyKitty();
		
		for(int i = 0; i<9; i++){
			if(i < 4){
				kitty.add(deck.dealCard());
			}
			for(int p = 0; p < NUM_PLAYERS; p++)
				players.get(p).addToHand(deck.dealCard());
		}
		players.get(0).sort();
		gui.resetHand();
	}
	
	/** newRound **************************************************************
	 * Sets the currentRound attribute to a new Round object. Represents the
	 * beginning of a new round of play 
	 *
	 *************************************************************************/
	
	void newRound(){
		currentRound = new Round();
		gui.refreshStatus();
		
	}
	
	/** newTrick **************************************************************
	 * Sets the currentTrick attribute to a new Trick object. Represents the
	 * start of a new Trick.
	 * 
	 **************************************************************************/
	
	void newTrick(){
		gui.clearLastTrick();
		gui.refreshStatus();
		if(currentRound.tricks.size() > 0){
			currentRound.addTrick(currentTrick);
			if(currentRound.tricks.size() > 9){
			state = 0;
			return;
			}
			currentTrick = new Trick(lastTrickWinner);
		}else{
			//lastTrickWinner = 0;
			currentTrick = new Trick(lastTrickWinner);
			currentRound.addTrick(currentTrick);
		}
		if(lastTrickWinner == 0){
			gui.enableHint();
			state = 1;
		}
		else{
			state = 2;
			gui.disableHint();
		}
		gui.updateTrick(currentTrick.cardsPlayed, currentTrick.getCurrentPlayerIndex(lastTrickWinner));
		//gui.clearLastTrick();
		if(currentRound.tricks.size() > 1 && currentRound.tricks.size() <= 9){
			gui.refreshLastTrick();
		}
	}
	
	
	
	/** playerLayCard **************************************************************
	 * Function to decide how to act once a user chooses a card to play.  If the
	 * move is valid, the card is moved from the player's hand to the trick, then
	 * the gui is updated.   
	 *
	 **************************************************************************/
	boolean playerLayCard(Card card){
		if(state == 1 && currentTrick.isValidMove(card, 0, players)){
			currentTrick.addCard(card);
			players.get(0).layCard(card);
			gui.updateTrick(currentTrick.cardsPlayed, currentTrick.getCurrentPlayerIndex(lastTrickWinner));
			if(currentTrick.cardsPlayed.size() == 4)
				state = 3;
			else state = 2;
			gui.disableHint();
			return true;
		}
		return false;
	}

	Round getCurrentRound(){				//returns current round
		return currentRound;
	}
	
	/** getGameStatus **************************************************************
	 * Uses state to determine the status of the game
	 * Is used to determine when the GUI will be updated by who's turn it is
	 * 
	 **************************************************************************/
	gameState getGameStatus(){
		switch (state){
		case 0:
			return gameState.IDLE;
		case 1: 
			return gameState.PLAYERTURN;			
		case 2: 
			return gameState.AITURN;
		case 3:
			return gameState.TRICKOVER;
		case 4:
			return gameState.PLAYERBID;
		case 5:
			return gameState.AIBID;
		case 6:
			return gameState.TRUMP;
		case 7:
			return gameState.KITTY;
		case 8:
			return gameState.CHOOSE_DIFFICULTY;
		}
		return null;
	}
	
	Trick getCurrentTrick(){				//returns current trick
		return currentTrick;
	}
	
	void repaint(){							//calls the repaint of the GUI
		gui.repaint();
	}
	
	void setState(int s){
		state = s;
	}

	public Player getPlayer(int index){		//returns the player at the given index
		if (index >= 0 && index <= 3)
			return players.get(index);
		return null;
	}
	
	public Team getTeam(int team){			//returns the team at the given index
		return teams.get(team);
	}
	
	public Suit getTrump(){					//returns the trump for the current round
		return currentRound.getTrumpSuit();
	}
	
	public void setTrump(Suit tr){
		currentRound.setTrump(tr) ;
	}
	
	public void setWinner(int i){			//sets winner to notify who starts next trick
		lastTrickWinner = i;
	}
	
	public int getWinner(){					//returns index of winner of last trick
		return lastTrickWinner;
	}
	public void printToConsole(String string){
		gui.printLine(string);
	}

	public void clearConsole() {
		gui.clearConsole();
		
	}
	
	public void refreshImages(){
		gui.refreshImages();
	}
	
	public void setBackground(ImageIcon image){
		gui.setBackground(image);
	}

	
	public int getPlayerBid() {
		return playerBid;
	}
	
	public void setCurrentBid(int value){
		currentBid = value;
	}
	
	public void disableBidPanel(){
		gui.disableBidPanel();
	}

	public void enableBidPanel(){
		gui.enableBidPanel();
	}
	
	public void disableTrumpPanel(){
		gui.disableTrumpPanel();
	}
	public Kitty getKitty() {
		return kitty;
	}

	public void refreshKitty(boolean isPlayer) {
		gui.refreshKitty(isPlayer);		
	}

	public void setDifficulty(Difficulty difficulty) {
		switch(difficulty){
			case EASY:
				players.set(1, new EasyAI(teams.get(1)));
				players.set(2, new HardAI(teams.get(0), this));
				players.set(3, new EasyAI(teams.get(1)));
				break;
			case NORMAL:
				players.set(1, new MediumAI(teams.get(1)));
				players.set(2, new MediumAI(teams.get(0)));
				players.set(3, new MediumAI(teams.get(1)));
				break;
			case HARD:
				players.set(1, new MediumAI(teams.get(1)));
				players.set(2, new EasyAI(teams.get(0)));
				players.set(3, new HardAI(teams.get(1), this));
				break;
			case HARDCORE:
				players.set(1, new HardAI(teams.get(1), this));
				players.set(2, new EasyAI(teams.get(0)));
				players.set(3, new HardAI(teams.get(1), this));
				break;
		}
		state = 4;
	}

	public void removeKitty() {
		gui.removeKitty();		
	}
	
	public boolean playerClickKitty(Card card){
		int cardPos;
		if(state == 7){
			cardPos = kitty.findCard(card);
			if(cardPos != -1){
				kittyCardPos = cardPos;
			}
			else{
				cardPos = players.get(0).findCard(card);
				
				if(cardPos != -1){
					playerCardPos = cardPos;
				}
			}
			if(kittyCardPos > -1 && playerCardPos > -1){
				Card kittyCard = kitty.cards.get(kittyCardPos);
				Card playerCard = players.get(0).cardsInHand.get(playerCardPos);
				kitty.swap(kittyCardPos, playerCard);
				players.get(0).swapCard(playerCardPos, kittyCard);
				kittyCardPos = -1;
				playerCardPos = -1;
				gui.refreshKitty(true);
			}
		}
		return true;
	}
	
	public void disableLastTrick() {
		gui.clearLastTrick();
		
	}

	public void clearTrick() {

		gui.disableTrick();		
	}
	
	public void acceptKitty(){
		state = 1;
		players.get(0).sort();
		gui.refreshKitty(true);
		removeKitty();
	}
	
	public void enableTrumpPanel() {
		gui.enableTrumpPanel();
	}

	public void toggleMusic() {
		if(!mthread.isPlaying()){
			mthread.startMusic();
		}
		else {
			mthread.stopMusic();		
		}
		gui.toggleSoundIcon();
	}
	
	public void setMusic(String filePath){
		mthread.setMusic(filePath);
	}
}


