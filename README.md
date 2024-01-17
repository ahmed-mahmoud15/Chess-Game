# Chess

This project is an implementation of the chess game logic. It is intended to be reused by any kind of application you're making.

## Documentation

### ClassicChessGame

This class extends `ChessGame`.

The following are the most important public APIs for this class:

1. A parameterless constructor so you can create a new game.
2. `makeMove` which is inherited from `ChessGame`. It returns `true` if the move was valid and made successfully.
3. `getGameStatus` which is one of:
    1. `GameStatus.BLACK_WON`
    2. `GameStatus.WHITE_WON`
    3. `GameStatus.WHITE_UNDER_CHECK`
    4. `GameStatus.BLACK_UNDER_CHECK`
    5. `GameStatus.STALEMATE`
    6. `GameStatus.IN_PROGRESS`
    7. `GameStatus.INSUFFICIENT_MATERIAL`

    NOTE: See comment in `updateGameStatus` in `ChessGame.java`.
4. `getAllValidMovesFromSquare`, which given a square, returns all squares that a move can be made to.
5. `getPieceAtSquare`, which returns the piece at a given square, or null if the square is empty.

### Move

This class represents a game move. It's immutable, and is represented by three instance variables.

1. `fromSquare`: The square you're moving from.
2. `toSquare`: The square you're moving to.
3. `pawnPromotion`:
    - If it's a promotion move, a value of:
        - `PawnPromotion.Knight`
        - `PawnPromotion.Bishop`
        - `PawnPromotion.Rook`
        - `PawnPromotion.Queen`
    - Otherwise, `PawnPromotion.None`

When calling `makeMove` on the chess game, you'll need to pass an object of the move class.

### Square

This class represents a square. It's immutable, and is represented by two instance variables.

1. `file`: This is the file (A-H) (i.e, column) of the square.
2. `rank`: This is the rank (1-8) (i.e, row) of the square.

Note: file and rank are standard chess terminology.

When calling `getPieceAtSquare` on the chess game, you'll need to pass an object of the square class.

An alternative representation would be to represent all the 64 squares as enumeration.
