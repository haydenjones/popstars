package popstars.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class GameAnalyzer {
    public enum Adj {
        UP(-1, 0), DOWN(1, 0), LEFT(0, -1), RIGHT(0, 1);

        private final int rowAdj;
        private final int colAdj;

        Adj(final int ra, final int ca) {
            rowAdj = ra;
            colAdj = ca;
        }

        public GamePos findMatch(final byte startByte, final GamePos pos, final byte[][] grid) {
            assert (startByte > 0);
            GamePos newPos = adjustGamePos(pos, grid);
            if (startByte == getByte(newPos, grid)) {
                return newPos;
            }
            return GamePos.NULL;
        }

        private GamePos adjustGamePos(final GamePos pos, final byte[][] grid) {
            int newRow = pos.getRow() + rowAdj;
            if (newRow < 0) {
                return GamePos.NULL;
            }
            else if (newRow >= grid.length) {
                return GamePos.NULL;
            }

            int newCol = pos.getCol() + colAdj;
            if (newCol < 0) {
                return GamePos.NULL;
            }
            else if (newCol >= grid[newRow].length) {
                return GamePos.NULL;
            }

            return GamePos.atRowCol(newRow, newCol);
        }
    }

    static byte getByte(final GamePos pos, final byte[][] grid) {
        if (pos.getRow() < 0) {
            return -1;
        }
        else if (pos.getRow() >= grid.length) {
            return -1;
        }
        else if (pos.getCol() < 0) {
            return -1;
        }
        else if (pos.getCol() >= grid[pos.getRow()].length) {
            return -1;
        }

        return grid[pos.getRow()][pos.getCol()];
    }

    static Set<GamePos> findGroup(final GamePos initialPos, final byte[][] grid) {
        final byte initialByte = getByte(initialPos, grid);
        if (initialByte <= 0) {
            return GamePos.EMPTY_SET;
        }

        GamePos matchPos = GamePos.NULL;

        lookForMatch: for (Adj adj : Adj.values()) {
            GamePos match = adj.findMatch(initialByte, initialPos, grid);
            if (match != GamePos.NULL) {
                matchPos = match;
                break lookForMatch;
            }
        }

        if (matchPos == GamePos.NULL) {
            return GamePos.EMPTY_SET;
        }

        TreeSet<GamePos> pieces = new TreeSet<GamePos>();
        pieces.add(initialPos);
        pieces.add(matchPos);

        return findGroup(initialByte, pieces, grid);
    }

    private static Set<GamePos> findGroup(final byte initialByte, final SortedSet<GamePos> pieces,
            final byte[][] grid) {
        TreeSet<GamePos> newPositions = new TreeSet<GamePos>();

        for (GamePos current : pieces) {
            for (Adj adj : Adj.values()) {
                GamePos possibleMatch = adj.findMatch(initialByte, current, grid);
                if (possibleMatch == GamePos.NULL) {
                    continue;
                }

                if (pieces.contains(possibleMatch)) {
                    continue;
                }

                newPositions.add(possibleMatch);
            }
        }

        if (newPositions.isEmpty()) {
            return pieces;
        }

        pieces.addAll(newPositions);
        return findGroup(initialByte, pieces, grid);
    }

    public static List<GameMove> findMoves(final GameBoard original) {
        GameBoard board = original.copy();
        byte[][] grid = board.getGrid();

        List<GameMove> moves = new ArrayList<GameMove>();

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                Set<GamePos> pieces = findGroup(GamePos.atRowCol(row, col), grid);
                if (!pieces.isEmpty()) {
                    GameMove gm = new GameMove(pieces);
                    for (GamePos gp : gm.getPieces()) {
                        grid[gp.getRow()][gp.getCol()] = 0;
                    }
                    moves.add(gm);
                }
            }
        }

        return moves;
    }

    // --- Constants and Variables
    // --- Constructor and Initialization Methods
    // --- Core and Helper Methods
    // --- Getter and Setter Methods
    // --- Delegate and Convenience Methods
    // --- Miscellaneous Methods
}