/*************************************************************************************
 * MediumAI (class)
 * 
 * A class implementing the AI interface and extending the Player class. Represents
 * the hardest AI strategy.
 * 
 * @author Grant O'Brien, Nicholas Noel and Michael McKenzie
 * 
 ************************************************************************************/

import java.util.ArrayList;
import java.util.Random;


public class MediumAI extends Player {
	
	/** MediumAI *************************************************************************
	 * Constructor for the EasyAI class. Calls the constructor of the Player class.
	 * 
	 * @param team - the team that the AI belongs to
	 *
	 ***********************************************************************************/
	
	public MediumAI(Team team) {
		super(team);
	}

	/** chooseCard *************************************************************************
	 * MediumAI's implementation of the chooseCard method from the Player class.
	 * A method for the AI to choose a card to lay. In this case, it is the easiest 
	 * method: a random card is chosen.
	 *
	 ************************************************************************************/
	
	@Override
	public Card aiLayCard(Trick trick, Suit trump, ArrayList<Player> players, Player player){
		Card chosen = null;
		chosen = choose(trick, trump, players, player);
		while(!trick.isValidMove(chosen, players.indexOf(player), players)){
			chosen = choose(trick, trump, players, player);
		}
		return chosen;
		
	}
	
	@Override
	public int aiMaxBid(){
		int count = 0;
		int counth = 0;
		int countd = 0;
		int counts = 0;
		int countc = 0;
		for(int j = 0; j<9; j++){
			if(cardsInHand.get(j).getRankIndex() == 9)
				count+=5;
		}
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
		count = counth;
		suit = Suit.HEARTS;
		if(countc > count){
			count = countc;
			suit = Suit.CLUBS;
		}
		if(counts > count){
			count = counts;
			suit = Suit.SPADES;
		}
		if(countd > count){
			count = countd;
			suit = Suit.DIAMONDS;
		}
		return ((count*5) + 40);
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
	
	public int cardExists(int rankIndex){
		for(int i = 0; i < cardsInHand.size(); i++){
			if(cardsInHand.get(i).getRankIndex() == rankIndex){
				return i;
			}
		}
		return -1;
	}
	
	public Card choose(Trick trick, Suit trump, ArrayList<Player> players, Player player){
		
		//first to lay, will lay best possible card (not necessarily trump)
		if(trick.cardsPlayed.size() == 0){
			for(int i = 9; i>5; i--){
				int position = cardExists(i);
				if(position != -1){
					if(trick.isValidMove(cardsInHand.get(position), players.indexOf(player), players))
						return cardsInHand.get(position);
				}
			}
		}
		
		//second to lay, try to beat first card
		if(trick.cardsPlayed.size() == 1){
			for(int i = 0; i<10; i++){
				int position = cardExists(i);
				if(position != -1 && trick.isBetter(cardsInHand.get(position), trick.getCard(0), trump)){
					if(trick.isValidMove(cardsInHand.get(position), players.indexOf(player), players))
						return cardsInHand.get(position);
				}
			}
		}
		
		//third to lay, give points to partner if they have the best card, else beat opponent
		if(trick.cardsPlayed.size() == 2){
			if(trick.isBetter(trick.getCard(1), trick.getCard(0), trump)){
				int position = cardExists(5);
					if(position != -1){
						if(trick.isValidMove(cardsInHand.get(position), players.indexOf(player), players))
							return cardsInHand.get(position);
					}
				position = cardExists(0);
					if(position != -1){
						if(trick.isValidMove(cardsInHand.get(position), players.indexOf(player), players))
							return cardsInHand.get(position);
					}
			}
			if(trick.isBetter(trick.getCard(1), trick.getCard(0), trump)){
				for(int i = 0; i<10; i++){
					int position = cardExists(i);
					if(position != -1){
						if(trick.isValidMove(cardsInHand.get(position), players.indexOf(player), players))
							return cardsInHand.get(position);
					}
				}
			}
			if(trick.isBetter(trick.getCard(0), trick.getCard(1), trump)){
				for(int i = 0; i<10; i++){
					int position = cardExists(i);
					if(position != -1 && trick.isBetter(cardsInHand.get(position), trick.getCard(1), trump)){
						if(trick.isValidMove(cardsInHand.get(position), players.indexOf(player), players))
							return cardsInHand.get(position);
					}
				}
			}

		}
		
		//fourth to lay, give points to partner if they have the best card, else beat opponents if possible
		if(trick.cardsPlayed.size() == 3){
			if(trick.isBetter(trick.getCard(1), trick.getCard(0), trump) && trick.isBetter(trick.getCard(1), trick.getCard(2), trump)){
				int position = cardExists(5);
					if(position != -1){
						if(trick.isValidMove(cardsInHand.get(position), players.indexOf(player), players))
							return cardsInHand.get(position);
					}
				position = cardExists(0);
					if(position != -1){
						if(trick.isValidMove(cardsInHand.get(position), players.indexOf(player), players))
							return cardsInHand.get(position);
					}
			}
			else{
				for(int i = 0; i<10; i++){
					int position = cardExists(i);
					if(position != -1 && trick.isBetter(cardsInHand.get(position), trick.getCard(0), trump) && trick.isBetter(cardsInHand.get(position), trick.getCard(2), trump)){
						if(trick.isValidMove(cardsInHand.get(position), players.indexOf(player), players))
							return cardsInHand.get(position);
					}
				}
			}
		}
		
		//if cannot give points to partner or beat opponents, will lay random card
		Random rand = new Random();
		int index = rand.nextInt(cardsInHand.size());
		Card card = cardsInHand.get(index);
		return card;
	}

}
