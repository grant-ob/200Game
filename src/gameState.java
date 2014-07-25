/** gameState *******************************************************************
 * enumeration of the possible game states
********************************************************************************/
public enum gameState {
	IDLE,					//no GUI interaction possible
	PLAYERTURN,				//GUI waits for player to choose a card
	AITURN,					//game follows AI functions to lay cards
	TRICKOVER,				//ready to calculate points and determine winner
	PLAYERBID,
	AIBID,
	TRUMP,
	KITTY,
	CHOOSE_DIFFICULTY
}
