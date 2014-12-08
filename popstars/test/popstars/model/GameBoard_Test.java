package popstars.model;

import org.junit.Test;

public class GameBoard_Test {

    // --- Constants and Variables
    // --- Constructor and Initialization Methods
    // --- Core and Helper Methods
    // --- Getter and Setter Methods
    // --- Delegate and Convenience Methods
    // --- Miscellaneous Methods

    @Test
    public void factory() {
        GameBoard board = GameBoard.getInstance(
                /**/
                "0123456789",
                /**/
                "0123456789",
                /**/
                "0123456789",
                /**/
                "0123456789",
                /**/
                "0123456789",
                /**/
                "0123456789",
                /**/
                "0123456789",
                /**/
                "0123456789",
                /**/
                "0123456789",
                /**/
                "0123456789");

        System.out.println(board);
    }
}
