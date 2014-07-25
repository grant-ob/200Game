
public enum Rank {
	FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE, NONE;
	
	public static final Rank[] ranks = {FIVE,SIX,SEVEN,EIGHT,NINE,TEN,JACK,QUEEN,KING,ACE};
	
	public int getValue(){
		switch(this){
			case FIVE:
				return 0;
			case SIX:
				return 1;
			case SEVEN:
				return 2;
			case EIGHT:
				return 3;
			case NINE:
				return 4;
			case TEN:
				return 5;
			case JACK:
				return 6;
			case QUEEN:
				return 7;
			case KING:
				return 8;
			case ACE:
				return 9;
		}
		return -1;
	}
}
