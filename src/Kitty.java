import java.util.ArrayList;

public class Kitty {
	
	public ArrayList<Card> cards;
	
	public Kitty(){
		cards = new ArrayList<Card>();
	}
	
	public Card getCard(int i){
		return cards.get(i);
	}
	
	public void swap(int kittyCardPos, Card card){
		cards.set(kittyCardPos, card);
	}
	
	public boolean fin(){
		return true;
	}
	
	public void add(Card card){
		cards.add(card);
	}
	
	public int findCard(Card card){
		return cards.indexOf(card);
	}
	
	public void emptyKitty(){
		cards.clear();
	}

	public boolean hasPointCards() {
		for(Card c : cards){
			if(c.getPoints() != 0){
				return true;
			}
		}
		return false;
	}
	
}
