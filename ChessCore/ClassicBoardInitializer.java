package ChessCore;

import ChessCore.Pieces.*;

public final class ClassicBoardInitializer implements BoardInitializer {
	private static final BoardInitializer instance = new ClassicBoardInitializer();
	private final PieceFactory pawnFactory = new PawnFactory();
	private final PieceFactory knightFactory = new KnightFactory();
	private final PieceFactory rookFactory = new RookFactory();
	private final PieceFactory bishopFactory = new BishopFactory();
	private final PieceFactory kingFactory = new KingFactory();
	private final PieceFactory queenFactory = new QueenFactory();

    private ClassicBoardInitializer() {
    }

    public static BoardInitializer getInstance() {
        return instance;
    }

    @Override
    public Piece[][] initialize() {
        Piece[][] initialState = {
            {rookFactory.createPiece(Player.WHITE), knightFactory.createPiece(Player.WHITE), bishopFactory.createPiece(Player.WHITE), queenFactory.createPiece(Player.WHITE), kingFactory.createPiece(Player.WHITE), bishopFactory.createPiece(Player.WHITE), knightFactory.createPiece(Player.WHITE), rookFactory.createPiece(Player.WHITE)},
            {pawnFactory.createPiece(Player.WHITE), pawnFactory.createPiece(Player.WHITE), pawnFactory.createPiece(Player.WHITE), pawnFactory.createPiece(Player.WHITE), pawnFactory.createPiece(Player.WHITE), pawnFactory.createPiece(Player.WHITE), pawnFactory.createPiece(Player.WHITE), pawnFactory.createPiece(Player.WHITE)},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {pawnFactory.createPiece(Player.BLACK), pawnFactory.createPiece(Player.BLACK), pawnFactory.createPiece(Player.BLACK), pawnFactory.createPiece(Player.BLACK), pawnFactory.createPiece(Player.BLACK), pawnFactory.createPiece(Player.BLACK), pawnFactory.createPiece(Player.BLACK), pawnFactory.createPiece(Player.BLACK)},
            {rookFactory.createPiece(Player.BLACK), knightFactory.createPiece(Player.BLACK), bishopFactory.createPiece(Player.BLACK), queenFactory.createPiece(Player.BLACK), kingFactory.createPiece(Player.BLACK), bishopFactory.createPiece(Player.BLACK), knightFactory.createPiece(Player.BLACK), rookFactory.createPiece(Player.BLACK)}
        };
        
        return initialState;
    }
}
