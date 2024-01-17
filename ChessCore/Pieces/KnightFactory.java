package ChessCore.Pieces;

import ChessCore.Player;

public class KnightFactory implements PieceFactory{

	@Override
    public Piece createPiece(Player owner) {
        return new Knight(owner);
    }
}
