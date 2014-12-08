package popstars.play;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import popstars.model.GameBoard;
import popstars.model.GamePos;

public class PopstarsPanel extends JPanel implements MouseListener {
    private static final long serialVersionUID = -3703664096288610818L;

    static final PopstarsPanel NULL = new PopstarsPanel(null);

    static final Color[] COLOURS = new Color[] { Color.BLACK, Color.ORANGE, Color.RED, Color.GREEN, Color.YELLOW,
        Color.BLUE };

    // --- Constants and Variables

    private final GameBoard board;
    private final int size = 40;
    private volatile GamePos selected = null;

    // --- Constructor and Initialization Methods

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
        byte[][] grid = board.getGrid();

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                int coloursIndex = grid[row][col];
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
        System.out.println(selected);
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
