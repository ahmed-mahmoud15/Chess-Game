package ChessCore;

public final class ClassicChessGame extends ChessGame{

    public ClassicChessGame() {
        super(ClassicBoardInitializer.getInstance());
    }

    public ClassicChessGame(ClassicChessGame game) {
        super(game);
    }

    @Override
    protected boolean isValidMoveCore(Move move) {
        return !Utilities.willOwnKingBeAttacked(this.getWhoseTurn(), move, this.getBoard());
    }

    @Override
    public ChessGame clone() {
        return new ClassicChessGame(this);
    }
}
