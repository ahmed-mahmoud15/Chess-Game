package ChessCore.Pieces;

import ChessCore.Player;

public interface PieceFactory {

	Piece createPiece(Player owner);
}
