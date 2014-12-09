package popstars.model;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class GameMove {
    // --- Constants and Variables

    private final SortedSet<GamePos> pieces = new TreeSet<GamePos>();

    // --- Constructor and Initialization Methods
    // --- Core and Helper Methods
    // --- Getter and Setter Methods

    public GameMove(final Set<GamePos> newPieces) {
        super();
        pieces.addAll(newPieces);
    }

    public SortedSet<GamePos> getPieces() {
        return pieces;
    }

    // --- Delegate and Convenience Methods
    // --- Miscellaneous Methods

    @Override
    public String toString() {
        return pieces.toString();
    }
}