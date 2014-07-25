/*************************************************************************************
 * GameRunner (class)
 * 
 * A class representing the logic of the game. Creates a Game object. The
 * gameLoop method runs the game by making calls to the Game class.
 * 
 * @author Grant O'Brien, Nicholas Noel and Michael McKenzie
 *
 *************************************************************************************/
public class GameRunner { 
	private Game game;
	
	/** GameRunner *******************************************************************
	 * The constructor for the GameRunner class. Creates a new game with the
	 * appropriate aiType passed to it.
	 * 
	 ********************************************************************************/
	
	public GameRunner(){
		game = new Game();
	}
	
	/** gameLoop() *******************************************************************
	 * Contains the main logic of the 200 game. Makes calls to the game class.	  
	 * 
	 ********************************************************************************/
	
	void gameLoop(){
		boolean passed[] = new boolean[4];
		
		game.printToConsole("Welcome to 200! \nThis program is not for profit or distribution...yet!");
		game.printToConsole("\nChoose game difficulty");
		while(game.getGameStatus() == gameState.CHOOSE_DIFFICULTY){
			waiting(1);
		}
		int firstToBid = 0;
		while(true){
			game.disableLastTrick();
			game.clearTrick();
			game.enableBidPanel();
			Suit trump = null;
			int team1Score = 0;
			int team2Score = 0;
			int winner = -1;
			int bidder = firstToBid;
			int currentBid = 45;
			int currentLeader = -1;
			game.setCurrentBid(45);
			
			passed[0] = false;
			passed[1] = false;
			passed[2] = false;
			passed[3] = false;
			game.newRound();			
			game.deal();
			game.refreshKitty(false);
			game.enableBidPanel();
			
			if(firstToBid == 0){
				game.printToConsole("\n " +toName(3)+ " will deal this round");
			}
			else{game.printToConsole("\n " +toName(firstToBid-1)+ " will deal this round");}
			game.printToConsole(" " +toName(firstToBid)+ " will be the first to bid");
			
			waiting(1);
			
			//bidding sequence
			BIDDING:{
			while(true){
				waiting(1);
				if(bidder == 0){
					if(passed[0] == true){
						game.printToConsole("\n You automatically pass because you passed last time");
						bidder++;
					}
					else{
						if(0 == currentLeader){
							game.printToConsole("\n You won the bid with a bid of " + currentBid);
							game.setState(6);
							game.printToConsole("\n Please choose trump");
							break BIDDING;
						}
						game.printToConsole("\n It is your turn to bid, you must bid more than " + currentBid + " or pass");
						game.setState(4);
						while(game.getGameStatus() == gameState.PLAYERBID){	//waits for human player to bid
							waiting(1);
						}
					
						if(game.getPlayerBid() == 0){
							passed[0] = true;
							game.printToConsole("\n You pass");
						}
						else{ 
							currentBid = game.getPlayerBid();
							game.printToConsole("\n You bid " + currentBid);
							currentLeader = 0;
						}
						bidder++;
					}
				}
				else{
					if(passed[bidder] == true){
						game.printToConsole("\n " + toName(bidder) + " automatically passes");
						
					}
					else{
					//game.printToConsole("AI got chance to bid");
					if(bidder == currentLeader){
						trump = game.getPlayer(bidder).suit;
						game.setTrump(trump);
						game.aiSwapTrump(game.getPlayer(bidder));
						game.printToConsole("\n " + toName(bidder) + " won the bid with a bid of " + currentBid);
						waiting(2);
						game.setCurrentBid(currentBid);
						break BIDDING;
					}
					//game.printToConsole("\nCurent player is " +firstToBid);
					int aiBid = game.aiBid(game.getPlayer(bidder), bidder);
					//game.printToConsole("\n AI Max Bid is " + aiBid);
					if( aiBid > currentBid){
						currentBid+=5;
						
						game.setCurrentBid(currentBid);
						game.printToConsole("\n " + toName(bidder) + " bids " + currentBid);
						currentLeader = bidder;
					}
					else{
						game.printToConsole("\n " + toName(bidder) + " passes.");
						passed[bidder] = true;
					}
					
					}
					if (bidder == 3)
						bidder = 0;
					else bidder++;
				}
			}
		}
		game.disableBidPanel();
		game.enableTrumpPanel();
		if(currentLeader == 0)										//player chooses trump if won bidding
			while(game.getGameStatus() == gameState.TRUMP){
				waiting(1);
			}
		else {
			game.disableTrumpPanel();}
		trump = game.getTrump();
		int tricknum = 9;													//there are 9 tricks in each round
		
		game.printToConsole("\n Trump is " + game.getCurrentRound().getTrumpSuit().toString());
		game.setState(7);		// Kitty phase
		
		if(currentLeader == 0){
			game.refreshKitty(true);	// Make kitty visible to player
			game.printToConsole("\nExchange cards from your hand to the kitty.");
			while(game.getGameStatus() == gameState.KITTY){
				waiting(1);
			} 
		}
		
		game.removeKitty();		
		
		while(tricknum > 0){
			
			if (tricknum == 9){												//first trick, human player goes first
				winner = currentLeader;
			}
			else {
				winner = game.getWinner();
			}

			game.setWinner(winner);
			game.newTrick();
			game.repaint();
			
			
			for(int i = 0; i<4; i++){										//4 iterations for each player to lay
				if (winner == 0){
					game.printToConsole("\n It is your turn!");
					while(game.getGameStatus() == gameState.PLAYERTURN){	//waits for human player to lay
						waiting(1);
						
					}
					winner++;
					
				}
				else {game.aiLayCard(game.getPlayer(winner));				//ai turn, loops winner back to 0 to continue turns if applicable
					game.repaint();
					waiting(1);
					winner++;
					if(winner == 4)
						winner = 0;
					
				}
				
			}
			
			int points = game.getCurrentTrick().getPoints();				//determines points in trick
			winner = game.getCurrentTrick().getWinnerIndex(trump);			//determines who will get points
			game.getPlayer(winner).addPointsWon(points);
			game.setWinner(winner);											//winner will start next trick
			game.getPlayer(winner).team.addToScore(points);
			if(game.getPlayer(winner).team == game.getTeam(0)){
				team1Score += points;
			}
			else{team2Score += points;}
			game.printToConsole("\n " + toName(winner) + " won! Their team got " + points + " points");
			game.printToConsole("\n Your team has " + team1Score + " points");
			game.printToConsole(" AI team has " + team2Score + " points");
			tricknum--;
			waiting(2);
			//game.clearConsole(); 
		}
		
		if(game.getPlayer(currentLeader).team == game.getTeam(0)){
			if(team1Score <= currentBid){
				game.getTeam(0).removeFromScore(currentBid);
				game.getTeam(0).removeFromScore(team1Score);
				game.printToConsole("\n Sorry, your team lost " + currentBid + " points this round");
			}
			
		}
		else{
			if(team2Score <= currentBid){
				game.getTeam(1).removeFromScore(currentBid);
				game.getTeam(1).removeFromScore(team2Score);
				int totalLost = currentBid+team1Score;
				game.printToConsole("\n AI team lost " + totalLost + " points this round");
			}
		}
		if(game.getTeam(0).getscore() >= 200){
			game.printToConsole("\n Congratulations, your team won the game!");
			game.getTeam(0).resetScore();
			game.getTeam(1).resetScore();
		}
		else{if(game.getTeam(1).getscore() >= 200){
				game.printToConsole("\n Ai team won the game :(");
				game.getTeam(0).resetScore();
				game.getTeam(1).resetScore();
			}
			else{if(game.getTeam(0).getscore() <= -200){
				game.printToConsole("\n Sorry, your team loses by going below -200 points. Better luck next time!");
				game.getTeam(0).resetScore();
				game.getTeam(1).resetScore();
			}
				else{if(game.getTeam(1).getscore() <= -200){
					game.printToConsole("\n Congratulations, your team wins by default because Team 2 went below -200 points");
					game.getTeam(0).resetScore();
					game.getTeam(1).resetScore();
				}
					else{if(game.getTeam(0).getscore() > game.getTeam(1).getscore())
						game.printToConsole("\n Your team is in the lead with " + game.getTeam(0).getscore() + " points!");
						else{ 
							game.printToConsole("\n Your team is losing with " + game.getTeam(0).getscore() + " points :(");
						waiting(1);}
					}
				}
			}
		}
		if(game.getTeam(0).getscore() == 0 && game.getTeam(1).getscore() == 0){
			game.printToConsole("\n New game will now begin...");
		}
		else{
			game.printToConsole("\n New round will now begin...");
		}
		firstToBid++;
		if (firstToBid == 4)
			firstToBid = 0;
		waiting(3);
		}
	}
	
	/** waiting (int n) *******************************************************************
	 * wait state function to improve playability of game	  
	 * game will pause for n seconds before continuing will next line
	 ********************************************************************************/
	public static void waiting (int n){
		long t0, t1;
		t0 =  System.currentTimeMillis();
		do{
			t1 = System.currentTimeMillis();
		}
		while ((t1 - t0) < (n * 1000));
	}
	
	public String toName(int i){
		if(i == 0)
			return "South";
		if(i ==1)
			return "West";
		if(i == 2)
			return "North";
		if(i == 3)
			return "East";
		return "NULL";
	}
}
