//package Game;

import java.util.ArrayList;
import java.util.Random;

/** Round *******************************************************************
 * Creates a new round which holds all the tricks and the trump suit
 * 
 ********************************************************************************/
public class Round {
	private Suit trumpSuit;
	public ArrayList<Trick> tricks ;
	
	public Round(){
		tricks = new ArrayList<Trick>();
		//Random selector = new Random();						//trump is randomly selected, in second iteration it will be determined by
		trumpSuit = Suit.NONE;     	//the player who wins the bid
	}
	
	void setTrump(Suit suit){
		trumpSuit = suit;
	}
	
	void addTrick(Trick trick){
		tricks.add(trick);
	}
	
	Trick getLastTrick(){									//in second iteration, human player will be able to see last trick to help
		return tricks.get(tricks.size()-1);					//in deciding which card to lay
	}
	
	Suit getTrumpSuit(){
		return trumpSuit;
	}
}
