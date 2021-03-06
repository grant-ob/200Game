
![banner](logo.png)
# Welcome to 200Game 👋
![Version](https://img.shields.io/badge/version-1.00.00-blue.svg?cacheSeconds=2592000)
> Java version of the card game &#34;200&#34;

## Usage 💻
To run the game, simply execute:

```sh
java -jar 200Game.jar
```

## About 200 🃏
200 is a turn-based point-trick card game that originated in Atlantic Canada. For more information, see [200 - Card Game Rules](https://www.pagat.com/kt5/200.html), or continue reading here for help with the game.

### Players and Cards Help
200 is generally agreed to be best for four players in partnerships - North and South play against East and West.
For this game, 4-player game with a kitty, a 36-card pack is created by removing all the 2's, 3's, 4's, from a standard 52-card pack without jokers. The cards in each suit rank from high to low: A-K-Q-J-10-9-8-7-5.

The cards have point values as follows:
- Each ace: 10 points
- Each ten: 10 points
- Each five: 5 points
- Other cards: 0 points

This makes 100 card points in the deck altogether.

The dealing, bidding and play are all clockwise.

### Deal Help
The first dealer is chosen at random. After each hand, the turn to deal passes to the left. Before each deal, the cards are shuffled. The dealer then deals 9 cards face down to each player.

### Bidding Help
Players now bid for the right to choose which suit will be trump, each bid representing the number
of points the bidder's partnership contracts to take in tricks if not outbid.

The player to the left of the dealer speaks first, and the bidding continues clockwise. The minimum
bid is 50, all bids must be multiples of 5, and each bid must be higher than the last. A player who
does not wish to bid can pass, but having passed cannot bid again in that auction.

The bidding continues for as many circuits as necessary until three players have passed, or until
someone bids 100, the highest possible bid.

The final (and highest) bidder wins the bid, and announces which suit will be trump for that hand.

### Kitty Help
The winner of the bid also gets to look at the Kitty, which is made up of 4 secret cards. They may
choose to substitute any card in their hand for the cards in the Kitty, bit they may not discard any
point cards, unless their hand is all point cards.

### Play Help
Having announced the trump suit, the winner of the bid leads to the first trick. Players must follow
suit if able to. A player who has no card of the suit led is free to play any card. The trick is won by
the highest trump in it, or, if it contains no trumps, by the highest card of the suit that was led. The
winner of each trick leads to the next.

The objective is to win tricks that contain card points (Aces, 10's and 5's). Tricks without points have
no value.

Playing a trump when you have no card of the non-trump suit that was led is known as "cutting".
There is no obligation to try to win the trick or to cut when you are unable to follow suit - it is legal
to discard from another suit. Indeed if you expect your partner to win the trick you will probably
want to discard a ten or five that might otherwise have been lost to the opponents.

### Scoring Help
Scores are kept in the sidebar to the right of the screen, the trump suit is always available here also.
When all the cards have been played, team scores are counted. If the winner of the bid's team has at least as many card points as the final bid, the total value of the cards in their tricks is added to their cumulative score. If the number of card points they took is less than the bid, the amount of the bid is subtracted from their cumulative score.

The opposing team add whatever card points they took in tricks to their cumulative score.
A team's cumulative score can be negative.

The first team to achieve a score of 200 points or more wins the game. If both teams reach 200 or more on the same deal then the bidding team wins.

The game also ends if one team reaches a negative score of 200 or worse while the other team's score is positive or zero. In that case, the team with minus 200 or worse loses the game.

### Tactics

#### Bidding
A good hand for bidding is one with aces or other high cards, preferably in sequence, and with an unbalanced distribution including one or two long suits. Point cards other than aces are a liability.

#### Kitty
When playing with a kitty, the kitty may improve the contractor's hand, but may sometimes make it worse. Best are aces or other high cards, or cards of your long suit. Worst are point cards, especially in your short suits - these can ruin an otherwise good hand.

#### Play
As in any trick-taking game, players should try to keep track of the cards that have been played, especially trumps, high cards and point cards. The winning bidder usually makes the last few tricks (he usually ends up with a trump or two). Knowing the exact trump position can greatly simplify your play. If you're with this player and know how many trumps he has, you know how and when you can save your points. This is even more important if he's not in your team. If you know how many trumps your opponent has, you'll know if and when you should gamble your point cards.

As in any partnership game, you should help your partner. For example when partner is winning the trick, you can throw a count card that might otherwise have been lost, but usually not an ace, since it might win a trick later. If your partner won the bid and you know he can cut (trump) a given suit, by all means lead that suit. He will be able to get rid of his unwanted cards (or cut if there are points in the trick).

If your team didn't win the bid, play your non-trump aces as soon as possible. Many players are tempted to keep their aces instead of playing them on a trick with no other points. This is a big gamble that should only be taken under exceptional circumstances. The reverse is true if your team won the bid and all of your opponents' trump cards have been played - now you can keep aces to capture other point cards. However, you should be careful, especially if your partner won the bid. Everyone will know you have that ace and your partner may not have another card of that suit to lead to it, in which case you risk that the suit will never be led again and you will have to throw your ace on an opponent's trick at the end.

If you only have K-5 (or even K-10) of a given suit, you should almost always lead the 5 (or 10). You were probably going to lose it anyway, and this way, you will probably win the second trick in that suit.

## Authors

👤 **Grant O'Brien, Nicholas Noel and Michael McKenzie**

*Originally written for Software Design 5895 Course Project, Winter 2011 @ Memorial University of Newfoundland 🎓*


## Show your support

Give a ⭐️ if this project helped you!


## Enjoy! ♦♣♥♠
***
_This README was generated with ❤️ by [readme-md-generator](https://github.com/kefranabg/readme-md-generator)_
