package ChessCore.Pieces;

import ChessCore.Player;

public class QueenFactory implements PieceFactory{

	@Override
    public Piece createPiece(Player owner) {
        return new Queen(owner);
    }
}
