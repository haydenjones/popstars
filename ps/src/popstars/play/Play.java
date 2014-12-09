package popstars.play;

import java.util.List;

import javax.swing.JOptionPane;

import popstars.model.GameAnalyzer;
import popstars.model.GameBoard;
import popstars.model.GameMove;
import popstars.model.GamePos;
import popstars.model.GameTree;

public class Play {
    public static final String[] lines = new String[] { "PRPRGYYPPG", "GYPRRYPPYY", "iPYiGRYRRP", "PiiiiGPRRR",
        "GiPYRGPGPP", "iRGPPRPGYR", "GYGRGPPiRY", "YiGRiGPRPY", "iiYGPGiGRi", "RYGRRGiPPG" };

    public static GameBoard getGameBoard() {
        String[] numLines = new String[lines.length];
        for (int i1 = 0; i1 < lines.length; i1++) {
            String numLine = lines[i1];
            numLine = numLine.replace('P', '1');
            numLine = numLine.replace('R', '2');
            numLine = numLine.replace('G', '3');
            numLine = numLine.replace('Y', '4');
            numLine = numLine.replace('i', '5');
            numLines[i1] = numLine;
        }

        GameBoard game = GameBoard.getInstance(numLines);
        return game;
    }

    public static void main2(final String[] args) {
        String[] numLines = new String[lines.length];
        for (int i1 = 0; i1 < lines.length; i1++) {
            String numLine = lines[i1];
            numLine = numLine.replace('P', '1');
            numLine = numLine.replace('R', '2');
            numLine = numLine.replace('G', '3');
            numLine = numLine.replace('Y', '4');
            numLine = numLine.replace('i', '5');
            numLines[i1] = numLine;
        }

        GameBoard game = getGameBoard();

        gui(game);
        // search(game);
    }

    static void search(final GameBoard first) {
        for (int maxHeight = 4; maxHeight < 7; maxHeight++) {
            System.out.println("");
            System.out.println(new java.util.Date());
            System.out.println("Max Height: " + maxHeight);
            GameTree root = new GameTree(first, maxHeight);
            root.call();
            System.out.println(root.getBestChoice());
            System.out.println(new java.util.Date());
        }
    }

    static void play(GameBoard game) {
        System.out.println(game);
        System.out.println("");

        List<GameMove> moves = GameAnalyzer.findMoves(game);
        GameMove found = null;
        GamePos pos = GamePos.atRowCol(3, 1);
        for (GameMove move : moves) {
            if (move.getPieces().contains(pos)) {
                found = move;
                break;
            }
        }

        System.out.println(found);

        game = game.performMove(found);

        System.out.println("");
        System.out.println(game);
    }

    static void gui(GameBoard game) {
        PopstarsPanel panel = PopstarsPanel.NULL;

        int option = JOptionPane.OK_OPTION;
        while (option == JOptionPane.OK_OPTION) {

            GamePos selectedPos = panel.getSelectedGamePos();
            if (selectedPos != null) {
                List<GameMove> moves = GameAnalyzer.findMoves(game);
                GameMove found = null;
                for (GameMove move : moves) {
                    if (move.getPieces().contains(selectedPos)) {
                        found = move;
                        break;
                    }
                }
                if (found != null) {
                    game = game.performMove(found);

                    System.out.println(game);
                    GameTree gt = new GameTree(game, 6);
                    gt.call();
                    System.out.println(gt.getBestChoice());
                }
            }

            panel = new PopstarsPanel(game);
            option = JOptionPane.showConfirmDialog(null, panel, "Score: " + game.getScore(),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        }
    }
    // --- Constants and Variables
    // --- Constructor and Initialization Methods
    // --- Core and Helper Methods
    // --- Getter and Setter Methods
    // --- Delegate and Convenience Methods
    // --- Miscellaneous Methods
}
