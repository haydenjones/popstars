package popstars.model;

public class GameChoice {

    // --- Constants and Variables

    private final int score;
    private final GameMove move;

    // --- Constructor and Initialization Methods

    public GameChoice(final GameMove gm, final int s) {
        move = gm;
        score = s;
    }

    // --- Core and Helper Methods
    // --- Getter and Setter Methods

    public int getScore() {
        return score;
    }

    public GameMove getMove() {
        return move;
    }

    // --- Delegate and Convenience Methods
    // --- Miscellaneous Methods

    @Override
    public String toString() {
        return "Choice can give " + score + " by " + move;
    }
}
