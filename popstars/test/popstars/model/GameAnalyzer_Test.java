package popstars.model;

import java.util.List;

import org.junit.Test;

public class GameAnalyzer_Test {

    // --- Constants and Variables
    // --- Constructor and Initialization Methods
    // --- Core and Helper Methods
    // --- Getter and Setter Methods
    // --- Delegate and Convenience Methods
    // --- Miscellaneous Methods

    @Test
    public void works() {
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

        List<GameMove> moves = GameAnalyzer.findMoves(board);
        for (GameMove gm : moves) {
            System.out.println(gm);
        }
    }
}
