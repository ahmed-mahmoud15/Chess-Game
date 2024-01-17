package frontend;

import ChessCore.Square;
import ChessCore.Pieces.*;

public class OccupiedTile {

    private Piece piece;
    private final Square square;

    public OccupiedTile(Square square, Piece piece) {
        this.piece = piece;
        this.square = square;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Square getSquare() {
        return square;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof OccupiedTile) {
            OccupiedTile o = (OccupiedTile) obj;
            if ((o.getSquare().getFile() == this.getSquare().getFile())
                    && (o.getSquare().getRank() == this.getSquare().getRank())) {
                Piece oPiece = o.getPiece();

                // Check if the Piece is of the same type
                if ((oPiece instanceof Pawn && this.piece instanceof Pawn)
                        || (oPiece instanceof Bishop && this.piece instanceof Bishop)
                        || (oPiece instanceof Knight && this.piece instanceof Knight)
                        || (oPiece instanceof Rook && this.piece instanceof Rook)
                        || (oPiece instanceof King && this.piece instanceof King)
                        || (oPiece instanceof Queen && this.piece instanceof Queen)) {
                    if (oPiece.getOwner() == this.piece.getOwner()) {
                        return true;
                    }
                }
            }
        }
        return super.equals(obj);
    }
}
