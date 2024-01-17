package ChessCore.Pieces;

import ChessCore.Player;

public class RookFactory implements PieceFactory{

	@Override
    public Piece createPiece(Player owner) {
        return new Rook(owner);
    }
}
