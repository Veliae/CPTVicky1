import arc.*;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class VideoPoker{
	public static void main (String[] args) {
		Console con = new Console("Poker", 1400, 800);
		BufferedImage imgpokermenu = con.loadImage("pokermenu.png");
		 

        // Calculate centered position
        int intX = (1400 - imgpokermenu.getWidth()) / 2;
        int intY = (800 - imgpokermenu.getHeight()) / 2;

        // Draw only the centered image
        con.drawImage(imgpokermenu, intX, intY);
        con.repaint();
    }
}

