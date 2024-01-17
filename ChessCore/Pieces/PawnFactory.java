package ChessCore.Pieces;

import ChessCore.Player;

public class PawnFactory implements PieceFactory{

	@Override
	public Piece createPiece(Player owner) {
		return new Pawn(owner);
	}

}
