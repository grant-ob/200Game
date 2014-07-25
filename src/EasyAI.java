/*************************************************************************************
 * EasyAI (class)
 * 
 * A class implementing the AI interface and extending the Player class. Represents
 * the simplest AI strategy.
 * 
 * @author Grant O'Brien, Nicholas Noe and Michael McKenzie
 * 
 ************************************************************************************/

import java.util.ArrayList;
import java.util.Random;


public class EasyAI extends Player {

	/** EasyAI *************************************************************************
	 * Constructor for the EasyAI class. Calls the constructor of the Player class.
	 * 
	 * @param team - the team that the AI belongs to
	 *
	 ***********************************************************************************/
	
	public EasyAI(Team team) {
		super(team);
	}

	/** chooseCard *************************************************************************
	 * EasyAI's implementation of the chooseCard method from the Player class.
	 * A method for the AI to choose a card to lay. In this case, it is the easiest 
	 * method: a random card is chosen.
	 *
	 ************************************************************************************/
	
	@Override
	public Card aiLayCard(Trick trick, Suit trump, ArrayList<Player> players, Player player) {
		Random rand = new Random();
		Card card = cardsInHand.get(rand.nextInt(cardsInHand.size()));
		while(!trick.isValidMove(card, players.indexOf(player), players)){
			card = cardsInHand.get(rand.nextInt(cardsInHand.size()));
			}
		return card;
	}
	
	@Override
	public void aiSwapTrump(Kitty kitty, Suit trump){
		int pointCount = 0;
		for(int i = 0; i < 9; i++){					//if all cards are point cards, none are swapped
			if(cardsInHand.get(i).getPoints() != 0)
				pointCount++;
		}
		if (pointCount == 9){return;}
		for(int j = 0; j <4; j++){
			if(kitty.getCard(j).getPoints() != 0){
				for(int k = 0; k < 9; k++){					
					if(cardsInHand.get(k).getPoints() == 0){
						cardsInHand.set(k, kitty.getCard(j));
						k = 9;
					}
				}
			}
		}
	}
	
	@Override
	public int aiMaxBid(){
		int count = 0;
		int counth = 0;
		int countd = 0;
		int counts = 0;
		int countc = 0;
		for(int i = 0; i<9; i++){
			if(cardsInHand.get(i).getSuit() == Suit.CLUBS)
				countc++;
			if(cardsInHand.get(i).getSuit() == Suit.HEARTS)
				counth++;
			if(cardsInHand.get(i).getSuit() == Suit.SPADES)
				counts++;
			if(cardsInHand.get(i).getSuit() == Suit.DIAMONDS)
				countd++;
		}
		if(countc > counth){
			count = countc;
			suit = Suit.CLUBS;
		}
		else{count = counth;
			suit = Suit.HEARTS;
		}
		if(counts > count){
			count = counts;
			suit = Suit.SPADES;
		}
		if(countd > count){
			count = countd;
			suit = Suit.DIAMONDS;
		}
		
		return (count*5 + 40);
	}

}
