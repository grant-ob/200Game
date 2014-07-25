/** Team *******************************************************************
 * Defines the teams which hold their score
 * opposite-facing players are on the same team and have the same score
 * 
 ********************************************************************************/
public class Team {
	private int score;
	
	public int getscore(){
		return score;
	}

	public void addToScore(int points){
		score+=points;
	}
	
	public void resetScore(){
		score = 0;
	}
	
	public void removeFromScore(int points){
		score-=points;
	}

	public Team(){
		score = 0;
	}

}
