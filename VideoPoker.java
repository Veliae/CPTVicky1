import arc.*;
import java.awt.image.BufferedImage;

public class VideoPoker {
    public static void main(String[] args) {
        Console con = new Console("Poker", 1392, 807);
        BufferedImage imgMenu = con.loadImage("pokermenu.png");

        // Draw menu image
        int intX = (1392 - imgMenu.getWidth()) / 2;
        int intY = (807 - imgMenu.getHeight()) / 2;
        con.drawImage(imgMenu, intX, intY);
        con.repaint();

        // Wait for valid key
        boolean bolWaiting = true;
        while (bolWaiting) {
            int intKey = con.getKey();

            if (intKey == Console.KEY_ENTER) {
				// Game screen
                VickysTools.playGame(con);  
                bolWaiting = false;
            } else if (intKey == Console.KEY_TAB) {
				// Leaderboard screen
                VickysTools.showLeaderboard(con); 
                bolWaiting = false;
            } else if (intKey == Console.KEY_BACK_SPACE) {
				// Quit screen
                VickysTools.quitScreen(con);  
                bolWaiting = false;
            }
        }
    }
}
