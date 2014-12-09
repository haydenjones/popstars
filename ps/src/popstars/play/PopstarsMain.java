package popstars.play;

import java.util.List;

import javax.swing.SwingUtilities;

import popstars.model.GameAnalyzer;
import popstars.model.GameBoard;
import popstars.model.GameMove;
import popstars.model.GamePos;

public class PopstarsMain implements Runnable {

    public static void main(final String[] args) {
        try {
            PopstarsMain doRun = new PopstarsMain();
            SwingUtilities.invokeAndWait(doRun);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- Constants and Variables

    private volatile GameBoard board = null;
    private volatile PopstarsJFrame pjf = null;

    // --- Constructor and Initialization Methods

    public PopstarsMain() {
        super();
        board = Play.getGameBoard();
    }

    // --- Core and Helper Methods

    @Override
    public void run() {
        pjf = new PopstarsJFrame(this);
        pjf.init();
        pjf.setVisible(true);
    }
    // --- Getter and Setter Methods
    // --- Delegate and Convenience Methods
    // --- Miscellaneous Methods

    public GameBoard getGameBoard() {
        return board;
    }

    public void tryMove(final GamePos selected) {
        GameMove found = null;

        List<GameMove> moves = GameAnalyzer.findMoves(board);
        for (GameMove move : moves) {
            if (move.getPieces().contains(selected)) {
                found = move;
            }
        }

        if (found != null) {
            board = board.performMove(found);

            if (pjf != null) {
                pjf.refreshBoard();
            }
        }
    }
}
