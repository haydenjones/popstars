package popstars.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GameTree implements Runnable {

    // --- Constants and Variables

    public static final HashSet<GameBoard> CACHE = new HashSet<GameBoard>();
    static final AtomicInteger TICKS = new AtomicInteger(0);

    private final int height;
    private final int maxHeight;
    private final GameBoard board;
    private final List<GameTree> moves = new ArrayList<GameTree>();
    private final GameMove lastMove;

    // --- Constructor and Initialization Methods

    public GameTree(final GameBoard gb, final int max) {
        this(gb, null, 0, max);
        CACHE.clear();
    }

    public GameTree(final GameBoard gb, final GameMove move, final int h, final int max) {
        super();
        height = h;
        maxHeight = max;
        board = gb;
        lastMove = move;
    }

    // --- Core and Helper Methods
    // --- Getter and Setter Methods
    // --- Delegate and Convenience Methods
    // --- Miscellaneous Methods

    public void run() {
        call();
        System.out.println("");
        System.out.println(this.getBestChoice());
    }
    
    public void call() {
        System.out.print(".");
        if (TICKS.incrementAndGet() >= 120) {
            System.out.println("");
            System.out.print(new java.util.Date() + " ");
            TICKS.set(0);
        }
        if (height >= maxHeight) {
            return;
        }

        List<GameMove> choices = GameAnalyzer.findMoves(board);
        for (GameMove choice : choices) {
            GameBoard gm = board.performMove(choice);
            if (CACHE.contains(gm)) {
                continue;
            }
            CACHE.add(gm);

            GameTree node = new GameTree(gm, choice, height + 1, maxHeight);
            moves.add(node);
            node.call();
        }
    }

    public int evaluate() {
        return board.getScore();
    }

    public GameChoice getBestChoice() {
        if (moves.isEmpty()) {
            return new GameChoice(lastMove, evaluate());
        }

        GameChoice best = null;

        for (GameTree kid : moves) {
            GameChoice next = kid.getBestChoice();
            if (best == null) {
                best = next;
            }
            else if (best.getScore() < next.getScore()) {
                best = next;
            }
        }

        if (best == null) {
            return new GameChoice(lastMove, evaluate());
        }

        if (lastMove != null) {
            return new GameChoice(lastMove, best.getScore());
        }
        return best;
    }
}