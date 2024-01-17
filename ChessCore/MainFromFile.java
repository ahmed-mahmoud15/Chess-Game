package ChessCore;

import ChessCore.Pieces.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MainFromFile {

    private static Scanner scanner;

	public static void main(String[] args) {
        ClassicChessGame game = new ClassicChessGame();
        File file = new File("Input.txt");
        try {
            scanner = new Scanner(file);
            String data;
            String[] squares;

            while (!game.isGameEnded()) {
                data = scanner.nextLine();
                squares = data.split(",");

                Square from = squareFromString(squares[0]);
                Square to = squareFromString(squares[1]);
                var pieceTO = game.getPieceAtSquare(to);
                Move mv = new Move(from, to);

                if (game.makeMove(mv)) {
                    if (pieceTO instanceof Pawn) {
                        System.out.println("Pawn Captured");
                    }
                    if (pieceTO instanceof Rook) {
                        System.out.println("Rook Captured");
                    }
                    if (pieceTO instanceof Knight) {
                        System.out.println("Knight Captured");
                    }
                    if (pieceTO instanceof Queen) {
                        System.out.println("Queen Captured");
                    }
                    if (pieceTO instanceof Bishop) {
                        System.out.println("Bishop Captured");
                    }

                    var status = game.getGameStatus();

                    if (status == GameStatus.WHITE_WON) {
                        System.out.println("White Won");
                    }
                    if (status == GameStatus.BLACK_WON) {
                        System.out.println("Black Won");
                    }
                    if (status == GameStatus.WHITE_UNDER_CHECK) {
                        System.out.println("White Check");
                    }
                    if (status == GameStatus.BLACK_UNDER_CHECK) {
                        System.out.println("Black Check");
                    }
                    if (status == GameStatus.STALEMATE) {
                        System.out.println("Stalemate");
                    }
                    if (status == GameStatus.INSUFFICIENT_MATERIAL) {
                        System.out.println("Insufficient Materials");
                    }
                } else {
                    System.out.println("invalid");
                }
            }

        } catch (FileNotFoundException ex) {
        } finally {
        	scanner.close();
        }
    }

    public static Square squareFromString(String src) {
        int file = src.charAt(0) - 'a';
        int rank = src.charAt(1) - '0';

        BoardFile brf;
        BoardRank brr;

        switch (file) {
            case 0:
                brf = BoardFile.A;
                break;
            case 1:
                brf = BoardFile.B;
                break;
            case 2:
                brf = BoardFile.C;
                break;
            case 3:
                brf = BoardFile.D;
                break;
            case 4:
                brf = BoardFile.E;
                break;
            case 5:
                brf = BoardFile.F;
                break;
            case 6:
                brf = BoardFile.G;
                break;
            default:
                brf = BoardFile.H;
        }

        switch (rank) {
            case 1:
                brr = BoardRank.FIRST;
                break;
            case 2:
                brr = BoardRank.SECOND;
                break;
            case 3:
                brr = BoardRank.THIRD;
                break;
            case 4:
                brr = BoardRank.FORTH;
                break;
            case 5:
                brr = BoardRank.FIFTH;
                break;
            case 6:
                brr = BoardRank.SIXTH;
                break;
            case 7:
                brr = BoardRank.SEVENTH;
                break;
            default:
                brr = BoardRank.EIGHTH;
        }

        Square sq = new Square(brf, brr);
        return sq;
    }

}