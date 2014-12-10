package popstars.model;

public class GameBoard {

    // --- Constants and Variables

    static final int[] PIECES_TO_POINTS = new int[101];
    static {
        int plus = 5;
        int total = 5;

        for (int i1 = 2; i1 < 101; i1++) {
            plus += 10;
            total += plus;
            PIECES_TO_POINTS[i1] = total;
        }
    }
    
    static int getPoints(final int numberOfBlocks) {
        return PIECES_TO_POINTS[numberOfBlocks];
    }

    public static GameBoard getInstance(final String... lines) {
        // Assuming that our grid is square...
        
        byte[][] columnBytes = new byte[lines.length][];
        for (int i1=0; i1<columnBytes.length; i1++) {
            columnBytes[i1] = new byte[lines.length];
        }
        
        int row=0;
        for (String line : lines) {
            char[] chars = line.toCharArray();
            for (int col=0; col<chars.length; col++) {
                columnBytes[col][row] = Byte.parseByte("" + chars[col]);
            }
            row++;
        }
        
        GameColumn[] columns = new GameColumn[lines.length];
        for (int i1=0; i1<columnBytes.length; i1++) {
            columns[i1] = GameColumn.getInstance(columnBytes[i1]);
        }
        return new GameBoard(columns, 0);
    }

    private final int score;
    private final GameColumn[] columns;

    // --- Constructor and Initialization Methods

    private GameBoard(final GameColumn[] newColumns, final int newScore) {
        super();
        columns = newColumns;
        score = newScore;
    }

    // --- Core and Helper Methods
    // --- Getter and Setter Methods

    public int getScore() {
        return score;
    }

    // --- Delegate and Convenience Methods

    public GameBoard performMove(final GameMove move) {
        if (move.getPieces().isEmpty()) {
            return this;
        }

        final int newScore = score + getPoints(move.getPieces().size());
        
        // Create a copy of the GameColumns
        GameColumn[] newColumns = new GameColumn[columns.length];
        System.arraycopy(columns, 0, newColumns, 0, columns.length);
        
        boolean squishLeft = false;
        for (GamePos pos : move.getPieces()) {
            GameColumn col = newColumns[pos.getCol()];
            col = col.removePiece(pos.getRow());
            squishLeft = squishLeft || (col == GameColumn.EMPTY_COLUMN);
            newColumns[pos.getCol()] = col;
        }
        
        // Now squish the columns to the left
        if (squishLeft) {
            GameColumn[] squishedColumns = new GameColumn[columns.length];
            for (int i1=0; i1<squishedColumns.length; i1++) {
                squishedColumns[i1] = GameColumn.EMPTY_COLUMN;
            }
            
            int index = 0;
            for (GameColumn gc : newColumns) {
                if (gc != GameColumn.EMPTY_COLUMN) {
                    squishedColumns[index] = gc;
                    index++;
                }
            }
            
            newColumns = squishedColumns;
        }
        
        return new GameBoard(newColumns, newScore);
    }

    // --- Miscellaneous Methods

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
        for (GameColumn col : columns) {
            sb.append(col);
            sb.append("\n");
        }
        return sb.toString();
    }

    public GameColumn[] getColumns() {
        return columns;
    }
}