package popstars.play;

import javax.swing.JFrame;
import popstars.model.GameTree;

public class PopstarsJFrame extends JFrame {

    // --- Constants and Variables

    private static final long serialVersionUID = -6474351789748446856L;
    private final PopstarsMain main;
    private volatile PopstarsPanel panel = null;

    // --- Constructor and Initialization Methods

    public PopstarsJFrame(final PopstarsMain popstarsMain) {
        super("Popstars");
        main = popstarsMain;
    }

    // --- Core and Helper Methods

    public void init() {
        panel = new PopstarsPanel(main);

        getContentPane().add(panel);
        if (main != null) {
            setTitle("Score: " + main.getGameBoard().getScore());
        }

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        pack();
    }

    public void refreshBoard() {
        getContentPane().remove(panel);
        panel = new PopstarsPanel(main);
        getContentPane().add(panel);
        setTitle("Score: " + main.getGameBoard().getScore());
        pack();
        repaint();
        
        GameTree gt = new GameTree(main.getGameBoard(), 5);
        Thread t = new Thread(gt);
        t.start();
    }

    // --- Getter and Setter Methods
    // --- Delegate and Convenience Methods
    // --- Miscellaneous Methods
}
