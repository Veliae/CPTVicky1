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
	public static void drawCenteredString(Console con, String strText, int intY) {
		int intCharWidth = 20; 
		int intTextWidth = strText.length() * intCharWidth;
		int intX = (1280 - intTextWidth) / 2;
		con.drawString(strText, intX, intY);
	}
	
	// drawCenteredString with better width calculation
	public static void drawCenteredStringPrecise(Console con, String strText, int intY) {
		// Get actual font 
		try {
			FontMetrics fm = con.getDrawFontMetrics();
			int intTextWidth = fm.stringWidth(strText);
			int intX = (1280 - intTextWidth) / 2;
			con.drawString(strText, intX, intY);
		} catch (Exception e) {
			// just incase font above fails
			drawCenteredString(con, strText, intY);
		}
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
			for (int j = 0; j < 51 - i; j++) {
				if (intDeck[j][2] > intDeck[j + 1][2]) {
					// Swap entire rows
					int[] intTemp = intDeck[j];
					intDeck[j] = intDeck[j + 1];
					intDeck[j + 1] = intTemp;
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
		
		// TODO: Show actual card values here
		// For now, just placeholder
		drawCenteredString(con, "[Cards will display here]", intStartY + 100);
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
	
	// Play one complete round of poker
	public static int playOneRound(Console con, String strPlayerName, int intMoney) {
		// Get bet
		int intBet = getBet(con, strPlayerName, intMoney);
		
		// Prepare and deal cards
		prepareDeck();
		dealInitialHand();
		
		// Show initial hand
		showInitialHand(con, strPlayerName, intMoney, intBet);
		
		// TODO: Add card swapping here
		// TODO: Add hand evaluation here
		// TODO: Add payout calculation here
		
		// For now, just return money minus bet (you lose)
		return intMoney - intBet;
	}
	
	// Get valid bet from player
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
	
	// Show the initial hand dealt
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
	
	// ==================== MENU SCREEN METHODS ====================
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
		
		con.getChar(); // Wait for any key
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
		String strTitle = "SECRET JOKE MENU *that I took off a random site :)*";
		int intTitleWidth = strTitle.length() * intCharWidth;
		int intTitleX = (1280 - intTitleWidth) / 2;
		con.drawString(strTitle, intTitleX, 200);
		
		con.setDrawColor(Color.WHITE);
		// Question line
		String strQuestion = "What is the biggest difference between a church and a poker room?(wait for ittt)";
		int intQuestionWidth = strQuestion.length() * intCharWidth;
		int intQuestionX = (1280 - intQuestionWidth) / 2;
		con.drawString(strQuestion, intQuestionX, 300);
		con.sleep(2000);
		
		con.setDrawColor(Color.CYAN);
		// Answer line
		String strAnswer = "In a poker room, you really mean it when you pray.";
		int intAnswerWidth = strAnswer.length() * intCharWidth;
		int intAnswerX = (1280 - intAnswerWidth) / 2;
		con.drawString(strAnswer, intAnswerX, 350);
		con.sleep(3000);
		
		con.setDrawColor(Color.GREEN);
		// Bonus joke line
		String strBonus = "It's only a gambling problem when I'm losing.";
		int intBonusWidth = strBonus.length() * intCharWidth;
		int intBonusX = (1280 - intBonusWidth) / 2;
		con.drawString(strBonus, intBonusX, 400);
		con.sleep(3000);
		
		con.setDrawColor(Color.LIGHT_GRAY);
		// Exit instruction
		String strExit = "OKay now you can press any key to return...";
		int intExitWidth = strExit.length() * intCharWidth;
		int intExitX = (1280 - intExitWidth) / 2;
		con.drawString(strExit, intExitX, 500);
		
		con.repaint();
		con.getChar(); // Wait for any key
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
		// update player/ "you"
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
