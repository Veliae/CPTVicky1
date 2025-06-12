// Vicky Wang
// Poker Game CPT
// Last update Thursday June 12
// version 1.3.2

import arc.*;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class VideoPoker {
    public static void main(String[] args) {
        Console con = new Console("Poker", 1280, 720);
        
        // Main menu loop - keeps returning to menu after each action
        boolean bolRunning = true;
        while (bolRunning) {
            int intChoice = showMainMenu(con);
            
            if (intChoice == 1) { // Play Game (Enter key)
                System.out.println("DEBUG: Starting game...");
                VickysTools.playGame(con);
            } else if (intChoice == 2) { // Leaderboard (Tab key)
                System.out.println("DEBUG: Showing leaderboard...");
                VickysTools.showLeaderboard(con);
            } else if (intChoice == 3) { // Help (H key)
                System.out.println("DEBUG: Showing help...");
                VickysTools.showHelp(con);
            } else if (intChoice == 4) { // Secret Menu (S key)
                System.out.println("DEBUG: Secret menu activated!");
                VickysTools.showSecretMenu(con);
            } else if (intChoice == 5) { // Quit (Backspace)
                System.out.println("DEBUG: Quitting game...");
                VickysTools.quitScreen(con);
                bolRunning = false;
            }
        }
    }
    // ==================== SCREEN 1 (MAIN MENU) ====================
    // Function: Main navigation area and allows users to access all the game features
    // Input: User key press gives intKey vari
    public static int showMainMenu(Console con) {
        // Clear screen and set background
        con.clear();
        con.setBackgroundColor(Color.BLACK);
        
        // Load and display menu image
        BufferedImage imgMenu = con.loadImage("pokermenu.png");
        // Center the image
        int intX = (1280 - imgMenu.getWidth()) / 2;
        int intY = (720 - imgMenu.getHeight()) / 2;
        con.drawImage(imgMenu, intX, intY);
        
        // Add menu options text overlay
        // con.setDrawColor(Color.WHITE);
        //VickysTools.drawCenteredString(con, "WELCOME TO VIDEO POKER", 50);
        
        // Menu options with key indicators
        //con.setDrawColor(Color.YELLOW);
        // VickysTools.drawCenteredString(con, "(ENTER) - Play Game", 500);
        // VickysTools.drawCenteredString(con, "(V) - View Leaderboard", 530);
        // VickysTools.drawCenteredString(con, "(H) - Recommended tut", 560);
        // VickysTools.drawCenteredString(con, "(BACKSPACE) - Quit", 590);
        
      con.repaint();
        
        // Wait for valid key input
        while (true) {
            int intKey = con.getKey();
            
            if (intKey == 10) { // Enter key
                return 1;
            } else if (intKey == 86 || intKey == 118) { // V or v key
                return 2;
            } else if (intKey == 72 || intKey == 104) { // H or h key
                return 3;
            } else if (intKey == 83 || intKey == 115) { // S or s key - SECRET!
                return 4;
            } else if (intKey == 8) { // Backspace
                return 5;
            } else {
                // Invalid key - show brief message
                con.setDrawColor(Color.RED);
                VickysTools.drawCenteredString(con, "Invalid key! Try again...", 650);
                con.repaint();
                con.sleep(1000);
                // Redraw menu
                return showMainMenu(con);
            }
        }
    }
}
