import arc.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class VickysTools {
	
	public static void drawCenteredString(Console con, String strText, int intY) {
		int intCharWidth = 20; 
		int intTextWidth = strText.length() * intCharWidth;
		int intX = (1392 - intTextWidth) / 2;
		con.drawString(strText, intX, intY);
	}
	
	public static void playGame(Console con) {
		// clear background 
		con.clear();
		con.setDrawColor(Color.BLACK);

		// draw new one
		con.fillRect(0, 0, 1392, 807);
		con.setDrawColor(Color.BLUE);

		// Ask for name
		drawCenteredString(con, "Welcome to Video Poker!", 200);
		drawCenteredString(con, "What is your name?", 260);
		String strPlayerName = con.readLine();

		// Starting money
		int intMoney = 1000;

		// Easter egg
		if (strPlayerName.equalsIgnoreCase("statitan")) {
			intMoney = 9999;
			drawCenteredString(con, "Cheat activated! $9999 awarded.", 320);
		}

		// Ask for bet
		drawCenteredString(con, "You have: $" + intMoney, 380);
		drawCenteredString(con, "How much would you like to bet?", 440);
		int intBet = con.readInt();

		// Validate bet
		while (intBet <= 0 || intBet > intMoney) {
			drawCenteredString(con, "Invalid bet. Try again.", 500);
			intBet = con.readInt();
	}

		// Show result
		con.clear();
		con.setDrawColor(Color.BLUE);
		drawCenteredString(con, "Name: " + strPlayerName, 300);
		drawCenteredString(con, "Bet: $" + intBet, 360);
		drawCenteredString(con, "Money left: $" + (intMoney - intBet), 420);

		con.sleep(3000);
	}

		// These arrays will keep names and money
		public static String[] arrNames = {"You", "Player 2", "Player 3", "Player 4"};
		public static int[] arrMoney = {1000, 1000, 1000, 1000};

		public static void showLeaderboard(Console con) {
			//clear screen
			con.clear();
			con.setDrawColor(Color.BLACK);
			con.fillRect(0, 0, 1392, 807);
			con.setDrawColor(Color.YELLOW);

			Font titleFont = new Font("Arial", Font.BOLD, 36);
			Font entryFont = new Font("Arial", Font.PLAIN, 28);

			drawCenteredString(con, "LEADERBOARD", 150);

			for (int count = 0; count < arrNames.length; count++) {
				String strEntry = arrNames[count] + ": $" + arrMoney[count];
				drawCenteredString(con, strEntry, 220 + count * 50);
		}

		con.sleep(3000);  // Let player view leaderboard
	}

		public static void updatePlayerMoney(int newMoney) {
			arrMoney[0] = newMoney; // Update "You" (player at index 0)
    }

		  public static void quitScreen(Console con) {
			con.clear();
			con.setDrawColor(Color.DARK_GRAY);
			con.fillRect(0, 0, 1392, 807);
			con.setDrawColor(Color.RED);

			Font font = new Font("Arial", Font.BOLD, 36);
			drawCenteredString(con, "Thanks for playing!", 300);
			drawCenteredString(con, "Goodbye!", 360);

			con.sleep(3000);
			con.closeConsole();
		}
			// Card deck arrays - 52 cards x 3 columns (value, suit, random number for sorting)
		public static int[][] intDeck = new int[52][3];
		// 5 cards x 2 (value, suit)
		public static int[][] intHand = new int[5][2]; 
		// Track which cards are used
		public static boolean[] bolUsedCards = new boolean[52]; 
		
		public static void drawCenteredString(Console con, String strText, int intY) {
			int intCharWidth = 20; 
			int intTextWidth = strText.length() * intCharWidth;
			int intX = (1392 - intTextWidth) / 2;
			con.drawString(strText, intX, intY);
		}
			
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
		// Bubble sort by random number (column 3) to shuffle
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
}
