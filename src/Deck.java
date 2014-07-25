//package Game;
/***************************************************************************
 * Deck (class)
 * 
 * A class representing a Deck of playing cards. Contains methods for
 * dealing and shuffling.
 * 
 * @author Grant O'Brien, Nicholas Noel and Michael McKenzie
 * 
 ***************************************************************************/

import java.util.ArrayList;
import java.util.Collections;


public class Deck {
	private ArrayList<Card> cards = new ArrayList<Card>();
	private int index = 0;
	
	/** Deck *********************************************************************
	 * Constructor for the deck class. Creates all necessary cards and adds them
	 * to the list of cards contained in the deck. 
	 *
	 *****************************************************************************/
	
	public Deck(){
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 10; j++){
				cards.add(new Card(Suit.suits[i], Rank.ranks[j]));
			}
		}
		
	}
	
	/** dealCard *******************************************************************
	 * Deals a cards. i.e. returns the next card in the deck. 
	 * Increments the index.
	 * 
	 * @return - returns the card at the current index
	 *
	 ******************************************************************************/
	
	Card dealCard(){
		return cards.get(index++);
	}

	/** reset **********************************************************************
	 * Resets the deck. i.e. shuffles the deck and resets the index
	 * 
	 ******************************************************************************/
	
	void reset(){
		shuffle();
		index = 0;
	}
	
	/** shuffle ********************************************************************
	 * Reorders the the cards in the deck in a random order using the built-in
	 * shuffle method. All possibilities of orderings occur with equal likelihood
	 * 
	 ******************************************************************************/
	private void shuffle(){
		Collections.shuffle(cards);
	}
	
}
