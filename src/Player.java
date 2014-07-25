import java.util.ArrayList;

/** Player *******************************************************************
 * General player class which all players (human and AI) extend
 * 
 ********************************************************************************/
public class Player {

	public static final int MAX_CARDS_IN_HAND = 9;
	
	protected ArrayList<Card> cardsInHand;
	protected Team team;
	protected int pointsWon;
	protected Suit suit;
	
	public Player(Team team){
		this.team = team;
		cardsInHand = new ArrayList<Card>();			//will be filled by Deal
		pointsWon = 0;
	}
	
	public boolean addToHand(Card card){
		if(cardsInHand.size() < MAX_CARDS_IN_HAND){	
			cardsInHand.add(card);
			return true;
		}
		return false;
	}
	
	public Team getTeam(){
		return team;
	}
		
	public int getPointsWon(){
		return pointsWon;
	}
	
	public void addPointsWon(int points){
		pointsWon+=points;
	}
	
	// class to be overwritten by ai types
	public Card aiLayCard(Trick trick, Suit trump, ArrayList<Player> players, Player player){ 		   
		return null;
	}
	
	public int aiMaxBid(){
		return 0;
	}
	
	// class to be overwritten by human class
	public boolean layCard(Card card){ 
		return false;
	}
	
	public void emptyHand(){
		cardsInHand.clear();
	}
	
	public int findCard(Card card){
		return cardsInHand.indexOf(card);
	}
	
	/** Sort() *******************************************************************
	 * Sorts the cards of the human player to improve playability
	 * 
	 ********************************************************************************/
	public void sort(){
		ArrayList<Card> sortedHand = new ArrayList<Card>();
		
		//goes through each suit and pulls cards from hand for that suit into temp ArrayList
		for(int i = 0; i < Suit.suits.length; i++){
			int cardsBeforeSuit = sortedHand.size();
			for(Card c : cardsInHand){	
				
				//Sort by suit by only grabbing cards from the current suit being added
				if(Suit.suits[i] == c.getSuit()){
					
					//Sort by rank by comparing this card to the previous card
					//   until its place is found
					int index = sortedHand.size();
					if(index > 0){
						while( index > cardsBeforeSuit && 		
								sortedHand.get(index-1).getRank().getValue() < c.getRank().getValue()){
							index--;
						}
					}
					sortedHand.add(index, c);
				}
			}
		}
		emptyHand();
		//Copy sorted hand to player's hand
		for(int i = 0; i < sortedHand.size(); i++){
			addToHand(sortedHand.get(i));
		}
	}
	
	public void removeCard(Card card){
		cardsInHand.remove(card);
	}
	
	public void swapCard(int playerCardPos, Card card){
		cardsInHand.set(playerCardPos, card);
	}

	public void aiSwapTrump(Kitty kitty, Suit trump) { // to be overriden by AI types			
	}
	
	
}
