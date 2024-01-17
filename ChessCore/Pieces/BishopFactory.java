package ChessCore.Pieces;

import ChessCore.Player;

public class BishopFactory implements PieceFactory{

	@Override
    public Piece createPiece(Player owner) {
        return new Bishop(owner);
    }
}
