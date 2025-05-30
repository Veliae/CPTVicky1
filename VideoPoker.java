import arc.*;
import java.awt.Color;

public class VideoPoker{
	public static void main (String[] args) {
		Console con = new Console("Poker", 1400, 800);
		int intX = 300;
		int intY = 200;
		
		while(intX > 0){ 
			//Draws Baclground before drawing all the elements at the top
			con.setDrawColor(Color.BLACK);
			con.setDrawColor(new Color(82, 20, 31));
			con.fillRect(30, 30, 1400, 800);
			
			con.setDrawColor(new Color(101, 50, 86));
			con.fillRoundRect(400 , 400, 100, 100, 400, 400);
			
		}
		}
}

