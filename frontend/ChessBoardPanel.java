package frontend;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import ChessCore.*;
import ChessCore.Pieces.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class ChessBoardPanel extends JPanel implements MouseListener {

    private static final int DIMENSION = 70;
    private Image[] whitePiecesImages;
    private Image[] blackPiecesImages;
    private ArrayList<OccupiedTile> whitePieces;
    private ArrayList<OccupiedTile> blackPieces;
    private final BoardFile[] files = BoardFile.values();
    private final BoardRank[] ranks = BoardRank.values();
    private ClassicChessGame game;
    private int clickCount;
    private final int[] selectedSquare;  // Array to store selected square coordinates
    private OccupiedTile selectedTile;
    private final ArrayList<String> moves;

    private final Undo undo;
    private Player whoseTurn;

    private static final Color CAPTURABLE = new Color(0, 184, 240);
    private static final Color NO_MOVES = new Color(79, 79, 79);
    private static final Color IN_CHECK = new Color(224, 13, 13);
    private static final Color VALID_MOVE = new Color(0, 240, 20);
    private static final Color WRONG_PLAYER_COLOR = new Color(92, 0, 0);
    private static final Color CURRENT_PIECE = new Color(239, 252, 149);
    private static final Color CURRENT_TILE = new Color(79, 79, 79);

    ChessBoardPanel(ClassicChessGame game) {

        this.setPreferredSize(new Dimension(8 * DIMENSION, 8 * DIMENSION));
        this.game = game;
        this.addMouseListener(this);
        selectedSquare = new int[2];
        initializeImages();
        initializeArrays();
        moves = new ArrayList<>();
        whoseTurn = game.getWhoseTurn();
        undo = new Undo();
    }

    private void initializeArrays() {

        whitePieces = new ArrayList<>();
        blackPieces = new ArrayList<>();
        readArrayLists();
    }

    private void readArrayLists() {

        whitePieces.clear();
        blackPieces.clear();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Square square = new Square(files[j], ranks[i]);
                Piece p = game.getPieceAtSquare(square);
                if (p != null) {

                    if (p.getOwner() == Player.WHITE) {
                        whitePieces.add(new OccupiedTile(square, p));
                    } else {
                        blackPieces.add(new OccupiedTile(square, p));
                    }
                }
            }
        }

    }

    private void initializeImages() {
        whitePiecesImages = new Image[6];
        blackPiecesImages = new Image[6];

        Image[] temp;

        for (int i = 0; i < 2; i++) {
            String color;
            if (i == 0) {
                temp = whitePiecesImages;
                color = "White";
            } else {
                temp = blackPiecesImages;
                color = "Black";
            }

            for (int j = 0; j < 6; j++) {
                // gets Image name/path
                String path;
                path = color;
                switch (j) {

                    case 0 ->
                        path += "Pawn";
                    case 1 ->
                        path += "Bishop";
                    case 2 ->
                        path += "Knight";
                    case 3 ->
                        path += "Rook";
                    case 4 ->
                        path += "King";
                    case 5 ->
                        path += "Queen";
                }
                path += ".png";

                temp[j] = (new ImageIcon(path)).getImage().getScaledInstance(DIMENSION,
                        DIMENSION, Image.SCALE_SMOOTH);
            }
        }
    }

    private int getAppropriateY(Square square) {

        if (whoseTurn == Player.BLACK) {
            return square.getRank().getValue();
        } else {
            return 7 - square.getRank().getValue();
        }
    }

    private int getAppropriateY(int y) {

        if (whoseTurn == Player.BLACK) {
            return 7 - y;
        } else {
            return y;
        }
    }

    private void drawBoard(Graphics g) {
        Color color;
        boolean isWhiteTile = whoseTurn == Player.WHITE;
        // paints 64 Tile Board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                color = isWhiteTile ? Color.white : Color.black;

                g.setColor(color);
                g.fillRect(i * DIMENSION, j * DIMENSION, DIMENSION, DIMENSION);

                isWhiteTile = !isWhiteTile;
            }
            isWhiteTile = !isWhiteTile;
        }
    }

    private void drawSelectedTile(Graphics g) {
        if (selectedTile == null) {
            return;
        }

        Color color;
        List<Square> list = game.getAllValidMovesFromSquare(selectedTile.getSquare());

        if (selectedTile.getPiece() == null) {
            color = CURRENT_TILE;
        } else if (selectedTile.getPiece().getOwner() != game.getWhoseTurn()) {
            color = WRONG_PLAYER_COLOR;
        } else {
            color = CURRENT_PIECE;
        }

        if (list.isEmpty() && selectedTile.getPiece() != null) {
            if (selectedTile.getPiece().getOwner() == game.getWhoseTurn()) {
                color = NO_MOVES;
            }
        }

        g.setColor(color);
        g.fillRect(selectedTile.getSquare().getFile().getValue() * DIMENSION,
                getAppropriateY(selectedTile.getSquare()) * DIMENSION,
                DIMENSION, DIMENSION);

        color = VALID_MOVE;
        g.setColor(color);
        for (Square s : list) {
            if (game.getPieceAtSquare(s) != null) {
                g.setColor(CAPTURABLE); //Capturable
            }
            g.fillRect(s.getFile().getValue() * DIMENSION, getAppropriateY(s) * DIMENSION, DIMENSION, DIMENSION);
            g.setColor(color);
        }

    }

    private void highlighKing(Graphics g) {
        if (game.getGameStatus() == GameStatus.WHITE_UNDER_CHECK) {

            Square s = Utilities.getKingSquare(Player.WHITE, game.getBoard());
            g.setColor(IN_CHECK);
            g.fillRect(s.getFile().getValue() * DIMENSION, getAppropriateY(s) * DIMENSION, DIMENSION, DIMENSION);

        } else if (game.getGameStatus() == GameStatus.BLACK_UNDER_CHECK) {
            Square s = Utilities.getKingSquare(Player.BLACK, game.getBoard());
            g.setColor(IN_CHECK);
            g.fillRect(s.getFile().getValue() * DIMENSION, getAppropriateY(s) * DIMENSION, DIMENSION, DIMENSION);
        }
    }

    private void drawPieces(Graphics g) {
        Image[] imageArray;
        ArrayList<OccupiedTile> occupiedByPiece;
        for (int i = 0; i < 2; i++) {
            if (i == 0) {
                occupiedByPiece = whitePieces;
                imageArray = whitePiecesImages;
            } else {
                occupiedByPiece = blackPieces;
                imageArray = blackPiecesImages;
            }

            for (OccupiedTile t : occupiedByPiece) {
                Image image = null;
                Piece p = t.getPiece();
                if (p instanceof Pawn) {
                    image = imageArray[0];
                } else if (p instanceof Bishop) {
                    image = imageArray[1];
                } else if (p instanceof Knight) {
                    image = imageArray[2];
                } else if (p instanceof Rook) {
                    image = imageArray[3];
                } else if (p instanceof King) {
                    image = imageArray[4];
                } else if (p instanceof Queen) {
                    image = imageArray[5];
                }

                int x = (t.getSquare().getFile().getValue() * DIMENSION);
                int y = (getAppropriateY(t.getSquare()) * DIMENSION);

                g.drawImage(image, x, y, this);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        drawBoard(g);
        drawSelectedTile(g);
        highlighKing(g);
        drawPieces(g);
    }

    public int getDimension() {
        return DIMENSION;
    }

    private BoardFile positionToFile(int position) {
        BoardFile brf;

        brf = switch (position / DIMENSION) {
            case 0 ->
                BoardFile.A;
            case 1 ->
                BoardFile.B;
            case 2 ->
                BoardFile.C;
            case 3 ->
                BoardFile.D;
            case 4 ->
                BoardFile.E;
            case 5 ->
                BoardFile.F;
            case 6 ->
                BoardFile.G;
            default ->
                BoardFile.H;
        };
        return brf;
    }

    private BoardRank positionToRank(int position) {
        BoardRank brr;

        brr = switch (getAppropriateY(position / DIMENSION) + 1) {
            case 8 ->
                BoardRank.FIRST;
            case 7 ->
                BoardRank.SECOND;
            case 6 ->
                BoardRank.THIRD;
            case 5 ->
                BoardRank.FORTH;
            case 4 ->
                BoardRank.FIFTH;
            case 3 ->
                BoardRank.SIXTH;
            case 2 ->
                BoardRank.SEVENTH;
            default ->
                BoardRank.EIGHTH;
        };
        return brr;
    }

    private boolean isValidPromotion(Piece fromPiece, Square toSquare, Move mv) {
        if (!(fromPiece instanceof Pawn) || mv.getAbsDeltaY() != 1 || fromPiece.getOwner() != game.getWhoseTurn()) {
            return false;
        }

        if (fromPiece.getOwner() == Player.WHITE && toSquare.getRank() == BoardRank.EIGHTH) {
            return true;
        } else if (fromPiece.getOwner() == Player.BLACK && toSquare.getRank() == BoardRank.FIRST) {
            return true;
        }

        return false;
    }

    private void checkStatus(Piece pieceTo) {
        if (pieceTo instanceof Pawn) {
            System.out.println("Pawn Captured");
        }
        if (pieceTo instanceof Rook) {
            System.out.println("Rook Captured");
        }
        if (pieceTo instanceof Knight) {
            System.out.println("Knight Captured");
        }
        if (pieceTo instanceof Queen) {
            System.out.println("Queen Captured");
        }
        if (pieceTo instanceof Bishop) {
            System.out.println("Bishop Captured");
        }

        var status = game.getGameStatus();

        if (status == GameStatus.WHITE_WON) {
            System.out.println("White Won");
            JOptionPane.showMessageDialog(null, "White Won", "game Status", JOptionPane.INFORMATION_MESSAGE);
        }
        if (status == GameStatus.BLACK_WON) {
            System.out.println("Black Won");
            JOptionPane.showMessageDialog(null, "Black Won", "game Status", JOptionPane.INFORMATION_MESSAGE);
        }
        if (status == GameStatus.WHITE_UNDER_CHECK) {
            System.out.println("White Check");
        }
        if (status == GameStatus.BLACK_UNDER_CHECK) {
            System.out.println("Black Check");
        }
        if (status == GameStatus.STALEMATE) {
            System.out.println("Stalemate");
            JOptionPane.showMessageDialog(null, "Stalemate", "game Status", JOptionPane.INFORMATION_MESSAGE);
        }
        if (status == GameStatus.INSUFFICIENT_MATERIAL) {
            System.out.println("Insufficient Materials");
            JOptionPane.showMessageDialog(null, "Insufficient Materials", "game Status", JOptionPane.INFORMATION_MESSAGE);
        }
        if (game.isGameEnded()) {
            writeMovesToFile();
            System.exit(0);
        }

    }

    public void writeMovesToFile() {
        try {
            FileWriter myWriter = new FileWriter("Output.txt");
            for (String line : moves) {
                line += "\n";
                myWriter.write(line);
            }

            myWriter.close();
        } catch (IOException e) {
        }
    }

    private void initializeClicks() {
        selectedTile = null;
        clickCount = 0;
    }

    public void undo() {
    	undo.undo();
    }
    
    public void restart() {
    	undo.restart();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (clickCount == 0) {
            selectedSquare[0] = e.getX();
            selectedSquare[1] = e.getY();

            Square from = new Square(positionToFile(selectedSquare[0]), positionToRank(selectedSquare[1]));
            Piece pieceFrom = game.getPieceAtSquare(from);

            if (pieceFrom != null) {
                selectedTile = new OccupiedTile(from, pieceFrom);
                repaint();
                clickCount++;
            } else {
                initializeClicks();
            }

        } else {
            int startX = selectedSquare[0];
            int startY = selectedSquare[1];
            int endX = e.getX();
            int endY = e.getY();

            Square from = new Square(positionToFile(startX), positionToRank(startY));
            Square to = new Square(positionToFile(endX), positionToRank(endY));

            Piece pieceFrom = game.getPieceAtSquare(from);
            Piece pieceTo = game.getPieceAtSquare(to);

            if (pieceFrom == null) {
                initializeClicks();
                repaint();
                return;
            }

            Move mv = new Move(from, to);

            if (pieceFrom.equals(pieceTo)) {
                initializeClicks();
                repaint();
                return;
            }
            if (pieceFrom != null && pieceTo != null
                    && pieceFrom.getOwner() == pieceTo.getOwner() && !pieceFrom.equals(pieceTo)) {
                selectedSquare[0] = e.getX();
                selectedSquare[1] = e.getY();
                selectedTile = new OccupiedTile(to, pieceTo);
                repaint();
                return;
            }

            if (pieceFrom.getOwner() != game.getWhoseTurn()) {

                selectedSquare[0] = e.getX();
                selectedSquare[1] = e.getY();
                selectedTile = new OccupiedTile(to, pieceTo);
                repaint();
                return;
            }

            if (isValidPromotion(pieceFrom, to, mv)) {
                PawnPromotion promotion;
                String[] validPromotions = {"Rook", "Knight", "Bishop", "Queen"};

                int selectedChoice = JOptionPane.showOptionDialog(null,
                        "Promote To",
                        "Promotion",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        validPromotions, validPromotions[3]);

                switch (selectedChoice) {
                    case 0 -> {
                        promotion = PawnPromotion.Rook;
                        System.out.println("Pawn promoted to Rook");
                    }
                    case 1 -> {
                        promotion = PawnPromotion.Knight;
                        System.out.println("Pawn promoted to Knight");
                    }
                    case 2 -> {
                        promotion = PawnPromotion.Bishop;
                        System.out.println("Pawn promoted to Bishop");
                    }
                    default -> {
                        promotion = PawnPromotion.Queen;
                        System.out.println("Pawn promoted to Queen");
                    }
                }

                mv = new Move(from, to, promotion);
            }

            if (pieceFrom != null && game.makeMove(mv)) {

                readArrayLists();
                whoseTurn = Utilities.revertPlayer(whoseTurn);
                repaint();
                checkStatus(pieceTo);
                moves.add(mv.getFromSquare().getFile().toString()
                        + (mv.getFromSquare().getRank().getValue() + 1)
                        + ","
                        + mv.getToSquare().getFile().toString()
                        + (mv.getToSquare().getRank().getValue() + 1));
                undo.games.push(game.clone());
            }
            initializeClicks();
            repaint();
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private class Undo {

        private Stack<ChessGame> games;

        private Undo() {
            games = new Stack<>();
            games.push(game.clone());
        }

        private void undo() {

            if (clickCount == 1) {
                initializeClicks();
            }

            if (games.size() > 1) {
                this.games.pop();
                game = (ClassicChessGame)games.peek().clone();
                readArrayLists();
                whoseTurn = Utilities.revertPlayer(whoseTurn);
                repaint();
            }
        }

        private void restart() {

            if (clickCount == 1) {
                initializeClicks();
            }

            while (games.size() > 1) {
                this.games.pop();
            }
            game = (ClassicChessGame)games.peek().clone();
            readArrayLists();
            if (whoseTurn == Player.BLACK) {
                whoseTurn = Player.WHITE;
            }
            repaint();

        }
    }

}