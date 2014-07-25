//package Game;
/*******************************************************************
 * Card (class)
 * 
 *  A class representing a Card from a standard deck.
 *  Contains attributes for suit, rank and image, as well as 
 *  operations for getting points, suit and rank.
 *  
 *  @author Grant O'Brien, Nicholas Noel and Michael McKenzie
 *  
 ********************************************************************/

import javax.swing.ImageIcon;

public final class Card {
	private Suit suit;
	private Rank rank;
	private ImageIcon image;
	
	public static Card backSide = new Card(Suit.NONE, Rank.NONE);
	
	public static final int HEIGHT = 97;
	public static final int WIDTH = 73;
	
	/** Card ****************************************************************************
	 * Constructor for the Card class.
	 * 
	 * @param suit - represents the suit of the card(clubs, diamonds, hearts or spades)
	 * 
	 * @param rank - the rank of the card. In this game the rank varies from 5 - A
	 * 
	 ************************************************************************************/
	
	public Card(Suit suit, Rank rank){
		this.suit = suit;
		this.rank = rank;

		String imageFile = "/resource/cards/" + rank.toString() + suit.toString() + ".gif";	// path of image file
		java.net.URL imageURL = this.getClass().getResource( imageFile );
		if( imageURL == null )
			System.out.println("Error getting URL");
		else
			image = new ImageIcon(imageURL);
		
			
	}
	
	public Card(Suit suit, Rank rank, ImageIcon image){
		this.suit = suit;
		this.rank = rank;
		this.image = image;
	}
	
	/** Points ****************************************************************************
	 * Returns an integer representing the point value of the card.
	 * 
	 * @return - returns 10 if the card is a 10 or Ace, 5 if it is a 5, 0 otherwise
	 * 
	 **************************************************************************************/
	
	int getPoints(){
		String rankName = rank.toString();
		if(rankName == "ACE" || rankName == "TEN")
			return 10;
		else if(rankName == "FIVE")
			return 5;
		return 0;
	}
	
	Suit getSuit(){		// getter for the suit attribute
		return suit;
	}
	
	Rank getRank(){		// getter for the rank attribute
		return rank;
	}
	
	ImageIcon getImageIcon(){ // getter for the image attribute
		return image;
	}
	
	/** getRankIndex *******************************************************************
	 * Returns and integer from 0 to 9 representing the rank of the card.
	 *  
	 * @return - 0 = card is a 5 ... 9 = card is and Ace
	 *
	 **********************************************************************************/
	int getRankIndex(){
		for(int i = 0; i < Rank.ranks.length; i++){
			if(Rank.ranks[i] == rank)
				return i;				
		}
		return -1;
	}
	
}
