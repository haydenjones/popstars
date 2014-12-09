package popstars.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public class GamePos implements Comparable<GamePos> {
    static final List<GamePos> CACHE = new ArrayList<GamePos>();
    static final AtomicLong COUNTER = new AtomicLong(0);

    public static GamePos atRowCol(final int r, final int c) {
        if (r < 0) {
            throw new IllegalArgumentException("Invalid Row: " + r);
        }
        if (c < 0) {
            throw new IllegalArgumentException("Invalid Col: " + c);
        }

        GamePos gp = new GamePos(r, c);
        int index = CACHE.indexOf(gp);
        if (index < 0) {
            CACHE.add(gp);
        }
        else {
            gp = CACHE.get(index);
        }
        return gp;
    }

    public static final GamePos NULL = new GamePos(-1, -1);

    public static final Set<GamePos> EMPTY_SET = Collections.emptySet();;

    // --- Constants and Variables

    private final int row;
    private final int col;

    // --- Constructor and Initialization Methods

    private GamePos(final int r, final int c) {
        super();
        row = r;
        col = c;
    }

    // --- Core and Helper Methods
    // --- Getter and Setter Methods

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof GamePos)) {
            return false;
        }
        GamePos gp = (GamePos) o;
        return (compareTo(gp) == 0);
    }

    @Override
    public int hashCode() {
        return Integer.valueOf((row * 21) + col).hashCode();
    }

    @Override
    public int compareTo(final GamePos gp) {
        int colDelta = gp.col - col;
        if (colDelta != 0) {
            return colDelta;
        }

        return row - gp.row;
    }

    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }
    // --- Delegate and Convenience Methods
    // --- Miscellaneous Methods
}