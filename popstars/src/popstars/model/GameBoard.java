package popstars.model;

import java.util.TreeSet;

public class GameBoard implements Cloneable {

    // --- Constants and Variables

    static int getPoints(final int numberOfBlocks) {
        if (numberOfBlocks < 2) {
            return 0;
        }

        int plus = 5;
        int total = 5;

        for (int i1 = 1; i1 < numberOfBlocks; i1++) {
            plus += 10;
            total += plus;
        }

        return total;
    }

    public static GameBoard getInstance(final String... lines) {
        byte[][] grid = new byte[lines.length][];
        int rowCount = 0;
        for (String l : lines) {
            char[] chars = l.toCharArray();
            byte[] row = new byte[chars.length];
            grid[rowCount] = row;
            for (int i1 = 0; i1 < chars.length; i1++) {
                row[i1] = Byte.parseByte(chars[i1] + "");
            }
            rowCount++;
        }
        return new GameBoard(grid, 0);
    }

    private final int score;
    private final byte[][] grid;

    // --- Constructor and Initialization Methods

    private GameBoard(final byte[][] byteGrid, final int newScore) {
        super();
        grid = byteGrid;
        score = newScore;
    }

    // --- Core and Helper Methods
    // --- Getter and Setter Methods

    public int getScore() {
        return score;
    }

    public byte[][] getGrid() {
        return grid;
    }

    // --- Delegate and Convenience Methods

    public GameBoard performMove(final GameMove move) {
        GameBoard newBoard = this.copy();
        byte[][] grid = newBoard.getGrid();

        // Remove the pieces...
        TreeSet<Integer> columns = new TreeSet<Integer>();
        for (GamePos pos : move.getPieces()) {
            grid[pos.getRow()][pos.getCol()] = 0;
            columns.add(pos.getCol());
        }

        // Have the columns sink
        for (Integer column : columns) {
            doneNow: while (true) {
                boolean lookAgain = true;
                again: while (lookAgain) {
                    lookAgain = false;

                    for (int row = grid.length - 1; row > 0; row--) {
                        if ((grid[row][column] == 0) && (grid[row - 1][column] > 0)) {
                            grid[row][column] = grid[row - 1][column];
                            grid[row - 1][column] = 0;
                            lookAgain = true;
                            break again;
                        }
                    }
                }

                if (!lookAgain) {
                    break doneNow;
                }
            }
        }

        // Have the columns shifted LEFT

        int newScore = score + getPoints(move.getPieces().size());

        return new GameBoard(grid, newScore);
    }

    // --- Miscellaneous Methods

    String toString(final byte[][] grid) {
        StringBuilder sb = new StringBuilder();
        for (byte[] bytes : grid) {
            for (byte b : bytes) {
                int i = 0 + b;
                sb.append(i);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof GameBoard)) {
            return false;
        }
        GameBoard gb = (GameBoard) o;
        if (this.score != gb.score) {
            return false;
        }
        return this.toString().equals(gb.toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.score);
        sb.append("\n");
        for (byte[] bytes : grid) {
            for (byte b : bytes) {
                int i = 0 + b;
                sb.append(i);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public GameBoard copy() {
        int newScore = this.getScore();
        byte[][] newGrid = new byte[this.grid.length][];
        for (int i1=0; i1<newGrid.length; i1++) {
            newGrid[i1] = new byte[grid[i1].length];
            System.arraycopy(grid[i1], 0, newGrid[i1], 0, grid[i1].length);
        }
        return new GameBoard(newGrid, newScore);
    }
}
