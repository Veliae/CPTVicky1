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

			drawCenteredString(con, "LEADERBOARD", 150, titleFont);

			for (int count = 0; count < arrNames.length; count++) {
				String strEntry = arrNames[count] + ": $" + arrMoney[count];
				drawCenteredString(con, strEntry, 220 + count * 50, entryFont);
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
			drawCenteredString(con, "Thanks for playing!", 300, font);
			drawCenteredString(con, "Goodbye!", 360, font);

			con.sleep(3000);
			con.closeConsole();
		}
}
