/** Humean *******************************************************************
 * class for human players, overrides layCard from player
 ********************************************************************************/
public class Human extends Player{

	public Human(Team team) {
		super(team);
	}
	
	@Override
	public boolean layCard(Card card){			//isValid has already been called, card is a valid lay  
		if(cardsInHand.isEmpty())
			return false;		
		cardsInHand.remove(card);
		return true;
	}
}
