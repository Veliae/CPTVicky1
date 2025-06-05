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

		public static void showLeaderboard(Console con) {
			// placeholder is okay
			con.clear();
			con.drawString("Leaderboard screen", 100, 100);
		}

		public static void quitScreen(Console con) {
			// placeholder is okay
			con.clear();
			con.drawString("Quit screen", 100, 100);
			con.sleep(2000);
			con.closeConsole();
}
}
