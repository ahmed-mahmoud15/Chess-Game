package frontend;

import ChessCore.*;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class ChessGameFrame extends JFrame {

    private final ClassicChessGame game;
    private ChessBoardPanel board;
//    private JButton undo;
    JMenuBar menuBar;
    JMenu menu;
    JMenuItem undo;
    JMenuItem restart;

    ChessGameFrame(ClassicChessGame game) {

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.game = game;
        board = new ChessBoardPanel(this.game);
        //this.setLayout(new BorderLayout());
        this.setTitle("Chess");
        //Removes frame/border
        //this.setUndecorated(true);
        menuBar = new JMenuBar();
        menu = new JMenu("Options");

        undo = new JMenuItem("Undo");
        undo.addActionListener(e -> {
            board.undo();
        });
        
        restart = new JMenuItem("Restart");
        restart.addActionListener(e -> {
            board.restart();
        });

        // Add the undo menu item to the "UNDO" menu
        menu.add(undo);
        menu.add(restart);

        // Add the "UNDO" menu to the menu bar
        menuBar.add(menu);

        setJMenuBar(menuBar);
        this.add(board);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setResizable(false);
    }
    
    public static void main(String[] args) {
        new ChessGameFrame(new ClassicChessGame());
    }
}
