package popstars.play;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import popstars.model.GameBoard;
import popstars.model.GameColumn;
import popstars.model.GamePos;

public class PopstarsPanel extends JPanel implements MouseListener {
    private static final long serialVersionUID = -3703664096288610818L;

    static final PopstarsPanel NULL = new PopstarsPanel((GameBoard) null);

    static final Color[] COLOURS = new Color[] { Color.BLACK, new Color(8388736), Color.RED, Color.GREEN, Color.YELLOW,
        Color.PINK };

    // --- Constants and Variables

    private final GameBoard board;
    private final int size = 40;
    private volatile GamePos selected = null;
    private volatile PopstarsMain main = null;

    // --- Constructor and Initialization Methods

    public PopstarsPanel(final PopstarsMain main) {
        this(main.getGameBoard());
        this.main = main;
    }

    public PopstarsPanel(final GameBoard gameBoard) {
        super();
        board = gameBoard;
        setPreferredSize(new Dimension(size * 10, size * 10));
        this.addMouseListener(this);
    }

    // --- Core and Helper Methods
    // --- Getter and Setter Methods
    // --- Delegate and Convenience Methods
    // --- Miscellaneous Methods

    @Override
    public void paint(final Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        GameColumn[] columns = board.getColumns();

        for (int col = 0; col < columns.length; col++) {
            GameColumn column = columns[col];
            byte[] grid = column.toByteArray();
            for (int row = 0; row < grid.length; row++) {
                int coloursIndex = grid[row];
                g2d.setPaint(COLOURS[coloursIndex]);
                g2d.fillRect(col * size + 1, row * size + 1, size - 2, size - 2);
            }
        }
    }

    @Override
    public void mouseClicked(final MouseEvent e) {
        if (e.getClickCount() != 2) {
            return;
        }

        selected = GamePos.atRowCol(e.getY() / size, e.getX() / size);
        System.out.println(selected + " " + main);

        if (main != null) {
            main.tryMove(selected);
        }
    }

    @Override
    public void mousePressed(final MouseEvent e) {
    }

    @Override
    public void mouseReleased(final MouseEvent e) {
    }

    @Override
    public void mouseEntered(final MouseEvent e) {
    }

    @Override
    public void mouseExited(final MouseEvent e) {
    }

    public GamePos getSelectedGamePos() {
        return selected;
    }
}
