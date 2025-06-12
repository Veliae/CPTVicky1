import arc.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class VickysTools {
	
	// ==================== CARD SYSTEM VARIABLES ====================
	// Card deck arrays - 52 cards x 3 columns (value, suit, random number for sorting)
		public static int[][] intDeck = new int[52][3];
		// 5 cards x 2 (value, suit)
		public static int[][] intHand = new int[5][2]; 
		// Track which cards are used
		public static boolean[] bolUsedCards = new boolean[52]; 
	 
		// ==================== LEADERBOARD VARIABLES ====================
	// These arrays will keep names and money
	public static String[] arrNames = {"You", "Player 2", "Player 3", "Player 4"};
	public static int[] arrMoney = {1000, 1000, 1000, 1000};
	
	// ==================== UTILITY METHODS ====================
	// Will be using this to keep everything centered 
	public static void drawCenteredString(Console con, String strText, int intY) {
		// check how wide each character is
		int intCharWidth = 14; 
		// Calculates total width of the text
		int intTextWidth = strText.length() * intCharWidth;
		// Calculates where to start drawing to center the text by dividing by 2
		int intX = (1280 - intTextWidth) / 2;
		con.drawString(strText, intX, intY);
	}
	
	// ==================== CARD METHODS ====================
	// Initialize and shuffle the deck
	public static void prepareDeck() {
		// Fill deck with cards
		int intCardIndex = 0;
		// 1=diamonds, 2=clubs, 3=hearts, 4=spades
		for (int intSuit = 1; intSuit <= 4; intSuit++) { 
			// 1-13 card values
			for (int intValue = 1; intValue <= 13; intValue++) {
				// Column 1: value 
				intDeck[intCardIndex][0] = intValue; 
				// Column 2: suit
				intDeck[intCardIndex][1] = intSuit; 
				// Column 3: random number for sorting 
				intDeck[intCardIndex][2] = (int)(Math.random() * 100) + 1; 
				intCardIndex++;
			}
		}
		// Bubble sort by random number from column 3 to shuffle
		for (int i = 0; i < 51; i++) {
			for (int v = 0; v < 51 - i; i++) {
				if (intDeck[v][2] > intDeck[v + 1][2]) {
					// Swap entire rows
					int[] intTemp = intDeck[v];
					intDeck[v] = intDeck[v + 1];
					intDeck[v + 1] = intTemp;
				}
			}
		}
		// Reset used cards tracker
		for (int i = 0; i < 52; i++) {
			bolUsedCards[i] = false;
		}
	}
	
	// Deal first 5 cards from deck to hand
	public static void dealInitialHand() {
		for (int i = 0; i < 5; i++) {
			// value
			intHand[i][0] = intDeck[i][0]; 
			// suit
			intHand[i][1] = intDeck[i][1]; 
			// Mark as used
			bolUsedCards[i] = true; 
		}
	}
	
	// Convert card value to display string
	public static String getCardValueString(int intValue) {
		if (intValue == 1) {
			return "A";
		} else if (intValue == 11) {
			return "J";
		} else if (intValue == 12) {
			return "Q";
		} else if (intValue == 13) {
			return "K";
		} else {
			return String.valueOf(intValue);
		}
	}
	
	// Convert suit to display string  
	public static String getSuitString(int intSuit) {
		if (intSuit == 1) {
			// Diamonds
			return "D"; 
		} else if (intSuit == 2) {
			// Clubs
			return "C";
		} else if (intSuit == 3) {
			// Hearts
			return "H"; 
		} else if (intSuit == 4) {
			// Spades
			return "S"; 
		} else {
			return "????";
		}
	}
	
	// Display the current hand with numbers above cards
	public static void displayHand(Console con, int intStartY) {
		con.setDrawColor(Color.WHITE);
		drawCenteredString(con, "Your Hand:", intStartY);
		
		// Show numbers 1-5 above cards
		String strNumbers = "1     2     3     4     5";
		drawCenteredString(con, strNumbers, intStartY + 60);
		
		// cards values and suits
		String strCards = "";
		for (int i = 0; i < 5; i++) {
			String strCard = getCardValueString(intHand[i][0]) + getSuitString(intHand[i][1]);
			strCards += strCard;
			if (i < 4) {
				// Space between the cards
				strCards += "    "; 
			}
		}
		drawCenteredString(con, strCards, intStartY + 100);
	}
		// Card swapping 
		public static void swapCards(Console con, String strPlayerName, int intMoney, int intBet) {
		con.clear();
		con.setDrawColor(Color.BLACK);
		con.fillRect(0, 0, 1280, 720);
		con.setDrawColor(Color.WHITE);
		con.repaint();
		
		drawCenteredString(con, "Player: " + strPlayerName, 100);
		drawCenteredString(con, "Bet: $" + intBet + " | Money: $" + intMoney, 140);
		
		displayHand(con, 200);
		
		con.setDrawColor(Color.YELLOW);
		drawCenteredString(con, "How many cards do you want to swap? (0-5)", 350);
		con.repaint();
		
		int intCardsToSwap = con.readInt();
		
		// Validate input
		while (intCardsToSwap < 0 || intCardsToSwap > 5) {
			con.setDrawColor(Color.RED);
			drawCenteredString(con, "Invalid! Must be 0-5. Try again:", 400);
			con.repaint();
			intCardsToSwap = con.readInt();
		}
		
		// If player wants to swap cards
		if (intCardsToSwap > 0) {
			con.setDrawColor(Color.CYAN);
			drawCenteredString(con, "Enter card positions to swap (1-5), one at a time:", 450);
			con.repaint();
			
			boolean[] bolSwapCard = new boolean[5];
			
			// Get which cards to swap
			for (int i = 0; i < intCardsToSwap; i++) {
				drawCenteredString(con, "Card position " + (i + 1) + ":", 500 + i * 30);
				con.repaint();
				int intPosition = con.readInt();
				
				// Validate position
				while (intPosition < 1 || intPosition > 5) {
					con.setDrawColor(Color.RED);
					drawCenteredString(con, "Invalid position! Must be 1-5:", 500 + i * 30);
					con.repaint();
					intPosition = con.readInt();
				}
				
				bolSwapCard[intPosition - 1] = true; // Mark for swapping
			}
			
			// Replace marked cards with new ones
			replaceCards(bolSwapCard);
		}
	}

	//  Replace cards from deck
	public static void replaceCards(boolean[] bolSwapCard) {
		// Start after the initial 5 cards
		int intDeckIndex = 5; 
		
		for (int i = 0; i < 5; i++) {
			if (bolSwapCard[i]) {
				// Find next unused card in deck
				while (intDeckIndex < 52 && bolUsedCards[intDeckIndex]) {
					intDeckIndex++;
				}
				
				if (intDeckIndex < 52) {
					// Replace card
					intHand[i][0] = intDeck[intDeckIndex][0]; // value
					intHand[i][1] = intDeck[intDeckIndex][1]; // suit
					bolUsedCards[intDeckIndex] = true; // Mark as used
					intDeckIndex++;
				}
			}
		}
	}

	//  Evaluate poker hand
	public static String evaluateHand() {
		// Count values and suits
		int[] intValueCounts = new int[14]; // Index 0 unused, 1-13 for card values
		int[] intSuitCounts = new int[5];   // Index 0 unused, 1-4 for suits
		
		// Count occurrences
		for (int i = 0; i < 5; i++) {
			intValueCounts[intHand[i][0]]++;
			intSuitCounts[intHand[i][1]]++;
		}
		
		// Check for flush (all same suit)
		boolean bolFlush = false;
		for (int i = 1; i <= 4; i++) {
			if (intSuitCounts[i] == 5) {
				bolFlush = true;
				break;
			}
		}
		
		// Check for straight
		boolean bolStraight = checkStraight(intValueCounts);
		
		// Check for royal flush (A, K, Q, J, 10 all same suit)
		if (bolFlush && bolStraight && intValueCounts[1] == 1 && intValueCounts[10] == 1) {
			return "Royal Flush";
		}
		
		// Check for straight flush
		if (bolFlush && bolStraight) {
			return "Straight Flush";
		}
		
		// Count pairs, three of a kind, four of a kind
		int intPairs = 0;
		int intThreeOfKind = 0;
		int intFourOfKind = 0;
		
		for (int i = 1; i <= 13; i++) {
			if (intValueCounts[i] == 2) {
				intPairs++;
			} else if (intValueCounts[i] == 3) {
				intThreeOfKind++;
			} else if (intValueCounts[i] == 4) {
				intFourOfKind++;
			}
		}
		
		// Determine hand type
		if (intFourOfKind == 1) {
			return "Four of a Kind";
		} else if (intThreeOfKind == 1 && intPairs == 1) {
			return "Full House";
		} else if (bolFlush) {
			return "Flush";
		} else if (bolStraight) {
			return "Straight";
		} else if (intThreeOfKind == 1) {
			return "Three of a Kind";
		} else if (intPairs == 2) {
			return "Two Pair";
		} else if (intPairs == 1) {
			// Check if it's Jacks or better
			for (int i = 11; i <= 13; i++) { // J, Q, K
				if (intValueCounts[i] == 2) {
					return "Pair of Jacks or Better";
				}
			}
			if (intValueCounts[1] == 2) { // Aces
				return "Pair of Jacks or Better";
			}
			return "Low Pair";
		} else {
			return "High Card";
		}
	}

	//  Check for straight
	public static boolean checkStraight(int[] intValueCounts) {
		// Check for regular straight (5 consecutive cards)
		int intConsecutive = 0;
		for (int i = 1; i <= 13; i++) {
			if (intValueCounts[i] == 1) {
				intConsecutive++;
				if (intConsecutive == 5) {
					return true;
				}
			} else {
				intConsecutive = 0;
			}
		}
		
		// Check for A-2-3-4-5 straight (wheel)
		if (intValueCounts[1] == 1 && intValueCounts[2] == 1 && intValueCounts[3] == 1 && 
			intValueCounts[4] == 1 && intValueCounts[5] == 1) {
			return true;
		}
		
		return false;
	}

	// Calculate payout based on hand
	public static int calculatePayout(String strHandType, int intBet) {
		if (strHandType.equals("Royal Flush")) {
			return intBet * 800;
		} else if (strHandType.equals("Straight Flush")) {
			return intBet * 50;
		} else if (strHandType.equals("Four of a Kind")) {
			return intBet * 25;
		} else if (strHandType.equals("Full House")) {
			return intBet * 9;
		} else if (strHandType.equals("Flush")) {
			return intBet * 6;
		} else if (strHandType.equals("Straight")) {
			return intBet * 4;
		} else if (strHandType.equals("Three of a Kind")) {
			return intBet * 3;
		} else if (strHandType.equals("Two Pair")) {
			return intBet * 2;
		} else if (strHandType.equals("Pair of Jacks or Better")) {
			return intBet * 1;
		} else {
			return 0; // No payout for low pairs or high card
		}
	}

	// 7. UPDATED playOneRound() - Now complete with all features
	public static int playOneRound(Console con, String strPlayerName, int intMoney) {
		// Get bet
		int intBet = getBet(con, strPlayerName, intMoney);
		
		// Prepare and deal cards
		prepareDeck();
		dealInitialHand();
		
		// Show initial hand
		showInitialHand(con, strPlayerName, intMoney, intBet);
		
		// Card swapping
		swapCards(con, strPlayerName, intMoney, intBet);
		
		// Show final hand
		showFinalHand(con, strPlayerName, intMoney, intBet);
		
		// Evaluate hand and calculate payout
		String strHandType = evaluateHand();
		int intPayout = calculatePayout(strHandType, intBet);
		
		// Show results
		showResults(con, strHandType, intPayout, intBet);
		
		// Return updated money
		return intMoney - intBet + intPayout;
	}

	//  Show final hand after swapping
	public static void showFinalHand(Console con, String strPlayerName, int intMoney, int intBet) {
		con.clear();
		con.setDrawColor(Color.BLACK);
		con.fillRect(0, 0, 1280, 720);
		con.setDrawColor(Color.WHITE);
		con.repaint();
		
		drawCenteredString(con, "Player: " + strPlayerName, 100);
		drawCenteredString(con, "Bet: $" + intBet + " | Money: $" + intMoney, 140);
		
		con.setDrawColor(Color.CYAN);
		drawCenteredString(con, "Your Final Hand:", 200);
		
		displayHand(con, 220);
		con.repaint();
		con.sleep(2000);
	}

	//  Show game results
	public static void showResults(Console con, String strHandType, int intPayout, int intBet) {
		con.clear();
		con.setDrawColor(Color.BLACK);
		con.fillRect(0, 0, 1280, 720);
		con.repaint();
		
		// ==================== WIN SCENARIO SCREEN (GAMEPLAY) ====================
		// Function: celebrates the players win
		// Input: Nun
		// Output: Hand type from strHandType variable, payout, profit calculation
		if (intPayout > 0) {
			// Win scenario
			con.setDrawColor(Color.GREEN);
			drawCenteredString(con, "🎉 WINNER! 🎉", 250);
			drawCenteredString(con, "Hand: " + strHandType, 300);
			drawCenteredString(con, "You won: $" + intPayout, 350);
			drawCenteredString(con, "Profit: $" + (intPayout - intBet), 400);
			
			// ==================== LOSE SCENARIO SCREEN (GAMEPLAY) ====================
			// Input: Nun
			// Output: Hand type from strHandType variable, lost amount from intBet variable, consolation message
		} else {
			// Lose scenario
			con.setDrawColor(Color.RED);
			drawCenteredString(con, "😞 No Win 😞", 250);
			drawCenteredString(con, "Hand: " + strHandType, 300);
			drawCenteredString(con, "You lost: $" + intBet, 350);
			drawCenteredString(con, "Better luck next time!", 400);
		}
		
		con.repaint();
		con.sleep(3000);
	}
		
	// ==================== MAIN GAME METHODS ====================
	public static void playGame(Console con) {
		// Get player name and starting money
		String strPlayerName = getPlayerName(con);
		int intMoney = getStartingMoney(strPlayerName, con);
		
		// Game loop - keep playing until out of money or quit
		boolean bolPlayAgain = true;
		while (bolPlayAgain && intMoney > 0) {
			intMoney = playOneRound(con, strPlayerName, intMoney);
			
			if (intMoney > 0) {
				bolPlayAgain = askPlayAgain(con);
			}
		}
		// Game over
		gameOver(con, strPlayerName, intMoney);
	}
	
	// Get player name
	public static String getPlayerName(Console con) {
		con.clear();
		con.setDrawColor(Color.BLACK);
		con.fillRect(0, 0, 1280, 720);
		con.setDrawColor(Color.BLUE);
		con.repaint();
		
		drawCenteredString(con, "Welcome to Video Poker!", 200);
		drawCenteredString(con, "What is your name?", 260);
		con.repaint();
		
		return con.readLine();
	}
	
	// Get starting money
	public static int getStartingMoney(String strPlayerName, Console con) {
		int intMoney = 1000;
		
		// Easter egg - cheat code
		if (strPlayerName.equalsIgnoreCase("statitan")) {
			intMoney = 9999;
			con.clear();
			con.setDrawColor(Color.BLACK);
			con.fillRect(0, 0, 1280, 720);
			con.setDrawColor(Color.GREEN);
			con.repaint();
			drawCenteredString(con, "Cheat activated! $9999 awarded.", 320);
			con.repaint();
			con.sleep(2000);
		}
		
		return intMoney;
	}
	// ==================== BETTING SCREEN (GAMEPLAY) ====================  
	// Function: Allows the player to place their bet for the current round as long as the numer is valid
	// Input: Bet amount
	// Output: Player name from strPlayerName variable and current name from intMoney variable
	public static int getBet(Console con, String strPlayerName, int intMoney) {
		con.clear();
		con.setDrawColor(Color.BLACK);
		con.fillRect(0, 0, 1280, 720);
		con.setDrawColor(Color.BLUE);
		con.repaint();
		
		drawCenteredString(con, "Player: " + strPlayerName, 200);
		drawCenteredString(con, "You have: $" + intMoney, 260);
		drawCenteredString(con, "How much would you like to bet?", 320);
		con.repaint();
		
		int intBet = con.readInt();
		
		// Validate bet
		while (intBet <= 0 || intBet > intMoney) {
			con.clear();
			con.setDrawColor(Color.BLACK);
			con.fillRect(0, 0, 1280, 720);
			con.setDrawColor(Color.RED);
			con.repaint();
			drawCenteredString(con, "Invalid bet! Must be between $1 and $" + intMoney, 200);
			drawCenteredString(con, "Try again:", 260);
			con.repaint();
			intBet = con.readInt();
		}
		
		return intBet;
	}
	// ==================== SCREEN INITIAL HAND DISPLAY (GAMEPLAY) ====================
	// Function: Show the initial hand dealt
	// Input: Nun
	// Output: Cards from intHand[i][0] and inthand[i][1], bet amounts from intBet and money from intMoney
	public static void showInitialHand(Console con, String strPlayerName, int intMoney, int intBet) {
		con.clear();
		con.setDrawColor(Color.BLACK);
		con.fillRect(0, 0, 1280, 720);
		con.setDrawColor(Color.WHITE);
		con.repaint();
		
		drawCenteredString(con, "Player: " + strPlayerName, 100);
		drawCenteredString(con, "Bet: $" + intBet + " | Money: $" + intMoney, 140);
		
		displayHand(con, 200);
		con.repaint();
		con.sleep(3000);
	}
	
	// Ask if player wants to play again
	public static boolean askPlayAgain(Console con) {
		con.clear();
		con.setDrawColor(Color.BLACK);
		con.fillRect(0, 0, 1280, 720);
		con.setDrawColor(Color.GREEN);
		con.repaint();
		
		drawCenteredString(con, "Play again? (y/n)", 300);
		con.repaint();
		con.sleep(1000); // Pause so player can see the question
		
		char chrResponse = con.getChar();
		return (chrResponse == 'y' || chrResponse == 'Y');
	}
	
	// Game over screen
	public static void gameOver(Console con, String strPlayerName, int intMoney) {
		updatePlayerMoney(intMoney);
		
		con.clear();
		con.setDrawColor(Color.BLACK);
		con.fillRect(0, 0, 1280, 720);
		con.setDrawColor(Color.RED);
		con.repaint();
		
		drawCenteredString(con, "Game Over!", 300);
		drawCenteredString(con, "Final Money: $" + intMoney, 340);
		drawCenteredString(con, "Thanks for playing, " + strPlayerName + "!", 380);
		con.repaint();
		con.sleep(3000);
	}
	
	// ==================== MENU TUT SCREEN ====================
	// Function: tells the player all the instructions for poker
	// Input: Any key
	// Output: Switches back to main menu screen
	public static void showHelp(Console con) {
		con.clear();
		con.setDrawColor(Color.BLACK);
		con.fillRect(0, 0, 1280, 720);
		con.setDrawColor(Color.CYAN);
		con.repaint();

		drawCenteredString(con, "HOW TO PLAY VIDEO POKER", 100);
		
		con.setDrawColor(Color.WHITE);
		drawCenteredString(con, "1. Place your bet", 180);
		drawCenteredString(con, "2. You get 5 cards", 210);
		drawCenteredString(con, "3. Choose which cards to keep", 240);
		drawCenteredString(con, "4. Replace unwanted cards", 270);
		drawCenteredString(con, "5. Get paid for winning hands!", 300);
		
		con.setDrawColor(Color.YELLOW);
		drawCenteredString(con, "WINNING HANDS (Minimum: Pair of Jacks)", 360);
		drawCenteredString(con, "Pair of Jacks+ : 1x bet", 390);
		drawCenteredString(con, "Two Pair : 2x bet", 420);
		drawCenteredString(con, "Three of a Kind : 3x bet", 450);
		drawCenteredString(con, "Straight : 4x bet", 480);
		drawCenteredString(con, "Flush : 6x bet", 510);
		drawCenteredString(con, "Full House : 9x bet", 540);
		drawCenteredString(con, "Four of a Kind : 25x bet", 570);
		drawCenteredString(con, "Straight Flush : 50x bet", 600);
		drawCenteredString(con, "Royal Flush : 800x bet", 630);
		
		con.setDrawColor(Color.GREEN);
		drawCenteredString(con, "Press any key to return to menu...", 680);
		con.repaint();
		
		// Wait for any key
		con.getChar(); 
	}


	public static void showSecretMenu(Console con) {
		con.clear();
		con.setDrawColor(Color.MAGENTA);
		con.fillRect(0, 0, 1280, 720);
		con.setDrawColor(Color.YELLOW);
		con.repaint();
		
		// centering the words so they dont run off the screen!!! - the pixels are 12 per character
		int intCharWidth = 12;

		// Title line
		drawCenteredString(con, "SECRET JOKE MENU *that I took off a random site *", 200);

		con.setDrawColor(Color.WHITE);
		// Question line
		drawCenteredString(con, "What is the biggest difference between a church and a poker room?(sorry in advance)", 300);
		con.repaint();
		con.sleep(2000);
		
		con.setDrawColor(Color.CYAN);
		// Answer line
		drawCenteredString(con, "In a poker room, you really mean it when you pray.", 350);
		con.repaint();
		con.sleep(3000);
		
		con.setDrawColor(Color.GREEN);
		// Bonus joke line
		drawCenteredString(con, "Btw its only a gambling problem when ur losing.", 400);
		con.repaint();
		con.sleep(1000);
		
		con.setDrawColor(Color.BLUE);
		//DONT TAKE SERIOUSLY 
		drawCenteredString(con, "BUT GAMBLING IS BAD SO DONT DO IT!!!!", 450);
		con.repaint();
		con.sleep(1500);
		
		con.setDrawColor(Color.LIGHT_GRAY);
		// Exit instruction
		drawCenteredString(con, "OKKKAay now you can press any key to return...", 500);

		
		con.repaint();
		// Wait for any key to be clicked
		con.getChar(); 
	}

	public static void showLeaderboard(Console con) {
		//clear screen
		con.clear();
		con.setDrawColor(Color.BLACK);
		con.fillRect(0, 0, 1280, 720);
		con.setDrawColor(Color.YELLOW);
		con.repaint();

		drawCenteredString(con, "LEADERBOARD", 150);

		for (int count = 0; count < arrNames.length; count++) {
			String strEntry = arrNames[count] + ": $" + arrMoney[count];
			drawCenteredString(con, strEntry, 220 + count * 50);
		}
		con.repaint();
		con.sleep(3000);  
	}

	public static void updatePlayerMoney(int newMoney) {
		// update player / "you"
		arrMoney[0] = newMoney; 
	}

	public static void quitScreen(Console con) {
		con.clear();
		con.setDrawColor(Color.DARK_GRAY);
		con.fillRect(0, 0, 1280, 720);
		con.setDrawColor(Color.RED);
		con.repaint();

		drawCenteredString(con, "Thanks for playing!", 300);
		drawCenteredString(con, "Goodbye!", 360);
		con.repaint();
		con.sleep(3000);
		con.closeConsole();
	}

	// Add animation to main menu (optional - call this from showMainMenu if ive still got time)
	public static void animateMenuTitle(Console con) {
		String strTitle = "VIDEO POKER";
		con.setDrawColor(Color.RED);
		
		// Animated title effect
		for (int i = 0; i <= strTitle.length(); i++) {
			con.setDrawColor(Color.BLACK);
			// Clear title area
			con.fillRect(0, 40, 1280, 60); 
			
			con.setDrawColor(Color.RED);
			String strPartial = strTitle.substring(0, i);
			drawCenteredString(con, strPartial, 80);
			con.repaint();
			con.sleep(200);
		}
	}
}
