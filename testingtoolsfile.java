import arc.*;
public class Testingtoolsfile {
    public static void main(String[] args) {
        Console con = new Console();
         // Array of integers (like card values)
        int[] arrCards = {9, 2, 7, 4, 5};

        // Display original array
        con.println("Original array:");
        for (int count = 0; count < arrCards.length; count++) {
            con.print(arrCards[count] + " ");
        }

        con.println();  // Line break (stop)

        // Bubble sort
        for (int pass = 0; pass < arrCards.length - 1; pass++) {
            for (int count = 0; count < arrCards.length - 1 - pass; count++) {
                if (arrCards[count] > arrCards[count + 1]) {
                    // Swap the two cards
                    int intTemp = arrCards[count];
                    arrCards[count] = arrCards[count + 1];
                    arrCards[count + 1] = intTemp;
                }
            }
        }

        // Display sorted array
        con.println("Sorted array:");
        for (int count = 0; count < arrCards.length; count++) {
            con.print(arrCards[count] + " ");
        }
    }
}
