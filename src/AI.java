/********************************************************************
 * AI (interface)
 * 
 * Interface for AI players. Provides the ability to use the
 * strategy pattern.
 * 
 * @author Grant O'Brien, Nicholas Noel and Michael McKenzie
 *
 ********************************************************************/

public interface AI {
	Card chooseCard();
	void removeCard(Card card);
}
