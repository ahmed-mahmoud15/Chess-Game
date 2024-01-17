package ChessCore.Pieces;

import ChessCore.Player;

public class KingFactory implements PieceFactory{

	@Override
    public Piece createPiece(Player owner) {
        return new King(owner);
    }
}
