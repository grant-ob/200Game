/*************************************************************************************
 * HardAI (class)
 * 
 * A class implementing the AI interface and extending the Player class. Represents
 * the hardest AI strategy.
 * 
 * @author Grant O'Brien, Nicholas Noel and Michael McKenzie
 * 
 ************************************************************************************/

import java.util.ArrayList;
import java.util.Random;


public class HardAI extends Player {
	private Game game;
	private ArrayList<Rank> Diamonds = new ArrayList<Rank>();
	private ArrayList<Rank> Clubs = new ArrayList<Rank>();
	private ArrayList<Rank> Hearts = new ArrayList<Rank>();
	private ArrayList<Rank> Spades = new ArrayList<Rank>();
	private Suit leadingSuit;
	
	/** HardAI *************************************************************************
	 * Constructor for the EasyAI class. Calls the constructor of the Player class.
	 * 
	 * @param team - the team that the AI belongs to
	 *
	 ***********************************************************************************/
	
	public HardAI(Team team, Game game) {
		super(team);
		this.game = game;
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
				Card card = this.layLow(trump);
				cardsInHand.remove(card);
				cardsInHand.add(kitty.getCard(j));
			}
			if((kitty.getCard(j).getPoints() == 0) && (kitty.getCard(j).getSuit() == trump)){
				Card card = this.layLow(trump);
				cardsInHand.remove(card);
				cardsInHand.add(kitty.getCard(j));
			}
		}
	}
	/** chooseCard *************************************************************************
	 * EasyAI's implementation of the chooseCard method from the Player class.
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
	
	
	public Card choose(Trick trick, Suit trump, ArrayList<Player> players, Player player){
		for(int i = 0; i < 10 ; i++){
			Diamonds.add(Rank.ranks[i]);
			Clubs.add(Rank.ranks[i]);
			Hearts.add(Rank.ranks[i]);
			Spades.add(Rank.ranks[i]);
		}
		
		System.out.println(trick.cardsPlayed.size() + " cards played this trick");		
		
		if(trick.cardsPlayed.size() > 0){
			leadingSuit = trick.cardsPlayed.get(0).getSuit();
		}
		
		Suit mostCommonSuit = getMostCommonSuit();
		
		ArrayList<Card> winners = new ArrayList<Card>();
		
		Random rand = new Random();
		
		updateRanks(trick);
				
		for(Card c : cardsInHand){
			if(numberBetter(c, trump) == 0){
				winners.add(c);
			}
		}
		
		switch(trick.cardsPlayed.size()){
			
		/**** FIRST TO LAY ****************************************/
			
			case 0:												
				
				System.out.println("\nFIRST TO LAY STRATEGY");
				
				if(winners.isEmpty()){	// no winners
					System.out.println("No winners in hand");
					
					for(Card c : getHighestCards()){
						if(c.getSuit() == trump){
							if(numberBetter(c, trump) - getNumberInHand(c.getSuit()) < 3){
							System.out.println("Laying high trump card.");
							return c;
							}
							else if(numberBetter(c, trump) - getNumberRemaining(trump) - getNumberInHand(c.getSuit()) < 3){
								System.out.println("Laying high non-trump card.");
								return c;
							}
						}
					}
					
					return layLow(trump);
					
				}
				
				else{ 	// winners exist
					System.out.println("Winners exist in hand");
					if(getNumberInHand(trump) > getNumberRemaining(trump)/4){					
						for(Card c : winners){
							if(c.getSuit() == trump){
								System.out.println("Laying trump suit winner");
								return c;	
							}
						}					
					}
				
					for(Card c : winners){
						if(c.getSuit() == mostCommonSuit){
							System.out.println("Laying common suit winner");
							return c;
						}
					}
					
					System.out.println("Laying random winner");
					return winners.get(rand.nextInt(winners.size()));
				
				}
				
		/***********************************************************/	
				
		/**** SECOND TO LAY ****************************************/	
			
			case 1:																
				System.out.println("\nSECOND TO LAY STRATEGY");
				if(!winners.isEmpty()){											// attempt to lay winning card of leadingSuit
					System.out.println("Winners exist in hand");
					for(Card c : winners){											
						if(c.getSuit() == leadingSuit){
							System.out.println("Laying leading suit winner");
							return c;
						}
					}
				}
				
				if(getHighestCard(leadingSuit) != null){
					System.out.println("Cards of leading suit exist in hand");
					if(trick.isBetter(trick.cardsPlayed.get(0), getHighestCard(leadingSuit), trump)){	// attempt to beat current card with leadingSuit
						System.out.println("Laying highest card of leading suit");
						return getHighestCard(leadingSuit);
					}
				}
				
				
				if(getLowestNonPoint(leadingSuit) != null){					// attempt to play lowest card of leadingSuit
					System.out.println("Laying lowest non point card of leading suit");
					return getLowestNonPoint(leadingSuit);
				}
				
				if(getLowestCard(leadingSuit) != null){
					System.out.println("Laying lowest card of leading suit");
					return getLowestCard(leadingSuit);
				}
				
								
				if(getNumberRemaining(leadingSuit) > getNumberInHand(leadingSuit) + 4 && leadingSuit != trump){					
					System.out.println("More than 4 cards of leading suit are remaining outside of players hand" +
							" and leading suit is not trump");
					if(getPointCard(trump) != null){
						System.out.println("Trumping with a point card");
						return getPointCard(trump);
					}
					
					if(getLowestCard(trump) != null){
						System.out.println("Trumping with lowest possible card");
						return getLowestCard(trump);
					}
					
				}

				return layLow(trump);

		/***********************************************************/	
		
		/**** THIRD TO LAY *****************************************/	
			
			case 2:
				System.out.println("\nTHIRD TO LAY STRATEGY");
				if((trick.isBetter(trick.cardsPlayed.get(1), trick.cardsPlayed.get(0), trump) 
						&& numberBetter(trick.cardsPlayed.get(0), trump) - getNumberRemaining(trump) <= 4 
							&& (getNumberRemaining(leadingSuit) > 4 || cardsInHand.size() < 4)) || isWinner(trick.cardsPlayed.get(0))){
					// teammate is winning & is probable card wont be beaten
						System.out.println("Teammate is winning and it is probable their card wont be beaten");
						
						if(getPointCard(leadingSuit) != null && getPointCard(leadingSuit).getRank() != Rank.ACE){
							System.out.println("Laying point card from leading suit");
							return getPointCard(leadingSuit);			//give points to team-mate
						}
						
						if(getLowestCard(leadingSuit) != null){
							System.out.println("Laying lowest card from leading suit");
							return getLowestCard(leadingSuit);
						}
				}
							
				else{		// team-mate is not winning or will probably be beaten
					System.out.println("Teammate is not winnning or it is probable their card will be beaten");

					if(getHighestCard(leadingSuit) != null){		// lay highest card of leading suit if it is likely to win		
						if(((trick.cardsPlayed.get(1).getSuit() != trump || leadingSuit == trump) 
								&& numberBetter(getHighestCard(leadingSuit), trump)	- getNumberRemaining(trump) <= 2 
								&& getNumberRemaining(leadingSuit) > 4)|| isWinner(getHighestCard(leadingSuit))){
							System.out.println("It is probable that highest card of leading suit will not be beaten");
							//it is probable players highest card wont be beaten
							System.out.println("Laying highest card of leading suit");
							return getHighestCard(leadingSuit);
						}							
					}
					
					if(getLowestNonPoint(leadingSuit) != null){			//follow suit wih lowest
						System.out.println("Laying lowest non point card of leading suit");
						return getLowestNonPoint(leadingSuit);
					}
					
					if(getLowestCard(leadingSuit) != null){
						System.out.println("Laying lowest card of leading suit");
						return getLowestCard(leadingSuit);
					}
					
					if(getNumberRemaining(leadingSuit) > getNumberInHand(leadingSuit) + 4 && leadingSuit != trump 
							&& trick.cardsPlayed.get(1).getSuit() != trump){					
						System.out.println("More than 4 cards of leading suit are remaining outside of players hand" +
								" and trump will be winning");
						if(getPointCard(trump) != null){
							System.out.println("Trumping with a point card");
							return getPointCard(trump);
						}
						
						if(getLowestCard(trump) != null){
							System.out.println("Trumping with lowest possible card");
							return getLowestCard(trump);
						}
					}
				}
				
				return layLow(trump);
				
				
		/***********************************************************/	
				
		/**** FOURTH TO LAY *****************************************/
			case 3:
				System.out.println("\nFOURTH TO LAY STRATEGY");
				if(trick.isBetter(trick.cardsPlayed.get(0), trick.cardsPlayed.get(1), trump) &&
						trick.isBetter(trick.cardsPlayed.get(2), trick.cardsPlayed.get(1), trump)){	// team-mate is Winning
					System.out.println("Teammate is winning");
											
						if(getPointCard(leadingSuit) != null && getPointCard(leadingSuit).getRank() != Rank.ACE){
							System.out.println("Laying point card from leading suit");
							return getPointCard(leadingSuit);			//give points to team-mate from leadingSuit
						}
						
						if(getLowestCard(leadingSuit) != null){			// follow suit with lowest
							System.out.println("Laying lowest card of leading suit");
							return getLowestCard(leadingSuit);
						}
						
						for(Suit s : Suit.suits){						// return point card non leading suit non trump
							if(s != leadingSuit && s != trump)
								if(getPointCard(s) != null && getPointCard(s).getRank() != Rank.ACE){
									System.out.println("Laying non trump point card");
									return getPointCard(s);
								}
						}						
						
						if(getNumberInHand(leadingSuit) != 0)			// give trump point card
							if(getPointCard(trump) != null && getPointCard(trump).getRank() != Rank.ACE){
								System.out.println("Laying trump point card");
								return getPointCard(trump);
							}
						
					}
				
				else{		// team-mate is not winning
					System.out.println("Teammate is not winning");
						
					if(getHighestCard(leadingSuit) != null){			// lay highest card of leading suit if it will win
						Card highest = getHighestCard(leadingSuit);
						if(trick.isBetter(trick.cardsPlayed.get(0), highest, trump) 
								&& trick.isBetter(trick.cardsPlayed.get(2), highest, trump)){
							System.out.println("Laying highest card of leading suit");
							return highest;
						}
					}
					
					if(getLowestNonPoint(leadingSuit) != null){			//follow suit wih lowest
						System.out.println("Laying lowest non point card from leading suit");
						return getLowestNonPoint(leadingSuit);
					}
					
					if(getLowestCard(leadingSuit) != null){					// follow suit with lowest
						System.out.println("Laying lowest card of leading suit");
						return getLowestCard(leadingSuit);
					}
					
					if(trick.cardsPlayed.get(2).getSuit() != trump){
						if(getPointCard(trump) != null && getPointCard(trump).getRank() != Rank.ACE 
								&& leadingSuit != trump){		//trump with points
							System.out.println("Laying trump point card");
							return getPointCard(trump);
						}
						
						if(getHighestCard(trump) != null){							// trump with highest if it will win
							Card highest = getHighestCard(trump);
							if(trick.isBetter(trick.cardsPlayed.get(0), highest, trump) 
									&& trick.isBetter(trick.cardsPlayed.get(2), highest, trump)){
								System.out.println("Laying highest trump card");
								return highest;
							}
						}
					}
				return layLow(trump);
				}
		}
		System.out.println("Laying random card");
		return cardsInHand.get(rand.nextInt(cardsInHand.size()));
	}
	

	
	private Card layLow(Suit trump) {
		Random rand = new Random();
		if(getLowestNonPointExclusive(trump) != null){
			System.out.println("Laying lowest non trump non point");
			ArrayList<Card> lowest = getLowestNonPointExclusive(trump);
			return lowest.get(rand.nextInt(lowest.size()));
		}
		
		if(getLowestCardsExclusive(trump) != null){
			System.out.println("Laying lowest non trump");
			ArrayList<Card> lowest = getLowestCardsExclusive(trump);
			return lowest.get(rand.nextInt(lowest.size()));
		}
		
		System.out.println("Laying lowest card");
		ArrayList<Card> lowest = getLowestCards();
		return lowest.get(rand.nextInt(lowest.size()));
	}

	private ArrayList<Card> getLowestNonPointExclusive(Suit suit) {
		ArrayList<Card> lowest = new ArrayList<Card>();
		for(int i = 0; i < 10; i++){
			for(Card c : cardsInHand){
				if(c.getRankIndex() == i && c.getSuit() != suit 
						&& c.getRank() != Rank.ACE && c.getRank() != Rank.TEN && c.getRank() != Rank.FIVE)
					lowest.add(c);
			}
			if(!lowest.isEmpty())
				return lowest;
		}
		return null;
	}

	private ArrayList<Card> getLowestCardsExclusive(Suit suit) {
		ArrayList<Card> lowest = new ArrayList<Card>();
		for(int i = 0; i < 10; i++){
			for(Card c : cardsInHand){
				if(c.getRankIndex() == i && c.getSuit() != suit)
					lowest.add(c);
			}
			if(!lowest.isEmpty())
				return lowest;
		}
		return null;
	}

	public int cardExists(int rankIndex, Suit suit){
		for(int i = 0; i < cardsInHand.size(); i++){
			if(cardsInHand.get(i).getRankIndex() == rankIndex && cardsInHand.get(i).getSuit() == suit){
				return i;
			}
		}
		return -1;
	}
	
	

	private ArrayList<Card> getLowestCards() {
		ArrayList<Card> lowest = new ArrayList<Card>();
		for(int i = 0; i < 10; i++){
			for(Card c : cardsInHand){
				if(c.getRankIndex() == i)
					lowest.add(c);
			}
			if(!lowest.isEmpty())
				return lowest;
		}			
		return null;
	}

	private ArrayList<Card> getHighestCards() {
		ArrayList<Card> highest = new ArrayList<Card>();
		for(int i = 9; i >= 0; i--){
			for(Card c : cardsInHand){
				if(c.getRankIndex() == i)
					highest.add(c);
			}
			if(!highest.isEmpty())
				return highest;
		}			
		return null;
	}
	
	

	private int numberBetter(Card c, Suit trump) {
		int count = 0;
		switch(c.getSuit()){
		case DIAMONDS:
			for(Rank r : Diamonds){
				if(r.getValue() > c.getRankIndex())
					count++;
			}
		case CLUBS:
			for(Rank r : Clubs){
				if(r.getValue() > c.getRankIndex())
					count++;
			}
		case HEARTS:
			for(Rank r : Hearts){
				if(r.getValue() > c.getRankIndex())
					count++;
			}
		case SPADES:
			for(Rank r : Spades){
				if(r.getValue() > c.getRankIndex())
					count++;
			}
		}
		if(c.getSuit() != trump)
			count += getNumberRemaining(trump);
		return count;
	}
	

	private Suit getMostCommonSuit() {
		int numCards = Diamonds.size();
		Suit mostCommon = Suit.DIAMONDS;
		
		if(Clubs.size() > numCards){
			numCards = Clubs.size();
			mostCommon = Suit.CLUBS;
		}
		
		if(Hearts.size() > numCards){
			numCards = Hearts.size();
			mostCommon = Suit.HEARTS;
		}
		
		if(Spades.size() > numCards){
			numCards = Spades.size();
			mostCommon = Suit.SPADES;
		}
		
		return mostCommon;		
	}

	private boolean isWinner(Card c) {
		switch(c.getSuit()){
		case DIAMONDS:
			if(getHighestRank(Diamonds, Suit.DIAMONDS) == c)
				return true;			
		case CLUBS:
			if(getHighestRank(Clubs, Suit.DIAMONDS) == c)
				return true;
		case HEARTS:
			if(getHighestRank(Hearts, Suit.DIAMONDS) == c)
				return true;
		case SPADES:
			if(getHighestRank(Hearts, Suit.DIAMONDS) == c)
				return true;
		}
		return false;
	}


	
	private void updateRanks(Trick trick) {
		for(Card c : trick.cardsPlayed){					// update remaining cards based on current trick
			removeFromRemaining(c);
		}
		for(Trick t : game.getCurrentRound().tricks){
			for(Card c : t.cardsPlayed){
				removeFromRemaining(c);								// update remaining cards based on previous tricks this round
			}
		}
		
	}

	private void removeFromRemaining(Card c) {
		switch(c.getSuit()){
		case DIAMONDS:
			if(Diamonds.indexOf(c) != -1)
				Diamonds.remove(Diamonds.indexOf(c.getRank()));
		case CLUBS:
			if(Clubs.indexOf(c) != -1)
				Clubs.remove(Clubs.indexOf(c.getRank()));
		case HEARTS:
			if(Hearts.indexOf(c) != -1)
				Hearts.remove(Hearts.indexOf(c.getRank()));
		case SPADES:
			if(Spades.indexOf(c) != -1)
				Spades.remove(Spades.indexOf(c.getRank()));
		}		
	}

	private Card getPointCard(Suit suit) {
		int fivePosition = cardExists(0, suit);
		if(fivePosition != -1){
			return cardsInHand.get(fivePosition);
		}
		
		int tenPosition = cardExists(5, suit);
		if(tenPosition != -1){
			return cardsInHand.get(tenPosition);
		}
		
		int acePosition = cardExists(9, suit);
		if(acePosition != -1){
			return cardsInHand.get(acePosition);
		}
		
		return null;
	}
	
	
	

	private int getNumberInHand(Suit suit) {
		int count = 0;
		for(Card c : cardsInHand){
			if(c.getSuit() == suit)
				count++;
		}
		return count;
	}
	
	
	

	private int getNumberRemaining(Suit suit) {
		switch(suit){
			case DIAMONDS:
				return Diamonds.size();
			case CLUBS:
				return Clubs.size();
			case HEARTS:
				return Hearts.size();
			case SPADES:
				return Spades.size();
		}
		return -1;
	}
	
	
	

	private Card getLowestCard(Suit suit) {
		for(int i = 0 ; i < 9; i++){									
			int position = cardExists(i, suit);
			if(position != -1)
				return cardsInHand.get(position); 
		}
		return null;
	}
	
	private Card getLowestNonPoint(Suit suit) {
		for(int i = 1 ; i < 8; i++){									
			int position = cardExists(i, suit);
			if(position != -1 && i != 5)
				return cardsInHand.get(position); 
		}
		return null;
	}

	
	
	
	private Card getHighestCard(Suit suit) {
		for(int i = 9; i >= 0; i--){
			int position = cardExists(i, suit);
			if(position != -1){
					return cardsInHand.get(position);
			}
		}
		return null;
	}
	
	private Card getHighestRank(ArrayList<Rank> ranks, Suit suit) {
		for(int i = 9; i <= 0; i++){
			for(Rank r : ranks){
				if(r.getValue() == i)
					return new Card(suit, r);
			}
		}
		return null;
	}


	public void reccomend(ArrayList<Card> cardsInHand, Game game) {
		Trick trick = game.getCurrentTrick();
		Suit trump = game.getCurrentRound().getTrumpSuit();		
		
		this.cardsInHand.clear();
		
		for(Card c : cardsInHand){
			this.cardsInHand.add(c);
		}
		
		for(int i = 0; i < 10 ; i++){
			Diamonds.add(Rank.ranks[i]);
			Clubs.add(Rank.ranks[i]);
			Hearts.add(Rank.ranks[i]);
			Spades.add(Rank.ranks[i]);
		}	
		
		if(trick.cardsPlayed.size() > 0){
			leadingSuit = trick.cardsPlayed.get(0).getSuit();
		}
		
		Suit mostCommonSuit = getMostCommonSuit();
		
		ArrayList<Card> winners = new ArrayList<Card>();
		
		updateRanks(trick);
				
		for(Card c : cardsInHand){
			if(numberBetter(c, trump) == 0){
				winners.add(c);
			}
		}
		
		switch(trick.cardsPlayed.size()){
			
		/**** FIRST TO LAY ****************************************/
			
			case 0:												
				
				game.printToConsole("\nFOLLOWING FIRST TO LAY STRATEGY");
				
				if(winners.isEmpty()){	// no winners
					game.printToConsole("There are no guaranteed winners in your hand");
					
					for(Card c : getHighestCards()){
						if(c.getSuit() == trump){
							if(numberBetter(c, trump) - getNumberInHand(c.getSuit()) < 3){
								game.printToConsole("It is recommended to lay the " + c.getRank() + " of " + c.getSuit());
								return;
							}
							else if(numberBetter(c, trump) - getNumberRemaining(trump) - getNumberInHand(c.getSuit()) < 3 && c.getSuit() != trump){
								game.printToConsole("It is recommended to lay the " + c.getRank() + " of " + c.getSuit());
								return;
							}
						}
					}
					Card c = layLow(trump);
					game.printToConsole("It is reccomended to lay the " + c.getRank() + " of " + c.getSuit());
					return;
					
				}
				
				else{ 	// winners exist
					game.printToConsole("Guaranteed winners exist in your hand");
					if(getNumberInHand(trump) > getNumberRemaining(trump)/4){					
						for(Card c : winners){
							if(c.getSuit() == trump){
								game.printToConsole("It is recommended to lay the " + c.getRank() + " of " + c.getSuit());
								return;
							}
						}					
					}
				
					for(Card c : winners){
						if(c.getSuit() == mostCommonSuit){
							game.printToConsole("It is reccomended to play your winner of the " + c.getRank() + " of " + c.getSuit());
							return;
						}
					}
					
					game.printToConsole("It is reccomended to lay a random winner. Winners are :");
					for(Card c : winners){
						game.printToConsole("The " + c.getRank() + " of " + c.getSuit());
					}
					return;
				
				}
				
		/***********************************************************/	
				
		/**** SECOND TO LAY ****************************************/	
			
			case 1:																
				game.printToConsole("\nFOLLOWING SECOND TO LAY STRATEGY");
				if(!winners.isEmpty()){											// attempt to lay winning card of leadingSuit
					game.printToConsole("Guaranteed winners exist in your hand");
					for(Card c : winners){											
						if(c.getSuit() == leadingSuit){
							game.printToConsole("It is recommended to lay the " + c.getRank() + " of " + c.getSuit());
							return;
						}
					}
				}
				
				if(getHighestCard(leadingSuit) != null){
					game.printToConsole("Cards of the leading suit exist in your hand");
					Card c = getHighestCard(leadingSuit);
					if(trick.isBetter(trick.cardsPlayed.get(0), c, trump)){	// attempt to beat current card with leadingSuit
						game.printToConsole("It is reccomended to the " + c.getRank() + " of " + c.getSuit());
						return;
					}
				}
				
				
				if(getLowestNonPoint(leadingSuit) != null){					// attempt to play lowest card of leadingSuit
					Card c = getLowestNonPoint(leadingSuit);
					game.printToConsole("It is reccomended to the " + c.getRank() + " of " + c.getSuit());
					return;
				}
				
				if(getLowestCard(leadingSuit) != null){
					Card c = getLowestCard(leadingSuit);
					game.printToConsole("It is reccomended to lay the " + c.getRank() + " of " + c.getSuit());
					return;
				}
				
								
				if(getNumberRemaining(leadingSuit) > getNumberInHand(leadingSuit) + 4 && leadingSuit != trump){					
					game.printToConsole("More than 4 cards of leading suit are remaining outside of your hand" +
							" and leading suit is not trump");
					if(getPointCard(trump) != null){
						Card c = getPointCard(trump);
						game.printToConsole("It is reccomended to lay the " + c.getRank() + " of " + c.getSuit());
						return;
					}
					
					if(getLowestCard(trump) != null){
						Card c = getLowestCard(trump);
						game.printToConsole("It is reccomended to lay the " + c.getRank() + " of " + c.getSuit());
						return;
					}
					
				}
				Card c = layLow(trump);
				game.printToConsole("It is reccomended to lay the " + c.getRank() + " of " + c.getSuit());
				return;

		/***********************************************************/	
		
		/**** THIRD TO LAY *****************************************/	
			
			case 2:
				game.printToConsole("\nFOLLOWING THIRD TO LAY STRATEGY");
				if((trick.isBetter(trick.cardsPlayed.get(1), trick.cardsPlayed.get(0), trump) 
						&& numberBetter(trick.cardsPlayed.get(0), trump) - getNumberRemaining(trump) <= 4 
							&& (getNumberRemaining(leadingSuit) > 4 || cardsInHand.size() < 4)) || isWinner(trick.cardsPlayed.get(0))){
					// teammate is winning & is probable card wont be beaten
						game.printToConsole("Your teammate is winning and it is probable their card won't be beaten");
						
						if(getPointCard(leadingSuit) != null && getPointCard(leadingSuit).getRank() != Rank.ACE){
							Card c1 = getPointCard(leadingSuit);
							game.printToConsole("It is reccomended to lay the " + c1.getRank() + " of " + c1.getSuit());
							return;			//give points to team-mate
						}
						
						if(getLowestCard(leadingSuit) != null){
							Card c1 = getLowestCard(leadingSuit);
							game.printToConsole("It is reccomended to lay the " + c1.getRank() + " of " + c1.getSuit());
							return;
						}
				}
							
				else{		// team-mate is not winning or will probably be beaten
					game.printToConsole("Either your teammate is not winnning or it is probable their card will be beaten");

					if(getHighestCard(leadingSuit) != null){		// lay highest card of leading suit if it is likely to win		
						if(((trick.cardsPlayed.get(1).getSuit() != trump || leadingSuit == trump) 
								&& numberBetter(getHighestCard(leadingSuit), trump)	- getNumberRemaining(trump) <= 2 
								&& getNumberRemaining(leadingSuit) > 4)|| isWinner(getHighestCard(leadingSuit))){
							game.printToConsole("It is probable that your highest card of the leading suit will not be beaten");
							//it is probable players highest card wont be beaten
							Card c1 = getHighestCard(leadingSuit);
							game.printToConsole("It is reccomended to lay the " + c1.getRank() + " of " + c1.getSuit());
							return;
						}							
					}
					
					if(getLowestNonPoint(leadingSuit) != null){			//follow suit wih lowest
						Card c1 = getLowestNonPoint(leadingSuit);
						game.printToConsole("It is reccomended to lay the " + c1.getRank() + " of " + c1.getSuit());
						return;
					}
					
					if(getLowestCard(leadingSuit) != null){
						Card c1 = getLowestCard(leadingSuit);
						game.printToConsole("It is reccomended to lay the " + c1.getRank() + " of " + c1.getSuit());
						return;
					}
					
					if(getNumberRemaining(leadingSuit) > getNumberInHand(leadingSuit) + 4 && leadingSuit != trump 
							&& trick.cardsPlayed.get(1).getSuit() != trump){					
						game.printToConsole("More than 4 cards of the leading suit are remaining outside of your hand" +
								" and trump will be winning if played");
						if(getPointCard(trump) != null){
							Card c1 = getPointCard(trump);
							game.printToConsole("It is reccomended to lay the " + c1.getRank() + " of " + c1.getSuit());
							return;
						}
						
						if(getLowestCard(trump) != null){
							Card c1 = getLowestCard(trump);
							game.printToConsole("It is reccomended to lay the " + c1.getRank() + " of " + c1.getSuit());
							return;
						}
					}
				}
				Card c1 = layLow(trump);
				game.printToConsole("It is reccomended to lay the " + c1.getRank() + " of " + c1.getSuit());
				return;
				
				
		/***********************************************************/	
				
		/**** FOURTH TO LAY *****************************************/
			case 3:
				game.printToConsole("\nFOLLOWING FOURTH TO LAY STRATEGY");
				if(trick.isBetter(trick.cardsPlayed.get(0), trick.cardsPlayed.get(1), trump) &&
						trick.isBetter(trick.cardsPlayed.get(2), trick.cardsPlayed.get(1), trump)){	// team-mate is Winning
						game.printToConsole("Your teammate is winning");
											
						if(getPointCard(leadingSuit) != null && getPointCard(leadingSuit).getRank() != Rank.ACE){
							Card c11 = getPointCard(leadingSuit);
							game.printToConsole("It is recommended to lay the " + c11.getRank() + " of " + c11.getSuit());
							return;			//give points to team-mate from leadingSuit
						}
						
						if(getLowestCard(leadingSuit) != null){			// follow suit with lowest
							Card c11 = getLowestCard(leadingSuit);
							game.printToConsole("It is reccomended to lay the " + c11.getRank() + " of " + c11.getSuit());
							return;
						}
						
						for(Suit s : Suit.suits){						// return point card non leading suit non trump
							if(s != leadingSuit && s != trump)
								if(getPointCard(s) != null && getPointCard(s).getRank() != Rank.ACE){
									Card c11 = getPointCard(s);
									game.printToConsole("It is reccomended to lay the " + c11.getRank() + " of " + c11.getSuit());
									return;
								}
						}						
						
						if(getNumberInHand(leadingSuit) != 0)			// give trump point card
							if(getPointCard(trump) != null && getPointCard(trump).getRank() != Rank.ACE && getPointCard(trump).getRank() != Rank.TEN){
								Card c11 = getPointCard(trump);
								game.printToConsole("It is reccomended to lay the " + c11.getRank() + " of " + c11.getSuit());
								return;
							}						
						
			}
				else{		// team-mate is not winning
					game.printToConsole("Your teammate is not winning");
						
					if(getHighestCard(leadingSuit) != null){			// lay highest card of leading suit if it will win
						Card c11 = getHighestCard(leadingSuit);
						if(trick.isBetter(trick.cardsPlayed.get(0), c11, trump) 
								&& trick.isBetter(trick.cardsPlayed.get(2), c11, trump)){
							game.printToConsole("It is reccomended to lay the " + c11.getRank() + " of " + c11.getSuit());
							return;
						}
					}
					
					if(getLowestNonPoint(leadingSuit) != null){			//follow suit wih lowest
						Card c11 = getLowestNonPoint(leadingSuit);
						game.printToConsole("It is recommended to lay the " + c11.getRank() + " of " + c11.getSuit());
						return;
					}
					
					if(getLowestCard(leadingSuit) != null){					// follow suit with lowest
						Card c11 = getLowestCard(leadingSuit);
						game.printToConsole("It is reccomended to lay the " + c11.getRank() + " of " + c11.getSuit());
						return;
					}
					
					if(trick.cardsPlayed.get(2).getSuit() != trump){
						if(getPointCard(trump) != null && getPointCard(trump).getRank() != Rank.ACE 
								&& leadingSuit != trump){		//trump with points
							Card c11 = getPointCard(trump);
							game.printToConsole("It is reccomended to lay the " + c11.getRank() + " of " + c11.getSuit());
							return;
						}
						
						if(getHighestCard(trump) != null){							// trump with highest if it will win
							Card c11 = getHighestCard(trump);
							if(trick.isBetter(trick.cardsPlayed.get(0), c11, trump) 
									&& trick.isBetter(trick.cardsPlayed.get(2), c11, trump)){
								game.printToConsole("It is reccomended to lay the " + c11.getRank() + " of " + c11.getSuit());
								return;
							}
						}
					}
				Card c11 = layLow(trump);
				game.printToConsole("It is reccomended to lay the " + c11.getRank() + " of " + c11.getSuit());	
				return;
				}
		}
		game.printToConsole("No specific reccomendation could be formulated. Lay a random card");
		return;
	}
}
