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
		int intX = (1392 - intTextWidth) / 2;
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
	// Deal first 5 cards from deck to hand
	public static void dealInitialHand() {
		for (int i = 0; i < 5; i++) {
			intHand[i][0] = intDeck[i][0]; // value
			intHand[i][1] = intDeck[i][1]; // suit
			bolUsedCards[i] = true; // Mark as used
		}
	}
}
