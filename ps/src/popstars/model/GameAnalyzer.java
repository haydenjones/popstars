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

        public GamePos findMatch(final byte startByte, final GamePos pos, final byte[][] colRowGrid) {
            assert (startByte > 0);
            GamePos newPos = adjustGamePos(pos, colRowGrid);
            if (startByte == getByte(newPos, colRowGrid)) {
                return newPos;
            }
            return GamePos.NULL;
        }

        private GamePos adjustGamePos(final GamePos pos, final byte[][] colRowGrid) {
            int newCol = pos.getCol() + colAdj;
            if (newCol < 0) {
                return GamePos.NULL;
            }
            else if (newCol >= colRowGrid.length) {
                return GamePos.NULL;
            }
            
            int newRow = pos.getRow() + rowAdj;
            if (newRow < 0) {
                return GamePos.NULL;
            }
            else if (newRow >= colRowGrid[newCol].length) {
                return GamePos.NULL;
            }

            return GamePos.atRowCol(newRow, newCol);
        }
    }

    static byte getByte(final GamePos pos, final byte[][] colRowGrid) {
        if (pos.getCol() < 0) {
            return -1;
        }
        else if (pos.getCol() >= colRowGrid.length) {
            return -1;
        }
        else if (pos.getRow() < 0) {
            return -1;
        }
        else if (pos.getRow() >= colRowGrid[pos.getCol()].length) {
            return -1;
        }

        return colRowGrid[pos.getCol()][pos.getRow()];
    }

    static Set<GamePos> findGroup(final GamePos initialPos, final byte[][] colRowGrid) {
        final byte initialByte = getByte(initialPos, colRowGrid);
        if (initialByte <= 0) {
            return GamePos.EMPTY_SET;
        }

        GamePos matchPos = GamePos.NULL;

        lookForMatch: for (Adj adj : Adj.values()) {
            GamePos match = adj.findMatch(initialByte, initialPos, colRowGrid);
            if (match != GamePos.NULL) {
                matchPos = match;
                break lookForMatch;
            }
        }

        if (matchPos == GamePos.NULL) {
            return GamePos.EMPTY_SET;
        }

        TreeSet<GamePos> pieces = new TreeSet<>();
        pieces.add(initialPos);
        pieces.add(matchPos);

        return findGroup(initialByte, pieces, colRowGrid);
    }

    private static Set<GamePos> findGroup(final byte initialByte, final SortedSet<GamePos> pieces,
            final byte[][] grid) {
        TreeSet<GamePos> newPositions = new TreeSet<>();

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
        List<GameMove> moves = new ArrayList<>();

        // Create a copy of the game board
        byte[][] colRowGrid = new byte[original.getColumns().length][];
        for (int colIndex=0; colIndex<colRowGrid.length; colIndex++) {
            colRowGrid[colIndex] = original.getColumns()[colIndex].toByteArray();
        }
        
        for (int colIndex=0; colIndex<colRowGrid.length; colIndex++) {
            for (int rowIndex = 0; rowIndex < colRowGrid[colIndex].length; rowIndex++) {
                final byte initByte = colRowGrid[colIndex][rowIndex];
                if (initByte == 0) {
                    continue;
                }
                
                TreeSet<GamePos> pieces = new TreeSet<>();
                pieces.add(GamePos.atRowCol(rowIndex, colIndex));
                Set<GamePos> group = findGroup(initByte, pieces, colRowGrid);
                
                // We have a valid group...

                if (group.size() > 1) {
                    GameMove move = new GameMove(group);
                    for (GamePos pos : group) {
                        colRowGrid[pos.getCol()][pos.getRow()] = 0;
                    }
                    moves.add(move);
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