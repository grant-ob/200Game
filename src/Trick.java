//package Game;

import java.util.ArrayList;

/********************************************************************************
 * Trick (class)
 * 
 * A class representing a Trick -- a card from each player.  
 * 
 * @author Grant O'Brien, Nicholas Noel, Michael McKenzie
 *
 ********************************************************************************/
public class Trick {
	public ArrayList<Card> cardsPlayed;
	private int winner;
	
	/** Trick*************************************************************
	 * Constructor for the Trick class.
	 * 
	 **************************************************************************/
	public Trick(int winner){					//winner index will start the trick
		cardsPlayed = new ArrayList<Card>();
		this.winner = winner;
	}
	
	ArrayList<Card> getCards(){
		return cardsPlayed;
	}
	
	Card getCard(int index){
		return cardsPlayed.get(index);
	}
	
	/** addCard ********************************************************************
	 * Add a card to this trick's cardPlayed ArrayList.  Only 4 cards can be 
	 * in any trick.
	 * 
	 * @param card - the card to add
	 * @returns - true if the action was successful
	 * 			- false otherwise
	 ******************************************************************************/
	public boolean addCard(Card card){
		if( cardsPlayed.size() < 4){
			cardsPlayed.add(card);
			return true;
		}
		return false;	
	}
	
	/** getPoints ********************************************************************
	 * Count and return the number of points on each card in the trick
	 * 
	 * @returns int - The total number of points in this trick
	 ******************************************************************************/
	int getPoints(){
		int points = 0;
		for(int i = 0; i < 4; i++)
			points += cardsPlayed.get(i).getPoints();
		return points;
	}
	
	/** getWinnerIndex *********************************************************
	 * Finds the highest ranked card in a hand and returns the owners position
	 * 
	 * @returns int - The position index of the winning player
	 ******************************************************************************/
	int getWinnerIndex(Suit trumpSuit){
		
		int winningPosition = 0;
		for(int i = 1; i < 4; i++){
			if(isBetter(cardsPlayed.get(winningPosition), cardsPlayed.get(i), trumpSuit)){
				winningPosition = i;
			}
		}
		winningPosition += winner;
		if( winningPosition >= 4 )
			winningPosition -= 4;
		System.out.println("\nWinning position is " + winningPosition + "\n");
		return winningPosition;
	}
	
	boolean isEmpty(){
		return cardsPlayed.isEmpty();
	}

	/** isBetter *********************************************************
	 * Compares the rank and suit of two cards and returns the better card
	 * (card value priority : trump suit > other suits & higher rank > lower rank)
	 * note: Ties are not possible as there is only one of each card
	 * 
	 * @returns - true if card2 is better than card
	 *          - false otherwise
	 ******************************************************************************/
	public boolean isBetter(Card card, Card card2, Suit trumpSuit) {
		int cardRank = card.getRankIndex();
		int card2Rank = card2.getRankIndex();
		if(card2.getSuit() == card.getSuit())	//case: same suit
			return (card2Rank > cardRank);
		else if(card2.getSuit() == trumpSuit)	//case: card2 is in trump
			return true;
		else if((card2.getSuit() == cardsPlayed.get(0).getSuit()) && (card.getSuit() != trumpSuit))	//case: card2 is the leading suit and card is not a trump
			return true;
		return false;
	}

	public int getCurrentPlayerIndex(int lastTrickWinner) {
		int index = 0;
		if(lastTrickWinner + cardsPlayed.size()-1 > 3)
			return lastTrickWinner + cardsPlayed.size() - 5;
		if(cardsPlayed.size() == 0)
			return lastTrickWinner;
		index = lastTrickWinner + cardsPlayed.size() - 1;
		return index;
	}
	
	/** isValidMove **************************************************************
	 * Checks to see if the card being played is valid.  
	 *
	 **************************************************************************/
	public boolean isValidMove(Card card, int index, ArrayList<Player> players){
		if(!cardsPlayed.isEmpty()){					//case: This is not the first card of the trick
			Suit leadingSuit = cardsPlayed.get(0).getSuit();
			if(leadingSuit != card.getSuit()){			//case: card is not in the suit of the first card played this trick		
				
				//A card of a suit that is not the leadingSuit is trying to be played
				//Look for another card in the player's hand that is of leadingSuit
				for(Card c : players.get(index).cardsInHand)
					if( c.getSuit() == leadingSuit){
						return false;
				}
				return true;
			}
		}
		return true;
	}
		
}
